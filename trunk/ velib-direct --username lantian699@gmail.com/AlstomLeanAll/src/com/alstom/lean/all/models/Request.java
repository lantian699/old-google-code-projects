package com.alstom.lean.all.models;


import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

public class Request extends ModelObject{
	
	private String numbre;
	private String title;
	private String description;
	private String incidentClientNumber;
	private String deadLine;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6842748563231704100L;

	public String getNumbre() {
		return numbre;
	}

	public void setNumbre(String numbre) {
		this.numbre = numbre;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIncidentClientNumber() {
		return incidentClientNumber;
	}

	public void setIncidentClientNumber(String incidentClientNumber) {
		this.incidentClientNumber = incidentClientNumber;
	}

	public String getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(String deadLine) {
		this.deadLine = deadLine;
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
