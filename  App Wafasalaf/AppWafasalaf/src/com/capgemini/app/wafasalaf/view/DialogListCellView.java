package com.capgemini.app.wafasalaf.view;

import com.capgemini.app.wafasalaf.R;
import com.capgemini.app.wafasalaf.models.Impaye;
import com.google.common.base.CaseFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogListCellView extends LinearLayout{

	
	private TextView tx_date_ech;
	private TextView tx_date_rejet;
	private TextView tx_montant;
	private TextView tx_motif;

	public DialogListCellView(Context context) {
		super(context);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.custom_dialog_cell_view, this);
	    
	    tx_date_ech = (TextView)findViewById(R.id.tx_date_ech);
	    tx_date_rejet = (TextView)findViewById(R.id.tx_date_rejet);
	    tx_montant = (TextView)findViewById(R.id.tx_detail_montant);
	    tx_motif = (TextView)findViewById(R.id.tx_detail_motif);
	    
	}
	
	
	public void setData(Impaye impaye, int position){
		
		
		switch (position) {
		case 0:
			
			tx_date_ech.setText("DATE ECH");
			tx_montant.setText("MONTANT");
			tx_date_rejet.setText("DATE REJET");
			tx_motif.setText("MOTIF");
			
			break;

		default:
			
			tx_date_ech.setText(impaye.getDateEch());
			tx_montant.setText(impaye.getMontant());
			tx_date_rejet.setText(impaye.getDateRejet());
			tx_motif.setText(impaye.getMotif());
			
			break;
		}
		
	}
	
	
	
}
