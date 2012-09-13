package com.alstom.power.sep.fragments;

import com.alstom.power.sep.R;
import com.alstom.power.sep.adapters.AssistanceRequestAdapter;
import com.alstom.power.sep.adapters.SectionedAdapter;
import com.alstom.power.sep.adapters.TreatmentDateAdapter;
import com.alstom.power.sep.adapters.TreatmentFlashageAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class TreatmentSectionFragment extends Fragment {
	
	private ListView listTreatment;
	private SectionedAdapter sectionedAdapter;
	private String nomSeparator;
	private TreatmentDateAdapter  treatmentDateAdapter;
	private TreatmentFlashageAdapter treatmentFlashageAdapter;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    
    	sectionedAdapter = new SectionedAdapter(getActivity());
    	
    	nomSeparator = "Treatment";
    	
    	treatmentDateAdapter = new TreatmentDateAdapter(getActivity(), 2);
    	treatmentFlashageAdapter = new TreatmentFlashageAdapter(getActivity(), 1);
    	
    	
    	
    	sectionedAdapter.addSection(nomSeparator, treatmentDateAdapter);
    	sectionedAdapter.addSection("Flashing et photo", treatmentFlashageAdapter);
    	
    	
    }
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		  View view = inflater.inflate(R.layout.fragment_detail, container, false);
		  listTreatment = (ListView) view.findViewById(R.id.list_ent_detail);
		  listTreatment.setAdapter(sectionedAdapter);
		 return view;
    }

}
