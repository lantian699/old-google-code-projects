package com.alstom.power.lean.adapters;


import java.util.LinkedHashMap;
import java.util.Map;

import com.alstom.power.lean.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;



/*
 * L'adapter permet d'afficher une liste avec des sections
 *  
 */

public class SectionedAdapter extends BaseAdapter {

	  
	    public final Map<String,Adapter> sections = new LinkedHashMap<String,Adapter>();  
	    public ArrayAdapter<String> headers;  
	    public final static int TYPE_SECTION_HEADER = 0;  
	    
	    public SectionedAdapter(Context context) {  
	        headers = new ArrayAdapter<String>(context, R.layout.listseparator);  

	    }  
	  
	 
	  
	    /**
	     * Add a new section to the separated list adapter
	     * @param section the section title to add
	     * @param adapter the child adapter for this section
	     */
	    public void addSection(String section, Adapter adapter) {  
	    	
	    	this.headers.add(section);  
	    		    	
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
	            if(position == 0) {
	            	View v = headers.getView(sectionnum, convertView, parent);  
	            		return v;
	           }
	            if(position < size) return adapter.getView(position - 1, convertView, parent);  
	  
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

