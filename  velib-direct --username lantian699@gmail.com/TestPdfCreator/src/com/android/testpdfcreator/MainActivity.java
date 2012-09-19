package com.android.testpdfcreator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	 private Document document = new Document();
     private PdfWriter pdfWriter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       document = new Document(PageSize.A4,10,10,10,10);
       document.addTitle("Work Report");
       document.addAuthor("Jean vincent HANY");
       
       
       try {
		pdfWriter = PdfWriter.getInstance(document,new FileOutputStream(Environment.getExternalStorageDirectory()+"/image.pdf"));
	    
	    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       
       
        	
        	
        	
       
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
