package it.fadeout.risckit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import it.fadeout.risckit.business.Country;
import it.fadeout.risckit.business.Event;
import it.fadeout.risckit.business.EventsByCountries;
import it.fadeout.risckit.business.Gis;
import it.fadeout.risckit.business.Media;
import it.fadeout.risckit.business.PdfCreator;
import it.fadeout.risckit.business.SVNUtils;
import it.fadeout.risckit.business.SocioImpact;
import it.fadeout.risckit.business.User;
import it.fadeout.risckit.data.CountryRepository;
import it.fadeout.risckit.data.EventRepository;
import it.fadeout.risckit.data.GisRepository;
import it.fadeout.risckit.data.MediaRepository;
import it.fadeout.risckit.data.Repository;
import it.fadeout.risckit.data.SocioImpactRepository;
import it.fadeout.risckit.data.UserRepository;
import it.fadeout.risckit.viewmodels.CountryViewModel;
import it.fadeout.risckit.viewmodels.EventByCountryViewModel;
import it.fadeout.risckit.viewmodels.EventByRegionViewModel;
import it.fadeout.risckit.viewmodels.EventViewModel;
import it.fadeout.risckit.viewmodels.GisViewModel;
import it.fadeout.risckit.viewmodels.HtmlViewModel;
import it.fadeout.risckit.viewmodels.MediaViewModel;
import it.fadeout.risckit.viewmodels.SocioImpactViewModel;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.tmatesoft.svn.core.SVNException;

import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;


@Path("/events")
public class EventResource {

	@Context
	ServletConfig servletConfig;
	
	@Context 
	ServletContext serveletContext;


	@POST
	@Path("/save")
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public EventViewModel SaveEvent(EventViewModel oViewModel) {

		Event oEvent = null;
		try
		{
			if (oViewModel != null)
			{
				EventRepository oRepo = new EventRepository();
				oEvent = new Event();
				oEvent.setEntity(oViewModel);
				if (oViewModel.getId() == null || oViewModel.getId() == 0)
				{
					oRepo.Save(oEvent);
				}
				else
				{
					oEvent.setId(oViewModel.getId());
					oRepo.Update(oEvent);
				}

				if (oEvent != null)
				{
					oViewModel.setId(oEvent.getId());
					//Load Country
					CountryRepository oRepoCountry = new CountryRepository();
					oEvent.setCountry(oRepoCountry.Select(oEvent.getCountryId(), Country.class));
					
					//socio impacts
					SocioImpactRepository oSocioRepo = new SocioImpactRepository();
					List<SocioImpact> oSocioImpacts = oSocioRepo.SelectByEvent(oEvent.getId());
					
					String sLocation = oViewModel.getCountryCode() + "_" + oViewModel.getRegionName();

					DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
					String sStartDate = dateFormatter.format(oEvent.getStartDate());

					SVNUtils oSvnUtils = new SVNUtils();
					String sDirPath = oViewModel.getLogin() + "/risckit/" + sStartDate + "_" + sLocation + "/raw/";
					
					//csv
					try
					{
						oSvnUtils.Commit(oEvent.GetCsvInputStream(oSocioImpacts), 
								oViewModel.getLogin(),
								servletConfig.getInitParameter("SvnUser"), 
								servletConfig.getInitParameter("SvnPwd"), 
								servletConfig.getInitParameter("SvnUserDomain"), 
								sDirPath + "Event.csv", 
								servletConfig.getInitParameter("SvnRepository"),
								sStartDate,
								sLocation);
					}
					catch(SVNException oEx)
					{

					}
				}
				else
					oViewModel = null;	
			}
		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();
		}

		return oViewModel;
	}

