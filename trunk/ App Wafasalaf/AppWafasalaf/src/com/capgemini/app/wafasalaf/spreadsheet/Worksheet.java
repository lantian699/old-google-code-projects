package com.capgemini.app.wafasalaf.spreadsheet;

public class Worksheet {
	//Table name
	public static final String TABLE_NAME_IMPAYE = "od6";
	public static final String TABLE_NAME_RECOUVREMENT = "od7";
	public static final String TABLE_NAME_CLIENT = "od4";
/*	public static final String TABLE_NAME_SYSTEM = "od5";
	public static final String TABLE_NAME_CP1 = "oda";
	public static final String TABLE_NAME_CP2 = "odb";
	public static final String TABLE_NAME_CP3 = "od8";
	public static final String TABLE_NAME_PROJECT = "od9";
	public static final String TABLE_NAME_PERSON = "ocy";
	public static final String TABLE_NAME_TASK = "ocz";
	public static final String TABLE_NAME_VISUALINSPECTION = "ocw";
	public static final String TABLE_NAME_MESUREMENT = "ocx";*/
	
	//RECOUVREMENT
	public static final String TABLE_RECOUVREMENT_COLUMN_CLIENT_ID = "clientid";
	public static final String TABLE_RECOUVREMENT_COLUMN_DATE_VISITE = "datevisitepro";
	public static final String TABLE_RECOUVREMENT_COLUMN_DATE_ARRIVE = "datearrive";
	public static final String TABLE_RECOUVREMENT_COLUMN_STATUT = "statut";
	public static final String TABLE_RECOUVREMENT_COLUMN_DATE_DEPART = "datedepart";
	
	//CLIENT
	public static final String TABLE_CLIENT_COLUMN_RESPONSABLE = "responsable";
	public static final String TABLE_CLIENT_COLUMN_PRODUIT = "produit";
	public static final String TABLE_CLIENT_COLUMN_NUMERO_AFFAIRE = "numeroaffaire";
	public static final String TABLE_CLIENT_COLUMN_NOM_COMPLET = "nomcomplet";
	public static final String TABLE_CLIENT_COLUMN_ADRESSE= "adresse";
	public static final String TABLE_CLIENT_COLUMN_GSM = "gsm";
	public static final String TABLE_CLIENT_COLUMN_TEL_DOMICILE = "teldomicile";
	public static final String TABLE_CLIENT_COLUMN_EMPLOYEUR = "employeur";
	public static final String TABLE_CLIENT_COLUMN_ADRESSE_PROFESSIONNELLE = "adresseprofessionnelle";
	public static final String TABLE_CLIENT_COLUMN_NOM_CONJOINT = "nomconjoint";
	public static final String TABLE_CLIENT_COLUMN_EMPLOYEUR_CONJOINT = "employeurconjoint";
	public static final String TABLE_CLIENT_COLUMN_TYPE_BIEN = "typedebien";
	public static final String TABLE_CLIENT_COLUMN_NOMBRE_DOSSIER = "nombrededossiervivant";
	public static final String TABLE_CLIENT_COLUMN_MONTANT_PRET = "montantdupret";
	public static final String TABLE_CLIENT_COLUMN_MENSUALITE = "mensualite";
	public static final String TABLE_CLIENT_COLUMN_CODE_BANQUE = "codebanque";
	public static final String TABLE_CLIENT_COLUMN_AGENCE = "agence";
	public static final String TABLE_CLIENT_COLUMN_NOMBRE_ECHEANCE = "nombreecheance";
	public static final String TABLE_CLIENT_COLUMN_PREMIER_ECHEANCE = "premierecheance";
	public static final String TABLE_CLIENT_COLUMN_DERNIER_ECHEANCE = "dernierecheance";
	public static final String TABLE_CLIENT_COLUMN_MONTANT_CRD = "montantcrd";
	public static final String TABLE_CLIENT_COLUMN_NOMBRE_INCIDENT = "nombreincident";
	public static final String TABLE_CLIENT_COLUMN_NOMBRE_IMPAYES = "nombreimpayes";
	public static final String TABLE_CLIENT_COLUMN_MONTANT_CHAQUES_IMPAYES = "montantchaquesimpayes";
	public static final Object TABLE_CLIENT_COLUMN_MONTANT_IMPAYE = "montantimpaye";
	//IMPAYES
	
	public static final String TABLE_IMPAYE_COLUMN_DATE_ECHEANCE = "dateecheance";
	public static final String TABLE_IMPAYE_COLUMN_DATE_REJET = "daterejet";
	public static final String TABLE_IMPAYE_COLUMN_MONTANT = "montant";
	public static final String TABLE_IMPAYE_COLUMN_MOTIF = "motif";
	public static final String TABLE_IMPAYE_COLUMN_CLIENT_ID = "clientid";
	
	

	
	
	
	// Worksheets (tabs)
	public enum Table{
		IMPAYE{
		    public String toString() {
		        return "od6";
		    }    
		}, 
		RECOUVREMENT{
		    public String toString() {
		        return "od7";
		    }
		}, 
		CLIENT{
		    public String toString() {
		        return "od4";
		    }
		}/*,
		SYSTEM{
		    public String toString() {
		        return "od5";
		    }
		},
		CP1{
		    public String toString() {
		        return "oda";
		    }
		},
		CP2{
		    public String toString() {
		        return "odb";
		    }
		},
		CP3{
		    public String toString() {
		        return "od8";
		    }
		},
		PROJECT{
		    public String toString() {
		        return "od9";
		    }
		},
		
		PERSON{
		    public String toString() {
		        return "ocy";
		    }
		},
		
		TASK{
		    public String toString() {
		        return "ocz";
		    }
		}
		,
		VISUALINSPECTION{
		    public String toString() {
		        return "ocw";
		    }
		},
		MESUREMENT{
		    public String toString() {
		        return "ocx";
		    }
		}*/
	}

}
