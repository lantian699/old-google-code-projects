package com.ciel.equipe.wordsearch.test;


import java.util.ArrayList;

import com.ciel.equipe.wordsearch.test.dictionnary.DictionaryFactory;
import com.ciel.equipe.wordsearch.test.model.Grid;
import com.ciel.equipe.wordsearch.test.util.Tools;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WordSearchActivity extends Activity {

	private String appVer;

	private DictionaryFactory dictionaryFactory;

	private Character[][] gridMatrix;

	final public static int DIALOG_ID_NO_WORDS = 0;
	final public static int DIALOG_ID_NO_WORDS_CUSTOM = 1;
	final public static int DIALOG_ID_GAME_OVER = 2;
	final public static int DIALOG_ID_HIGH_SCORES_LOCAL_SHOW = 3;
	final public static int DIALOG_ID_GAME_NEW = 5;
	final public static int DIALOG_ID_INTRO_INPUT_TYPE = 6;
	final public static int DIALOG_ID_INTRO_DONATE = 7;
	final public static int DIALOG_ID_DONATE = 8;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);
//		setupViewGrid(10);

		
	}
	
	
	public void setupViewGrid(int gridSize) {

		ViewGroup gridTable = (ViewGroup) this.findViewById(R.id.gridTable);
		dictionaryFactory = new DictionaryFactory(this);
		gridMatrix = Grid.generateGrid(
				dictionaryFactory.getDictionary(getString(R.string.RANDOM)), 9,
				0, gridSize);

		setWordTextView(Grid.getListWord());
		gridTable.removeAllViews();
		Point point = new Point();

		TextView[][] gridView = new TextView[gridSize][];

		for (point.y = 0; point.y < gridSize; point.y++) {
			this.getLayoutInflater()
					.inflate(R.layout.grid_row, gridTable, true);
			ViewGroup row = (ViewGroup) gridTable.getChildAt(point.y);
			TextView[] rowText = new TextView[gridSize];
			for (point.x = 0; point.x < gridSize; point.x++) {
				this.getLayoutInflater().inflate(R.layout.grid_text_view, row,
						true);
				TextView view = (TextView) row.getChildAt(point.x);
				view.setText(gridMatrix[point.x][point.y].toString());
				rowText[point.x] = view;
			}
			gridView[point.y] = rowText;
		}

		gridTable.requestLayout();
			
		}

	public void setWordTextView(ArrayList<String> selectedList){
		
		for(int i=0; i<selectedList.size(); i++){
			ViewGroup wordTextView = (ViewGroup) this.findViewById(R.id.wordTextView);
			View view = this.getLayoutInflater().inflate(R.layout.word_view, wordTextView, true);
			TextView tv = (TextView)wordTextView.getChildAt(i);
			tv.setText(selectedList.get(i));
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case R.id.menu_new_game:
			
			setupViewGrid(10);
			
			break;

		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}


	

}
