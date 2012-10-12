package com.alstom.lean.all.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Person extends ModelObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1512557016880721771L;

	public static final String TABLE_PERSON_COLUMN_PARENT = "parent";

	@DatabaseField
	private String name;
	@DatabaseField
	private String profile;
	@DatabaseField
	private String telNumber;
	@DatabaseField
	private String email;
	@DatabaseField
	private String parent;
	
	
	
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
