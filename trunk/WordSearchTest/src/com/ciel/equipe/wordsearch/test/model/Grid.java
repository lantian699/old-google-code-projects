package com.ciel.equipe.wordsearch.test.model;


import java.util.ArrayList;
import java.util.Random;

import com.ciel.equipe.wordsearch.test.dictionnary.IDictionary;

import android.graphics.Point;
import android.graphics.Path.FillType;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ListPopupWindow;

public class Grid implements Parcelable{

	
	private static final String TAG = "Grid";
	public static final int NN = 0;
	public static final int SS = 1;
	public static final int EE = 2;
	public static final int WW = 3;
	public static final int NE = 4;
	public static final int NW = 5;
	public static final int SW = 6;
	public static final int SE = 7;
	

	
	
	
	private static Character[][] gridMatrix;
	private static int size;
	private static Random random;
	private static boolean isAdd = true;
	private static ArrayList<ExistPoint> listPoints = new ArrayList<ExistPoint>();
	private static int numAdd ;
	private static ArrayList<String> selectedList;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	
	private Grid(int size) {
		// Log.v(LOG_TAG, "Grid created size: "+size);
		this.size = size;
		random = new Random();
		
	}
	
	public static Character[][] generateGrid(IDictionary dic, int maxWords,
			int minLength, int size) {

		Grid grid = new Grid(size);
		selectedList = new ArrayList<String>();
		
		for(int i=0; i<maxWords; i++){
			String word = dic.getNextWord(minLength, size/2);
			selectedList.add(word);
		}
		
		Log.i(TAG, "Grid generated !"+ selectedList.size() + " words selected");
//		System.out.println("selectedList = " + selectedList);
		
		gridMatrix = new Character [size][size];
		for(String word : selectedList){
			
			insertWord(word);
			numAdd++;
			Log.i(TAG, word + " added");
			
			
		}
		
		fillEmpty();
	
		return gridMatrix;
	}
	
