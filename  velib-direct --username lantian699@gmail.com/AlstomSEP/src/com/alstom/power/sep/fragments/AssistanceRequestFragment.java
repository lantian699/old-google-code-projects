package com.alstom.power.sep.fragments;

import com.alstom.power.sep.R;
import com.alstom.power.sep.adapters.AssistanceRequestAdapter;
import com.alstom.power.sep.adapters.DetailSectionAdapter;
import com.alstom.power.sep.adapters.SectionedAdapter;
import com.alstom.power.sep.models.Enterprise;
import com.alstom.power.sep.models.Project;
import com.alstom.power.sep.models.Request;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AssistanceRequestFragment extends Fragment{
	
	 private ListView listRequest;
	 private SectionedAdapter sectionedAdapter;
	 private AssistanceRequestAdapter requestAdapter;
	 private String nomSeparator;
	private Request request;
		
	 
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	    
	    	sectionedAdapter = new SectionedAdapter(getActivity());
	    	
	    	nomSeparator = "Assistance Request";
	    	
	    	simulationRequest();
	    	
	    	requestAdapter = new AssistanceRequestAdapter(getActivity(), request);
	    	
	    	
	    	sectionedAdapter.addSection(nomSeparator, requestAdapter);
	    	
	    	
	    	
	    }
	 
	 
	@Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		  View view = inflater.inflate(R.layout.fragment_detail, container, false);
		  listRequest = (ListView) view.findViewById(R.id.list_ent_detail);
		  listRequest.setAdapter(sectionedAdapter);
		 return view;
     }
	
	public void simulationRequest(){
		request = new Request();
		request.setNumbre("124");
		request.setTitle("Title request 1");
		request.setDescription("It is the description of request 1");
		request.setDeadLine("July 12 12:00");
		request.setIncidentClientNumber("2343");
	}

}
