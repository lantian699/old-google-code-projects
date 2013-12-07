package com.iubiquity.spreadsheets.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class Nat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6150519839047301486L;
	
	
	
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private String cameraId;
	@DatabaseField
	private String protocole;
	@DatabaseField
	private String type;
	@DatabaseField
	private String externalPort;
	@DatabaseField
	private String destIP;
	@DatabaseField
	private String destPort;
	@DatabaseField
	private String nicVendor;
	@DatabaseField
	private String deviceType;
	@DatabaseField
	private String sid;
	
	
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getNicVendor() {
		return nicVendor;
	}
	public void setNicVendor(String nicVendor) {
		this.nicVendor = nicVendor;
	}
	public String getCameraId() {
		return cameraId;
	}
	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}
	public String getProtocole() {
		return protocole;
	}
	public void setProtocole(String protocole) {
		this.protocole = protocole;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExternalPort() {
		return externalPort;
	}
	public void setExternalPort(String externalPort) {
		this.externalPort = externalPort;
	}
	public String getDestIP() {
		return destIP;
	}
	public void setDestIP(String destIP) {
		this.destIP = destIP;
	}
	public String getDestPort() {
		return destPort;
	}
	public void setDestPort(String destPort) {
		this.destPort = destPort;
	}
	
	
	

}
