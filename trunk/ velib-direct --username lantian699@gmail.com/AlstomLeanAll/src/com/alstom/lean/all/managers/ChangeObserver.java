package com.alstom.lean.all.managers;

import com.alstom.lean.all.models.ModelObject;



/**
 * 
 */
public interface ChangeObserver {
	
	public void onChange();
	public void onChange(String res);
	public void onChange(String res, ModelObject model);
}
