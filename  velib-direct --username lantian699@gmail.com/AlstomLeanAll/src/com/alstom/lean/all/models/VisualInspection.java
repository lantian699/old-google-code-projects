package com.alstom.lean.all.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class VisualInspection extends ModelObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1915072194977539505L;
	public static final String TABLE_INSPECTION_COLUMN_PARENT = "parent";

	@DatabaseField
	private String description;
	@DatabaseField
	private String key;
	@DatabaseField
	private String value;
	@DatabaseField
	private String attachment;
	@DatabaseField
	private String parent;
	@DatabaseField
	private String selfUrl;
	@DatabaseField
	private String updateTime;
	
	
	
	public String getSelfUrl() {
		return selfUrl;
	}
	public void setSelfUrl(String selfUrl) {
		this.selfUrl = selfUrl;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

}
