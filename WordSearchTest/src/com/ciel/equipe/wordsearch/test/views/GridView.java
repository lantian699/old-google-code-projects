package com.ciel.equipe.wordsearch.test.views;

import java.util.Random;

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

	private static final long WAIT_TIME = 100;
	private Paint paint;
	private Bitmap bitmap;
	private Canvas canvas;
	private int mov_x;
	private int mov_y;
	private Object object;
	
	public GridView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
	}
	
	

	public GridView(Context context ) {
		super(context);
		// TODO Auto-generated constructor stub
		
		init();
	
	}
	
	public void init(){
		object = new Object();
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(5);
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		bitmap = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);		
		this.canvas = canvas;
	}
	
	
	
	@Override
	public boolean onTouchEvent (MotionEvent event) {
		
		switch (event.getAction()) {
		

		case MotionEvent.ACTION_DOWN:
			mov_x = (int) event.getX();
			mov_y = (int)event.getY();
			
			break;

			
		case MotionEvent.ACTION_MOVE:
			
			System.out.println("x= "+event.getX() + "  y=" +event.getY()+"  canvas = " +canvas );

			canvas.drawLine(22, 34, 123, 43, paint);
			invalidate();	
			break;

		case MotionEvent.ACTION_UP:
		
					
			break;

		default:
			break;
		}
		
		synchronized (object) {
			
			try {
				object.wait(WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return true;
	}

}
