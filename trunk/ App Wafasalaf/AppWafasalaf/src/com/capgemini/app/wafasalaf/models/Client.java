package com.capgemini.app.wafasalaf.models;

import com.j256.ormlite.field.DatabaseField;

public class Client extends ModelObjet{

	private static final long serialVersionUID = -8789139643740998562L;

	public static final String COLUMN_NAME_CLIENT_ID = "nAffaire";
	
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private String responsable;
	@DatabaseField
	private String nAffaire;
	@DatabaseField
	private String produit;
	@DatabaseField
	private String nom;
	@DatabaseField
	private String adresse;
	@DatabaseField
	private String gsm;
	@DatabaseField
	private String tel;
	@DatabaseField
	private String employeur;
	@DatabaseField
	private String adressePro;



	@DatabaseField
	private String nomConjoint;
	@DatabaseField
	private String employeurConjoint;

	@DatabaseField
	private String typeBien;
	@DatabaseField
	private String nbDossierVivant;

	@DatabaseField
	private String montantPret;
	@DatabaseField
	private String mensualite;
	@DatabaseField
	private String agence;
	@DatabaseField
	private String codeBanque;

	@DatabaseField
	private String nbEcheance;
	@DatabaseField
	private String premierEch;
	@DatabaseField
	private String dernierEch;
	@DatabaseField
	private String montantCRD;
	@DatabaseField
	private String nbIncident;
	@DatabaseField
	private String nbImpayes;
	@DatabaseField
	private String montantChaqueImpayes;
	@DatabaseField
	private String montantImapye;
	
	
	
	
	public String getMontantImapye() {
		return montantImapye;
	}
	public void setMontantImapye(String montantImapye) {
		this.montantImapye = montantImapye;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public String getnAffaire() {
		return nAffaire;
	}
	public void setnAffaire(String nAffaire) {
		this.nAffaire = nAffaire;
	}
	public String getProduit() {
		return produit;
	}
	public void setProduit(String produit) {
		this.produit = produit;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getGsm() {
		return gsm;
	}
	public void setGsm(String gsm) {
		this.gsm = gsm;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmployeur() {
		return employeur;
	}
	public void setEmployeur(String employeur) {
		this.employeur = employeur;
	}
	public String getAdressePro() {
		return adressePro;
	}
	public void setAdressePro(String adressePro) {
		this.adressePro = adressePro;
	}
	public String getNomConjoint() {
		return nomConjoint;
	}
	public void setNomConjoint(String nomConjoint) {
		this.nomConjoint = nomConjoint;
	}
	public String getEmployeurConjoint() {
		return employeurConjoint;
	}
	public void setEmployeurConjoint(String employeurConjoint) {
		this.employeurConjoint = employeurConjoint;
	}
	public String getTypeBien() {
		return typeBien;
	}
	public void setTypeBien(String typeBien) {
		this.typeBien = typeBien;
	}
	public String getNbDossierVivant() {
		return nbDossierVivant;
	}
	public void setNbDossierVivant(String nbDossierVivant) {
		this.nbDossierVivant = nbDossierVivant;
	}
	public String getMontantPret() {
		return montantPret;
	}
	public void setMontantPret(String montantPret) {
		this.montantPret = montantPret;
	}
	public String getMensualite() {
		return mensualite;
	}
	public void setMensualite(String mensualite) {
		this.mensualite = mensualite;
	}
	public String getAgence() {
		return agence;
	}
	public void setAgence(String agence) {
		this.agence = agence;
	}
	public String getCodeBanque() {
		return codeBanque;
	}
	public void setCodeBanque(String codeBanque) {
		this.codeBanque = codeBanque;
	}
	public String getNbEcheance() {
		return nbEcheance;
	}
	public void setNbEcheance(String nbEcheance) {
		this.nbEcheance = nbEcheance;
	}
	public String getPremierEch() {
		return premierEch;
	}
	public void setPremierEch(String premierEch) {
		this.premierEch = premierEch;
	}
	public String getDernierEch() {
		return dernierEch;
	}
	public void setDernierEch(String dernierEch) {
		this.dernierEch = dernierEch;
	}
	public String getMontantCRD() {
		return montantCRD;
	}
	public void setMontantCRD(String montantCRD) {
		this.montantCRD = montantCRD;
	}
	public String getNbIncident() {
		return nbIncident;
	}
	public void setNbIncident(String nbIncident) {
		this.nbIncident = nbIncident;
	}
	public String getNbImpayes() {
		return nbImpayes;
	}
	public void setNbImpayes(String nbImpayes) {
		this.nbImpayes = nbImpayes;
	}
	public String getMontantChaqueImpayes() {
		return montantChaqueImpayes;
	}
	public void setMontantChaqueImpayes(String montantChaqueImpayes) {
		this.montantChaqueImpayes = montantChaqueImpayes;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
