package com.alstom.lean.all.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ComponentLevel3 extends ModelObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1035560059857442496L;
	
	@DatabaseField
	private String name;
	@DatabaseField
	private String CP3Id;
	@DatabaseField
	private String CP2Id;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCP3Id() {
		return CP3Id;
	}
	public void setCP3Id(String cP3Id) {
		CP3Id = cP3Id;
	}
	public String getCP2Id() {
		return CP2Id;
	}
	public void setCP2Id(String cP2Id) {
		CP2Id = cP2Id;
	}
	
	
	

}
