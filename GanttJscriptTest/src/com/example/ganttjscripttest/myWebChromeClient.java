package com.example.ganttjscripttest;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class myWebChromeClient extends WebChromeClient{
	@Override
	public boolean onJsAlert(WebView view, String url, String message,
			JsResult result) {
		// TODO Auto-generated method stub
		
		//System.out.println("javascript error message :   " + message);
		
		return super.onJsAlert(view, url, message, result);
	}

}
