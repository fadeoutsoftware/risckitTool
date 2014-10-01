package it.fadeout.risckit.viewmodels;

public class EventByCountryViewModel {
	
	
	private Integer id;
	
	private String countryName;
	
	private String countryCode;
	
	private Integer eventsCount;
	
	private String lat;
	
	private String lon;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public Integer getEventsCount() {
		return eventsCount;
	}

	public void setEventsCount(Integer eventsCount) {
		this.eventsCount = eventsCount;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
}
