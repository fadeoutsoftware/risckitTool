package it.fadeout.risckit.business;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	private Integer m_iId;

	@Column(name="countryid")
	private Integer m_iCountryId;

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
	private Integer m_iWaveHeightType;

	@Column(name="waveheightvalue")
	private BigDecimal m_dWaveHeightValue;

	@Column(name="waveheightinspire")
	private String m_sWaveHeightInspire;

	@Column(name="waveheighttimeseries")
	private String m_sWaveHeightTimeSeries;

	@Column(name="wavedirectiontype")
	private Integer m_iWaveDirectionType;

	@Column(name="wavedirectiondegree")
	private BigDecimal m_dWaveDirectionDegree;

	@Column(name="wavedirectionclustered")
	private String m_dWaveDirectionClustered;

	@Column(name="wavedirectioninspire")
	private String m_sWaveDirectionInspire;

	@Column(name="wavedirectiontimeseries")
	private String m_sWaveDirectionTimeSeries;

	@Column(name="windintensitytype")
	private Integer m_iWindIntensityType;

	@Column(name="windintensityvalue")
	private BigDecimal m_dWindIntensityValue;

	@Column(name="windintensityinspire")
	private String m_sWindIntensityInspire;

	@Column(name="windintensitytimeseries")
	private String m_sWindIntensitySeries;

	@Column(name="winddirectiontype")
	private Integer m_iWindDirectionType;

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
	private Integer m_iReporedCasualtiesNumber;

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
	private Integer m_iCostDetail;

	@Column(name="descriptionofmeasure")
	private String m_sDescriptionOfMeasure;

	@Column(name="waterleveltype")
	private Integer m_iWaterLevelType;

	@Column(name="waterlevelvalue")
	private BigDecimal m_dWaterLevelValue;

	@Column(name="waterlevelinspire")
	private String m_sWaterLevelInspire;

	@Column(name="waterleveltimeseries")
	private String m_sWaterLevelTimeSeries;

	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="countryid", nullable=true, insertable=false, updatable=false)
	private Country m_oCountry;

	public Integer getId() {
		return m_iId;
	}

	public void setId(Integer m_iId) {
		this.m_iId = m_iId;
	}

	public Integer getCountryId() {
		return m_iCountryId;
	}

	public void setCountryId(Integer m_iCountryId) {
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

	public Integer getWaveHeightType() {
		return m_iWaveHeightType;
	}

	public void setWaveHeightType(Integer m_iWaveHeightType) {
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

	public Integer getWaveDirectionType() {
		return m_iWaveDirectionType;
	}

	public void setWaveDirectionType(Integer m_iWaveDirectionType) {
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

	public Integer getWindIntensityType() {
		return m_iWindIntensityType;
	}

	public void setWindIntensityType(Integer m_iWindIntensityType) {
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

	public Integer getWindDirectionType() {
		return m_iWindDirectionType;
	}

	public void setWindDirectionType(Integer m_iWindDirectionType) {
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

	public Integer getReporedCasualtiesNumber() {
		return m_iReporedCasualtiesNumber;
	}

	public void setReporedCasualtiesNumber(Integer m_iReporedCasualtiesNumber) {
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

	public Integer getCostDetail() {
		return m_iCostDetail;
	}

	public void setCostDetail(Integer m_iCostDetail) {
		this.m_iCostDetail = m_iCostDetail;
	}

	public String getDescriptionOfMeasure() {
		return m_sDescriptionOfMeasure;
	}

	public void setDescriptionOfMeasure(String m_sDescriptionOfMeasure) {
		this.m_sDescriptionOfMeasure = m_sDescriptionOfMeasure;
	}

	public Integer getWaterLevelType() {
		return m_iWaterLevelType;
	}

	public void setWaterLevelType(Integer m_iWaterLevelType) {
		this.m_iWaterLevelType = m_iWaterLevelType;
	}

	public BigDecimal getWaterLevelValue() {
		return m_dWaterLevelValue;
	}

	public void setWaterLevelValue(BigDecimal m_dWaterLevelValue) {
		this.m_dWaterLevelValue = m_dWaterLevelValue;
	}

	public String getWaterLevelInspire() {
		return m_sWaterLevelInspire;
	}

	public void setWaterLevelInspire(String m_sWaterLevelInspire) {
		this.m_sWaterLevelInspire = m_sWaterLevelInspire;
	}

	public String getWaterLevelTimeSeries() {
		return m_sWaterLevelTimeSeries;
	}

	public void setWaterLevelTimeSeries(String m_sWaterLevelTimeSeries) {
		this.m_sWaterLevelTimeSeries = m_sWaterLevelTimeSeries;
	}

	public Country getCountry() {
		return m_oCountry;
	}

	public void setCountry(Country m_oCountry) {
		this.m_oCountry = m_oCountry;
	}

	public InputStream GetCsvInputStream()
	{
		ArrayList<String> oList = new ArrayList<String>();

		String sHeader =  
				"CountryName;" + 
						"StartDate;" +
						"StartHour;" +
						"Description;" +
						"Unit;" +
						"UnitValue;" +
						"UnitType;" + 
						"WaveHeightType;" + 
						"WaveHeightValue;" + 
						"WaveHeightInspire;" + 
						"WaveHeightTimeSeries;" +
						"WaveDirectionType;" + 
						"WaveDirectionValue;" +
						"WaveDirectionInspire;" +
						"WaveDirectionTimeSeries;" +
						"WindIntensityType;" + 
						"WindIntensityValue;" + 
						"WindIntensityInspire;" + 
						"WindIntensityTimeSeries;" + 
						"WindDirectionType;" + 
						"WindDirectionValue;" + 
						"WindDirectionInspire;" + 
						"WindDirectionTimeSeries;" +
						"WaterLevelType;" +
						"WaterLevelValue;" +
						"WaterLevelInspire;" +
						"WaterLevelTimeSeries;" +
						"PeakWaterDischarge;" + 
						"PeakWaterInpire;" +
						"PeakWaterTimeSeries;" + 
						"FloodHeight;" +
						"FloodHeightInspire;" + 
						"FloodHeightTimeSeries;" +
						"ReporedCasualtiesNumber;" + 
						"ReporedCasualtiesDescription;" +
						"ReporedCasualtiesInspire;" + 
						"ReporedCasualtiesTimeSeries;" + 
						"DamageToBuildingsDescription;" +
						"DamageToBuildingsCost;" + 
						"DamageToBuildingsInspire;" + 
						"DamageToBuildingsTimeSeries;" +
						"CostDetail;" + 
						"DescriptionOfMeasure;" + 
						"\r\n";

		oList.add(sHeader);

		String sCountryName = "";
		String sStartDate = "";
		String sStartHour = "";
		String sDescription = "";
		String sUnit = "Hour";
		String sUnitValue = "";
		String sUnitType = "Approximate";
		String sWaveHeightType = "";
		String sWaveHeightValue = "";
		String sWaveHeightInspire = "";
		String sWaveHeightTimeSeries = "";
		String sWaveDirectionType = "";
		String sWaveDirectionValue = "";
		String sWaveDirectionInspire = "";
		String sWaveDirectionTimeSeries = "";
		String sWindIntensityType = "";
		String sWindIntensityValue = "";
		String sWindIntensityInspire = "";
		String sWindIntensityTimeSeries = "";
		String sWindDirectionType = "";
		String sWindDirectionValue = "";
		String sWindDirectionInspire = "";
		String sWindDirectionTimeSeries = "";
		String sWaterLevelType = "";
		String sWaterLevelValue = "";
		String sWaterLevelInspire = "";
		String sWaterLevelTimeSeries = "";
		String sPeakWaterDischarge = "";
		String sPeakWaterInpire = "";
		String sPeakWaterTimeSeries = "";
		String sFloodHeight = "";
		String sFloodHeightInspire = "";
		String sFloodHeightTimeSeries = "";
		String sReporedCasualtiesNumber = "";
		String sReporedCasualtiesDescription = "";
		String sReporedCasualtiesInspire = "";
		String sReporedCasualtiesTimeSeries = "";
		String sDamageToBuildingsDescription = "";
		String sDamageToBuildingsCost = "";
		String sDamageToBuildingsInspire = "";
		String sDamageToBuildingsTimeSeries = "";
		String sCostDetail = "";
		String sDescriptionOfMeasure = "";
		if (this.getCountry() != null)
			sCountryName = String.valueOf(this.getCountry().getName());

		if (this.m_oStartDate != null)
		{
			DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
			sStartDate = dateFormatter.format(this.getStartDate());
		}
		if (this.m_sDescription != null)
			sDescription = String.valueOf(this.m_sDescription);
		if (this.getUnitHour() == null || !this.getUnitHour())
			sUnit = "Day";
		if (this.getUnitApproximated() == null || !this.getUnitApproximated())
			sUnitType = "Exact";
		if(this.getUnitValue() != null)
			sUnitValue = this.getUnitValue().toString();

		if (this.getWaveHeightType() != null)
		{
			if (this.getWaveHeightType() == 0)
				sWaveHeightType = "Mean significant during event";
			else if (this.getWaveHeightType() == 1)
				sWaveHeightType = "Peak significant";
			else
				sWaveHeightType = "Maximum";
		}
		if (this.getWaveHeightValue() != null)
			sWaveHeightValue = this.getWaveHeightValue().toString();

		if (this.getWaveHeightInspire() != null)
			sWaveHeightInspire = this.getWaveHeightInspire();

		if (this.getWaveHeightTimeSeries() != null)
			sWaveHeightTimeSeries = this.getWaveHeightTimeSeries();

		if (this.getWaveDirectionType() != null)
		{
			if (this.getWaveDirectionType() == 0)
			{
				sWaveDirectionType = "Degrees from N";
				sWaveDirectionValue = this.getWaveDirectionDegree().toString();
			}
			else
			{
				sWaveDirectionType = "Compass";
				sWaveDirectionValue = this.getWaveDirectionClustered().toString();
			}
		}

		if (this.getWaveDirectionInspire() != null)
			sWaveDirectionInspire = this.getWaveDirectionInspire();

		if (this.getWaveDirectionTimeSeries() != null)
			sWaveDirectionTimeSeries = this.getWaveDirectionTimeSeries();

		if (this.getWindIntensityType() != null)
		{
			if (this.getWindIntensityType() == 0)
			{
				sWindIntensityType = "Mean speed during event";
			}
			else if (this.getWindIntensityType() == 1)
			{
				sWindIntensityType = "Maximum speed";
			}
			else
				sWindIntensityType = "Maximum gust";
		}

		if (this.getWindIntensityValue() != null)
			sWindIntensityValue = this.getWindIntensityValue().toString();

		if (this.getWindIntensityInspire() != null)
			sWindIntensityInspire = this.getWindIntensityInspire();

		if (this.getWindIntensitySeries() != null)
			sWindIntensityTimeSeries = this.getWindIntensitySeries();

		if (this.getWindDirectionType() != null)
		{
			if (this.getWindDirectionType() == 0)
			{
				sWindDirectionType = "Degrees from N";
				sWindDirectionValue = this.getWindDirectionDegree().toString();
			}
			else
			{
				sWindDirectionType = "Compass";
				sWindDirectionValue = this.getWaveDirectionClustered().toString();
			}

		}

		if (this.getWindDirectionInspire() != null)
			sWindDirectionInspire = this.getWindDirectionInspire();

		if (this.getWindDirectionTimeSeries() != null)
			sWindDirectionTimeSeries = this.getWindDirectionTimeSeries();

		if (this.getWaterLevelType() != null)
		{
			if (this.getWaterLevelType() == 0)
			{
				sWaterLevelType = "Total water level";
			}
			else
			{
				sWaterLevelType = "Astronomical tide";
			}
		}

		if (this.getWaterLevelValue() != null)
			sWaterLevelValue = this.getWaterLevelValue().toString();

		if (this.getWaterLevelInspire() != null)
			sWaterLevelInspire = this.getWaterLevelInspire();

		if (this.getWaterLevelTimeSeries() != null)
			sWaterLevelTimeSeries = this.getWaterLevelTimeSeries();

		if (this.getPeakWaterDischarge() != null)
			sPeakWaterDischarge = this.getPeakWaterDischarge().toString();

		if (this.getPeakWaterInspire() != null)
			sPeakWaterInpire = this.getPeakWaterInspire();

		if (this.getPeakWaterTimeSeries() != null)
			sPeakWaterTimeSeries = this.getPeakWaterTimeSeries();

		if (this.getFloodHeight() != null)
			sFloodHeight = this.getFloodHeight().toString();

		if (this.getFloodHeightInspire() != null)
			sFloodHeightInspire = this.getFloodHeightInspire();

		if (this.getFloodHeightTimeSeries() != null)
			sFloodHeightTimeSeries = this.getFloodHeightTimeSeries();

		if (this.getReporedCasualtiesNumber() != null)
			sReporedCasualtiesNumber = this.getReporedCasualtiesNumber().toString();

		if (this.getReporedCasualtiesDescription() != null)
			sReporedCasualtiesDescription = this.getReporedCasualtiesDescription();

		if (this.getReporedCasualtiesInspire() != null)
			sReporedCasualtiesInspire = this.getReporedCasualtiesInspire();

		if (this.getReporedCasualtiesTimeSeries() != null)
			sReporedCasualtiesTimeSeries = this.getReporedCasualtiesTimeSeries();

		if (this.getDamageToBuildingsDescription() != null)
			sDamageToBuildingsDescription = this.getDamageToBuildingsDescription();

		if (this.getDamageToBuildingsCost() != null)
			sDamageToBuildingsCost = this.getDamageToBuildingsCost().toString();

		if (this.getDamageToBuildingsInspire() != null)
			sDamageToBuildingsInspire = this.getDamageToBuildingsInspire();

		if (this.getDamageToBuildingsTimeSeries() != null)
			sDamageToBuildingsTimeSeries = this.getDamageToBuildingsTimeSeries();

		if (this.getCostDetail() != null)
			sCostDetail = this.getCostDetail().toString();

		if (this.getDescriptionOfMeasure() != null)
			sDescriptionOfMeasure = this.getDescriptionOfMeasure();

		String sRecord = 
				sCountryName + ";" +
						sStartDate + ";" +
						sStartHour + ";" +
						sDescription + ";" +
						sUnit + ";" +
						sUnitValue + ";" +
						sUnitType + ";" + 
						sWaveHeightType + ";" +
						sWaveHeightValue + ";" +
						sWaveHeightTimeSeries + ";" +
						sWaveDirectionType + ";" +
						sWaveDirectionValue + ";" + 
						sWaveDirectionInspire + ";" + 
						sWaveDirectionTimeSeries + ";" +
						sWindIntensityType + ";" + 
						sWindIntensityValue + ";" + 
						sWindIntensityInspire + ";" + 
						sWindIntensityTimeSeries + ";" + 
						sWindDirectionType + ";" + 
						sWindDirectionValue + ";" + 
						sWindDirectionInspire + ";" + 
						sWindDirectionTimeSeries + ";" + 
						sWaterLevelType + ";" + 
						sWaterLevelValue + ";" + 
						sWaterLevelInspire + ";" + 
						sWaterLevelTimeSeries + ";" + 
						sPeakWaterDischarge + ";" + 
						sPeakWaterInpire + ";" + 
						sPeakWaterTimeSeries + ";" + 
						sFloodHeight + ";" + 
						sFloodHeightInspire + ";" + 
						sFloodHeightTimeSeries + ";" +
						sReporedCasualtiesNumber + ";" + 
						sReporedCasualtiesDescription + ";" +
						sReporedCasualtiesInspire + ";" + 
						sReporedCasualtiesTimeSeries + ";" + 
						sDamageToBuildingsDescription + ";" +
						sDamageToBuildingsCost + ";" + 
						sDamageToBuildingsInspire + ";" +
						sDamageToBuildingsTimeSeries + "" +
						sCostDetail + "" +
						sDescriptionOfMeasure + "";
		oList.add(sRecord);

		InputStream oInputStream;
		try {
			oInputStream = new SequenceInputStream(asStream(oList.iterator()));
			return oInputStream;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Enumeration<InputStream> asStream(final Iterator<String> it) {
		return new Enumeration<InputStream>() {

			@Override
			public boolean hasMoreElements() {
				return it.hasNext();
			}

			@Override
			public InputStream nextElement() {
				try {
					return new ByteArrayInputStream(it.next().getBytes("UTF-8"));
				} catch (UnsupportedEncodingException ex) {
					throw new RuntimeException(ex);
				}
			}

		};
	} 

}
