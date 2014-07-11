package it.fadeout.risckit.viewmodels;

import javax.xml.bind.annotation.XmlRootElement;

import it.fadeout.risckit.business.Country;

@XmlRootElement
public class CountryViewModel {
	
	private int id;
	
	private String countryname;
	
	private String countrycode;

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
	
}
