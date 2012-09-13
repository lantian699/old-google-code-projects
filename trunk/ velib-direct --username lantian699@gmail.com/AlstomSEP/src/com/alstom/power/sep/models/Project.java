package com.alstom.power.sep.models;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

public class Project extends ModelObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2166171668458005335L;
	private String name;
	private String type;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
