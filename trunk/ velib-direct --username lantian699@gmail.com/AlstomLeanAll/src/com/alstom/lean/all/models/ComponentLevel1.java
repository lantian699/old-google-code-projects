package com.alstom.lean.all.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ComponentLevel1 extends ModelObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3825674904197361530L;

	
	@DatabaseField
	private String name;
	@DatabaseField
	private String CP1Id;
	@DatabaseField
	private String systemId;
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCP1Id() {
		return CP1Id;
	}
	public void setCP1Id(String cP1Id) {
		CP1Id = cP1Id;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	
	
}
