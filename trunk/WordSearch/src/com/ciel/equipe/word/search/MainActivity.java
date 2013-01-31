package com.ciel.equipe.word.search;

import java.io.InputStream;
import java.sql.SQLException;

import org.apache.http.util.EncodingUtils;

import com.ciel.equipe.word.search.helpers.DatabaseHelper;
import com.ciel.equipe.word.search.models.Chinese;
import com.j256.ormlite.dao.Dao;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tv;
	private DatabaseHelper dataHelper;
	private Dao<Chinese, ?> chineseDao;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (TextView)findViewById(R.id.textView);
		dataHelper = DatabaseHelper.getInstance(this);
		
		readRessources();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
	
	public void readRessources(){
		
		String res = null; 
		try{ 
		InputStream in = getAssets().open("chinese_character.txt"); 
		int length = in.available();    
		byte [] buffer = new byte[length];        
		in.read(buffer);         
		   res = EncodingUtils.getString(buffer, "UTF-8");
		   //res = EncodingUtils.getString(buffer, "UNICODE"); 
		   //res = EncodingUtils.getString(buffer, "GBK"); 
		   in.close();            
		   }catch(Exception e){ 
		      e.printStackTrace();         
		   } 
		tv.setText(res);
	
		
		for(int i=0; i<res.length(); i=i+2){
			
			String subString = res.substring(i,i+2);
			if (subString != " ") {
				try {
					chineseDao = dataHelper.getDao(Chinese.class);
					Chinese chinese = new Chinese();
					chinese.setCharacter(subString);
					chineseDao.create(chinese);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		tv.setTextColor(Color.BLACK);
		tv.setTextSize(30);
		tv.setMovementMethod(ScrollingMovementMethod.getInstance()); 
//		Typeface typeface = Typeface.create("宋体", Typeface.NORMAL);
//		tv.setTypeface(typeface);
		
		
		}

}
