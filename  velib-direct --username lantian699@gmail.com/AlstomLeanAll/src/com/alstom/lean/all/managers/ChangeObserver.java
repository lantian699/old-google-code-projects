package com.alstom.lean.all.managers;



/**
 * 
 */
public interface ChangeObserver {
	public void onChange();
	
	public void onChange(String res);
}
