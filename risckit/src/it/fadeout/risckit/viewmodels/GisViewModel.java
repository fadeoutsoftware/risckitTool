package it.fadeout.risckit.viewmodels;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GisViewModel {
	
	private Integer id;
	
	private String GisFile;
	
	private String InspireFile;
	
	private int eventId;

	public String getGisFile() {
		return GisFile;
	}

	public void setGisFile(String gisFile) {
		GisFile = gisFile;
	}

	public String getInspireFile() {
		return InspireFile;
	}

	public void setInspireFile(String inspireFile) {
		InspireFile = inspireFile;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
