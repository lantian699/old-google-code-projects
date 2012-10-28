package com.capgemini.app.wafasalaf.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capgemini.app.wafasalaf.LocationActivity;
import com.capgemini.app.wafasalaf.R;
import com.capgemini.app.wafasalaf.models.Client;
import com.capgemini.app.wafasalaf.models.Recouvrement;

public class DetailClientCellView extends LinearLayout{
	
	
	public static final String LIST_COLUMN_INDENTIFICATION_DU_CLIENT="identificantion du client";
	public static final String LIST_COLUMN_INDENTIFICATION_DU_CONJOINT="identificantion du conjoint";
	public static final String LIST_COLUMN_INDENTIFICATION_DU_BIEN="identificantion du bien";
	public static final String LIST_COLUMN_HISTORIQUE_CLIENT="Historique client";
	public static final String LIST_COLUMN_DONNEE_TECHNIQUE="Donnees techniques";
	public static final String LIST_COLUMN_CARACTESTIQUES_PRET="Caractestique pret";
	
	private Context context;
	private TextView cellName;
	private TextView cellNameValue;


	public DetailClientCellView(Context context) {
		super(context);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.detail_client_cell_view, this);
	      
	    this.context = context;
	    
	    cellName = (TextView)findViewById(R.id.tx_cell_name);
	    cellNameValue = (TextView)findViewById(R.id.tx_cell_name_value);
	    
	    
	}
	
	
	public void setData(final Client client, String type, int position){
		
		if(type.equals(LIST_COLUMN_INDENTIFICATION_DU_CLIENT)){
			
			switch (position) {
			case 0:
				cellName.setText("Produit/N. Affaire : ");
				cellNameValue.setText(client.getProduit() + "   " +client.getnAffaire());
				break;
			case 1:
				cellName.setText("Nom Complet : ");
				cellNameValue.setText(client.getNom());
				break;
			case 2:
				cellName.setText("Adresse : ");
				cellNameValue.setText(client.getAdresse());
				this.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						Intent intent = new Intent();
						intent.setClass(context, LocationActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						context.startActivity(intent);
					}
				});
				break;
			case 3:
				cellName.setText("GSM : ");
				cellNameValue.setText(client.getGsm());
				this.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ client.getGsm()));
						context.startActivity(intent);
					}
				});
				
				break;
			case 4:
				cellName.setText("Tel. Domicile : ");
				cellNameValue.setText(client.getTel());
				
				this.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ client.getTel()));
						context.startActivity(intent);
					}
				});
				break;
			case 5:
				cellName.setText("Employeur : ");
				cellNameValue.setText(client.getEmployeur());
				break;
			case 6:
				cellName.setText("Adresse Professionnelle :  ");
				cellNameValue.setText(client.getAdressePro());
				break;
			default:
				break;
			}
			
		}else if(type.equals(LIST_COLUMN_INDENTIFICATION_DU_CONJOINT)){
			
			switch (position) {
			case 0:
				cellName.setText("Nom Complet : ");
				cellNameValue.setText(client.getNomConjoint());
				break;
				
			case 1:
				cellName.setText("Employeur : ");
				cellNameValue.setText(client.getEmployeurConjoint());
				break;
				

			default:
				break;
			}
		}else if(type.equals(LIST_COLUMN_INDENTIFICATION_DU_BIEN)){
			
			switch (position) {
			case 0:
				cellName.setText("Type de bien : ");
				cellNameValue.setText(client.getTypeBien());
				break;
				
		
			default:
				break;
			}
		}else if(type.equals(LIST_COLUMN_HISTORIQUE_CLIENT)){
			
			switch (position) {
			case 0:
				cellName.setText("Nbr. de dossiers vivants : ");
				cellNameValue.setText(client.getNbDossierVivant());
				break;
				
		
			default:
				break;
			}
		}else if(type.equals(LIST_COLUMN_DONNEE_TECHNIQUE)){
			
			switch (position) {
			case 0:
				cellName.setText("Motant du pret : ");
				cellNameValue.setText(String.valueOf(client.getMontantPret()));
				break;
			case 1:
				cellName.setText("Mensualité : ");
				cellNameValue.setText(String.valueOf(client.getMensualite()));
				break;
			case 2:
				cellName.setText("Banque/Agence : ");
				cellNameValue.setText(String.valueOf(client.getCodeBanque()) + "   " + String.valueOf(client.getAgence()));
				break;
			case 3:
				cellName.setText("Code Banque : ");
				cellNameValue.setText(String.valueOf(client.getCodeBanque()));
				break;
		
			default:
				break;
			}
		}else if(type.equals(LIST_COLUMN_CARACTESTIQUES_PRET)){
			
			switch (position) {
			case 0:
				cellName.setText("Nb. d'échéance: ");
				cellNameValue.setText(String.valueOf(client.getNbEcheance()));
				break;
			case 1:
				cellName.setText("1er Echéance : ");
				cellNameValue.setText(String.valueOf(client.getPremierEch()));
				break;
			case 2:
				cellName.setText("Dernière Echéance: ");
				cellNameValue.setText(client.getDernierEch());
				break;
			case 3:
				cellName.setText("Montant CRD : ");
				cellNameValue.setText(String.valueOf(client.getMontantCRD()));
				break;
			case 4:
				cellName.setText("Nbr. Incident: ");
				cellNameValue.setText(String.valueOf(client.getNbIncident()));
				break;
			case 5:
				cellName.setText("Nbr. Impayés: ");
				cellNameValue.setText(String.valueOf(client.getNbImpayes()));
				break;
			case 6:
				cellName.setText("Montant chaques Impayés: ");
				cellNameValue.setText(String.valueOf(client.getMontantChaqueImpayes()));
				break;
		
			default:
				break;
			}
		}
		
	}


}
