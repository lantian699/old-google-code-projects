package com.capgemini.app.wafasalaf.managers;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import android.os.Handler;

public class ListManager {
	
	private List<ChangeObserver> observers = new CopyOnWriteArrayList<ChangeObserver>();

	private Handler mHandler;
	
	
	public ListManager() {
		super();
		mHandler = new Handler();
	}
	
	public void registerListChangeObserver(ChangeObserver observer) {
		synchronized (observers) {
			observers.add(observer);
		}
	}
	
	
	public void notifyListChange() {
		
		synchronized (observers) {
			for (final ChangeObserver observer : observers) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						observer.onChange();						
					}
				});
			}
		}
	}
	
	

}
