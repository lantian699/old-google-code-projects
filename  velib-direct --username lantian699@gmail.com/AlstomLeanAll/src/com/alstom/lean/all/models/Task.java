package com.alstom.lean.all.models;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Task extends ModelObject{
	
	private static final long serialVersionUID = 743718237794767050L;
	
	public static final String TABLE_TASK_COLUMN_PARENT_NAME = "parentProject";
	
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String recordSheet;
	@DatabaseField
	private String workInstruction;
	@DatabaseField
	private String begin;
	@DatabaseField
	private String end;
	@DatabaseField
	private String status;
	@DatabaseField
	private boolean requiresWitnessPoint;
	@DatabaseField
	private String type;
	@DatabaseField
	private String parentProject;
	@DatabaseField
	private String selfUrl;
	@DatabaseField
	private String updateTime;
	@DatabaseField
	private String attachment;
	
	
	
	
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getParentProject() {
		return parentProject;
	}
	public void setParentProject(String parentProject) {
		this.parentProject = parentProject;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRecordSheet() {
		return recordSheet;
	}
	public void setRecordSheet(String recordSheet) {
		this.recordSheet = recordSheet;
	}
	public String getWorkInstruction() {
		return workInstruction;
	}
	public void setWorkInstruction(String workInstruction) {
		this.workInstruction = workInstruction;
	}
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
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