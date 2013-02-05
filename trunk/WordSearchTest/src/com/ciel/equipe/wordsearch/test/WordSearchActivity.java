package com.ciel.equipe.wordsearch.test;

import com.ciel.equipe.wordsearch.test.dictionnary.DictionaryFactory;
import com.ciel.equipe.wordsearch.test.model.Grid;
import com.ciel.equipe.wordsearch.test.util.Tools;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
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
		
		setContentView(R.layout.activity_main);
		setupViewGrid(10);

		
	}
	
	
	public void setupViewGrid(int gridSize) {

		ViewGroup gridTable = (ViewGroup) this.findViewById(R.id.gridTable);

			dictionaryFactory = new DictionaryFactory(this);
			gridMatrix = Grid.generateGrid(dictionaryFactory.getDictionary(getString(R.string.RANDOM)), 12, 0, gridSize);
			gridTable.removeAllViews();
			Point point = new Point();
			
			TextView [][] gridView = new TextView[gridSize][];
		
			for (point.y = 0; point.y < gridSize; point.y++) {
				this.getLayoutInflater().inflate(R.layout.grid_row, gridTable, true);
				ViewGroup row = (ViewGroup)gridTable.getChildAt(point.y);
				TextView[] rowText = new TextView[gridSize];
				for (point.x = 0; point.x < gridSize; point.x++) {
					this.getLayoutInflater().inflate(R.layout.grid_text_view, row, true);
					TextView view = (TextView)row.getChildAt(point.x);
					view.setText(gridMatrix[point.x][point.y].toString());
					rowText[point.x] = view;
				}
				gridView[point.y] = rowText;
			}
	
			gridTable.requestLayout();
			
		}
	

}
