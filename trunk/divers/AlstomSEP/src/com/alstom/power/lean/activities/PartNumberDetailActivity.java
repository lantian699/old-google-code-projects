package com.alstom.power.lean.activities;

import java.util.ArrayList;

import com.alstom.power.lean.R;
import com.alstom.power.lean.model3d.Model3DTurbineActivity;
import com.alstom.power.lean.pdfviewer.PdfViewerActivity;
import com.alstom.power.lean.views.TreatmentFlashageCellView;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PartNumberDetailActivity extends ListActivity{

	private String partNumber;
	private ArrayAdapter<String> listPartAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		partNumber = getIntent().getStringExtra(TreatmentFlashageCellView.EXTRA_NAME_PART_NUMBER);
		
		listPartAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData());
		
		setListAdapter(listPartAdapter);
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		switch (position) {
		
			
		case 2:
			Uri uri = Uri.parse("file:///sdcard/Download/2140ESI_8P_Post_BTS-DUT_BD.pdf");
	        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
	        intent.setClass(this, PdfViewerActivity.class);
	        startActivity(intent);
			
			break;
			
		case 3:
			Intent in = new Intent();
			in.setClass(this, Model3DTurbineActivity.class);
			startActivity(in);
			
			break;

		default:
			break;
		}
		
		
	}
	
	private ArrayList<String> getData(){
		
		ArrayList<String> listPartNumber = new ArrayList<String>();
		listPartNumber.add("Description : " +partNumber);
		listPartNumber.add("Doc");
		listPartNumber.add("2D Plan");
		listPartNumber.add("3D Plan");
		listPartNumber.add("Start time : 2012/09/17 12:00");
		listPartNumber.add("End time : 2012/09/19 13:00");
		
		return listPartNumber;
		
	}
}
