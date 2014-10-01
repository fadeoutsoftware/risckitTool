package it.fadeout.risckit.viewmodels;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class EventViewModel {
	
	private Integer id;
	
	private String countryCode;
	
	private String countryName;
	
	private Integer countryId;
	
	private String startDate;
	
	private String startHour;
	
	private String description;
	
	private Boolean unitHour;
	
	private BigDecimal unitValue;
	
	private Boolean unitApproximated;
	
	private Integer waveHeightType;
	
	private BigDecimal waveHeightValue;
	
	private String waveHeightInspire;
	
	private String waveHeightTimeSeries;
	
	private Integer waveDirectionType;
	
	private BigDecimal waveDirectionDegree;
	
	private String waveDirectionClustered;
	
	private String waveDirectionInspire;
	
	private String waveDirectionTimeSeries;
	
	private Integer windIntensityType;
	
	private BigDecimal windIntensityValue;
	
	private String windIntensityInspire;
	
	private String windIntensitySeries;
	
	private Integer windDirectionType;
	
	private BigDecimal windDirectionDegree;
	
	private String windDirectionClustered;
	
	private String windDirectionInspire;
	
	private String windDirectionTimeSeries;
	
	private BigDecimal peakWaterDischarge;
	
	private String peakWaterInpire;
	
	private String peakWaterTimeSeries;
	
	private BigDecimal floodHeight;
	
	private String floodHeightInspire;
	
	private String floodHeightTimeSeries;
	
	private Integer reporedCasualtiesNumber;
	
	private String reporedCasualtiesDescription;
	
	private String reporedCasualtiesInspire;
	
	private String reporedCasualtiesTimeSeries;
	
	private String damageToBuildingsDescription;
	
	private BigDecimal damageToBuildingsCost;
	
	private String damageToBuildingsInspire;
	
	private String damageToBuildingsTimeSeries;
	
	private Integer costDetail;
	
	private String descriptionOfMeasure;
	
	private String login;
	
	private String regionName;
	
	private Integer waterLevelType;
	
	private BigDecimal waterLevelValue;
	
	private String waterLevelInspire;
	
	private String waterLevelTimeSeries;
	
	private Integer userId;
	
	private ArrayList<MediaViewModel> media;
	
	private String lat;
	
	private String lon;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String m_sCountryCode) {
		this.countryCode = m_sCountryCode;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer m_iCountryId) {
		this.countryId = m_iCountryId;
	}   
	
	public String getStartDate() {
		/*
		try {
			if (startDate != null)
				return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		*/
		return this.startDate;
	}

	public void setStartDate(String m_oStartDate) {
		this.startDate = m_oStartDate;
	}

	public String getStartHour() {
		/*
		try {
			if (startHour != null)
				return new SimpleDateFormat("hh:mm", Locale.ENGLISH).parse(startHour);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		*/
		
		return this.startHour;
	}

	public void setStartHour(String m_oStartHour) {
		this.startHour = m_oStartHour;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String m_sDescription) {
		this.description = m_sDescription;
	}

	public Boolean getUnitHour() {
		return unitHour;
	}

	public void setUnitHour(Boolean m_bUnitHour) {
		this.unitHour = m_bUnitHour;
	}

	public BigDecimal getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(BigDecimal m_dUnitValue) {
		this.unitValue = m_dUnitValue;
	}

	public Boolean getUnitApproximated() {
		return unitApproximated;
	}

	public void setUnitApproximated(Boolean m_bUnitApproximated) {
		this.unitApproximated = m_bUnitApproximated;
	}

	public Integer getWaveHeightType() {
		return waveHeightType;
	}

	public void setWaveHeightType(Integer m_iWaveHeightType) {
		this.waveHeightType = m_iWaveHeightType;
	}

	public BigDecimal getWaveHeightValue() {
		return waveHeightValue;
	}

	public void setWaveHeightValue(BigDecimal m_dWaveHeightValue) {
		this.waveHeightValue = m_dWaveHeightValue;
	}

	public String getWaveHeightInspire() {
		return waveHeightInspire;
	}

	public void setWaveHeightInspire(String m_sWaveHeightInpire) {
		this.waveHeightInspire = m_sWaveHeightInpire;
	}

	public String getWaveHeightTimeSeries() {
		return waveHeightTimeSeries;
	}

	public void setWaveHeightTimeSeries(String m_sWaveHeightTimeSeries) {
		this.waveHeightTimeSeries = m_sWaveHeightTimeSeries;
	}

	public Integer getWaveDirectionType() {
		return waveDirectionType;
	}

	public void setWaveDirectionType(Integer m_iWaveDirectionType) {
		this.waveDirectionType = m_iWaveDirectionType;
	}

	public BigDecimal getWaveDirectionDegree() {
		return waveDirectionDegree;
	}

	public void setWaveDirectionDegree(BigDecimal m_dWaveDirectionDegree) {
		this.waveDirectionDegree = m_dWaveDirectionDegree;
	}

	public String getWaveDirectionClustered() {
		return waveDirectionClustered;
	}

	public void setWaveDirectionClustered(String m_dWaveDirectionClustered) {
		this.waveDirectionClustered = m_dWaveDirectionClustered;
	}

	public String getWaveDirectionInspire() {
		return waveDirectionInspire;
	}

	public void setWaveDirectionInspire(String m_sWaveDirectionInpire) {
		this.waveDirectionInspire = m_sWaveDirectionInpire;
	}

	public String getWaveDirectionTimeSeries() {
		return waveDirectionTimeSeries;
	}

	public void setWaveDirectionTimeSeries(String m_sWaveDirectionTimeSeries) {
		this.waveDirectionTimeSeries = m_sWaveDirectionTimeSeries;
	}

	public Integer getWindIntensityType() {
		return windIntensityType;
	}

	public void setWindIntensityType(Integer m_iWindIntensityType) {
		this.windIntensityType = m_iWindIntensityType;
	}

	public BigDecimal getWindIntensityValue() {
		return windIntensityValue;
	}

	public void setWindIntensityValue(BigDecimal m_dWindIntensityValue) {
		this.windIntensityValue = m_dWindIntensityValue;
	}

	public String getWindIntensityInspire() {
		return windIntensityInspire;
	}

	public void setWindIntensityInspire(String m_sWindIntensityInpire) {
		this.windIntensityInspire = m_sWindIntensityInpire;
	}

	public String getWindIntensitySeries() {
		return windIntensitySeries;
	}

	public void setWindIntensitySeries(String m_sWindIntensitySeries) {
		this.windIntensitySeries = m_sWindIntensitySeries;
	}

	public Integer getWindDirectionType() {
		return windDirectionType;
	}

	public void setWindDirectionType(Integer m_iWindDirectionType) {
		this.windDirectionType = m_iWindDirectionType;
	}

	public BigDecimal getWindDirectionDegree() {
		return windDirectionDegree;
	}

	public void setWindDirectionDegree(BigDecimal m_dWindDirectionDegree) {
		this.windDirectionDegree = m_dWindDirectionDegree;
	}

	public String getWindDirectionClustered() {
		return windDirectionClustered;
	}

	public void setWindDirectionClustered(String m_dWindDirectionClustered) {
		this.windDirectionClustered = m_dWindDirectionClustered;
	}

	public String getWindDirectionInspire() {
		return windDirectionInspire;
	}

	public void setWindDirectionInspire(String m_sWindDirectionInpire) {
		this.windDirectionInspire = m_sWindDirectionInpire;
	}

	public String getWindDirectionTimeSeries() {
		return windDirectionTimeSeries;
	}

	public void setWindDirectionTimeSeries(String m_sWindDirectionTimeSeries) {
		this.windDirectionTimeSeries = m_sWindDirectionTimeSeries;
	}

	public BigDecimal getPeakWaterDischarge() {
		return peakWaterDischarge;
	}

	public void setPeakWaterDischarge(BigDecimal m_dPeakWaterDischarge) {
		this.peakWaterDischarge = m_dPeakWaterDischarge;
	}

	public String getPeakWaterInspire() {
		return peakWaterInpire;
	}

	public void setPeakWaterInspire(String m_sPeakWaterInpire) {
		this.peakWaterInpire = m_sPeakWaterInpire;
	}

	public String getPeakWaterTimeSeries() {
		return peakWaterTimeSeries;
	}

	public void setPeakWaterTimeSeries(String m_sPeakWaterTimeSeries) {
		this.peakWaterTimeSeries = m_sPeakWaterTimeSeries;
	}

	public BigDecimal getFloodHeight() {
		return floodHeight;
	}

	public void setFloodHeight(BigDecimal m_dFloodHeight) {
		this.floodHeight = m_dFloodHeight;
	}

	public String getFloodHeightInspire() {
		return floodHeightInspire;
	}

	public void setFloodHeightInspire(String m_sFloodHeightInpire) {
		this.floodHeightInspire = m_sFloodHeightInpire;
	}

	public String getFloodHeightTimeSeries() {
		return floodHeightTimeSeries;
	}

	public void setFloodHeightTimeSeries(String m_sFloodHeightTimeSeries) {
		this.floodHeightTimeSeries = m_sFloodHeightTimeSeries;
	}

	public Integer getReporedCasualtiesNumber() {
		return reporedCasualtiesNumber;
	}

	public void setReporedCasualtiesNumber(Integer m_iReporedCasualtiesNumber) {
		this.reporedCasualtiesNumber = m_iReporedCasualtiesNumber;
	}

	public String getReporedCasualtiesDescription() {
		return reporedCasualtiesDescription;
	}

	public void setReporedCasualtiesDescription(
			String m_iReporedCasualtiesDescription) {
		this.reporedCasualtiesDescription = m_iReporedCasualtiesDescription;
	}

	public String getReporedCasualtiesInspire() {
		return reporedCasualtiesInspire;
	}

	public void setReporedCasualtiesInspire(String m_sReporedCasualtiesInpire) {
		this.reporedCasualtiesInspire = m_sReporedCasualtiesInpire;
	}

	public String getReporedCasualtiesTimeSeries() {
		return reporedCasualtiesTimeSeries;
	}

	public void setReporedCasualtiesTimeSeries(
			String m_sReporedCasualtiesTimeSeries) {
		this.reporedCasualtiesTimeSeries = m_sReporedCasualtiesTimeSeries;
	}

	public String getDamageToBuildingsDescription() {
		return damageToBuildingsDescription;
	}

	public void setDamageToBuildingsDescription(
			String m_sDamageToBuildingsDescription) {
		this.damageToBuildingsDescription = m_sDamageToBuildingsDescription;
	}

	public BigDecimal getDamageToBuildingsCost() {
		return damageToBuildingsCost;
	}

	public void setDamageToBuildingsCost(BigDecimal m_dDamageToBuildingsCost) {
		this.damageToBuildingsCost = m_dDamageToBuildingsCost;
	}

	public String getDamageToBuildingsInspire() {
		return damageToBuildingsInspire;
	}

	public void setDamageToBuildingsInspire(String m_sDamageToBuildingsInpire) {
		this.damageToBuildingsInspire = m_sDamageToBuildingsInpire;
	}

	public String getDamageToBuildingsTimeSeries() {
		return damageToBuildingsTimeSeries;
	}

	public void setDamageToBuildingsTimeSeries(
			String m_sDamageToBuildingsTimeSeries) {
		this.damageToBuildingsTimeSeries = m_sDamageToBuildingsTimeSeries;
	}

	public Integer getCostDetail() {
		return costDetail;
	}

	public void setCostDetail(Integer m_iCostDetail) {
		this.costDetail = m_iCostDetail;
	}

	public String getDescriptionOfMeasure() {
		return descriptionOfMeasure;
	}

	public void setDescriptionOfMeasure(String m_sDescriptionOfMeasure) {
		this.descriptionOfMeasure = m_sDescriptionOfMeasure;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Integer getWaterLevelType() {
		return waterLevelType;
	}

	public void setWaterLevelType(Integer waterLevelType) {
		this.waterLevelType = waterLevelType;
	}

	public BigDecimal getWaterLevelValue() {
		return waterLevelValue;
	}

	public void setWaterLevelValue(BigDecimal waterLevelValue) {
		this.waterLevelValue = waterLevelValue;
	}

	public String getWaterLevelInspire() {
		return waterLevelInspire;
	}

	public void setWaterLevelInspire(String waterLevelInspire) {
		this.waterLevelInspire = waterLevelInspire;
	}

	public String getWaterLevelTimeSeries() {
		return waterLevelTimeSeries;
	}

	public void setWaterLevelTimeSeries(String waterLevelTimeSeries) {
		this.waterLevelTimeSeries = waterLevelTimeSeries;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public ArrayList<MediaViewModel> getMedia() {
		return media;
	}

	public void setMedia(ArrayList<MediaViewModel> media) {
		this.media = media;
	}
	
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
}
