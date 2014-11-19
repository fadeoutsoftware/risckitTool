package it.fadeout.risckit.viewmodels;

import javax.xml.bind.annotation.XmlRootElement;

import it.fadeout.risckit.business.Country;

@XmlRootElement
public class CountryViewModel {
	
	private int id;
	
	private String countryname;
	
	private String countrycode;
	
	private String lat;
	
	private String lon;

	public int getid() {
		return id;
	}

	public void setid(int m_iId) {
		this.id = m_iId;
	}

	public String getcountryname() {
		return countryname;
	}

	public void setcountryname(String m_sCountryName) {
		this.countryname = m_sCountryName;
	}

	public String getCountryCode() {
		return countrycode;
	}

	public void setCountryCode(String countrycode) {
		this.countrycode = countrycode;
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
	
}
