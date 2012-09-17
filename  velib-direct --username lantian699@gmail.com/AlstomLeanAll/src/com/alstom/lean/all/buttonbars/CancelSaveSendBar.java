package com.alstom.lean.all.buttonbars;


import com.alstom.lean.all.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

public class CancelSaveSendBar extends LinearLayout{
	
	public CancelSaveSendBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(HORIZONTAL);
		setGravity(Gravity.CENTER);
		setWeightSum(1.0f);
		
		LayoutInflater.from(context).inflate(R.layout.cancelsavesendbar, this, true);
		
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CancelSaveSendBar, 0, 0);
		
		String text = array.getString(R.styleable.CancelSaveSendBar_cancelSaveSendButton1);
		if (text != null)
			((Button) findViewById(R.id.cancelsavesendbar_cancel)).setText(text);
		
		text = array.getString(R.styleable.CancelSaveSendBar_cancelSaveSendButton2);
		if (text != null)
			((Button) findViewById(R.id.cancelsavesendbar_save)).setText(text);
		
		text = array.getString(R.styleable.CancelSaveSendBar_cancelSaveSendButton3);
		if (text != null)
			((Button) findViewById(R.id.cancelsavesendbar_send)).setText(text);
		
		array.recycle();
	}
}
