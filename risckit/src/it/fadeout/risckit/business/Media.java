package it.fadeout.risckit.business;

import it.fadeout.risckit.viewmodels.MediaViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="risckit.media")
@XmlRootElement
public class Media {

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer m_iId;
	
	@Column(name="eventid")
	private Integer m_iEventId;
	
	@Column(name="file")
	private String m_sFile;
	
	@Column(name="lat")
	private String m_sLat;
	
	@Column(name="lon")
	private String m_sLon;
	
	@Column(name="date")
	private Date m_oDate;
	
	@Column(name="description")
	private String m_sDescription;
	
	@Column(name="thumbnail")
	private String m_sThumbnail;

	public Integer getId() {
		return m_iId;
	}

	public void setId(Integer m_iId) {
		this.m_iId = m_iId;
	}

	public String getFile() {
		return m_sFile;
	}

	public void setFile(String m_sFile) {
		this.m_sFile = m_sFile;
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

	public Integer getEventId() {
		return m_iEventId;
	}

	public void setEventId(Integer m_iEventId) {
		this.m_iEventId = m_iEventId;
	}

	public Date getDate() {
		return m_oDate;
	}

	public void setDate(Date m_oDate) {
		this.m_oDate = m_oDate;
	}

	public String getDescription() {
		return m_sDescription;
	}

	public void setDescription(String m_sDescription) {
		this.m_sDescription = m_sDescription;
	}
	
	public String getThumbnail() {
		return m_sThumbnail;
	}

	public void setThumbnail(String m_sThumbnail) {
		this.m_sThumbnail = m_sThumbnail;
	}
	
	public void setEntity(MediaViewModel oMediaViewModel) throws ParseException
	{
		this.setEventId(oMediaViewModel.getEventId());
		this.setLat(oMediaViewModel.getLat());
		this.setLon(oMediaViewModel.getLon());
		this.setFile(oMediaViewModel.getDownloadPath());
		
		try {
			this.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(oMediaViewModel.getDate()));
		}
		catch(Exception oEx)
		{
			try {
				this.setDate(new SimpleDateFormat("yyyy-dd-MM").parse(oMediaViewModel.getDate()));
			}
			catch(Exception oEx2)
			{
				try {
					this.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(oMediaViewModel.getDate()));
				}
				catch(Exception oEx4)
				{
					try {
						this.setDate(new SimpleDateFormat("MM-dd-yyyy").parse(oMediaViewModel.getDate()));
					}
					catch(Exception oEx5)
					{
						oEx5.printStackTrace();
					}									
				}				
			}
		}
		
		
		this.setDescription(oMediaViewModel.getDescription());
		this.setThumbnail(oMediaViewModel.getThumbnail());
	}
	
	public MediaViewModel getViewModel()
	{
		MediaViewModel oViewModel = new MediaViewModel();
		oViewModel.setId(this.getId());
		oViewModel.setEventId(this.getEventId());
		oViewModel.setLat(this.getLat());
		oViewModel.setLon(this.getLon());
		oViewModel.setDownloadPath(this.getFile());
		if (this.getDate() != null)
		{
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
			String sDate = dateFormatter.format(this.getDate());
			oViewModel.setDate(sDate);
		}
			
		oViewModel.setDescription(this.getDescription());
		oViewModel.setThumbnail(this.getThumbnail());
		return oViewModel;
	}

	
	
}
