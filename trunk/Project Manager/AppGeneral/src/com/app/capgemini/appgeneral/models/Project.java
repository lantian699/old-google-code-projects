package com.app.capgemini.appgeneral.models;

import com.j256.ormlite.field.DatabaseField;

public class Project extends ModelObjet{

	private static final long serialVersionUID = -8789139643740998562L;

	public static final String COLUMN_NAME_PROJECT_ID = "projectId";
	
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private String projectId;
	@DatabaseField
	private String name;
	@DatabaseField
	private String startDate;
	@DatabaseField
	private String endDate;
	@DatabaseField
	private String location;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
	
	
	
	
	
	
	
}
