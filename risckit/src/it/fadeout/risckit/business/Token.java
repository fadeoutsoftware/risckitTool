package it.fadeout.risckit.business;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="risckit.tokens")
@XmlRootElement

public class Token {
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer m_iId;
	
	@Column(name="userid")
	private Integer m_iUserId;
	
	@Column(name="token")
	private String m_sToken;
	
	@Column(name="timestamp")
	private long m_sTimestamp;
	
	public Integer getId() {
		return m_iId;
	}

	public void setId(Integer m_iId) {
		this.m_iId = m_iId;
	}

	public Integer getUserId() {
		return m_iUserId;
	}

	public void setUserId(Integer m_iUserId) {
		this.m_iUserId = m_iUserId;
	}

	public String getToken() {
		return m_sToken;
	}

	public void setToken(String m_sToken) {
		this.m_sToken = m_sToken;
	}

	public long getTimestamp() {
		return m_sTimestamp;
	}

	public void setTimestamp(long l) {
		this.m_sTimestamp = l;
	}
	
	
}
