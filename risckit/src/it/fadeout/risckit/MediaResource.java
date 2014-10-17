package it.fadeout.risckit;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javax.servlet.ServletContext;
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
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.tmatesoft.svn.core.SVNException;

@Path("/media")
public class MediaResource {

	@Context
	ServletConfig servletConfig;

	@Context 
	ServletContext serveletContext;

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
					oMediaViewModel = oMedia.getViewModel();
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
	public MediaViewModel UploadFile(@FormDataParam("file") InputStream file, @FormDataParam("mediaid") Integer iMediaId, @FormDataParam("file") FormDataContentDisposition fileDetail,@FormDataParam("login") String sUserLogin, @FormDataParam("startDate") String sStartDate, @FormDataParam("regionName") String sRegionName, @FormDataParam("countryCode") String sCountryCode) throws IOException
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// Fake code simulating the copy
			// You can generally do better with nio if you need...
			// And please, unlike me, do something about the Exceptions :D
			byte[] buffer = new byte[1024];
			int len;
			while ((len = file.read(buffer)) > -1 ) {
				baos.write(buffer, 0, len);
			}
			baos.flush();

			// Open new InputStreams using the recorded bytes
			// Can be repeated as many times as you wish
			InputStream isImage = new ByteArrayInputStream(baos.toByteArray()); 
			InputStream isOpenEarth = new ByteArrayInputStream(baos.toByteArray()); 


			MediaViewModel oReturnViewModel = null;
			MediaRepository oRepo = new MediaRepository();
			Media oMedia = oRepo.Select(iMediaId, Media.class);
			String sLocation = sCountryCode + "_" + sRegionName;
			//Thumb
			String sProjectPath = servletConfig.getInitParameter("ProjectPath") + "img/thumb/";
			String mimeType = serveletContext.getMimeType(fileDetail.getFileName());
			if (mimeType.startsWith("image"))
			{
				//Write Thumb
				String sThumbPath = oRepo.CreateThumb(isImage, sProjectPath, oMedia, fileDetail.getFileName());
				if (sThumbPath != "-1")
				{
					oMedia.setThumbnail(sThumbPath);
				}
			}

			SVNUtils oSvnUtils = new SVNUtils();
			String sDirPath = sUserLogin + "/risckit/" + sStartDate + "_" + sLocation + "/raw/";
			boolean bError = false;
			try{

				oSvnUtils.Commit(isOpenEarth,
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
			if (!bError)
			{
				String sPathRepository = servletConfig.getInitParameter("SvnRepository") + sDirPath + fileDetail.getFileName();
				oMedia.setFile(sPathRepository);
				oRepo.Update(oMedia);
			}
			if (oMedia != null)
			{
				oReturnViewModel = oMedia.getViewModel();
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

	@GET
	@Path("/{idmedia}")
	@Produces({"application/json", "application/xml", "text/xml"})
	public MediaViewModel getMedia(@PathParam("idmedia") int iIdMedia) {

		MediaViewModel oReturnValue = null;
		MediaRepository oMediaRepository = new MediaRepository();
		Media oMedia = oMediaRepository.Select(iIdMedia, Media.class);
		if (oMedia != null)
			oReturnValue = oMedia.getViewModel();
		return oReturnValue;
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

				boolean bRet = oRepo.Delete(oMedia);

				if (bRet)
				{
					EventRepository oEventRepo = new EventRepository();
					Event oEvent =  oEventRepo.Select(iIdEvent, Event.class);

					CountryRepository oCountryRepo = new CountryRepository();
					Country oCountry = oCountryRepo.Select(oEvent.getCountryId(), Country.class);

					String sLocation = oCountry.getCountryCode() + "_" + oCountry.getName();
					DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
					String sStartDate = dateFormatter.format(oEvent.getStartDate());

					UserRepository oUserRepo = new UserRepository();
					User oUser =  oUserRepo.Select(oEvent.getUserId(), User.class);

					if (sRepoFile != null)
					{
						oRepo.DeleteMediaFile(oUser.getUserName(),
								servletConfig.getInitParameter("SvnUser"),
								servletConfig.getInitParameter("SvnPwd"),
								servletConfig.getInitParameter("SvnUserDomain"),
								sRepoFile,
								servletConfig.getInitParameter("SvnRepository"),
								sStartDate,
								sLocation);
					}

				}
			}

		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();

		}
		finally
		{
			List<Media> oMediaList = oRepo.SelectByEvent(iIdEvent);
			if (oMediaList != null)
			{
				oReturnList = new ArrayList<MediaViewModel>();
				for (Media oItem : oMediaList) {
					oReturnList.add(oItem.getViewModel());
				}
			}
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

		String sRepoFile = oMedia.getFile();
		String[] sSplitString = sRepoFile.split("/");
		final String sTemp = sSplitString[sSplitString.length - 1];

		//Delete File if present
		SVNUtils oSvnUtils = new SVNUtils();
		File oFile = new File(System.getProperty("java.io.tmpdir") + sTemp);
		OutputStream oOut = new FileOutputStream(oFile);

		try
		{
			oSvnUtils.GetFile(
					oUser.getUserName(),
					servletConfig.getInitParameter("SvnUser"), 
					servletConfig.getInitParameter("SvnPwd"), 
					servletConfig.getInitParameter("SvnUserDomain"), 
					oMedia.getFile(), 
					servletConfig.getInitParameter("SvnRepository"),
					oOut);
		}
		catch (SVNException oEx)
		{
			ResponseBuilder response = Response.noContent();
			return response.build();
		}

		ResponseBuilder response = Response.ok(oFile);
		response.header("Content-Disposition", "attachment; filename=\""
				+ sTemp + "\"");
		return response.build();

	}

	@GET
	@Path("/preview/{idMedia}")
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public String getFilePreview(@PathParam("idMedia") int iIdMedia ) throws Exception {

		MediaRepository oMediaRepository = new MediaRepository();
		Media oMedia = oMediaRepository.Select(iIdMedia, Media.class); 

		EventRepository oEventRepo = new EventRepository();
		Event oEvent =  oEventRepo.Select(oMedia.getEventId(), Event.class);

		UserRepository oUserRepo = new UserRepository();
		User oUser =  oUserRepo.Select(oEvent.getUserId(), User.class);

		String sRepoFile = oMedia.getFile();
		String[] sSplitString = sRepoFile.split("/");
		final String sTemp = sSplitString[sSplitString.length - 1];

		SVNUtils oSvnUtils = new SVNUtils();
		File oFile = new File(servletConfig.getInitParameter("ProjectPath") + "img/thumb/temp/" + sTemp);

		if (!oFile.exists())
		{
			OutputStream oOut = new FileOutputStream(oFile);
			try
			{
				oSvnUtils.GetFile(
						oUser.getUserName(),
						servletConfig.getInitParameter("SvnUser"), 
						servletConfig.getInitParameter("SvnPwd"), 
						servletConfig.getInitParameter("SvnUserDomain"), 
						oMedia.getFile(), 
						servletConfig.getInitParameter("SvnRepository"),
						oOut);
			}
			catch (SVNException oEx)
			{
				return null;
			}
		}

		//Ritorno il path solo se � un'immagine
		String mimeType = serveletContext.getMimeType(oFile.getName());
		if (mimeType.startsWith("image"))
		{
			return sTemp;
		}

		return null;

	}


}
