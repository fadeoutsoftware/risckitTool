package it.fadeout.risckit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import it.fadeout.risckit.business.Country;
import it.fadeout.risckit.business.Event;
import it.fadeout.risckit.business.Media;
import it.fadeout.risckit.business.SVNUtils;
import it.fadeout.risckit.business.User;
import it.fadeout.risckit.data.CountryRepository;
import it.fadeout.risckit.data.EventRepository;
import it.fadeout.risckit.data.MediaRepository;
import it.fadeout.risckit.data.UserRepository;
import it.fadeout.risckit.viewmodels.EventViewModel;
import it.fadeout.risckit.viewmodels.MediaViewModel;

import javax.servlet.ServletConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/media")
public class MediaResource {

	@Context
	ServletConfig servletConfig;

	@POST
	@Path("/save")
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})
	public MediaViewModel SaveMedia(MediaViewModel oMediaViewModel) {

		try
		{
			if (oMediaViewModel != null)
			{
				MediaRepository oRepo = new MediaRepository();
				Media oMedia = new Media();
				oMedia.setEntity(oMediaViewModel);
				if (oMediaViewModel.getId() == null || oMediaViewModel.getId() == 0)
					oRepo.Save(oMedia);
				else
				{
					oMedia.setId(oMediaViewModel.getId());
					oRepo.Update(oMedia);
				}

				if (oMedia != null)
					oMediaViewModel.setId(oMedia.getId());
			}
		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();

		}

		return oMediaViewModel;
	}

	@SuppressWarnings("unused")
	@POST
	@Path("/upload")
	@Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
	public List<MediaViewModel> UploadFile(@FormDataParam("file") InputStream file, @FormDataParam("mediaid") Integer iMediaId, @FormDataParam("file") FormDataContentDisposition fileDetail,@FormDataParam("login") String sUserLogin, @FormDataParam("startDate") String sStartDate, @FormDataParam("regionName") String sRegionName, @FormDataParam("countryCode") String sCountryCode) throws IOException
	{
		try
		{
			List<MediaViewModel> oReturnViewModel = null;
			MediaRepository oRepo = new MediaRepository();
			Media oMedia = oRepo.Select(iMediaId, Media.class);
			String sLocation = sCountryCode + "_" + sRegionName;

			SVNUtils oSvnUtils = new SVNUtils();
			String sDirPath = "/" + sUserLogin + "/risckit/" + sStartDate + "_" + sLocation + "/raw/";
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
			oMedia.setFile(sPathRepository);

			oRepo.Update(oMedia);
			if (oMedia != null)
			{
				List<Media> oMediaList = oRepo.SelectByEvent(oMedia.getEventId());
				if (oMediaList != null && oMediaList.size() > 0)
				{
					oReturnViewModel = new ArrayList<MediaViewModel>();
					for (Media media : oMediaList) {
						oReturnViewModel.add(media.getViewModel());
					}
				}
			}

			return oReturnViewModel;
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
	public List<MediaViewModel> getMediaList(@PathParam("idevent") int iIdEvent) {

		List<MediaViewModel> oReturnList = new ArrayList<MediaViewModel>();
		MediaRepository oMediaRepository = new MediaRepository();
		List<Media> oMediaList = oMediaRepository.SelectByEvent(iIdEvent);
		for (Media oMedia : oMediaList) {
			oReturnList.add(oMedia.getViewModel());
		}

		return oReturnList;
	}

	@POST
	@Path("/delete/{idmedia}/{idevent}")
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})
	public List<MediaViewModel> Delete(@PathParam("idmedia") int iIdMedia, @PathParam("idevent") int iIdEvent) {

		List<MediaViewModel> oReturnList = null;
		MediaRepository oRepo = new MediaRepository();
		try
		{
			Media oMedia = oRepo.Select(iIdMedia, Media.class);
			if (oMedia != null)
			{
				String sRepoFile = oMedia.getFile();
				String[] sSplitString = sRepoFile.split("/");
				if (sSplitString != null && sSplitString.length > 0)
				{
					sRepoFile = sSplitString[sSplitString.length - 1];
				}

				boolean bRet = oRepo.Delete(oMedia);

				if (bRet)
				{
					EventRepository oEventRepo = new EventRepository();
					Event oEvent =  oEventRepo.Select(iIdEvent, Event.class);

					CountryRepository oCountryRepo = new CountryRepository();
					Country oCountry = oCountryRepo.Select(oEvent.getCountryId(), Country.class);

					String sLocation = oCountry.getCountryCode() + "_" + oCountry.getName();
					DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
					String sStartDate = dateFormatter.format(oEvent.getStartDate());
					sStartDate = sStartDate.replace("/", "_");

					UserRepository oUserRepo = new UserRepository();
					User oUser =  oUserRepo.Select(oEvent.getUserId(), User.class);

					//Delete File if present
					SVNUtils oSvnUtils = new SVNUtils();

					oSvnUtils.Delete(
							oUser.getUserName(),
							servletConfig.getInitParameter("SvnUser"), 
							servletConfig.getInitParameter("SvnPwd"), 
							servletConfig.getInitParameter("SvnUserDomain"), 
							sRepoFile, 
							servletConfig.getInitParameter("SvnRepository"),
							sStartDate,
							sLocation);
				}
			}

			List<Media> oMediaList = oRepo.SelectByEvent(iIdEvent);
			if (oMediaList != null)
			{
				oReturnList = new ArrayList<MediaViewModel>();
				for (Media oItem : oMediaList) {
					oReturnList.add(oItem.getViewModel());
				}
			}
		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();

		}

		return oReturnList;
	}


	@GET
	@Path("/download/{idMedia}")
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getFile(@PathParam("idMedia") int iIdMedia ) throws Exception {

		MediaRepository oMediaRepository = new MediaRepository();
		Media oMedia = oMediaRepository.Select(iIdMedia, Media.class); 
		
		EventRepository oEventRepo = new EventRepository();
		Event oEvent =  oEventRepo.Select(oMedia.getEventId(), Event.class);
		
		UserRepository oUserRepo = new UserRepository();
		User oUser =  oUserRepo.Select(oEvent.getUserId(), User.class);
		
		StreamingOutput stream = new StreamingOutput() {
			@Override
			public void write(OutputStream os) throws IOException,
			WebApplicationException {
				Writer writer = new BufferedWriter(new OutputStreamWriter(os));
				writer.write("file");
				writer.flush();
			}
		};
		
		//Delete File if present
		SVNUtils oSvnUtils = new SVNUtils();
		OutputStream oOut = new ByteArrayOutputStream();
		

		oSvnUtils.GetFile(
				oUser.getUserName(),
				servletConfig.getInitParameter("SvnUser"), 
				servletConfig.getInitParameter("SvnPwd"), 
				servletConfig.getInitParameter("SvnUserDomain"), 
				oMedia.getFile(), 
				servletConfig.getInitParameter("SvnRepository"),
				oOut);
		
		stream.write(oOut);

		return Response.ok(stream).build();

	}

}
