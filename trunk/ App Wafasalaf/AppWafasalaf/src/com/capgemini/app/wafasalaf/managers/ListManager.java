package com.capgemini.app.wafasalaf.managers;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.capgemini.app.wafasalaf.models.DatabaseHelper;
import com.capgemini.app.wafasalaf.models.ModelObjet;

import android.content.Context;
import android.os.Handler;

public class ListManager {
	
	private static ListManager mInstance;
	private List<ChangeObserver> observers = new CopyOnWriteArrayList<ChangeObserver>();
	private List<ChangeDisplayObserver> observersDisplay = new CopyOnWriteArrayList<ChangeDisplayObserver>();
	private List<ChangeObserver> observersMap = new CopyOnWriteArrayList<ChangeObserver>();
	private Handler mHandler;
	
	
	public ListManager() {
		super();
		mHandler = new Handler();
	}
	
	public static ListManager getInstance() {
		if (mInstance == null) {
			mInstance = new ListManager();

		}
		return mInstance;
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
	
	public void registerMapChangeObserver(ChangeObserver observer) {
		synchronized (observersMap) {
			observersMap.add(observer);
		}
	}
	
	
	public void notifyMapChange() {
		
		synchronized (observersMap) {
			for (final ChangeObserver observer : observersMap) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						observer.onChange();						
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
