package it.fadeout.risckit.business;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="risckit.eventsbycountries")
@XmlRootElement
public class EventsByCountries {

	@Id
	@Column(name="id")
	private Integer m_iId;
	
	@Column(name="countrycode")
	private String m_sCountryCode;
	
	@Column(name="name")
	private String m_sName;

	@Column(name="count")
	private Integer m_sEventCount;

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

	public Integer getId() {
		return m_iId;
	}

	public void setId(Integer m_iId) {
		this.m_iId = m_iId;
	}

	public String getCountryCode() {
		return m_sCountryCode;
	}

	public void setCountryCode(String m_sCountryCode) {
		this.m_sCountryCode = m_sCountryCode;
	}
	
}
