package it.fadeout.risckit.viewmodels;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class EventViewModel {
	
	private String countryCode;
	
	private int countryId;
	
	private String startDate;
	
	private String startHour;
	
	private String description;
	
	private Boolean unitHour;
	
	private BigDecimal unitValue;
	
	private Boolean unitApproximated;
	
	private int waveHeightType;
	
	private BigDecimal waveHeightValue;
	
	private String m_sWaveHeightInspire;
	
	private String m_sWaveHeightTimeSeries;
	
	private int waveDirectionType;
	
	private BigDecimal waveDirectionDegree;
	
	private String waveDirectionClustered;
	
	private String m_sWaveDirectionInspire;
	
	private String m_sWaveDirectionTimeSeries;
	
	private int windIntensityType;
	
	private BigDecimal windIntensityValue;
	
	private String m_sWindIntensityInspire;
	
	private String m_sWindIntensitySeries;
	
	private int windDirectionType;
	
	private BigDecimal windDirectionDegree;
	
	private String windDirectionClustered;
	
	private String m_sWindDirectionInspire;
	
	private String m_sWindDirectionTimeSeries;
	
	private BigDecimal peakWaterDischarge;
	
	private String m_sPeakWaterInpire;
	
	private String m_sPeakWaterTimeSeries;
	
	private BigDecimal floodHeight;
	
	private String m_sFloodHeightInspire;
	
	private String m_sFloodHeightTimeSeries;
	
	private int reporedCasualtiesNumber;
	
	private String reporedCasualtiesDescription;
	
	private String m_sReporedCasualtiesInspire;
	
	private String m_sReporedCasualtiesTimeSeries;
	
	private String damageToBuildingsDescription;
	
	private BigDecimal damageToBuildingsCost;
	
	private String m_sDamageToBuildingsInspire;
	
	private String m_sDamageToBuildingsTimeSeries;
	
	private int costDetail;
	
	private String descriptionOfMeasure;
	
	private GisViewModel GIS;
	
	private ArrayList<MediaViewModel> Media;
	
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String m_sCountryCode) {
		this.countryCode = m_sCountryCode;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int m_iCountryId) {
		this.countryId = m_iCountryId;
	}
	
	public Date getStartDate() {
		
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	public void setStartDate(String m_oStartDate) {
		this.startDate = m_oStartDate;
	}

	public Date getStartHour() {
		try {
			return new SimpleDateFormat("hh:mm").parse(startHour);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
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

	public int getWaveHeightType() {
		return waveHeightType;
	}

	public void setWaveHeightType(int m_iWaveHeightType) {
		this.waveHeightType = m_iWaveHeightType;
	}

	public BigDecimal getWaveHeightValue() {
		return waveHeightValue;
	}

	public void setWaveHeightValue(BigDecimal m_dWaveHeightValue) {
		this.waveHeightValue = m_dWaveHeightValue;
	}

	public String getWaveHeightInspire() {
		return m_sWaveHeightInspire;
	}

	public void setWaveHeightInspire(String m_sWaveHeightInpire) {
		this.m_sWaveHeightInspire = m_sWaveHeightInpire;
	}

	public String getWaveHeightTimeSeries() {
		return m_sWaveHeightTimeSeries;
	}

	public void setWaveHeightTimeSeries(String m_sWaveHeightTimeSeries) {
		this.m_sWaveHeightTimeSeries = m_sWaveHeightTimeSeries;
	}

	public int getWaveDirectionType() {
		return waveDirectionType;
	}

	public void setWaveDirectionType(int m_iWaveDirectionType) {
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
		return m_sWaveDirectionInspire;
	}

	public void setWaveDirectionInspire(String m_sWaveDirectionInpire) {
		this.m_sWaveDirectionInspire = m_sWaveDirectionInpire;
	}

	public String getWaveDirectionTimeSeries() {
		return m_sWaveDirectionTimeSeries;
	}

	public void setWaveDirectionTimeSeries(String m_sWaveDirectionTimeSeries) {
		this.m_sWaveDirectionTimeSeries = m_sWaveDirectionTimeSeries;
	}

	public int getWindIntensityType() {
		return windIntensityType;
	}

	public void setWindIntensityType(int m_iWindIntensityType) {
		this.windIntensityType = m_iWindIntensityType;
	}

	public BigDecimal getWindIntensityValue() {
		return windIntensityValue;
	}

	public void setWindIntensityValue(BigDecimal m_dWindIntensityValue) {
		this.windIntensityValue = m_dWindIntensityValue;
	}

	public String getWindIntensityInspire() {
		return m_sWindIntensityInspire;
	}

	public void setWindIntensityInspire(String m_sWindIntensityInpire) {
		this.m_sWindIntensityInspire = m_sWindIntensityInpire;
	}

	public String getWindIntensitySeries() {
		return m_sWindIntensitySeries;
	}

	public void setWindIntensitySeries(String m_sWindIntensitySeries) {
		this.m_sWindIntensitySeries = m_sWindIntensitySeries;
	}

	public int getWindDirectionType() {
		return windDirectionType;
	}

	public void setWindDirectionType(int m_iWindDirectionType) {
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
		return m_sWindDirectionInspire;
	}

	public void setWindDirectionInspire(String m_sWindDirectionInpire) {
		this.m_sWindDirectionInspire = m_sWindDirectionInpire;
	}

	public String getWindDirectionTimeSeries() {
		return m_sWindDirectionTimeSeries;
	}

	public void setWindDirectionTimeSeries(String m_sWindDirectionTimeSeries) {
		this.m_sWindDirectionTimeSeries = m_sWindDirectionTimeSeries;
	}

	public BigDecimal getPeakWaterDischarge() {
		return peakWaterDischarge;
	}

	public void setPeakWaterDischarge(BigDecimal m_dPeakWaterDischarge) {
		this.peakWaterDischarge = m_dPeakWaterDischarge;
	}

	public String getPeakWaterInspire() {
		return m_sPeakWaterInpire;
	}

	public void setPeakWaterInspire(String m_sPeakWaterInpire) {
		this.m_sPeakWaterInpire = m_sPeakWaterInpire;
	}

	public String getPeakWaterTimeSeries() {
		return m_sPeakWaterTimeSeries;
	}

	public void setPeakWaterTimeSeries(String m_sPeakWaterTimeSeries) {
		this.m_sPeakWaterTimeSeries = m_sPeakWaterTimeSeries;
	}

	public BigDecimal getFloodHeight() {
		return floodHeight;
	}

	public void setFloodHeight(BigDecimal m_dFloodHeight) {
		this.floodHeight = m_dFloodHeight;
	}

	public String getFloodHeightInspire() {
		return m_sFloodHeightInspire;
	}

	public void setFloodHeightInspire(String m_sFloodHeightInpire) {
		this.m_sFloodHeightInspire = m_sFloodHeightInpire;
	}

	public String getFloodHeightTimeSeries() {
		return m_sFloodHeightTimeSeries;
	}

	public void setFloodHeightTimeSeries(String m_sFloodHeightTimeSeries) {
		this.m_sFloodHeightTimeSeries = m_sFloodHeightTimeSeries;
	}

	public int getReporedCasualtiesNumber() {
		return reporedCasualtiesNumber;
	}

	public void setReporedCasualtiesNumber(int m_iReporedCasualtiesNumber) {
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
		return m_sReporedCasualtiesInspire;
	}

	public void setReporedCasualtiesInspire(String m_sReporedCasualtiesInpire) {
		this.m_sReporedCasualtiesInspire = m_sReporedCasualtiesInpire;
	}

	public String getReporedCasualtiesTimeSeries() {
		return m_sReporedCasualtiesTimeSeries;
	}

	public void setReporedCasualtiesTimeSeries(
			String m_sReporedCasualtiesTimeSeries) {
		this.m_sReporedCasualtiesTimeSeries = m_sReporedCasualtiesTimeSeries;
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
		return m_sDamageToBuildingsInspire;
	}

	public void setDamageToBuildingsInspire(String m_sDamageToBuildingsInpire) {
		this.m_sDamageToBuildingsInspire = m_sDamageToBuildingsInpire;
	}

	public String getDamageToBuildingsTimeSeries() {
		return m_sDamageToBuildingsTimeSeries;
	}

	public void setDamageToBuildingsTimeSeries(
			String m_sDamageToBuildingsTimeSeries) {
		this.m_sDamageToBuildingsTimeSeries = m_sDamageToBuildingsTimeSeries;
	}

	public int getCostDetail() {
		return costDetail;
	}

	public void setCostDetail(int m_iCostDetail) {
		this.costDetail = m_iCostDetail;
	}

	public String getDescriptionOfMeasure() {
		return descriptionOfMeasure;
	}

	public void setDescriptionOfMeasure(String m_sDescriptionOfMeasure) {
		this.descriptionOfMeasure = m_sDescriptionOfMeasure;
	}

	public GisViewModel getGis() {
		return GIS;
	}

	public void setGis(GisViewModel gis) {
		GIS = gis;
	}

	public ArrayList<MediaViewModel> getMedia() {
		return Media;
	}

	public void setMedia(ArrayList<MediaViewModel> media) {
		Media = media;
	}
	
}
