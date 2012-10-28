package com.capgemini.app.wafasalaf.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Impaye extends ModelObjet{


	private static final long serialVersionUID = -7641979932813984165L;

	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private String montant;
	@DatabaseField
	private String motif;
	@DatabaseField
	private String client;
	@DatabaseField
	private String dateEch;
	@DatabaseField
	private String dateRejet;
	
	
	
	public String getMontant() {
		return montant;
	}
	public void setMontant(String montant) {
		this.montant = montant;
	}
	public String getMotif() {
		return motif;
	}
	public void setMotif(String motif) {
		this.motif = motif;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getDateEch() {
		return dateEch;
	}
	public void setDateEch(String dateEch) {
		this.dateEch = dateEch;
	}
	public String getDateRejet() {
		return dateRejet;
	}
	public void setDateRejet(String dateRejet) {
		this.dateRejet = dateRejet;
	}
	
	
	
	
}
