package com.alstom.lean.all.models;

import java.io.File;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Finding extends ModelObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4138123572906575267L;

	@DatabaseField
	private String title;
	@DatabaseField
	private String findingId;
	@DatabaseField
	private String type;
	@DatabaseField
	private String description;
	@DatabaseField
	private File attachment;
	@DatabaseField
	private String status;
	@DatabaseField
	private boolean requiresWitnessPoint;

	
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFindingId() {
		return findingId;
	}
	public void setFindingId(String findingId) {
		this.findingId = findingId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public File getAttachment() {
		return attachment;
	}
	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isRequiresWitnessPoint() {
		return requiresWitnessPoint;
	}
	public void setRequiresWitnessPoint(boolean requiresWitnessPoint) {
		this.requiresWitnessPoint = requiresWitnessPoint;
	}
	
	
	
}