	@SuppressWarnings("unused")
	@POST
	@Path("/upload")
	@Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
	public String UploadFile(@FormDataParam("file") InputStream file, @FormDataParam("eventid") Integer iEventId, @FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("parameter") String sNameProperty, @FormDataParam("login") String sUserLogin, @FormDataParam("startDate") String sStartDate, @FormDataParam("regionName") String sRegionName, @FormDataParam("countryCode") String sCountryCode) throws IOException
	{
		try
		{
			//Carico il progetto e lo aggiorno con il nuovo path
			EventRepository oRepo = new EventRepository();
			Event oEvent = oRepo.Select(iEventId, Event.class);

			String sLocation = sCountryCode + "_" + sRegionName;

			SVNUtils oSvnUtils = new SVNUtils();
			String sDirPath = sUserLogin + "/risckit/" + sStartDate + "_" + sLocation + "/raw/";
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
			sStartDate = dateFormatter.format(oEvent.getStartDate());
			boolean bError = false;
			try
			{
				oSvnUtils.Commit(file,
						sUserLogin,
						servletConfig.getInitParameter("SvnUser"), 
						servletConfig.getInitParameter("SvnPwd"), 
						servletConfig.getInitParameter("SvnUserDomain"), 
						sDirPath + fileDetail.getFileName(), 
						servletConfig.getInitParameter("SvnRepository"),
						sStartDate,
						sLocation);
			}
			catch(SVNException oEx)
			{
				bError = true;
			}
			String sPathRepository = null;
			if (!bError)
			{
				sPathRepository = servletConfig.getInitParameter("SvnRepository") + sDirPath + fileDetail.getFileName();
				oEvent.setPathRepository(sNameProperty, sPathRepository);
				oRepo.Update(oEvent);
			}

			return sPathRepository;
		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();
			return null;
		}
	}

	@GET
	@Path("/{id}")
	@Produces({"application/json", "application/xml", "text/xml"})
	public EventViewModel getEvent(@PathParam("id") int iIdEvent) {

		EventRepository oRepo = new EventRepository();
		Event oEvent = oRepo.Select(iIdEvent, Event.class);

		CountryRepository oCountryRepo = new CountryRepository();
		List<Country> oCountries = oCountryRepo.SelectAll(Country.class);

		if (oEvent != null)
			return oEvent.getViewModel(oCountries);
		else
			return null;
	}

	@GET
	@Path("/all/{iduser}")
	@Produces({"application/json", "application/xml", "text/xml"})
	public List<EventViewModel> getEventList(@PathParam("iduser") int iIdUser) {

		List<EventViewModel> oReturnList = null;
		EventRepository oRepo = new EventRepository();
		SocioImpactRepository oSocioImpactsRepo = new SocioImpactRepository();
		List<Event> oEvents = oRepo.SelectByUser(iIdUser);
		
		CountryRepository oCountryRepo = new CountryRepository();
		List<Country> oCountries = oCountryRepo.SelectAll(Country.class);

		if (oEvents != null)
		{
			oReturnList = new ArrayList<EventViewModel>();
			for (Event event : oEvents) {
				long iCount = oSocioImpactsRepo.SelectCount(event.getId());
				boolean bHas = false;
				if (iCount > 0)
					bHas = true;
				EventViewModel oEventViewModel =  event.getViewModel(oCountries);
				oEventViewModel.setHasSocioImpacts(bHas);
				oReturnList.add(oEventViewModel);
			}
		}

		return oReturnList;
	}

