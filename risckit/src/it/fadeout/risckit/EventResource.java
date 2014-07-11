package it.fadeout.risckit;

import java.io.IOException;
import java.io.InputStream;

import it.fadeout.risckit.business.Event;
import it.fadeout.risckit.data.EventRepository;
import it.fadeout.risckit.viewmodels.EventViewModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;


@Path("/events")
public class EventResource {
	
	@POST
	@Path("/save")
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public EventViewModel SaveEvent(EventViewModel oViewModel) {

		try
		{
			if (oViewModel != null)
			{
				EventRepository oRepo = new EventRepository();
				Event oEvent = new Event();
				oEvent.setCountryId(oViewModel.getCountryId());
				oEvent.setStartDate(oViewModel.getStartDate());
				oEvent.setStartHour(oViewModel.getStartHour());
				oEvent.setDescription(oViewModel.getDescription());
				oEvent.setUnitHour(oViewModel.getUnitHour());
				oEvent.setUnitValue(oViewModel.getUnitValue());
				oEvent.setUnitApproximated(oViewModel.getUnitApproximated());
				oEvent.setWaveHeightType(oViewModel.getWaveHeightType());
				oEvent.setWaveHeightValue(oViewModel.getWaveHeightValue());
				oEvent.setWaveDirectionType(oViewModel.getWaveDirectionType());
				oEvent.setWaveDirectionDegree(oViewModel.getWaveDirectionDegree());
				oEvent.setWaveDirectionClustered(oViewModel.getWaveDirectionClustered());
				oEvent.setWindIntensityType(oViewModel.getWindIntensityType());
				oEvent.setWindIntensityValue(oViewModel.getWindIntensityValue());
				oEvent.setWindDirectionType(oViewModel.getWindDirectionType());
				oEvent.setWindDirectionDegree(oViewModel.getWindDirectionDegree());
				oEvent.setWindDirectionClustered(oViewModel.getWindDirectionClustered());
				oEvent.setPeakWaterDischarge(oViewModel.getPeakWaterDischarge());
				oEvent.setFloodHeight(oViewModel.getFloodHeight());
				oEvent.setReporedCasualtiesNumber(oViewModel.getReporedCasualtiesNumber());
				oEvent.setReporedCasualtiesDescription(oViewModel.getReporedCasualtiesDescription());
				oEvent.setDamageToBuildingsDescription(oViewModel.getDamageToBuildingsDescription());
				oEvent.setDamageToBuildingsCost(oViewModel.getDamageToBuildingsCost());
				oEvent.setCostDetail(oViewModel.getCostDetail());
				oEvent.setDescriptionOfMeasure(oViewModel.getDescriptionOfMeasure());
				
				oRepo.Save(oEvent);
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
	@Consumes({MediaType.MULTIPART_FORM_DATA})
	public Integer UploadFile(@FormDataParam("file") InputStream file) throws IOException
	{
		try
		{
			//String sFileName = fileDetail.getFileName();
			
			return 0;
		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();
			return null;
		}
	}

}
