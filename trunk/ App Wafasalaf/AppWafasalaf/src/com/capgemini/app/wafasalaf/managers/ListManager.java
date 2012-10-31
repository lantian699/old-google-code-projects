package com.capgemini.app.wafasalaf.managers;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.capgemini.app.wafasalaf.models.ModelObjet;

import android.os.Handler;

public class ListManager {
	
	private List<ChangeObserver> observers = new CopyOnWriteArrayList<ChangeObserver>();
	private List<ChangeDisplayObserver> observersDisplay = new CopyOnWriteArrayList<ChangeDisplayObserver>();
	private List<ChangeMapObserver> observersMap = new CopyOnWriteArrayList<ChangeMapObserver>();
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
	
	public void registerMapChangeObserver(ChangeMapObserver observer) {
		synchronized (observersMap) {
			observersMap.add(observer);
		}
	}
	
	
	public void notifyMapChange(final ModelObjet objet) {
		
		synchronized (observersMap) {
			for (final ChangeMapObserver observer : observersMap) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						observer.onChange(objet);						
					}
				});
			}
		}
		}
	
	

	public void registerListDisplayChangeObserver(ChangeDisplayObserver observer) {
		synchronized (observersDisplay) {
			observersDisplay.add(observer);
		}
	}
	
	
	public void notifyListDisplayChange(final boolean isDisplay, final int num) {
		
		synchronized (observersDisplay) {
			for (final ChangeDisplayObserver observer : observersDisplay) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						observer.onChange(isDisplay, num);						
					}
				});
			}
		}
	
	
}
	
	

}
