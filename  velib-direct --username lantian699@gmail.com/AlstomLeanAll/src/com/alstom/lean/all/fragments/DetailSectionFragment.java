package com.alstom.lean.all.fragments;



import java.util.ArrayList;
import java.util.List;

import com.alstom.lean.all.R;
import com.alstom.lean.all.activities.MyProjectActivity;
import com.alstom.lean.all.adapters.DetailSectionAdapter;
import com.alstom.lean.all.adapters.SectionedAdapter;
import com.alstom.lean.all.models.Enterprise;
import com.alstom.lean.all.models.Factory;
import com.alstom.lean.all.models.Project;

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
    
    public DetailSectionFragment() {
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    
    
    	simulationEnterprise();
    	
    	sectionedAdapter = new SectionedAdapter(getActivity());
    	detailSectionAdapter = new DetailSectionAdapter(getActivity(), enterprise,factory);
    	
    	sectionedAdapter.addSection(getString(R.string.title_detail), detailSectionAdapter);
    	
    	
    	
    	
    }
	
	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		  View view = inflater.inflate(R.layout.fragment_detail, container, false);
		  listDetail = (ListView) view.findViewById(R.id.list_ent_detail);
		  listDetail.setAdapter(sectionedAdapter);
		 return view;
     }

	 private void simulationEnterprise(){
		 
		 
		 ArrayList<Factory> listFactory = MyProjectActivity.simulationFactory();
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