	@GET
	@Path("/byregion/{countryid}")
	@Produces({"application/json", "application/xml", "text/xml"})
	public List<EventViewModel> getEventListMap(@PathParam("countryid") Integer iCountryId) {



		List<EventViewModel> oReturnList = null;
		EventRepository oRepo = new EventRepository();
		MediaRepository oMediaRepo = new MediaRepository();
		GisRepository oGisRepo = new GisRepository();
		SocioImpactRepository oSocioRepo = new SocioImpactRepository();
		List<Event> oEvents = oRepo.SelectByRegion(iCountryId);

		CountryRepository oCountryRepo = new CountryRepository();
		List<Country> oCountries = oCountryRepo.SelectAll(Country.class);

		if (oEvents != null)
		{
			try
			{
				oReturnList = new ArrayList<EventViewModel>();
				for (Event event : oEvents) {
					EventViewModel oEventViewModel =  event.getViewModel(oCountries);
					List<Media> oMediaList = oMediaRepo.SelectByEvent(event.getId());
					if (oMediaList != null)
					{
						for (Media media : oMediaList) {
							if (oEventViewModel.getMedia() == null)
								oEventViewModel.setMedia(new ArrayList<MediaViewModel>());
							oEventViewModel.getMedia().add(media.getViewModel());
						}
					}

					Gis oGis = oGisRepo.SelectByEvent(event.getId());
					if (oGis != null)
					{
						if (oEventViewModel.getGis() == null)
							oEventViewModel.setGis(new GisViewModel());
						oEventViewModel.setGis(oGis.getViewModel());
					}
					List<SocioImpact> oImpacts = oSocioRepo.SelectByEvent(event.getId());
					if (oImpacts != null)
					{
						for (SocioImpact socioImpact : oImpacts) {
							if (oEventViewModel.getSocioimpacts() == null)
								oEventViewModel.setSocioimpacts(new ArrayList<SocioImpactViewModel>());
							oEventViewModel.getSocioimpacts().add(socioImpact.GetViewModel());
						}
					}
					oReturnList.add(oEventViewModel);
				}
			}
			finally
			{
				
			}
		}



		return oReturnList;
	}

	@GET
	@Path("/bycountry/{countrycode}")
	@Produces({"application/json", "application/xml", "text/xml"})
	public List<EventByRegionViewModel> getEventByCountry(@PathParam("countrycode") String sCountryCode) {

		List<EventByRegionViewModel> oReturnList = null;
		EventRepository oRepo = new EventRepository();
		List<Event> oEvents = oRepo.SelectByCountries(sCountryCode);
		CountryRepository oCountryRepo = new CountryRepository();

		HashMap<Integer, EventByRegionViewModel> oMap = new HashMap<Integer, EventByRegionViewModel>();

		if (oEvents != null)
		{
			oReturnList = new ArrayList<EventByRegionViewModel>();
			for (Event event : oEvents) {
				if (!oMap.containsKey(event.getCountryId()))
				{
					Country oCountry = oCountryRepo.Select(event.getCountryId(), Country.class);
					EventByRegionViewModel oViewModel = new EventByRegionViewModel();
					if (event.getLat() != null && event.getLon() != null)
					{
						oViewModel.setLat(event.getLat());
						oViewModel.setLon(event.getLon());
					}
					oViewModel.setRegionName(oCountry.getName());
					oViewModel.setRegionId(oCountry.getId());
					oViewModel.setEventsCount(1);
					oMap.put(event.getCountryId(), oViewModel);
				}
				else
				{
					if (event.getLat() != null && event.getLon() != null)
					{
						oMap.get(event.getCountryId()).setLat(event.getLat());
						oMap.get(event.getCountryId()).setLon(event.getLon());
					}
					oMap.get(event.getCountryId()).setEventsCount(oMap.get(event.getCountryId()).getEventsCount()+1);
				}

			}

			for (Integer key : oMap.keySet()) {
				oReturnList.add(oMap.get(key));
			}
		}

		return oReturnList;
	}


	@GET
	@Path("/groupevent")
	@Produces({"application/json", "application/xml", "text/xml"})
	public List<EventByCountryViewModel> getEventByCountryForMap() {

		List<EventByCountryViewModel> oReturnList = null;
		Repository<EventsByCountries> oRepo = new Repository<EventsByCountries>();
		List<EventsByCountries> oEvents = oRepo.SelectAll(EventsByCountries.class);

		if (oEvents != null)
		{
			oReturnList = new ArrayList<EventByCountryViewModel>();
			for (EventsByCountries event : oEvents) {
				EventByCountryViewModel oViewModel = new EventByCountryViewModel();
				oViewModel.setId(event.getId());
				oViewModel.setCountryName(event.getName());
				oViewModel.setCountryCode(event.getCountryCode());
				oViewModel.setEventsCount(event.getEventCount());
				oViewModel.setLat(event.getLat());
				oViewModel.setLon(event.getLon());
				oReturnList.add(oViewModel);
			}
		}

		return oReturnList;
	}

