package it.fadeout.risckit;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import it.fadeout.risckit.business.Event;
import it.fadeout.risckit.business.SVNUtils;
import it.fadeout.risckit.data.EventRepository;
import it.fadeout.risckit.viewmodels.EventViewModel;

import javax.servlet.ServletConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;


@Path("/events")
public class EventResource {
	
	@Context
	ServletConfig servletConfig;
	
	
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
				if (oViewModel.getId() == 0)
					oRepo.Save(oEvent);
				else
					oRepo.Update(oEvent);
				
				if (oEvent != null)
					oViewModel.setId(oEvent.getId());
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
	public String UploadFile(@FormDataParam("file") InputStream file, @FormDataParam("eventid") Integer iEventId, @FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("parameter") String sNameProperty) throws IOException
	{
		try
		{
			//String sFileName = fileDetail.getFileName();
			
			SVNUtils oSvnUtils = new SVNUtils();
			String sDirPath = "/" + servletConfig.getInitParameter("SvnUser") + "/Events/" + iEventId + "/";
			String sUser = servletConfig.getInitParameter("SvnUserDomain") + "\\" + servletConfig.getInitParameter("SvnUser");
			oSvnUtils.Commit(file, sUser, servletConfig.getInitParameter("SvnPwd"), sDirPath, sDirPath + fileDetail.getFileName(), servletConfig.getInitParameter("SvnRepository"));
			
			//Se ho effettuato l'update, carico il progetto e lo aggiorno con il nuovo path
			EventRepository oRepo = new EventRepository();
			Event oEvent = oRepo.Select(iEventId, Event.class);
			
			String sPathRepository = servletConfig.getInitParameter("SvnRepository") + sDirPath + fileDetail.getFileName();
			
			//EventRepository oRepo = new EventRepository();
			//Event oEvent = oRepo.Select(iEventId, Event.class);
			//String sPathRepository = fileDetail.getFileName();
			
			//TODO: da modificare usando la reflection, ora ho fatto così per fare un pò prima
			if (sNameProperty == "waveHeightInspire")
				oEvent.setWaveHeightInspire(sPathRepository);
            if (sNameProperty == "waveHeightTimeSeries")
            	oEvent.setWaveHeightTimeSeries(sPathRepository);
            if (sNameProperty == "waveDirectionInspire")
            	oEvent.setWaveDirectionInspire(sPathRepository);
            if (sNameProperty == "waveDirectionTimeSeries")
            	oEvent.setWaveDirectionTimeSeries(sPathRepository);
            if (sNameProperty == "windIntensityInspire")
            	oEvent.setWindIntensityInspire(sPathRepository);
            if (sNameProperty == "windIntensitySeries")
            	oEvent.setWindIntensitySeries(sPathRepository);
            if (sNameProperty == "windDirectionInspire")
            	oEvent.setWindDirectionInspire(sPathRepository);
            if (sNameProperty == "windDirectionTimeSeries")
            	oEvent.setWindDirectionTimeSeries(sPathRepository);
            if (sNameProperty == "peakWaterInpire")
            	oEvent.setPeakWaterInspire(sPathRepository);
            if (sNameProperty == "peakWaterTimeSeries")
            	oEvent.setPeakWaterTimeSeries(sPathRepository);
            if (sNameProperty == "floodHeightInspire")
            	oEvent.setFloodHeightInspire(sPathRepository);
            if (sNameProperty == "floodHeightTimeSeries")
            	oEvent.setFloodHeightTimeSeries(sPathRepository);
            if (sNameProperty == "reporedCasualtiesInspire")
            	oEvent.setReporedCasualtiesInspire(sPathRepository);
            if (sNameProperty == "reporedCasualtiesTimeSeries")
            	oEvent.setReporedCasualtiesTimeSeries(sPathRepository);
            if (sNameProperty == "damageToBuildingsInspire")
            	oEvent.setDamageToBuildingsInspire(sPathRepository);
            if (sNameProperty == "damageToBuildingsTimeSeries")
            	oEvent.setDamageToBuildingsTimeSeries(sPathRepository);
            
            oRepo.Update(oEvent);
			
			return sPathRepository;
		}
		catch(Exception oEx)
		{
			oEx.printStackTrace();
			return null;
		}
	}

}
