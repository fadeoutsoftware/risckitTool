package it.fadeout.risckit.business;

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
	private int m_iId;
	
	@Column(name="eventid")
	private int m_iEventId;
	
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

	public int getId() {
		return m_iId;
	}

	public void setId(int m_iId) {
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

	public int getEventId() {
		return m_iEventId;
	}

	public void setEventId(int m_iEventId) {
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
	
}
