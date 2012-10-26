package com.capgemini.app.wafasalaf.models;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Recouvrement extends ModelObjet{

	private static final long serialVersionUID = 2462157631823076092L;
	
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private String clientId;
	@DatabaseField
	private String dateVisitePro;
	@DatabaseField
	private String dateArrive;
	@DatabaseField
	private String statut;
	@DatabaseField
	private String dateDepart;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String client) {
		this.clientId = client;
	}
	public String getDateVisitePro() {
		return dateVisitePro;
	}
	public void setDateVisitePro(String dateVisitePro) {
		this.dateVisitePro = dateVisitePro;
	}
	public String getDateArrive() {
		return dateArrive;
	}
	public void setDateArrive(String dateArrive) {
		this.dateArrive = dateArrive;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public String getDateDepart() {
		return dateDepart;
	}
	public void setDateDepart(String dateDepart) {
		this.dateDepart = dateDepart;
	}
	
	
	
}
