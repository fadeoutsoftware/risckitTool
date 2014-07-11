package it.fadeout.risckit.business;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="risckit.events")
@XmlRootElement
public class Event {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int m_iId;

	@Column(name="countryid")
	private int m_iCountryId;
	
	@Column(name="startdate")
	private Date m_oStartDate;
	
	@Column(name="starthour")
	private Date m_oStartHour;
	
	@Column(name="description")
	private String m_sDescription;
	
	@Column(name="unithour")
	private Boolean m_bUnitHour;
	
	@Column(name="unitvalue")
	private BigDecimal m_dUnitValue;
	
	@Column(name="unitapproximated")
	private Boolean m_bUnitApproximated;
	
	@Column(name="waveheighttype")
	private int m_iWaveHeightType;
	
	@Column(name="waveheightvalue")
	private BigDecimal m_dWaveHeightValue;
	
	@Column(name="waveheightinspire")
	private String m_sWaveHeightInspire;
	
	@Column(name="waveheighttimeseries")
	private String m_sWaveHeightTimeSeries;
	
	@Column(name="wavedirectiontype")
	private int m_iWaveDirectionType;
	
	@Column(name="wavedirectiondegree")
	private BigDecimal m_dWaveDirectionDegree;
	
	@Column(name="wavedirectionclustered")
	private String m_dWaveDirectionClustered;
	
	@Column(name="wavedirectioninspire")
	private String m_sWaveDirectionInspire;
	
	@Column(name="wavedirectiontimeseries")
	private String m_sWaveDirectionTimeSeries;
	
	@Column(name="windintensitytype")
	private int m_iWindIntensityType;
	
	@Column(name="windintensityvalue")
	private BigDecimal m_dWindIntensityValue;
	
	@Column(name="windintensityinspire")
	private String m_sWindIntensityInspire;
	
	@Column(name="windintensitytimeseries")
	private String m_sWindIntensitySeries;
	
	@Column(name="winddirectiontype")
	private int m_iWindDirectionType;
	
	@Column(name="winddirectiondegree")
	private BigDecimal m_dWindDirectionDegree;
	
	@Column(name="winddirectionclustered")
	private String m_dWindDirectionClustered;
	
	@Column(name="winddirectioninspire")
	private String m_sWindDirectionInspire;
	
	@Column(name="winddirectiontimeseries")
	private String m_sWindDirectionTimeSeries;
	
	@Column(name="peakwaterdischarge")
	private BigDecimal m_dPeakWaterDischarge;
	
	@Column(name="peakwaterinspire")
	private String m_sPeakWaterInpire;
	
	@Column(name="peakwatertimeseries")
	private String m_sPeakWaterTimeSeries;
	
	@Column(name="floodheight")
	private BigDecimal m_dFloodHeight;
	
	@Column(name="floodinspire")
	private String m_sFloodHeightInspire;
	
	@Column(name="floodtimeseries")
	private String m_sFloodHeightTimeSeries;
	
	@Column(name="reporedcasualtiesnumber")
	private int m_iReporedCasualtiesNumber;
	
	@Column(name="reporedcasualtiesdescription")
	private String m_iReporedCasualtiesDescription;
	
	@Column(name="reporedcasualtiesinspire")
	private String m_sReporedCasualtiesInspire;
	
	@Column(name="reporedcasualtiesimeseries")
	private String m_sReporedCasualtiesTimeSeries;
	
	@Column(name="damagetobuildingsdescription")
	private String m_sDamageToBuildingsDescription;
	
	@Column(name="damagetobuildingscost")
	private BigDecimal m_dDamageToBuildingsCost;
	
	@Column(name="damagetobuildingsinspire")
	private String m_sDamageToBuildingsInspire;
	
	@Column(name="damagetobuildingstimeseries")
	private String m_sDamageToBuildingsTimeSeries;
	
	@Column(name="costdetail")
	private int m_iCostDetail;
	
	@Column(name="descriptionofmeasure")
	private String m_sDescriptionOfMeasure;
	
	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn 
	private Country m_oCountry;

	public int getId() {
		return m_iId;
	}

	public void setId(int m_iId) {
		this.m_iId = m_iId;
	}

	public int getCountryId() {
		return m_iCountryId;
	}

	public void setCountryId(int m_iCountryId) {
		this.m_iCountryId = m_iCountryId;
	}

	public Date getStartDate() {
		return m_oStartDate;
	}

	public void setStartDate(Date m_oStartDate) {
		this.m_oStartDate = m_oStartDate;
	}

	public Date getStartHour() {
		return m_oStartHour;
	}

	public void setStartHour(Date m_oStartHour) {
		this.m_oStartHour = m_oStartHour;
	}

	public String getDescription() {
		return m_sDescription;
	}

	public void setDescription(String m_sDescription) {
		this.m_sDescription = m_sDescription;
	}

	public Boolean getUnitHour() {
		return m_bUnitHour;
	}

