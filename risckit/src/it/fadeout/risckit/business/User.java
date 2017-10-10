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

	@Column(name="usersurname")
	private String m_sUserSurname;

	@Column(name="role")
	private String m_sRole;

	@Column(name="address")
	private String m_sAddress;

	@Column(name="institutionname")
	private String m_sInstitutionName;

	@Column(name="state")
	private String m_sState;

	@Column(name="phonenumber")
	private String m_sPhonenumber;

	@Column(name="reason")
	private String m_sReason;

	@Column(name="isconfirmed")
	private Boolean m_bIsConfirmed;

	@Column(name="email")
	private String m_sEmail;
	
	@Column(name="firstname")
	private String m_sFirstName;
	
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

	public String getRole() {
		return m_sRole;
	}

	public void setRole(String m_sRole) {
		this.m_sRole = m_sRole;
	}

	public String getAddress() {
		return m_sAddress;
	}

	public void setAddress(String m_sAddress) {
		this.m_sAddress = m_sAddress;
	}

	public String getInstitutionName() {
		return m_sInstitutionName;
	}

	public void setInstitutionName(String m_sInstitutionName) {
		this.m_sInstitutionName = m_sInstitutionName;
	}

	public String getState() {
		return m_sState;
	}

	public void setState(String m_sState) {
		this.m_sState = m_sState;
	}

	public String getPhonenumber() {
		return m_sPhonenumber;
	}

	public void setPhonenumber(String m_sPhonenumber) {
		this.m_sPhonenumber = m_sPhonenumber;
	}

	public String getReason() {
		return m_sReason;
	}

	public void setReason(String m_sReason) {
		this.m_sReason = m_sReason;
	}

	public Boolean getIsConfirmed() {
		return m_bIsConfirmed;
	}

	public void setIsConfirmed(Boolean m_bIsConfirmed) {
		this.m_bIsConfirmed = m_bIsConfirmed;
	}

	public String getEmail() {
		return m_sEmail;
	}

	public void setEmail(String m_sEmail) {
		this.m_sEmail = m_sEmail;
	}

	public String getUserSurname() {
		return m_sUserSurname;
	}

	public void setUserSurname(String m_sUserSurname) {
		this.m_sUserSurname = m_sUserSurname;
	}

	public String getFirstName() {
		return m_sFirstName;
	}

	public void setFirstName(String m_sFirstName) {
		this.m_sFirstName = m_sFirstName;
	}
	
}
