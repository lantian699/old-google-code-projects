package com.alstom.lean.all.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Unit extends ModelObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4410068852945636321L;

	public static final String TABLE_UNIT_COLUMN_BLOCK_ID = "blockId";

	@DatabaseField
	private String name;
	@DatabaseField
	private String untiId;
	@DatabaseField
	private String blockId;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUntiId() {
		return untiId;
	}
	public void setUntiId(String untiId) {
		this.untiId = untiId;
	}
	public String getBlockId() {
		return blockId;
	}
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	
	

}
