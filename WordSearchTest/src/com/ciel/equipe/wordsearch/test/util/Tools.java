package com.ciel.equipe.wordsearch.test.util;

import android.graphics.Point;

public class Tools {
	
	public static int convertPointToId(Point p, int size){
		
		return p.x+p.y * size ;
	}

}
