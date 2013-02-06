package com.alstom.lean.all.model3d;

import java.io.IOException;

import com.android.opgl.test.Test03;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Model3DFragment extends Fragment{
	
	
	private GLSurfaceView glSurface;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		glSurface=new GLSurfaceView(getActivity());
		Test03 test03=new Test03(glSurface.getContext());
		glSurface.setRenderer(test03);
		glSurface.setOnTouchListener(test03);	

		
		
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return glSurface;
	}

}
