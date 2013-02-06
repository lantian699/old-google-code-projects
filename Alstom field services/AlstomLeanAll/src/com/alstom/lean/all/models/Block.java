package com.alstom.lean.all.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Block extends ModelObject{

	private static final long serialVersionUID = -1690125103937575237L;

	public static final String TABLE_BLOCK_COLUMN_PLANT_ID = "plantId";
	
	@DatabaseField
	private String name;
	@DatabaseField
	private String blockId;
	@DatabaseField
	private String plantId;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBlockId() {
		return blockId;
	}
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	public String getPlantId() {
		return plantId;
	}
	public void setPlantId(String plantId) {
		this.plantId = plantId;
	}
	
	

}
