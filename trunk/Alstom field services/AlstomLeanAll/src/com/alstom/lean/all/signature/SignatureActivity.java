package com.alstom.lean.all.signature;

import java.io.ByteArrayOutputStream;

import com.alstom.lean.all.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SignatureActivity extends Activity implements OnClickListener{

	public static final String KEY_BYTE_SIGNATURE = "byteSignature";
	private SignatureView signatureView;

	private Button btn_cancel;
	private Button btn_ok;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tra_signature);
		signatureView = (SignatureView) findViewById(R.id.signature_view);
		
		btn_cancel = (Button)findViewById(R.id.btn_sig_cancel);
		btn_ok = (Button)findViewById(R.id.btn_sig_ok);
		
		btn_cancel.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
	}

	public void onClickButtonRight() {
		// get Bitmap from SignatureView
		Bitmap b = signatureView.save();
		// calculate the number of pixels
		/*int pixels = b.getHeight() * b.getRowBytes();
		// instanciate an OutputStream to handle the pixels
		ByteArrayOutputStream baos = new ByteArrayOutputStream(pixels);
		// we record it as a PNG (lossless format)
		b.compress(CompressFormat.PNG, 0, baos);
		// we get the bytes array
		byte[] bytes = baos.toByteArray();
		// ContentValues is a wrapper class used to insert in db

		// Création de l'intent
*/		Intent intent = new Intent();
//		intent.putExtra(KEY_BYTE_SIGNATURE, bytes);
		intent.putExtra(KEY_BYTE_SIGNATURE, b);
		// On retourne le résultat avec l'intent
		setResult(RESULT_OK, intent);
		// On termine cette activité
		finish();

	}

	public void onClickButtonLeft(View v) {
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_sig_ok:
			onClickButtonRight();
			break;
			
		case R.id.btn_sig_cancel:
			
			finish();
			break;

		default:
			break;
		}
	}
}
