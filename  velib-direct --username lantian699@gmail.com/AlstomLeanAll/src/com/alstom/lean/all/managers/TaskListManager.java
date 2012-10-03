package com.alstom.lean.all.managers;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import android.os.Handler;

public class TaskListManager {
	
	private List<ChangeObserver> observers = new CopyOnWriteArrayList<ChangeObserver>();
	private List<ChangeObserver> observersForBarcode = new CopyOnWriteArrayList<ChangeObserver>();
	private List<ChangeObserver> observersAddMesure = new CopyOnWriteArrayList<ChangeObserver>();
	private List<ChangeObserver> observersTaskFilter = new CopyOnWriteArrayList<ChangeObserver>();
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
	
	
	public void registerBarcodeChangeObserver(ChangeObserver observer) {
		synchronized (observersForBarcode) {
			observersForBarcode.add(observer);
		}
	}
	
	
	public void notifyBarcodeChange(final String res) {
		
		synchronized (observersForBarcode) {
			for (final ChangeObserver observer : observersForBarcode) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						observer.onChange(res);						
					}
				});
			}
		}
	}
	
	
	public void registerAddMesureChangeObserver(ChangeObserver observer) {
		synchronized (observersAddMesure) {
			observersAddMesure.add(observer);
		}
	}
	
	
	public void notifyAddMesureChange(final String res) {
		
		synchronized (observersAddMesure) {
			for (final ChangeObserver observer : observersAddMesure) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						observer.onChange(res);						
					}
				});
			}
		}
	}
	
	
	public void registerTaskFilterChangeObserver(ChangeObserver observer) {
		synchronized (observersForBarcode) {
			observersForBarcode.add(observer);
		}
	}
	
	
	public void notifyTaskFilterChange(final String res) {
		
		synchronized (observersForBarcode) {
			for (final ChangeObserver observer : observersForBarcode) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						observer.onChange(res);						
					}
				});
			}
		}
	}
	
	
	public void registerRefreshListChangeObserver(ChangeObserver observer) {
		synchronized (observersTaskFilter) {
			observersTaskFilter.add(observer);
		}
	}
	
	
	public void notifyRefreshListChange(final String res) {
		
		synchronized (observersTaskFilter) {
			for (final ChangeObserver observer : observersTaskFilter) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						observer.onChange(res);						
					}
				});
			}
		}
	}

}
