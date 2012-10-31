package com.capgemini.app.wafasalaf.view;

import java.sql.SQLException;
import java.util.List;

import com.capgemini.app.wafasalaf.LocationActivity;
import com.capgemini.app.wafasalaf.R;
import com.capgemini.app.wafasalaf.models.Client;
import com.capgemini.app.wafasalaf.models.DatabaseHelper;
import com.capgemini.app.wafasalaf.models.Recouvrement;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListClientCellView extends LinearLayout{

	private Context context;
	
	private TextView nomClient;
	private TextView dateVisite;

	private DatabaseHelper dataHelper;

	private Dao<Client, ?> clientDao;

	private Client client;

	private ImageView btn_phone_call;

	private ImageView btn_location;

	private TextView tx_loc_num;


	public ListClientCellView(final Context context) {
		super(context);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.list_client_cell_view, this);
	      
	    this.context = context;
	    
	    nomClient = (TextView)findViewById(R.id.nom_client);
	    dateVisite = (TextView)findViewById(R.id.date_pro);
	    dataHelper = DatabaseHelper.getInstance(context);
	    btn_phone_call = (ImageView)findViewById(R.id.btn_phone_call);
	    btn_location = (ImageView)findViewById(R.id.btn_location);
	    tx_loc_num = (TextView)findViewById(R.id.tx_loc_num);
	    
	    btn_location.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				intent.setClass(context, LocationActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(intent);
			}
		});
	    
	    
	    btn_phone_call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ client.getGsm()));
				context.startActivity(intent);
			}
		});
	    
	}
	
	
	public void setData(Recouvrement recouvert, int position){
		
		tx_loc_num.setText(String.valueOf(position+1));
		try {
			clientDao = dataHelper.getDao(Client.class);
			QueryBuilder<Client, ?> queryBuilder = clientDao.queryBuilder();
			queryBuilder.where().eq(Client.COLUMN_NAME_CLIENT_ID, recouvert.getClientId());
			PreparedQuery<Client> preparedQuery = queryBuilder.prepare();
			List<Client> listClient = clientDao.query(preparedQuery);
			
			if(listClient.size() > 0){
				client = listClient.get(0);
				
				nomClient.setText(client.getNom());
				dateVisite.setText(recouvert.getDateVisitePro());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
