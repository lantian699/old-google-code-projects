package com.alstom.lean.all.fragments;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alstom.lean.all.R;
import com.alstom.lean.all.activities.MyProjectModeTabletActivity;
import com.alstom.lean.all.adapters.DetailSectionAdapter;
import com.alstom.lean.all.adapters.PersonAdapter;
import com.alstom.lean.all.adapters.SectionedAdapter;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Enterprise;
import com.alstom.lean.all.models.Factory;
import com.alstom.lean.all.models.Person;
import com.alstom.lean.all.models.Plant;
import com.alstom.lean.all.models.Project;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;



public class DetailSectionFragment extends Fragment{


    private DetailSectionAdapter detailSectionAdapter;
	private Enterprise enterprise;
	private Project project;
	private ListView listDetail;
	private Factory factory;
	private SectionedAdapter sectionedAdapter;
	private DatabaseHelper dataHelper;
	private Dao<Person, ?> personDao;
    
    public DetailSectionFragment(Project project, DatabaseHelper dataHelper) {
    	this.project = project;
    	this.dataHelper = dataHelper;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    
    
    	simulationEnterprise();
    	
    	sectionedAdapter = new SectionedAdapter(getActivity());
    	detailSectionAdapter = new DetailSectionAdapter(getActivity(), enterprise,factory);
    	
    	sectionedAdapter.addSection(getString(R.string.title_detail), detailSectionAdapter);
    	
    	try {
			personDao = dataHelper.getDao(Person.class);
			QueryBuilder<Person, ?> queryBuilder = personDao.queryBuilder();
			queryBuilder.where().eq(Person.TABLE_PERSON_COLUMN_PARENT, project.getName());
			PreparedQuery<Person> preparedQuery = queryBuilder.prepare();
			List<Person> listPerson = personDao.query(preparedQuery);
			
			for (Person person : listPerson) {
				
				PersonAdapter personAdapter = new PersonAdapter(getActivity(), person);
				sectionedAdapter.addSection(person.getProfile(), personAdapter);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    	
    }
	
	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		  View view = inflater.inflate(R.layout.fragment_detail, container, false);
		  listDetail = (ListView) view.findViewById(R.id.list_ent_detail);
		  listDetail.setAdapter(sectionedAdapter);
		 return view;
     }

	 private void simulationEnterprise(){
		 
		 
		 ArrayList<Factory> listFactory = MyProjectModeTabletActivity.simulationFactory();
		 factory = listFactory.get(0);
		 
		 enterprise = new Enterprise();
		 enterprise.setName("Alstom");
		 enterprise.setAddress("12 Rue alma paris");
		 enterprise.setGeoCordinate(factory.getLatitude() + " "+factory.getLongitude() );
		 enterprise.setTelephone("06 72 82 72 24");
		 enterprise.setContactName("Jean vincent");
		 enterprise.setContactEmail("dede@de.ceo");
		 
		 
		 
	 }
	 
}
