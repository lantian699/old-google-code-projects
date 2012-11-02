package com.capgemini.app.wafasalaf;

import java.sql.SQLException;
import java.util.List;

import com.capgemini.app.wafasalaf.adapters.DialogListAdapter;
import com.capgemini.app.wafasalaf.models.Client;
import com.capgemini.app.wafasalaf.models.DatabaseHelper;
import com.capgemini.app.wafasalaf.models.Impaye;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;

public class CustomDialog extends Dialog {
	 private ListView listView;
	private DatabaseHelper dataHelper;
	private DialogListAdapter adapter;
	private Dao<Impaye, ?> impayeDao;
	private List<Impaye> listImpaye;
	private Context context;
	private Client client;
	private Button buttonYes;

	public CustomDialog(Context context, Client client) {
	  super(context);
	  
	  this.context = context;
	  this.client = client;
	  
	 }
	 
	  protected void onCreate(Bundle savedInstanceState){
	   super.onCreate(savedInstanceState);
	   
	   setContentView(R.layout.custom_dialog);
	   setTitle("DETAIL IMPAYES");
	   
	   
	   dataHelper = DatabaseHelper.getInstance(context);
	   
	   try {
		impayeDao = dataHelper.getDao(Impaye.class);
		QueryBuilder<Impaye, ?> queryBuilder = impayeDao.queryBuilder();
		queryBuilder.where().eq(Impaye.IMPAYE_COLUMN_NAME_CLIENT_ID, client.getnAffaire());
		PreparedQuery<Impaye> preparedQuery = queryBuilder.prepare();
		listImpaye = impayeDao.query(preparedQuery);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		listView = (ListView) findViewById(R.id.listView_dialog);
		adapter = new DialogListAdapter(context, listImpaye);
		listView.setAdapter(adapter);
		buttonYes = (Button) findViewById(R.id.button_yes);
		buttonYes.setHeight(5);
		buttonYes.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();

			}
		});

	}
}
