package com.capgemini.app.wafasalaf.view;

import java.sql.SQLException;
import java.util.List;

import com.capgemini.app.wafasalaf.R;
import com.capgemini.app.wafasalaf.models.Client;
import com.capgemini.app.wafasalaf.models.DatabaseHelper;
import com.capgemini.app.wafasalaf.models.Recouvrement;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListClientCellView extends LinearLayout{

	private Context context;
	
	private TextView nomClient;
	private TextView dateVisite;

	private DatabaseHelper dataHelper;

	private Dao<Client, ?> clientDao;


	public ListClientCellView(Context context) {
		super(context);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.list_client_cell_view, this);
	      
	    this.context = context;
	    
	    nomClient = (TextView)findViewById(R.id.nom_client);
	    dateVisite = (TextView)findViewById(R.id.date_pro);
	    dataHelper = DatabaseHelper.getInstance(context);
	    
	}
	
	
	public void setData(Recouvrement recouvert){
		
		try {
			clientDao = dataHelper.getDao(Client.class);
			QueryBuilder<Client, ?> queryBuilder = clientDao.queryBuilder();
			queryBuilder.where().eq(Client.COLUMN_NAME_CLIENT_ID, recouvert.getClientId());
			PreparedQuery<Client> preparedQuery = queryBuilder.prepare();
			List<Client> listClient = clientDao.query(preparedQuery);
			
			if(listClient.size() > 0){
				Client client = listClient.get(0);
				
				nomClient.setText(client.getNom());
				dateVisite.setText(recouvert.getDateVisitePro());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
