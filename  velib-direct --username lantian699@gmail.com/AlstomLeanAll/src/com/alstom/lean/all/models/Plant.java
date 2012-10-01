package com.alstom.lean.all.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Plant extends ModelObject{

	
	private static final long serialVersionUID = -514139835119095532L;


	public static final String TABLE_PLANT_COLUMN_PROJECT_NAME = "projectName";
	

	@DatabaseField
	private String name;
	@DatabaseField
	private String plantId;
	@DatabaseField
	private String projectName;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlantId() {
		return plantId;
	}
	public void setPlantId(String plantId) {
		this.plantId = plantId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	

}
