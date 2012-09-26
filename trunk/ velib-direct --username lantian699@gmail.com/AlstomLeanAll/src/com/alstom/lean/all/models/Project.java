package com.alstom.lean.all.models;


import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import android.location.Location;

@DatabaseTable
public class Project extends ModelObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2166171668458005335L;
	

	@DatabaseField
	private String name;
	@DatabaseField
	private String type;
	@DatabaseField
	private String location;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	

}
