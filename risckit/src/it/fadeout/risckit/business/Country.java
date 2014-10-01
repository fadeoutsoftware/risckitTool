package it.fadeout.risckit.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.transaction.Transactional;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name="risckit.countries")
@XmlRootElement
public class Country {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer Id;
	
	@Column(name="nutscode")
	private String NutsCode;
	
	@Column(name="name")
	private String Name;
	
	@Column(name="nutslevel")
	private String NutsLevel;
	
	@Column(name="countrycode")
	private String CountryCode;
	
	@Column(name="lat")
	private String m_sLat;
	
	@Column(name="lon")
	private String m_sLon;
	
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getNutsCode() {
		return NutsCode;
	}

	public void setNutsCode(String nutsCode) {
		NutsCode = nutsCode;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getNutsLevel() {
		return NutsLevel;
	}

	public void setNutsLevel(String nutsLevel) {
		NutsLevel = nutsLevel;
	}

	public String getCountryCode() {
		return CountryCode;
	}

	public void setCountryCode(String countryCode) {
		CountryCode = countryCode;
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

	
}
