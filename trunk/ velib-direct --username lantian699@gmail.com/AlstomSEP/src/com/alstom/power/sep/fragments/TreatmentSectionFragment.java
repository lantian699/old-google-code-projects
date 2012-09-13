package com.alstom.power.sep.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alstom.power.sep.R;
import com.alstom.power.sep.adapters.SectionedAdapter;
import com.alstom.power.sep.adapters.TreatmentDateAdapter;
import com.alstom.power.sep.adapters.TreatmentFlashageAdapter;
import com.alstom.power.sep.views.TreatmentDateCellView;
import com.alstom.power.sep.views.TreatmentFlashageCellView;

public class TreatmentSectionFragment extends Fragment {
	
	private ListView listTreatment;
	private SectionedAdapter sectionedAdapter;
	private TreatmentDateAdapter  treatmentDateAdapter;
	private TreatmentFlashageAdapter treatmentFlashageAdapter;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    
    	sectionedAdapter = new SectionedAdapter(getActivity());
    	treatmentDateAdapter = new TreatmentDateAdapter(getActivity(), 2);
    	treatmentFlashageAdapter = new TreatmentFlashageAdapter(getActivity(), 2);
    	
    	
    	
    	sectionedAdapter.addSection(getString(R.string.title_treatment), treatmentDateAdapter);
    	sectionedAdapter.addSection(getString(R.string.title_flash), treatmentFlashageAdapter);
    	
    	
    }
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		  View view = inflater.inflate(R.layout.fragment_detail, container, false);
		  listTreatment = (ListView) view.findViewById(R.id.list_ent_detail);
		  listTreatment.setAdapter(sectionedAdapter);
		 return view;
    }

	
	
	
}
