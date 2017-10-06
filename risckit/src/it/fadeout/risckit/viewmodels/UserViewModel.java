package it.fadeout.risckit.viewmodels;

public class UserViewModel {
	
	private int id;
	
	private String userName;
	
	private String password;
	
	private Boolean isAdmin;
	
	//New Properties
	private String email;
	private String userSurname;
	private String institutionName;
	private String role;
	private String adresses;
	private String state;
	private String phoneNumber;
	private String reason;
	private Boolean isConfirmed;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	/************ New properties get-set **************/
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(Boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public String getUserSurname() {
		return userSurname;
	}


	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAdresses() {
		return adresses;
	}

	public void setAdresses(String adresses) {
		this.adresses = adresses;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
