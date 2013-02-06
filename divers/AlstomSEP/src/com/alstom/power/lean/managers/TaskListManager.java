package com.alstom.power.lean.managers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import android.os.Handler;

public class TaskListManager {
	
	private List<ChangeObserver> observers = new CopyOnWriteArrayList<ChangeObserver>();
	//private List<ChangeObserver> observersForSearch = new CopyOnWriteArrayList<ChangeObserver>();
	private Handler mHandler;
	
	
	public TaskListManager() {
		super();
		mHandler = new Handler();
	}
	
	public void registerChangeObserver(ChangeObserver observer) {
		synchronized (observers) {
			observers.add(observer);
		}
	}
	
	
	public void notifyChange() {
		
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
	
	
	/*public void registerSearchChangeObserver(ChangeObserver observer) {
		synchronized (observersForSearch) {
			observersForSearch.add(observer);
		}
	}
	
	
	public void notifySearchChange() {
		
		synchronized (observersForSearch) {
			for (final ChangeObserver observer : observersForSearch) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						observer.onChange();						
					}
				});
			}
		}
	}*/

}
