package it.fadeout.risckit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.fadeout.risckit.business.Country;
import it.fadeout.risckit.business.Event;
import it.fadeout.risckit.business.SVNUtils;
import it.fadeout.risckit.data.CountryRepository;
import it.fadeout.risckit.data.EventRepository;
import it.fadeout.risckit.viewmodels.CountryViewModel;
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
				oEvent.setWaterLevelType(oViewModel.getWaterLevelType());
				oEvent.setWaterLevelValue(oViewModel.getWaterLevelValue());
				oEvent.setWaterLevelInspire(oViewModel.getWaterLevelInspire());
				oEvent.setWaterLevelTimeSeries(oViewModel.getWaterLevelTimeSeries());
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
					//csv
					
					String sLocation = oViewModel.getCountryCode() + "_" + oViewModel.getRegionName();
					DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
					String sStartDate = dateFormatter.format(oEvent.getStartDate());
					sStartDate = sStartDate.replace("/", "_");
					SVNUtils oSvnUtils = new SVNUtils();
					String sDirPath = "/" + oViewModel.getLogin() + "/risckit/" + sStartDate + "_" + sLocation + "/raw/";
					//csv
					oSvnUtils.Commit(oEvent.GetCsvInputStream(), 
							oViewModel.getLogin(),
							servletConfig.getInitParameter("SvnUser"), 
							servletConfig.getInitParameter("SvnPwd"), 
							servletConfig.getInitParameter("SvnUserDomain"), 
							sDirPath + "Event.csv", 
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
			String sDirPath = "/" + sUserLogin + "/risckit/" + sStartDate + "_" + sLocation + "/raw/";
			Date dtStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(sStartDate);
			DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
			sStartDate = dateFormatter.format(dtStartDate);
			sStartDate = sStartDate.replace("/", "_");
			
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
