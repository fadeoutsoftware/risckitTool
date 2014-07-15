package it.fadeout.risckit;

import java.io.IOException;
import java.io.InputStream;

import it.fadeout.risckit.business.Gis;
import it.fadeout.risckit.business.Media;
import it.fadeout.risckit.data.GisRepository;
import it.fadeout.risckit.data.MediaRepository;
import it.fadeout.risckit.viewmodels.GisViewModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/gis")
public class GisResource {


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
				oGis.setEventId(oGisViewModel.getEventId());
				oGis.setGisFile(oGisViewModel.getGisFile());
				oGis.setInspireFile(oGisViewModel.getInspireFile());
				oRepo.Save(oGis);
				
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
	public String UploadFile(@FormDataParam("file") InputStream file, @FormDataParam("gisid") Integer iGisId, @FormDataParam("type") Integer iType, @FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException
	{
		try
		{

			GisRepository oRepo = new GisRepository();
			Gis oGis = oRepo.Select(iGisId, Gis.class);
			String sPathRepository = fileDetail.getFileName();
			if (iType == 0)
				oGis.setGisFile(sPathRepository);
			else
				oGis.setInspireFile(sPathRepository);

			oRepo.Update(oGis);

			return sPathRepository;
		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();
			return null;
		}
	}
}
