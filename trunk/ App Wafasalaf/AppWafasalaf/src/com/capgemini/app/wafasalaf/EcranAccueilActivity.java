package com.capgemini.app.wafasalaf;

import java.sql.SQLException;
import java.util.List;

import com.capgemini.app.wafasalaf.models.DatabaseHelper;
import com.capgemini.app.wafasalaf.models.Recouvrement;
import com.capgemini.app.wafasalaf.spreadsheet.SynchronizationTask;
import com.j256.ormlite.dao.Dao;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class EcranAccueilActivity extends Activity {

    private DatabaseHelper dataHelper;
	private Dao<Recouvrement, ?> recouvertDao;
	private List<Recouvrement> listRecouvert;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_accueil);
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        
        dataHelper = DatabaseHelper.getInstance(this);
        try {
			recouvertDao  = dataHelper.getDao(Recouvrement.class);
			listRecouvert = recouvertDao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(listRecouvert.size() == 0)
        new SynchronizationTask(this, DatabaseHelper.getInstance(this)).execute("getAll");
		else {
			startActivity(new Intent(this, ListeClientActivity.class));
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_ecran_accueil, menu);
        return true;
    }
}
