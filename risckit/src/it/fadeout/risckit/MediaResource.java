package it.fadeout.risckit;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import it.fadeout.risckit.business.Event;
import it.fadeout.risckit.business.Media;
import it.fadeout.risckit.data.EventRepository;
import it.fadeout.risckit.data.MediaRepository;
import it.fadeout.risckit.viewmodels.EventViewModel;
import it.fadeout.risckit.viewmodels.MediaViewModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/media")
public class MediaResource {

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
				oMedia.setEventId(oMediaViewModel.getEventId());
				oMedia.setLat(oMediaViewModel.getLat());
				oMedia.setLon(oMediaViewModel.getLon());
				oMedia.setFile(oMediaViewModel.getDownloadPath());
				oMedia.setDate(oMediaViewModel.getDate());
				oMedia.setDescription(oMediaViewModel.getDescription());
				oRepo.Save(oMedia);
					
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
	
	@POST
	@Path("/update/{mediaid}")
	@Consumes({"application/xml", "application/json", "text/xml"})
	@Produces({"application/json"})
	public MediaViewModel UpdateMedia(MediaViewModel oMediaViewModel, @PathParam("mediaid") Integer iMediaId) {

		try
		{
			if (oMediaViewModel != null)
			{
				MediaRepository oRepo = new MediaRepository();
				Media oMedia = new Media();
				oMedia.setId(iMediaId);
				oMedia.setEventId(oMediaViewModel.getEventId());
				oMedia.setLat(oMediaViewModel.getLat());
				oMedia.setLon(oMediaViewModel.getLon());
				oMedia.setFile(oMediaViewModel.getDownloadPath());
				oMedia.setDate(oMediaViewModel.getDate());
				oMedia.setDescription(oMediaViewModel.getDescription());
				oRepo.Update(oMedia);
				
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
	public String UploadFile(@FormDataParam("file") InputStream file, @FormDataParam("mediaid") Integer iMediaId, @FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException
	{
		try
		{

			MediaRepository oRepo = new MediaRepository();
			Media oMedia = oRepo.Select(iMediaId, Media.class);
			String sPathRepository = fileDetail.getFileName();
			oMedia.setFile(sPathRepository);

			oRepo.Update(oMedia);

			return sPathRepository;
		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();
			return null;
		}
	}

}
