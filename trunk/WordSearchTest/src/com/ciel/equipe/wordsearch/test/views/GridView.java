package com.ciel.equipe.wordsearch.test.views;

import java.util.Random;

import com.ciel.equipe.wordsearch.test.model.Grid;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class GridView extends LinearLayout{

	
	public static final int SIZE = 10;
	private static final long WAIT_TIME = 0;
	private Paint paint;
	private float startX;
	private float startY;
	private float endX;
	private float endY;
	private Random random;
	private int windowWidth;
	private int windowHeight;
	private boolean isDrawing;
	private int unitWidth;
	private int unitHeight;
	private int direction = 0;
	private int step = 0;
	private float  unitWidHei;
	public GridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	
		random = new Random();
		init(Color.rgb(random.nextInt(255)+1, random.nextInt(255)+1, random.nextInt(255)+1),16*2);
	}
	
	public GridView(Context context ) {
		super(context);
		// TODO Auto-generated constructor stub
		
		init(Color.rgb(random.nextInt(255)+1, random.nextInt(255)+1, random.nextInt(255)+1),16*2);
	
	}
	
	public void init(int color, int width){
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(width);
		paint.setColor(color);
		paint.setAntiAlias(true);
		
		
		windowWidth  = getWidth();
		windowHeight = getHeight();
		
		unitWidth = windowWidth/SIZE;
		unitHeight = windowHeight/SIZE;
		unitWidHei = (float) Math.sqrt(unitWidth*unitWidth + unitHeight* unitHeight);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);		
	
			
		dragReaction(canvas, direction, step);	
		
	}
	
	
	public void dragReaction(Canvas canvas, int direction, int step){
		
		int numlineStart = (int) (startX/unitWidth);
		int numRowStart = (int) (startY/unitHeight);
		
//		int numlineEnd = (int) (endX/unitWidth);
//		int numRowEnd = (int) (endY/unitHeight);
		
		System.out.println("is draw = " + direction);
		
		if(isDrawing){
			init(Color.RED, 16*2);
			
			switch (direction) {
				case Grid.NN:
					 int startx = numlineStart*unitWidth+unitWidth/2;
					 int starty = numRowStart*unitHeight;
					 canvas.drawLine(startx, starty, startx , starty- unitHeight*step, paint);
					 System.out.println("start y" + (starty- unitHeight*step));
					break;
					
				case Grid.SS:
					 int startx1 = numlineStart*unitWidth+unitWidth/2;
					 int starty1 = numRowStart*unitHeight;
					 canvas.drawLine(startx1, starty1, startx1 , starty1+ unitHeight*step, paint);
					break;
								
				case Grid.WW:
					int startx2 =numlineStart*unitWidth;
					 int starty2 =  numRowStart*unitHeight + unitHeight/2;
					 canvas.drawLine(startx2, starty2, startx2 - unitHeight*step , starty2, paint);
					break;
					
					
				case Grid.EE:
					int startx3 =numlineStart*unitWidth;
					 int starty3 =  numRowStart*unitHeight + unitHeight/2;
					 canvas.drawLine(startx3, starty3, startx3 + unitHeight*step , starty3, paint);
					break;
					
//				case Grid.NE:
//					
//					 canvas.drawLine(numlineStart*unitWidth, numRowStart*unitHeight + unitHeight, numlineEnd*unitWidth , numRowEnd*unitHeight, paint);
//					break;
//				case Grid.NW:
//					 canvas.drawLine(numlineStart*unitWidth+unitWidth, numRowStart*unitHeight + unitHeight, numlineEnd*unitWidth , numRowEnd*unitHeight, paint);
//					break;
//				case Grid.SE:
//						
//					canvas.drawLine(numlineStart*unitWidth, numRowStart*unitHeight, numlineEnd*unitWidth , numRowEnd*unitHeight, paint);
//					break;
//					
//				case Grid.SW:
//					canvas.drawLine(numlineStart*unitWidth+unitWidth, numRowStart*unitHeight , numlineEnd*unitWidth , numRowEnd*unitHeight, paint);
//					break;
	
				default:
					break;
			}
       
		}
	}
	
	

	
	
	@Override
	public boolean onTouchEvent (MotionEvent event) {
		
		switch (event.getAction()) {
		

		case MotionEvent.ACTION_DOWN:
			init(Color.rgb(random.nextInt(254)+1, random.nextInt(254)+1, random.nextInt(254)+1),16*2);
			startX = event.getX();
			startY = event.getY();
//			System.out.println("startx= "+event.getX() + "  starty=" +event.getY());
			isDrawing = true;
			break;

			
		case MotionEvent.ACTION_MOVE:
			
//			System.out.println("endx= "+event.getX() + "  endy=" +event.getY() );

			endX = event.getX();
			endY = event.getY();
			
			if(endX-startX > unitWidth && endY-startY > unitHeight){
				direction = Grid.SE;
				step = (int) (root(startX, startY, endX, endY)/unitWidHei);
			}
			else if(endX-startX > unitWidth && endY-startY < unitHeight){
				direction = Grid.NE;
				step = (int) (root(startX, startY, endX, endY)/unitWidHei);
			}
			else if(endX-startX < unitWidth && endY-startY > unitHeight){
				direction = Grid.SW;
				step = (int) (root(startX, startY, endX, endY)/unitWidHei);
			}
			else if(endX-startX < unitWidth && endY-startY < unitHeight){
				direction = Grid.NW;
				step = (int) (root(startX, startY, endX, endY)/unitWidHei);
			}

			else if(Math.abs(endX - startX)<= unitHeight  && endY<(startY-unitHeight)){
				direction = Grid.NN;
				step = ((int) (Math.abs(endY - startY)/unitHeight) )+ 1;
			}
			else if(Math.abs(endX - startX)<= unitHeight&& endY>(startY+unitHeight)){
				direction = Grid.SS;
				step = ((int) (Math.abs(endY - startY)/unitHeight) )+ 1;
			}
			else if(endX > (startX+ unitWidth )&& Math.abs(endY - startY)<= unitHeight){
				direction = Grid.WW;
				step = ((int) (Math.abs(endX - startX)/unitWidth) )+ 1;
			}
			else if(endX < (startX-unitWidth) && Math.abs(endY - startY)<= unitHeight){
				direction = Grid.EE;
				step = ((int) (Math.abs(endX - startX)/unitWidth) )+ 1;
			}
			
			
//			System.out.println("step = " + step);
			invalidate();	
			break;

		case MotionEvent.ACTION_UP:
		
					
			break;

		default:
			break;
		}
		
//		synchronized (object) {
//			
//			try {
//				object.wait(WAIT_TIME);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
		
		return true;
	}

	public float root(float x1, float y1, float x2, float y2){
		
		return   (float) Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
	}
	
}
