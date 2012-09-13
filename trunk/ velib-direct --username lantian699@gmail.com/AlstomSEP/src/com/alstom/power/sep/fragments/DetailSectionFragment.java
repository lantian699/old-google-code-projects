package com.alstom.power.sep.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alstom.power.sep.R;
import com.alstom.power.sep.adapters.DetailSectionAdapter;
import com.alstom.power.sep.adapters.SectionedAdapter;
import com.alstom.power.sep.models.Enterprise;
import com.alstom.power.sep.models.Project;


public class DetailSectionFragment extends Fragment{

    public static final String ARG_SECTION_NUMBER = "section_number";
    private SectionedAdapter sectionedAdapter;
    private DetailSectionAdapter detailSectionAdapter;
	private String nomSeparator;
	private Enterprise enterprise;
	private Project project;
	private ListView listDetail;
    
    public DetailSectionFragment() {
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    
    	sectionedAdapter = new SectionedAdapter(getActivity());
    	simulationEnterprise();
    	nomSeparator = "Enterprise Detail";
    	
    	detailSectionAdapter = new DetailSectionAdapter(getActivity(), enterprise, project, nomSeparator);
    	
    	
    	sectionedAdapter.addSection(nomSeparator, detailSectionAdapter);
    	
    	
    	
    }
	
	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		  View view = inflater.inflate(R.layout.fragment_detail, container, false);
		  listDetail = (ListView) view.findViewById(R.id.list_ent_detail);
		  listDetail.setAdapter(sectionedAdapter);
		 return view;
     }

	 private void simulationEnterprise(){
		 
		 enterprise = new Enterprise();
		 enterprise.setName("Alstom");
		 enterprise.setAddress("12 Rue alma paris");
		 enterprise.setGeoCordinate("12.345555 34.4455544");
		 enterprise.setTelephone("06 72 82 72 24");
		 enterprise.setContactName("Jean vincent");
		 enterprise.setContactEmail("dede@de.ceo");
		 
		 
		 
	 }
	 
}
