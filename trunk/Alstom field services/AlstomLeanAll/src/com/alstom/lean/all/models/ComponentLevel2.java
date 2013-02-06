package com.alstom.lean.all.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ComponentLevel2 extends ModelObject{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2314006107689102463L;
	
	@DatabaseField
	private String name;
	@DatabaseField
	private String CP2Id;
	@DatabaseField
	private String CP1Id;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCP2Id() {
		return CP2Id;
	}
	public void setCP2Id(String cP2Id) {
		CP2Id = cP2Id;
	}
	public String getCP1Id() {
		return CP1Id;
	}
	public void setCP1Id(String cP1Id) {
		CP1Id = cP1Id;
	}
	
	
	
}
