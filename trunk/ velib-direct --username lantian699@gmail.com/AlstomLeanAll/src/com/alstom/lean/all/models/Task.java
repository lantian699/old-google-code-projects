package com.alstom.lean.all.models;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

public class Task extends ModelObject{
	
	private static final long serialVersionUID = 743718237794767050L;

	private String name;
	private File recordSheet;
	private String workInstruction;
	private String begin;
	private String end;
	private String status;
	private boolean requiresWitnessPoint;
	private ArrayList<Task> listSubTasks;
	
	
	
	public ArrayList<Task> getListSubTasks() {
		return listSubTasks;
	}
	public void setListSubTasks(ArrayList<Task> listSubTasks) {
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
