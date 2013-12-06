package com.iubiquity.spreadsheets.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

public class User  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1276838256569284934L;
	
	
	
	@DatabaseField
	private String username;
	@DatabaseField
	private String email;
	@DatabaseField
	private String password;


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
