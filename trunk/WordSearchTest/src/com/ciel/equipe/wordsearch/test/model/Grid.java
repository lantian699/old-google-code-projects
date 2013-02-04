package com.ciel.equipe.wordsearch.test.model;


import java.util.ArrayList;
import java.util.Random;

import com.ciel.equipe.wordsearch.test.dictionnary.IDictionary;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Grid implements Parcelable{

	
	private static final String TAG = "Grid";
	private static Character[][] gridMatrix;
	private int size;
	private Character[] gridInternals;
	private static Random random = new Random();

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	
	private Grid(Integer size) {
		// Log.v(LOG_TAG, "Grid created size: "+size);
		this.size = size;
		this.gridInternals = new Character[size * size];
	}
	
	public static Grid generateGrid(IDictionary dic, int maxWords,
			int minLength, int size) {

		Grid grid = new Grid(size);
		ArrayList<String> selectedList = new ArrayList<String>();
		
		for(int i=0; i<maxWords; i++){
			String word = dic.getNextWord(minLength, size);
			selectedList.add(word);
		}
		
		Log.i(TAG, "Grid generated !"+ selectedList.size() + " words selected");
		System.out.println("selectedList = " + selectedList);
		
		gridMatrix = new Character [size][size];
		for(String word : selectedList){
			
			gridMatrix[random.nextInt(size)][random.nextInt(size)] = word.charAt(random.nextInt(word.length()));
			
		}
//		for(int i=0; i<10;i++)
//			for(int y=0; y<10;y++)
//		System.out.println("grid matrix = " + gridMatrix[i][y]);
		return grid;
	}
	
	
	public void insertWord(String word){
		
		
		
	}
	

	
	

}
