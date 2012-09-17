package com.alstom.lean.all.models;


import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

public class Task extends ModelObject{
	
	private static final long serialVersionUID = 743718237794767050L;
	
	private String label;
	private String name;
	private String date;
	private boolean isMandatory;
	private boolean isValidated;
	private boolean isProcessing;
	

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public boolean isValidated() {
		return isValidated;
	}

	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}
	

	public boolean isProcessing() {
		return isProcessing;
	}

	public void setProcessing(boolean isProcessing) {
		this.isProcessing = isProcessing;
	}

	@Override
	public void loadIdFromNode(Node node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Class<? extends ModelObject>, List<Node>> loadFromNode(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