	public static ArrayList<String> getListWord(){
		return selectedList;
	}
	
	
	public static void insertWord(String word){
		
		
		while (true){
			int direction = random.nextInt(7);
//			Log.i(TAG, "direction  = " + direction);
			listPoints.clear();
			Point center = new Point(random.nextInt(size), random.nextInt(size));
	
			
			
			if(direction == NN){
					if(center.y >=word.length()){
						for(int i=0; i< word.length(); i++){
							
							if(gridMatrix[center.x][center.y] ==  null || gridMatrix[center.x][center.y].equals(word.charAt(i)) ){
								
								
								ExistPoint exPoint = new ExistPoint();
								exPoint.setX(center.x);
								exPoint.setY(center.y);
								if(gridMatrix[center.x][center.y] != null)
								exPoint.setCh(gridMatrix[center.x][center.y]);
								listPoints.add(exPoint);
								
								gridMatrix[center.x][center.y] = word.charAt(i);
//								System.out.println("NN " +gridMatrix[center.x][center.y] + " x= " +center.x+ " y="+center.y);
								center.y--;
								
								isAdd = true;
							}else{
								for(ExistPoint point : listPoints){

									if(point.getCh() == Character.MIN_VALUE)
									gridMatrix[point.getX()][point.getY()] = null;
									else
										gridMatrix[point.getX()][point.getY()] = point.getCh();
								}
								isAdd = false;
								break;
							}
						}
						
						if(isAdd)
							break;
							else
								continue;
						
					}else 
						continue;
			}
				
			if(direction == SS){
				if(size - center.y >=word.length()){
					for(int i=0; i< word.length(); i++){
						
						if(gridMatrix[center.x][center.y] ==  null || gridMatrix[center.x][center.y].equals(word.charAt(i)) ){
							ExistPoint exPoint = new ExistPoint();
							exPoint.setX(center.x);
							exPoint.setY(center.y);
							if(gridMatrix[center.x][center.y] != null)
							exPoint.setCh(gridMatrix[center.x][center.y]);
							listPoints.add(exPoint);
							gridMatrix[center.x][center.y] = word.charAt(i);
//							System.out.println("SS "+gridMatrix[center.x][center.y] + " x= " +center.x+ " y="+center.y);
							center.y++;
							isAdd = true;
						}else{
							
							for(ExistPoint point : listPoints){
								if(point.getCh() == Character.MIN_VALUE)
									gridMatrix[point.getX()][point.getY()] = null;
									else
										gridMatrix[point.getX()][point.getY()] = point.getCh();
							}
							isAdd = false;
							break;
						}
							
					}
					
					if(isAdd)
						break;
						else
							continue;
				}else 
					continue;
			
			}
			
			
			if(direction == WW){
				if(center.x >=word.length()){
					for(int i=0; i< word.length(); i++){
						
						if(gridMatrix[center.x][center.y] ==  null || gridMatrix[center.x][center.y].equals(word.charAt(i)) ){
							ExistPoint exPoint = new ExistPoint();
							exPoint.setX(center.x);
							exPoint.setY(center.y);
							if(gridMatrix[center.x][center.y] != null)
							exPoint.setCh(gridMatrix[center.x][center.y]);
							listPoints.add(exPoint);
							gridMatrix[center.x][center.y] = word.charAt(i);
//							System.out.println("WW  "+gridMatrix[center.x][center.y] + " x= " +center.x+ " y="+center.y);
							center.x--;
							isAdd = true;
						} else {
							for(ExistPoint point : listPoints){
								if(point.getCh() == Character.MIN_VALUE)
									gridMatrix[point.getX()][point.getY()] = null;
									else
										gridMatrix[point.getX()][point.getY()] = point.getCh();
							}
							isAdd = false;
							break;
						}
					}
					
					if(isAdd)
						break;
						else
							continue;
				}else 
					continue;
			
			}
			
			if(direction == EE){
				if(size - center.x >= word.length()){
					for(int i=0; i< word.length(); i++){
						
						if(gridMatrix[center.x][center.y] ==  null || gridMatrix[center.x][center.y].equals(word.charAt(i)) ){
							ExistPoint exPoint = new ExistPoint();
							exPoint.setX(center.x);
							exPoint.setY(center.y);
							if(gridMatrix[center.x][center.y] != null)
							exPoint.setCh(gridMatrix[center.x][center.y]);
							listPoints.add(exPoint);
							gridMatrix[center.x][center.y] = word.charAt(i);
//							System.out.println("EE "+gridMatrix[center.x][center.y] + " x= " +center.x+ " y="+center.y);
							center.x++;
							isAdd = true;
						}else{
							for(ExistPoint point : listPoints){
								if(point.getCh() == Character.MIN_VALUE)
									gridMatrix[point.getX()][point.getY()] = null;
									else
										gridMatrix[point.getX()][point.getY()] = point.getCh();
							}
							isAdd = false;
							break;
						}
					}
					if(isAdd)
						break;
						else
							continue;
				}else 
					continue;
			
			}
			
			if(direction == NE){
				if(size - center.x >=word.length() && center.y >=word.length()){
					for(int i=0; i< word.length(); i++){
						
						if(gridMatrix[center.x][center.y] ==  null || gridMatrix[center.x][center.y].equals(word.charAt(i)) ){
							
							ExistPoint exPoint = new ExistPoint();
							exPoint.setX(center.x);
							exPoint.setY(center.y);
							if(gridMatrix[center.x][center.y] != null)
							exPoint.setCh(gridMatrix[center.x][center.y]);
							listPoints.add(exPoint);
							
							gridMatrix[center.x][center.y] = word.charAt(i);
//							System.out.println("NE " +gridMatrix[center.x][center.y] + " x= " +center.x+ " y="+center.y);
							center.x++;
							center.y--;
							
							isAdd = true;
						}else{
							for(ExistPoint point : listPoints){
								if(point.getCh() == Character.MIN_VALUE)
									gridMatrix[point.getX()][point.getY()] = null;
									else
										gridMatrix[point.getX()][point.getY()] = point.getCh();
							}
							isAdd = false;
							break;
						}
					}
					
					if(isAdd)
						break;
						else
							continue;
				}else 
					continue;
			
			}
			
			if(direction == NW){
				if(center.y >=word.length() && center.x >= word.length()){
					for(int i=0; i< word.length(); i++){
						
						if(gridMatrix[center.x][center.y] ==  null || gridMatrix[center.x][center.y].equals(word.charAt(i)) ){
							
							
							ExistPoint exPoint = new ExistPoint();
							exPoint.setX(center.x);
							exPoint.setY(center.y);
							if(gridMatrix[center.x][center.y] != null)
							exPoint.setCh(gridMatrix[center.x][center.y]);
							listPoints.add(exPoint);
							
							gridMatrix[center.x][center.y] = word.charAt(i);
//							System.out.println("NW  "+gridMatrix[center.x][center.y] + " x= " +center.x+ " y="+center.y);
							center.x--;
							center.y--;
							isAdd = true;
						}else{
							for(ExistPoint point : listPoints){
								if(point.getCh() == Character.MIN_VALUE)
									gridMatrix[point.getX()][point.getY()] = null;
									else
										gridMatrix[point.getX()][point.getY()] = point.getCh();
							}
							isAdd = false;
							break;
						}
					}
					if(isAdd)
						break;
						else
							continue;
				}else 
					continue;
			
			}
		
			if(direction == SE){
				if(size - center.x >=word.length() && size- center.y >= word.length()){
					for(int i=0; i< word.length(); i++){
						
						if(gridMatrix[center.x][center.y] ==  null || gridMatrix[center.x][center.y].equals(word.charAt(i)) ){
							
							ExistPoint exPoint = new ExistPoint();
							exPoint.setX(center.x);
							exPoint.setY(center.y);
							if(gridMatrix[center.x][center.y] != null)
							exPoint.setCh(gridMatrix[center.x][center.y]);
							listPoints.add(exPoint);
							
							
							gridMatrix[center.x][center.y] = word.charAt(i);
//							System.out.println("SE  "+ gridMatrix[center.x][center.y] + " x= " +center.x+ " y="+center.y);
							center.x++;
							center.y++;
							isAdd = true;
						}else{
							for(ExistPoint point : listPoints){
								if(point.getCh() == Character.MIN_VALUE)
									gridMatrix[point.getX()][point.getY()] = null;
									else
										gridMatrix[point.getX()][point.getY()] = point.getCh();
							}
							isAdd = false;
							break;
						}
					}
					if(isAdd)
						break;
						else
							continue;
				}else 
					continue;
			
			}
			
			
			if(direction == SW){
				if(center.x >= word.length() && size-center.y >= word.length()){
					for(int i=0; i< word.length(); i++){
						
						if(gridMatrix[center.x][center.y] ==  null || gridMatrix[center.x][center.y].equals(word.charAt(i)) ){
							
							ExistPoint exPoint = new ExistPoint();
							exPoint.setX(center.x);
							exPoint.setY(center.y);
							if(gridMatrix[center.x][center.y] != null)
							exPoint.setCh(gridMatrix[center.x][center.y]);
							listPoints.add(exPoint);
							
							
							gridMatrix[center.x][center.y] = word.charAt(i);
//							System.out.println("SW "+gridMatrix[center.x][center.y] + " x= " +center.x+ " y="+center.y);
							center.x--;
							center.y++;
							isAdd = true;
						}else{
							for(ExistPoint point : listPoints){
								if(point.getCh() == Character.MIN_VALUE)
									gridMatrix[point.getX()][point.getY()] = null;
									else
										gridMatrix[point.getX()][point.getY()] = point.getCh();
							}
							isAdd = false;
							break;
						}
					}
					if(isAdd)
					break;
					else
						continue;
					
				}else 
					continue;
			
			}
		
			}
	
	}

	public static void fillEmpty(){
		
		Point point = new Point();
		for(point.y=0; point.y<size; point.y++)
			for (point.x = 0; point.x < size; point.x++) {
				
				if(gridMatrix[point.x][point.y] == null){
					
					gridMatrix[point.x][point.y] = (char)(random.nextInt(26)+((int)'A'));
//					gridMatrix[point.x][point.y] = '1';
				}
				
			}
		
	}
	

	
	

}
