package it.fadeout.risckit.business;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="risckit.eventsbycountries")
@XmlRootElement
public class EventsByCountries {

	@Id
	@Column(name="idrow")
	private Integer m_RowId;
	
	@Column(name="id")
	private Integer m_iId;
	
	@Column(name="countrycode")
	private String m_sCountryCode;
	
	@Column(name="name")
	private String m_sName;

	@Column(name="result")
	private Integer m_sEventCount;
	
	@Column(name="startdate")
	private Date m_dtStartDate;
	
	
	public String getName() {
		return m_sName;
	}
	
	@Column(name="lat")
	private String m_sLat;
	
	@Column(name="lon")
	private String m_sLon;

	public void setName(String m_sName) {
		this.m_sName = m_sName;
	}

	public Integer getEventCount() {
		return m_sEventCount;
	}

	public void setEventCount(Integer m_sEventCount) {
		this.m_sEventCount = m_sEventCount;
	}

	public String getLat() {
		return m_sLat;
	}

	public void setLat(String m_sLat) {
		this.m_sLat = m_sLat;
	}

	public String getLon() {
		return m_sLon;
	}

	public void setLon(String m_sLon) {
		this.m_sLon = m_sLon;
	}

	public int getId() {
		return m_iId;
	}

	public void setId(int m_iId) {
		this.m_iId = m_iId;
	}

	public String getCountryCode() {
		return m_sCountryCode;
	}

	public void setCountryCode(String m_sCountryCode) {
		this.m_sCountryCode = m_sCountryCode;
	}

	public Date getStartDate() {
		return m_dtStartDate;
	}

	public void setStartDate(Date m_dtStartDate) {
		this.m_dtStartDate = m_dtStartDate;
	}

	public Integer getRowId() {
		return m_RowId;
	}

	public void setRowId(Integer m_RowId) {
		this.m_RowId = m_RowId;
	}
	
}
