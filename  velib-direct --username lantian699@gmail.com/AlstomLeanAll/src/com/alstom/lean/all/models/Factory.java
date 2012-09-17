package com.alstom.lean.all.models;


import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

public class Factory extends ModelObject{

	private static final long serialVersionUID = 428207367869150152L;
	
	private double latitude;
	private double longitude;
	private String name;
	private String address;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
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
