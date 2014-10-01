package it.fadeout.risckit.viewmodels;

public class EventByRegionViewModel {
	
	private Integer regionId;
	
	private String regionName;
	
	private Integer eventsCount;
	
	private String lat;
	
	private String lon;

	

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
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

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

}
