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
	
	@DatabaseField
	private String name;
	@DatabaseField
	private File recordSheet;
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
	private ArrayList<String> listSubTasks;
	
	
	
	public ArrayList<String> getListSubTasks() {
		return listSubTasks;
	}
	public void setListSubTasks(ArrayList<String> listSubTasks) {
		this.listSubTasks = listSubTasks;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public File getRecordSheet() {
		return recordSheet;
	}
	public void setRecordSheet(File recordSheet) {
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
