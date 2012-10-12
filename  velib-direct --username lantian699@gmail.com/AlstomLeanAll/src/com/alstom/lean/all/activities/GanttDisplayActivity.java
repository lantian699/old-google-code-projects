package com.alstom.lean.all.activities;


import com.alstom.lean.all.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class GanttDisplayActivity extends Activity{
	
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_gantt_display); 

	      webView=(WebView) this.findViewById(R.id.webView);   
	      webView.setWebChromeClient(new myWebChromeClient());

	      WebSettings setting=webView.getSettings();
	      setting.setJavaScriptEnabled(true); 
	      setting.setAllowFileAccessFromFileURLs(true);
	      setting.setAllowUniversalAccessFromFileURLs(true);
	      
	      System.out.println("setting = " + setting.getAllowFileAccessFromFileURLs());

	    webView.loadUrl("file:///android_asset/jscript.html"); 
//	    webView.loadUrl("file:///android_asset/gantt_loadxml.html"); 
	}

}
