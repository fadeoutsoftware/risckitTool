package it.fadeout.risckit.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="risckit.users")
@XmlRootElement
public class User {

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer m_iId;
	
	@Column(name="username")
	private String m_sUserName;
	
	@Column(name="password")
	private String m_sPassword;

	@Column(name="isadmin")
	private Boolean m_bIsAdmin;

	
	public Integer getId() {
		return m_iId;
	}

	public void setId(Integer m_iId) {
		this.m_iId = m_iId;
	}

	@Column(name="username")
	public String getUserName() {
		return m_sUserName;
	}

	public void setUserName(String m_sUserName) {
		this.m_sUserName = m_sUserName;
	}

	@Column(name="password")
	public String getPassword() {
		return m_sPassword;
	}

	public void setPassword(String m_sPassword) {
		this.m_sPassword = m_sPassword;
	}

	public Boolean getIsAdmin() {
		return m_bIsAdmin;
	}

	public void setIsAdmin(Boolean m_bIsAdmin) {
		this.m_bIsAdmin = m_bIsAdmin;
	}
}
