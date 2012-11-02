package com.capgemini.app.wafasalaf.fragments;

import java.sql.SQLException;
import java.util.List;

import com.capgemini.app.wafasalaf.ListClientActivity;
import com.capgemini.app.wafasalaf.R;
import com.capgemini.app.wafasalaf.adapters.DetailClientAdapter;
import com.capgemini.app.wafasalaf.adapters.SectionedAdapter;
import com.capgemini.app.wafasalaf.models.Client;
import com.capgemini.app.wafasalaf.models.DatabaseHelper;
import com.capgemini.app.wafasalaf.models.Recouvrement;
import com.capgemini.app.wafasalaf.view.DetailClientCellView;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class DetailClientFragment extends Fragment{

	private TextView tx_responsable;
	private DatabaseHelper dataHelper;
	private Dao<Client, ?> clientDao;
	private String clientId;
	private Recouvrement recouvert;
	private DetailClientAdapter adapterClient;
	private DetailClientAdapter adapterConjoint;
	private DetailClientAdapter adapterBien;
	private DetailClientAdapter adapterHistorique;
	private DetailClientAdapter adapterPret;
	private DetailClientAdapter adapterDonnee;
	private ListView listView;
	private SectionedAdapter SectionedAdapter;
	private TextView tx_nom_client;
	private TextView tx_montant_impaye;
	
	
	public DetailClientFragment(Recouvrement recouvert){
		this.recouvert = recouvert;
	}

	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	 
		 View view = inflater.inflate(R.layout.activity_detail_client, container, false);
		 
		 tx_nom_client = (TextView)view.findViewById(R.id.tx_nom_client);
		 tx_montant_impaye = (TextView)view.findViewById(R.id.tx_montant_impaye);
		  tx_responsable = (TextView)view.findViewById(R.id.tx_responsable);
	      listView = (ListView) view.findViewById(R.id.listview_detail_client);
	      
	        SectionedAdapter = new SectionedAdapter(getActivity(), null);
//	        recouvert = (Recouvrement) context.getIntent().getSerializableExtra(ListeClientActivity.BUNDLE_RECOUVERT);
	        
	        dataHelper = DatabaseHelper.getInstance(getActivity());
	        try {
				clientDao = dataHelper.getDao(Client.class);
				QueryBuilder<Client, ?> queryBuilder = clientDao.queryBuilder();
				queryBuilder.where().eq(Client.COLUMN_NAME_CLIENT_ID, recouvert.getClientId());
				PreparedQuery<Client> preparedQuery = queryBuilder.prepare();
				List<Client> listClient = clientDao.query(preparedQuery);
				
				if(listClient.size() > 0){
					Client client = listClient.get(0);
					
					tx_nom_client.setText(client.getNom());
					tx_montant_impaye.setText(client.getMontantImapye());
					tx_responsable.setText(client.getResponsable());
					adapterClient = new  DetailClientAdapter(getActivity(), DetailClientCellView.LIST_COLUMN_INDENTIFICATION_DU_CLIENT, 7, client);
					adapterConjoint = new  DetailClientAdapter(getActivity(), DetailClientCellView.LIST_COLUMN_INDENTIFICATION_DU_CONJOINT, 2, client);
					adapterBien = new  DetailClientAdapter(getActivity(), DetailClientCellView.LIST_COLUMN_INDENTIFICATION_DU_BIEN, 1, client);
					adapterHistorique = new  DetailClientAdapter(getActivity(), DetailClientCellView.LIST_COLUMN_HISTORIQUE_CLIENT, 1, client);
					adapterDonnee = new  DetailClientAdapter(getActivity(), DetailClientCellView.LIST_COLUMN_DONNEE_TECHNIQUE, 4, client);
					adapterPret = new  DetailClientAdapter(getActivity(), DetailClientCellView.LIST_COLUMN_CARACTESTIQUES_PRET, 7, client);
					
					SectionedAdapter.addSection(DetailClientCellView.LIST_COLUMN_INDENTIFICATION_DU_CLIENT, adapterClient);
					SectionedAdapter.addSection(DetailClientCellView.LIST_COLUMN_INDENTIFICATION_DU_CONJOINT, adapterConjoint);
					SectionedAdapter.addSection(DetailClientCellView.LIST_COLUMN_INDENTIFICATION_DU_BIEN, adapterBien);
					SectionedAdapter.addSection(DetailClientCellView.LIST_COLUMN_HISTORIQUE_CLIENT, adapterHistorique);
					SectionedAdapter.addSection(DetailClientCellView.LIST_COLUMN_DONNEE_TECHNIQUE, adapterDonnee);
					SectionedAdapter.addSection(DetailClientCellView.LIST_COLUMN_CARACTESTIQUES_PRET, adapterPret);
					
					listView.setAdapter(SectionedAdapter);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 return view;
	 }
}
