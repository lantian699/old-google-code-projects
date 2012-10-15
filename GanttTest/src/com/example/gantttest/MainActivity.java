package com.example.gantttest;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends Activity {

    private WebView webView;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        webView=(WebView) this.findViewById(R.id.webView);   
	      webView.setWebChromeClient(new myWebChromeClient());
	      
	      WebSettings setting=webView.getSettings();
	      setting.setJavaScriptEnabled(true); 

//	    webView.loadUrl("file:///android_asset/jscript.html"); 
	    webView.loadUrl("file:///android_asset/gantt_loadxml.html"); 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
