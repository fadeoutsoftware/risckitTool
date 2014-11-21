package it.fadeout.risckit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import it.fadeout.risckit.business.Country;
import it.fadeout.risckit.business.Event;
import it.fadeout.risckit.business.Gis;
import it.fadeout.risckit.business.Media;
import it.fadeout.risckit.business.SVNUtils;
import it.fadeout.risckit.business.User;
import it.fadeout.risckit.data.CountryRepository;
import it.fadeout.risckit.data.EventRepository;
import it.fadeout.risckit.data.GisRepository;
import it.fadeout.risckit.data.MediaRepository;
import it.fadeout.risckit.data.UserRepository;
import it.fadeout.risckit.viewmodels.GisViewModel;
import it.fadeout.risckit.viewmodels.MediaViewModel;

import javax.servlet.ServletConfig;
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

@Path("/gis")
public class GisResource {

	@Context
	ServletConfig servletConfig;

	@POST
	@Path("/save")
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})
	public GisViewModel SaveGis(GisViewModel oGisViewModel) {

		try
		{
			if (oGisViewModel != null)
			{
				GisRepository oRepo = new GisRepository();

				Gis oGis = new Gis();
				oGis.setEntity(oGisViewModel);
				if (oGisViewModel.getId() == null || oGisViewModel.getId() == 0)
					oRepo.Save(oGis);
				else
				{
					oGis.setId(oGisViewModel.getId());
					oRepo.Update(oGis);
				}

				if (oGis != null)
					oGisViewModel.setId(oGis.getId());

				return oGisViewModel;
			}
		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@POST
	@Path("/upload")
	@Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
	public GisViewModel UploadFile(@FormDataParam("file") InputStream file, @FormDataParam("gisid") Integer iGisId, @FormDataParam("type") Integer iType, @FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("login") String sUserLogin, @FormDataParam("startDate") String sStartDate, @FormDataParam("regionName") String sRegionName, @FormDataParam("countryCode") String sCountryCode) throws IOException
	{
		try
		{

			GisRepository oRepo = new GisRepository();
			Gis oGis = oRepo.Select(iGisId, Gis.class);

			String sLocation = sCountryCode + "_" + sRegionName;

			SVNUtils oSvnUtils = new SVNUtils();
			String sDirPath = sUserLogin + "/risckit/" + sStartDate + "_" + sLocation + "/raw/";
			//csv
			oSvnUtils.Commit(file,
					sUserLogin,
					servletConfig.getInitParameter("SvnUser"), 
					servletConfig.getInitParameter("SvnPwd"), 
					servletConfig.getInitParameter("SvnUserDomain"), 
					sDirPath + fileDetail.getFileName(), 
					servletConfig.getInitParameter("SvnRepository"),
					sStartDate,
					sLocation);

			String sPathRepository = servletConfig.getInitParameter("SvnRepository") + sDirPath + fileDetail.getFileName();

			if (iType == 0)
				oGis.setGisFile(sPathRepository);
			else
				oGis.setInspireFile(sPathRepository);

			oRepo.Update(oGis);

			if (oGis!= null)
				return oGis.getViewModel();

			return null;
		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();
			return null;
		}
	}

	@GET
	@Path("/event/{idevent}")
	@Produces({"application/json", "application/xml", "text/xml"})
	public GisViewModel getGis(@PathParam("idevent") int iIdEvent) {

		GisViewModel oReturnValue = null;
		GisRepository oGisRepository = new GisRepository();
		Gis oGis = oGisRepository.SelectByEvent(iIdEvent);
		if (oGis != null)
			oReturnValue = oGis.getViewModel();

		return oReturnValue;
	}

	@POST
	@Path("/delete/{idgis}/{idevent}/{type}")
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})
	public GisViewModel Delete(@PathParam("idgis") int iIdGis, @PathParam("idevent") int iIdEvent, @PathParam("type") Integer iType) {

		GisViewModel oReturnValue = null;
		GisRepository oRepo = new GisRepository();
		try
		{
			Gis oGis = oRepo.Select(iIdGis, Gis.class);
			String sPathFile = null;
			if (oGis != null)
			{
				if (iType.equals(0))
				{
					sPathFile = oGis.getGisFile();
					oGis.setGisFile(null);

				}
				else
				{
					sPathFile = oGis.getInspireFile();
					oGis.setInspireFile(null);
				}

				if (oGis.getGisFile() == null && oGis.getGisFile() == null)
					oRepo.Delete(oGis);
				else
				{
					oRepo.Update(oGis);
					if (oGis != null)
						oReturnValue = oGis.getViewModel();
				}

				EventRepository oEventRepo = new EventRepository();
				Event oEvent =  oEventRepo.Select(iIdEvent, Event.class);

				CountryRepository oCountryRepo = new CountryRepository();
				Country oCountry = oCountryRepo.Select(oEvent.getCountryId(), Country.class);

				String sLocation = oCountry.getCountryCode() + "_" + oCountry.getName();
				DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
				String sStartDate = dateFormatter.format(oEvent.getStartDate());

				UserRepository oUserRepo = new UserRepository();
				User oUser =  oUserRepo.Select(oEvent.getUserId(), User.class);

				//Delete File if present
				SVNUtils oSvnUtils = new SVNUtils();

				if (sPathFile != null)
				{
					oRepo.DeleteGisFile(
							oUser.getUserName(),
							servletConfig.getInitParameter("SvnUser"), 
							servletConfig.getInitParameter("SvnPwd"), 
							servletConfig.getInitParameter("SvnUserDomain"), 
							sPathFile, 
							servletConfig.getInitParameter("SvnRepository"),
							sStartDate,
							sLocation);

				}
			}


		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();
		}

		return oReturnValue;
	}


	@GET
	@Path("/download/{idgis}/{type}")
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getFile(@PathParam("idgis") int iIdGis, @PathParam("type") Integer iType) throws Exception {

		GisRepository oGisRepository = new GisRepository();
		Gis oGis = oGisRepository.Select(iIdGis, Gis.class); 

		EventRepository oEventRepo = new EventRepository();
		Event oEvent =  oEventRepo.Select(oGis.getEventId(), Event.class);

		UserRepository oUserRepo = new UserRepository();
		User oUser =  oUserRepo.Select(oEvent.getUserId(), User.class);
		String sRepoFile = null;
		if (iType.equals(0))
			sRepoFile = oGis.getGisFile();
		else
			sRepoFile = oGis.getInspireFile();
		String[] sSplitString = sRepoFile.split("/");
		final String sTemp = sSplitString[sSplitString.length - 1];;

		//Delete File if present
		SVNUtils oSvnUtils = new SVNUtils();
		File oFile = new File(System.getProperty("java.io.tmpdir") + sTemp);
		OutputStream oOut = new FileOutputStream(oFile);


		oSvnUtils.GetFile(
				oUser.getUserName(),
				servletConfig.getInitParameter("SvnUser"), 
				servletConfig.getInitParameter("SvnPwd"), 
				servletConfig.getInitParameter("SvnUserDomain"), 
				oGis.getGisFile(), 
				servletConfig.getInitParameter("SvnRepository"),
				oOut);


		ResponseBuilder response = Response.ok(oFile);
		response.header("Content-Disposition", "attachment; filename=\""
				+ sTemp + "\"");
		return response.build();


	}
}