	@GET
	@Path("/download/{idEvent}/{parameter}")
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getFile(@PathParam("idEvent") int iIdEvent, @PathParam("parameter") String sParameter) throws Exception {

		EventRepository oEventRepository = new EventRepository();
		Event oEvent = oEventRepository.Select(iIdEvent, Event.class); 

		UserRepository oUserRepo = new UserRepository();
		User oUser =  oUserRepo.Select(oEvent.getUserId(), User.class);

		String sRepoFile = oEvent.getPathRepository(sParameter);
		String[] sSplitString = sRepoFile.split("/");
		final String sTemp = sSplitString[sSplitString.length - 1];

		//Delete File if present
		SVNUtils oSvnUtils = new SVNUtils();
		File oFile = new File(System.getProperty("java.io.tmpdir") + sTemp);
		OutputStream oOut = new FileOutputStream(oFile);

		try{
			oSvnUtils.GetFile(
					oUser.getUserName(),
					servletConfig.getInitParameter("SvnUser"), 
					servletConfig.getInitParameter("SvnPwd"), 
					servletConfig.getInitParameter("SvnUserDomain"), 
					oEvent.getPathRepository(sParameter), 
					servletConfig.getInitParameter("SvnRepository"),
					oOut);
		}
		catch(SVNException oEx)
		{
			ResponseBuilder response = Response.noContent();
			return response.build();
		}

		ResponseBuilder response = Response.ok(oFile);
		response.header("Content-Disposition", "attachment; filename=\""
				+ sTemp + "\"");
		return response.build();

	}