	public void setUnitHour(Boolean m_bUnitHour) {
		this.m_bUnitHour = m_bUnitHour;
	}

	public BigDecimal getUnitValue() {
		return m_dUnitValue;
	}

	public void setUnitValue(BigDecimal m_dUnitValue) {
		this.m_dUnitValue = m_dUnitValue;
	}

	public Boolean getUnitApproximated() {
		return m_bUnitApproximated;
	}

	public void setUnitApproximated(Boolean m_bUnitApproximated) {
		this.m_bUnitApproximated = m_bUnitApproximated;
	}

	public int getWaveHeightType() {
		return m_iWaveHeightType;
	}

	public void setWaveHeightType(int m_iWaveHeightType) {
		this.m_iWaveHeightType = m_iWaveHeightType;
	}

	public BigDecimal getWaveHeightValue() {
		return m_dWaveHeightValue;
	}

	public void setWaveHeightValue(BigDecimal m_dWaveHeightValue) {
		this.m_dWaveHeightValue = m_dWaveHeightValue;
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
		return m_iWaveDirectionType;
	}

	public void setWaveDirectionType(int m_iWaveDirectionType) {
		this.m_iWaveDirectionType = m_iWaveDirectionType;
	}

	public BigDecimal getWaveDirectionDegree() {
		return m_dWaveDirectionDegree;
	}

	public void setWaveDirectionDegree(BigDecimal m_dWaveDirectionDegree) {
		this.m_dWaveDirectionDegree = m_dWaveDirectionDegree;
	}

	public String getWaveDirectionClustered() {
		return m_dWaveDirectionClustered;
	}

	public void setWaveDirectionClustered(String m_dWaveDirectionClustered) {
		this.m_dWaveDirectionClustered = m_dWaveDirectionClustered;
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
		return m_iWindIntensityType;
	}

	public void setWindIntensityType(int m_iWindIntensityType) {
		this.m_iWindIntensityType = m_iWindIntensityType;
	}

	public BigDecimal getWindIntensityValue() {
		return m_dWindIntensityValue;
	}

	public void setWindIntensityValue(BigDecimal m_dWindIntensityValue) {
		this.m_dWindIntensityValue = m_dWindIntensityValue;
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
		return m_iWindDirectionType;
	}

	public void setWindDirectionType(int m_iWindDirectionType) {
		this.m_iWindDirectionType = m_iWindDirectionType;
	}

	public BigDecimal getWindDirectionDegree() {
		return m_dWindDirectionDegree;
	}

	public void setWindDirectionDegree(BigDecimal m_dWindDirectionDegree) {
		this.m_dWindDirectionDegree = m_dWindDirectionDegree;
	}

	public String getWindDirectionClustered() {
		return m_dWindDirectionClustered;
	}

	public void setWindDirectionClustered(String m_dWindDirectionClustered) {
		this.m_dWindDirectionClustered = m_dWindDirectionClustered;
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
		return m_dPeakWaterDischarge;
	}

	public void setPeakWaterDischarge(BigDecimal m_dPeakWaterDischarge) {
		this.m_dPeakWaterDischarge = m_dPeakWaterDischarge;
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
		return m_dFloodHeight;
	}

	public void setFloodHeight(BigDecimal m_dFloodHeight) {
		this.m_dFloodHeight = m_dFloodHeight;
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
		return m_iReporedCasualtiesNumber;
	}

	public void setReporedCasualtiesNumber(int m_iReporedCasualtiesNumber) {
		this.m_iReporedCasualtiesNumber = m_iReporedCasualtiesNumber;
	}

	public String getReporedCasualtiesDescription() {
		return m_iReporedCasualtiesDescription;
	}

	public void setReporedCasualtiesDescription(
			String m_iReporedCasualtiesDescription) {
		this.m_iReporedCasualtiesDescription = m_iReporedCasualtiesDescription;
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
		return m_sDamageToBuildingsDescription;
	}

	public void setDamageToBuildingsDescription(
			String m_sDamageToBuildingsDescription) {
		this.m_sDamageToBuildingsDescription = m_sDamageToBuildingsDescription;
	}

	public BigDecimal getDamageToBuildingsCost() {
		return m_dDamageToBuildingsCost;
	}

	public void setDamageToBuildingsCost(BigDecimal m_dDamageToBuildingsCost) {
		this.m_dDamageToBuildingsCost = m_dDamageToBuildingsCost;
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
		return m_iCostDetail;
	}

	public void setCostDetail(int m_iCostDetail) {
		this.m_iCostDetail = m_iCostDetail;
	}

	public String getDescriptionOfMeasure() {
		return m_sDescriptionOfMeasure;
	}

	public void setDescriptionOfMeasure(String m_sDescriptionOfMeasure) {
		this.m_sDescriptionOfMeasure = m_sDescriptionOfMeasure;
	}

	public Country getCountry() {
		return m_oCountry;
	}

	public void setCountry(Country m_oCountry) {
		this.m_oCountry = m_oCountry;
	}
	
}
