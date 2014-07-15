package it.acrotec.rest.api.admin;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserTableRowWithPassword extends UserTableRow {

	String password;

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
