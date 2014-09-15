package it.fadeout.risckit.viewmodels;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class MediaViewModel {
	
	private Integer id;
	
	private String lat;
	
	private String lon;
	
	private String downloadPath;
	
	private int eventId;
	
	private String date;
	
	private String description;
	
	private String shortDownloadPath;
	
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

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String filesAttach) {
		downloadPath = filesAttach;
	}

	public String getDate() {
		/*
		try {
			if (date != null)
				return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return date;
	}

	public void setDate(String m_odate) {
		this.date = m_odate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShortDownloadPath() {

		if (downloadPath != null)
		{
			String[] sSplitString = downloadPath.split("/");
			if (sSplitString != null && sSplitString.length > 0)
			{
				shortDownloadPath = sSplitString[sSplitString.length - 1];
			}
			else
				shortDownloadPath = downloadPath;
		}	
		return shortDownloadPath;
	}

	public void setShortDownloadPath(String shortDownloadPath) {
		this.shortDownloadPath = shortDownloadPath;
	}
		
}
