package com.alstom.lean.all.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class VisualInspection extends ModelObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1915072194977539505L;

	@DatabaseField
	private String description;
	@DatabaseField
	private String key;
	@DatabaseField
	private String value;
	@DatabaseField
	private String attachment;
	
	
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
