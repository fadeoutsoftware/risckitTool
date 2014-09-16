package it.fadeout.risckit.viewmodels;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GisViewModel {
	
	private Integer id;
	
	private String shortGisFile;
	
	private String shortInspireFile;
	
	private int eventId;
	
	private String downloadGisPath;
	
	private String downloadInspirePath;
	
	public String getShortGisFile() {
		if (downloadGisPath != null)
		{
			String[] sSplitString = downloadGisPath.split("/");
			if (sSplitString != null && sSplitString.length > 0)
			{
				shortGisFile = sSplitString[sSplitString.length - 1];
			}
			else
				shortGisFile = downloadGisPath;
		}	
		return shortGisFile;
		
	}

	public void setShortGisFile(String gisFile) {
		shortGisFile = gisFile;
	}

	public String getShortInspireFile() {
		if (downloadInspirePath != null)
		{
			String[] sSplitString = downloadInspirePath.split("/");
			if (sSplitString != null && sSplitString.length > 0)
			{
				shortInspireFile = sSplitString[sSplitString.length - 1];
			}
			else
				shortInspireFile = downloadInspirePath;
		}	
		return shortInspireFile;
	}

	public void setShortInspireFile(String inspireFile) {
		shortInspireFile = inspireFile;
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

	public String getDownloadGisPath() {
		return downloadGisPath;
	}

	public void setDownloadGisPath(String downloadGisPath) {
		this.downloadGisPath = downloadGisPath;
	}

	public String getDownloadInspirePath() {
		return downloadInspirePath;
	}

	public void setDownloadInspirePath(String downloadInspirePath) {
		this.downloadInspirePath = downloadInspirePath;
	}
	
}
