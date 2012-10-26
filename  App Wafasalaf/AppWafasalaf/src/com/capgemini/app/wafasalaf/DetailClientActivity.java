package com.capgemini.app.wafasalaf;

import java.sql.SQLException;
import java.util.List;

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
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class DetailClientActivity extends Activity {
	
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_client);
        
        tx_responsable = (TextView)findViewById(R.id.tx_responsable);
        listView = (ListView) findViewById(R.id.listview_detail_client);
        SectionedAdapter = new SectionedAdapter(this);
        recouvert = (Recouvrement) getIntent().getSerializableExtra(ListeClientActivity.BUNDLE_RECOUVERT);
        
        dataHelper = DatabaseHelper.getInstance(this);
        try {
			clientDao = dataHelper.getDao(Client.class);
			QueryBuilder<Client, ?> queryBuilder = clientDao.queryBuilder();
			queryBuilder.where().eq(Client.COLUMN_NAME_CLIENT_ID, recouvert.getClientId());
			PreparedQuery<Client> preparedQuery = queryBuilder.prepare();
			List<Client> listClient = clientDao.query(preparedQuery);
			
			if(listClient.size() > 0){
				Client client = listClient.get(0);
				
				tx_responsable.setText(client.getResponsable());
				adapterClient = new  DetailClientAdapter(this, DetailClientCellView.LIST_COLUMN_INDENTIFICATION_DU_CLIENT, 7, client);
				adapterConjoint = new  DetailClientAdapter(this, DetailClientCellView.LIST_COLUMN_INDENTIFICATION_DU_CONJOINT, 2, client);
				adapterBien = new  DetailClientAdapter(this, DetailClientCellView.LIST_COLUMN_INDENTIFICATION_DU_BIEN, 1, client);
				adapterHistorique = new  DetailClientAdapter(this, DetailClientCellView.LIST_COLUMN_HISTORIQUE_CLIENT, 1, client);
				adapterDonnee = new  DetailClientAdapter(this, DetailClientCellView.LIST_COLUMN_DONNEE_TECHNIQUE, 4, client);
				adapterPret = new  DetailClientAdapter(this, DetailClientCellView.LIST_COLUMN_CARACTESTIQUES_PRET, 7, client);
				
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detail_client, menu);
        return true;
    }
}
