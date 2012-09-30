package com.alstom.lean.all.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class Mesurement extends ModelObject{

	
	private static final long serialVersionUID = -1051026845347916111L;
	public static final String TABLE_MESURE_COLUMN_PARENT = "parent";

	@DatabaseField
	private String description;
	@DatabaseField
	private String key;
	@DatabaseField
	private String label;
	@DatabaseField
	private String value;
	@DatabaseField
	private String unit;
	@DatabaseField
	private String attachment;
	@DatabaseField
	private String rule;
	@DatabaseField
	private String selfUrl;
	@DatabaseField
	private String updateTime;
	@DatabaseField
	private String parent;
	

	
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
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
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	
	
	

}
