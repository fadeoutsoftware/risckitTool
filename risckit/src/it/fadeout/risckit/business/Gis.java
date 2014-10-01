package it.fadeout.risckit.business;

import it.fadeout.risckit.viewmodels.GisViewModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="risckit.gis")
@XmlRootElement
public class Gis {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer m_iId;
	
	@Column(name="eventid")
	private Integer m_iEventId;
	
	@Column(name="gisfile")
	private String m_sGisFile;
	
	@Column(name="inspirefile")
	private String m_sInspireFile;

	public String getGisFile() {
		return m_sGisFile;
	}

	public void setGisFile(String m_sGisFile) {
		this.m_sGisFile = m_sGisFile;
	}

	public String getInspireFile() {
		return m_sInspireFile;
	}

	public void setInspireFile(String m_sInspireFile) {
		this.m_sInspireFile = m_sInspireFile;
	}

	public Integer getEventId() {
		return m_iEventId;
	}

	public void setEventId(Integer m_iEventId) {
		this.m_iEventId = m_iEventId;
	}

	public Integer getId() {
		return m_iId;
	}

	public void setId(Integer m_iId) {
		this.m_iId = m_iId;
	}
	
	public void setEntity(GisViewModel oGisViewModel)
	{
		this.setEventId(oGisViewModel.getEventId());
		this.setGisFile(oGisViewModel.getDownloadGisPath());
		this.setInspireFile(oGisViewModel.getDownloadInspirePath());
	}
	
	public GisViewModel getViewModel()
	{
		GisViewModel oViewModel = new GisViewModel();
		oViewModel.setId(this.getId());
		oViewModel.setEventId(this.getEventId());
		oViewModel.setDownloadGisPath(this.getGisFile());
		oViewModel.setDownloadInspirePath(this.getInspireFile());
		
		return oViewModel;
	}

}