	@POST
	@Path("/deleteattach/{idEvent}/{parameter}")
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})
	public EventViewModel DeleteAttach(@PathParam("idEvent") int iIdEvent, @PathParam("parameter") String sParameter) {

		EventViewModel oReturnValue = null;
		EventRepository oRepo = new EventRepository();
		try
		{
			Event oEvent = oRepo.Select(iIdEvent, Event.class);
			String sPathFile = oEvent.getPathRepository(sParameter);
			if (oEvent != null)
			{
				oEvent.setPathRepository(sParameter, null);
				oRepo.Update(oEvent);

				CountryRepository oCountryRepo = new CountryRepository();
				List<Country> oCountries = oCountryRepo.SelectAll(Country.class);
				Country oCountry = oCountryRepo.Select(oEvent.getCountryId(), Country.class);

				if (oEvent != null)
					oReturnValue = oEvent.getViewModel(oCountries);

				String sLocation = oCountry.getCountryCode() + "_" + oCountry.getName();
				DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
				String sStartDate = dateFormatter.format(oEvent.getStartDate());

				UserRepository oUserRepo = new UserRepository();
				User oUser =  oUserRepo.Select(oEvent.getUserId(), User.class);

				try
				{
					oRepo.DeleteEventFile(
							oUser.getUserName(),
							servletConfig.getInitParameter("SvnUser"), 
							servletConfig.getInitParameter("SvnPwd"), 
							servletConfig.getInitParameter("SvnUserDomain"), 
							sPathFile, 
							servletConfig.getInitParameter("SvnRepository"),
							sStartDate,
							sLocation);
				}
				catch(SVNException oEx)
				{

				}
			}


		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();

		}

		return oReturnValue;
	}

	@POST
	@Path("/delete/{idEvent}")
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})
	public Integer Delete(@PathParam("idEvent") int iIdEvent) {

		EventRepository oRepo = new EventRepository();
		boolean bError = false;
		try
		{
			Event oEvent = oRepo.Select(iIdEvent, Event.class);
			//Provo a cancellare tutti i file

			if (oEvent != null)
			{

				CountryRepository oCountryRepo = new CountryRepository();
				List<Country> oCountries = oCountryRepo.SelectAll(Country.class);
				Country oCountry = oCountryRepo.Select(oEvent.getCountryId(), Country.class);

				String sLocation = oCountry.getCountryCode() + "_" + oCountry.getName();
				DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
				String sStartDate = dateFormatter.format(oEvent.getStartDate());

				UserRepository oUserRepo = new UserRepository();
				User oUser =  oUserRepo.Select(oEvent.getUserId(), User.class);

				for (String sPathFile : oEvent.getPathRepository()) {
					try
					{
						oRepo.DeleteEventFile(
								oUser.getUserName(),
								servletConfig.getInitParameter("SvnUser"), 
								servletConfig.getInitParameter("SvnPwd"), 
								servletConfig.getInitParameter("SvnUserDomain"), 
								sPathFile, 
								servletConfig.getInitParameter("SvnRepository"),
								sStartDate,
								sLocation);
					}
					catch(SVNException oEx)
					{

					}
				}

				//Delete csv
				String sCsvDirPath = oUser.getUserName() + "/risckit/" + sStartDate + "_" + sLocation + "/raw/";
				try
				{
					oRepo.DeleteEventFile(
							oUser.getUserName(),
							servletConfig.getInitParameter("SvnUser"), 
							servletConfig.getInitParameter("SvnPwd"), 
							servletConfig.getInitParameter("SvnUserDomain"), 
							sCsvDirPath, 
							servletConfig.getInitParameter("SvnRepository"),
							sStartDate,
							sLocation);
				}
				catch(SVNException oEx)
				{

				}


				//Delete Event
				return oRepo.Delete(oEvent, oUser.getUserName(), 
						servletConfig.getInitParameter("SvnUser"), 
						servletConfig.getInitParameter("SvnPwd"), 
						servletConfig.getInitParameter("SvnUserDomain"), 
						servletConfig.getInitParameter("SvnRepository"),
						sStartDate,
						sLocation);

			}


		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();
			bError = true;
		}

		if (bError)
			return -1;

		return 0;
	}

	@POST
	@Path("/pdf")
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public String CretePdf(HtmlViewModel oHtml) {

		String sFileName = UUID.randomUUID().toString() + ".pdf";
		File oFile = new File(servletConfig.getInitParameter("ProjectPath") + "pdf/" + sFileName);
		OutputStream oOut = null;
		try
		{
			oOut = new FileOutputStream(oFile);
			Document document = new Document();
			PdfWriter oPdfWriter = PdfWriter.getInstance(document, oOut);
			document.open();
			InputStream is = new ByteArrayInputStream(oHtml.getHtml().getBytes());
			XMLWorkerHelper.getInstance().parseXHtml(oPdfWriter, document, is);
			document.close();
			oOut.flush();
			oOut.close();
		}
		catch (Exception oEx)
		{
			try {
				oOut.flush();
				oOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
			
		}
		
		return sFileName;
	}
	
	
	@GET
	@Path("/pdf/{idevent}")
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response CretePdf2(@PathParam("idevent") int iIdEvent) {

		String sFileName = UUID.randomUUID().toString() + ".pdf";
		File oFile = new File(servletConfig.getInitParameter("ProjectPath") + "pdf/" + sFileName);
		
		try
		{
			PdfCreator oPdfCreator = new PdfCreator(servletConfig.getInitParameter("ProjectPath"),
					servletConfig.getInitParameter("SvnUser"), 
					servletConfig.getInitParameter("SvnPwd"), 
					servletConfig.getInitParameter("SvnUserDomain"),
					servletConfig.getInitParameter("SvnRepository"));
			oPdfCreator.CreatePdf(iIdEvent, oFile, serveletContext);
			
		}
		catch(Exception oEx)
		{
		ResponseBuilder response = Response.noContent();
		return response.build();
		}
	
		ResponseBuilder response = Response.ok(oFile);
		response.header("Content-Disposition", "attachment; filename=\""
				+ sFileName + "\"");
		response.header("content-type", "application/pdf");
		response.header("Content-lenght", oFile.length());
		return response.build();
	}

}
