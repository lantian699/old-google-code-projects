package com.alstom.power.sep.models;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

public class Enterprise extends ModelObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2915339580236671516L;
	private String name;
	private String address;
	private String geoCordinate;
	private String telephone;
	private String contactName;
	private String contactEmail;
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
	public String getGeoCordinate() {
		return geoCordinate;
	}
	public void setGeoCordinate(String geoCordinate) {
		this.geoCordinate = geoCordinate;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
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
