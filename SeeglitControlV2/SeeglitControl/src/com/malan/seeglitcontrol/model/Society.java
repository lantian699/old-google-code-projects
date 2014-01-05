package com.malan.seeglitcontrol.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

public class Society implements Serializable{
	
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private String sid;
	@DatabaseField
	private String name;
	@DatabaseField
	private String uid;
	
	
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	

}
