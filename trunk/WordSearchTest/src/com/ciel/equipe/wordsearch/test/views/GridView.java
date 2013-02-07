package com.ciel.equipe.wordsearch.test.views;

import java.util.Random;

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

	private static final long WAIT_TIME = 0;
	private Paint paint;
	private Canvas canvas;
	private int mov_x;
	private int mov_y;
	private float endX;
	private float endY;
	
	public GridView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init(Color.RED, 40);
	}
	
	

	public GridView(Context context ) {
		super(context);
		// TODO Auto-generated constructor stub
		
		init(Color.RED, 40);
	
	}
	
	public void init(int color, int width){
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(width);
		paint.setColor(color);
		paint.setAntiAlias(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);		

            canvas.drawLine(mov_x, mov_y, endX	, endY, paint);
        
	}
	
	
	
	@Override
	public boolean onTouchEvent (MotionEvent event) {
		
		switch (event.getAction()) {
		

		case MotionEvent.ACTION_DOWN:
			mov_x = (int) event.getX();
			mov_y = (int)event.getY();
	
			break;

			
		case MotionEvent.ACTION_MOVE:
			
//			System.out.println("x= "+event.getX() + "  y=" +event.getY()+"  canvas = " +canvas );

			endX= event.getX();
			endY = event.getY();
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

}
