package com.capgemini.app.wafasalaf;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.app.wafasalaf.adapters.ListClientAdapter;
import com.capgemini.app.wafasalaf.adapters.SectionedAdapter;
import com.capgemini.app.wafasalaf.models.DatabaseHelper;
import com.capgemini.app.wafasalaf.models.Recouvrement;
import com.j256.ormlite.dao.Dao;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListeClientActivity extends Activity implements OnItemClickListener{
	
	public static final String BUNDLE_RECOUVERT = "bundle_recouvert";
	
	private ListView listView;
	private List<Recouvrement> listClient;
	private List<Recouvrement> listClientEnCours;
	private List<Recouvrement> listClientTermine;
	private DatabaseHelper dataHelper;
	private Dao<Recouvrement, ?> listClientDao;
	private SectionedAdapter sectionAdapter;
	private ListClientAdapter enCoursAdapter;
	private ListClientAdapter termineAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_client);
        
        listView = (ListView)findViewById(R.id.listview_client);
        sectionAdapter = new SectionedAdapter(this);
        dataHelper = DatabaseHelper.getInstance(this);
        
        try {
			listClientDao = dataHelper.getDao(Recouvrement.class);
			listClient = listClientDao.queryForAll();
			listClientEnCours = new ArrayList<Recouvrement>();
			listClientTermine = new ArrayList<Recouvrement>();
			
			for (Recouvrement recouvert : listClient) {
				
				if(recouvert.getStatut().equals("en cours")){
					listClientEnCours.add(recouvert);
				}else if(recouvert.getStatut().equals("termine")) {
					listClientTermine.add(recouvert);
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		enCoursAdapter = new ListClientAdapter(this, listClientEnCours);
		termineAdapter = new ListClientAdapter(this, listClientTermine);
		sectionAdapter.addSection("VISITE EN COURS", enCoursAdapter);
		sectionAdapter.addSection("VISITE TERMINEE", termineAdapter);
		
		listView.setAdapter(sectionAdapter);
		listView.setOnItemClickListener(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
		

		Recouvrement recouvert  = (Recouvrement) listView.getItemAtPosition(position);
		Intent intent = new Intent();
		intent.setClass(this, DetailClientRecouvrementActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(BUNDLE_RECOUVERT, recouvert);
		startActivity(intent);
		
	}
}
