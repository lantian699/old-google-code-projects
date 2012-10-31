package com.capgemini.app.wafasalaf.adapters;



import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.capgemini.app.wafasalaf.R;
import com.capgemini.app.wafasalaf.managers.ListManager;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;



/*
 * L'adapter permet d'afficher une liste avec des sections
 *  
 */

public class SectionedAdapter extends BaseAdapter {

	  
	    public final Map<String,Adapter> sections = new LinkedHashMap<String,Adapter>();  
//	    public ArrayAdapter<String> headers;
		private ListManager manager;
	    public final static int TYPE_SECTION_HEADER = 0;
		protected static final String LIST_STATUTS_OPEN = "open";  
		protected static final String LIST_STATUTS_CLOSE = "close";
		private boolean isDisplay = false;
		private ListSeparatorAdapter header;
		private ArrayList<ListSeparatorAdapter> headers;
		private Context context;
		
		public SectionedAdapter(Context context) {  
//			headers = new ArrayAdapter<String>(context, R.layout.listseparator);
			header = new ListSeparatorAdapter(context);
	    }  
		
	    public SectionedAdapter(Context context, ListManager manager) { 
	    	this.context = context;
//	        headers = new ArrayAdapter<String>(context, R.layout.listseparator);  
//	        headers = new ListSeparatorAdapter(context);
	    	headers = new ArrayList<ListSeparatorAdapter>();
	        this.manager = manager;
	    }  

	    /**
	     * Add a new section to the separated list adapter
	     * @param section the section title to add
	     * @param adapter the child adapter for this section
	     */
	    public void addSection(String section, Adapter adapter) {  
	    	header = new ListSeparatorAdapter(context);
	    	if(adapter instanceof ListClientAdapter)
	    	this.header.add(section,((ListClientAdapter)adapter).getRealCount());
	    	else
	    		this.header.add(section,adapter.getCount());
	    	headers.add(header);
	    	  	    	
	        this.sections.put(section, adapter);  
	        
	    }  
	    

	
	    
	    // to correctly find the selected item among the child Adapters, we walk through subtracting
	    // from the original position until we find either a header (position = 0) or item in the
	    // current child Adapter (position < size).
	    
	    public Object getItem(int position) {  
	        for(String section : this.sections.keySet()) {  
	            Adapter adapter = sections.get(section);  
	            // size = number of items in the section, including header (+1)
	            int size = adapter.getCount() + 1; 
	  
	            // check if position inside this section  
	            if(position == 0) return section;  
	            if(position < size) return adapter.getItem(position - 1);  
	  
	            // otherwise jump into next section  
	            position -= size;  
	        }  
	        return null;  
	    }  
	    
	    public int getCount() {  
	        // total together all sections, plus one for each section header  
	        int total = 0;  
	        for(Adapter adapter : this.sections.values())  
	            total += adapter.getCount() + 1;  
	        return total;  
	    }  
	  
	    @Override
	    public int getViewTypeCount() {  
	        // assume that headers count as one, then total all sections  
	        int total = 1;  
	        for(Adapter adapter : this.sections.values())  
	            total += adapter.getViewTypeCount();  

	        return total;  
	    }  
	    
	    @Override
	    public int getItemViewType(int position) {  
	        int type = 1;  
	        for(Object section : this.sections.keySet()) {  
	            Adapter adapter = sections.get(section);  
	            int size = adapter.getCount() + 1;  
	  
	            // check if position inside this section  
	            if(position == 0) {
	         
	            	return TYPE_SECTION_HEADER;  
	            }
	            if(position < size) return type + adapter.getItemViewType(position - 1);  
	  
	            // otherwise jump into next section  
	            position -= size;  
	            type += adapter.getViewTypeCount();  
	        }  
	        return IGNORE_ITEM_VIEW_TYPE;  
	    }  
	  
	    public boolean areAllItemsSelectable() {  
	        return false;  
	    }  
	    
	    @Override
	    public boolean isEnabled(int position) {  
	        return (getItemViewType(position) != TYPE_SECTION_HEADER);  
	    }  
	  
	    public View getView(int position, View convertView, ViewGroup parent) { 
	    	 int sectionnum = 0;
	    	
		
	    	
	        for(String section : this.sections.keySet()) {  
	            Adapter adapter = sections.get(section);  
	            int size = adapter.getCount() + 1;  
	  
	            // check if position inside this section  
			if (position == 0) {
				View v = headers.get(sectionnum).getView(sectionnum, convertView, parent);
				v.setTag(sectionnum);
				v.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						if(manager != null){
							manager.notifyListDisplayChange(!isDisplay, Integer.valueOf(v.getTag().toString()));
							isDisplay = ! isDisplay;
						}
					}
				});
	        		
	            		return v;
	           }
	            if(position < size)
	            	return adapter.getView(position - 1, convertView, parent);  
	  
	            // otherwise jump into next section  
	            position -= size;  
	            sectionnum++;  
	        }  
	        return null;  
	    }  
	  
	    public long getItemId(int position) {  
	        for(String section : this.sections.keySet()) {  
	            Adapter adapter = sections.get(section);  
	            // size = number of items in the section, including header (+1)
	            int size = adapter.getCount() + 1; 
	  
	            // check if position inside this section  
	            if(position == 0)
	            	return TYPE_SECTION_HEADER;  
	            if(position < size)
	            	return adapter.getItemId(position - 1);  
	  
	            // otherwise jump into next section  
	            position -= size;  
	        }  
	        return -1;  
	    } 
	  
	}  

