package com.example.ganttjscripttest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends Activity {

	  private WebView webView; 

	  @TargetApi(16)
	@Override 
	  public void onCreate(Bundle savedInstanceState) { 

	      super.onCreate(savedInstanceState); 

	      setContentView(R.layout.activity_main); 
	      webView=(WebView) this.findViewById(R.id.webView);        
	      WebSettings setting=webView.getSettings(); 
	      System.out.println("file allow = " + setting.getAllowFileAccessFromFileURLs() +
	    		  "  universal allow = " + setting.getAllowUniversalAccessFromFileURLs());

	      setting.setAllowFileAccessFromFileURLs(true);
	      setting.setJavaScriptEnabled(true); 
	      webView.setWebChromeClient(new myWebChromeClient());
	      setting.setSupportZoom(true);	      
	      

	    //  webView.loadUrl("file:///android_asset/jscript.html"); 
	      webView.loadUrl("file:///android_asset/gantt_loadxml.html"); 
	  }
}
