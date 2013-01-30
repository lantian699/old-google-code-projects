package ciel.equipe.years;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.util.EncodingUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AffichageActivity extends Activity {
	
	
	
	TextView tv1;
	TextView tvTop;
	TextView tvBottom;
	ImageView arrow_left;
	ImageView arrow_right;
	RelativeLayout rtLayoutTop;
	RelativeLayout rtLayoutBottom;
	RelativeLayout RtLayout;
	private static int mScreenWidth;  
    private static int mScreenHeight;  
    private static int widthPixels; 
    private static int heightPixels; 
    private static float density;
    ArrayList<HashMap<String, Object>> listItem;
    ListView mainlist;
    Boolean estAffiche = false;

    int idChapter=0;
    int nameChapter=0;
    int flag=0;
    Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
    int year;
    int month;
    int day;
    String Chapter="";
    int line =0;
    
    
    
    /** Called when the activity is first created.qsdqsq */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
       
        
        listItem = new ArrayList<HashMap<String, Object>>();
        mainlist = (ListView)findViewById(R.id.list1);
    	
        
        t.setToNow(); // 取得系统时间。
      	  year = t.year;
      	 month = t.month;
      	 day = t.monthDay;
      // adView = (AdView)this.findViewById(R.id.ad); // show the advertisement
       //adView.loadAd(new AdRequest());
        
       // iv1 = (ImageView)findViewById(R.id.imageView1);
       tv1 = (TextView)findViewById(R.id.textView01); 
 
       tvTop = (TextView)findViewById(R.id.timuTop);
       tvTop.setTextColor(Color.TRANSPARENT);
       tvTop.setBackgroundColor(Color.TRANSPARENT);
       
       arrow_left = (ImageView)findViewById(R.id.arrowLeft);
       arrow_right = (ImageView)findViewById(R.id.arrowRight);
       arrow_left.setAlpha(0);
       arrow_right.setAlpha(0);
       arrow_left.setBackgroundColor(Color.TRANSPARENT);
       arrow_right.setBackgroundColor(Color.TRANSPARENT);
       
       
       
       rtLayoutTop = (RelativeLayout) findViewById(R.id.relativeLayoutTop); 
       rtLayoutBottom = (RelativeLayout) findViewById(R.id.relativeLayoutBottom);
       rtLayoutTop.setBackgroundColor(Color.TRANSPARENT);
       rtLayoutBottom.setBackgroundColor(Color.TRANSPARENT);
       
       
       RtLayout = (RelativeLayout) findViewById(R.id.rtLayout1);
       
       
      
   
       
       //System.out.println(mScreenWidth+"x"+mScreenHeight);
       //Intent rcv = getIntent();
       Bundle bundle = new Bundle();
       bundle = this.getIntent().getExtras();
	   idChapter   = bundle.getInt("idChapter");
       nameChapter = bundle.getInt("nameChapter");
       line = bundle.getInt("line");
       flag		=bundle.getInt("flag");
       Chapter =getResources().getString(nameChapter);
       tvTop.setText(Chapter);
       LireRAW(idChapter);
       
       
       tv1.setOnClickListener(new View.OnClickListener() {
       	 public void onClick(View v){
       		 
       		 if(estAffiche == false){
       			rtLayoutTop.setBackgroundColor(0xBf000000);
       			rtLayoutBottom.setBackgroundColor(0xBf000000);
       			arrow_left.setAlpha(200);
       			arrow_right.setAlpha(200);
       			tvTop.setTextColor(Color.WHITE);
       			
       		estAffiche = true;
       		 }
       		 else {
       			rtLayoutTop.setBackgroundColor(0x00000000);
       			rtLayoutBottom.setBackgroundColor(0x00000000);
       			tvTop.setTextColor(0x00000000);
       			arrow_left.setAlpha(0);
       			arrow_right.setAlpha(0);

           		estAffiche = false;
       			 
       		 }
       		// System.out.println("hah");
       		 
       		 
   		 }
       	
       	
       	
       }
       		); //END OF TV1 setOnClickListener   
        
    }// END OF ONCREATE

    
  
    /**
     * Pour créer un menu
     */
    @Override
	public boolean onCreateOptionsMenu (Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.xml.menu_affiche, menu);
		
		return true;
	}
	
	void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
    	
        float textSize ;
        
    	
        switch (item.getItemId()) {
        
        
        case R.id.menu01:
        	
        	textSize=tv1.getTextSize(); // la fonction getTextSize  retourne la taille en pixels, donc on doit le transmettre en dip
   
            tv1.setTextSize((float) (textSize/1.5+1));
        	
            return true;
            
            
        case R.id.menu02:
        	textSize=tv1.getTextSize();

        	tv1.setTextSize((float) (textSize/1.5-1));
            return true;
            
        case R.id.menu03:
        	
        		Intent in = new Intent();
    		
    		in.setClass(AffichageActivity.this, CatalogActivity.class);
    		this.finish();
    	    //  显示Activity1
    	    startActivity(in);
            
            return true;
            
            
        case R.id.menu04:
        	
        	final int line =tv1.getScrollY();
        	
        	
        	final CharSequence[] items = {"添加书签", "读取书签", "取消"};

        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setTitle("书签操作");
        	builder.setItems(items, new DialogInterface.OnClickListener() {
        	    public void onClick(DialogInterface dialog, int item) {
        	    	
        	    	if(item == 0){
        	    		
        	    		EcrireXML(idChapter,Chapter, line , year, month, day,nameChapter,flag);
        	    		
        	    		showToast("已经成功添加书签");
        	    	}
        	    	
        	    	if(item == 1){
        	    	
        	    		
        	    		LireXML();
        	    	}
        	    	
        	       
        	    }
        	});
        	AlertDialog alert = builder.create();
        	
        	alert.show();
        	
            return true;
            
            
            
        case R.id.menu05:
        	
        	//new HelpDialog(AffichageActivity.this).show();//afficher les informations d'auteur.
        	
        	/*Intent intent = new Intent(this, CommentActivity.class);
        	startActivity(intent);*/
            return true;
            
            
            
       case R.id.menu06:
        	
        	new AlertDialog.Builder(this)
			
			
			.setMessage("您是否想要退出?")
			.setNegativeButton("否", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			})
			.setPositiveButton("是", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					
					close();
				}
			}).show();
            
        	
            return true;
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    
    
    /**cette fonction définit les réaction quand on apuie sur l'image arrow_left
     * 
     */
    
    public void arrowLeftOnClick(View view){
    	
    	
    	if(flag == 0){
    		
    		showToast("这已经是第一节，不能再向前了，哈哈");
    		return;
    		
    	}
    	
    	Intent in = new Intent();
		Bundle bundle = new Bundle();
	    bundle.putInt("idChapter",idChapter-1 );
	    bundle.putInt("nameChapter", nameChapter-1);
	    bundle.putInt("flag", flag-1);
	    bundle.putInt("line", line);
		in.setClass(AffichageActivity.this, YearsActivity.class);
		in.putExtras(bundle);
		this.finish();
	    //  显示Activity1
	    startActivity(in);
    }
  
    /**cette fonction définit les réaction quand on apuie sur l'image arrow_right
     * 
     */
    
  public void arrowRightOnClick(View view){
	  	
	  
	  if(flag == 26){
  		
  		showToast("这已经是最后一节，不能再向后了，哈哈");
  		return;
  		
  	}
	  
	  
	  	Intent in = new Intent();
		Bundle bundle = new Bundle();
	    bundle.putInt("idChapter",idChapter+1 );
	    bundle.putInt("nameChapter", nameChapter+1);
	    bundle.putInt("flag", flag+1);
	    bundle.putInt("line", line);
		in.setClass(AffichageActivity.this, YearsActivity.class);
		in.putExtras(bundle);
		//this.finish();
	    //  显示Activity1
	    startActivity(in);
    	
    	System.out.println("je suis right");
    }
  
    
    
    //
public void LireRAW(int id ){
	
	String res = ""; 
	try{ 
	InputStream in = getResources().openRawResource(id); 
	//在\Test\res\raw\bbi.txt,
	   int length = in.available();       
	   byte [] buffer = new byte[length];        
	   in.read(buffer);         
	   //res = EncodingUtils.getString(buffer, "UTF-8");
	   //res = EncodingUtils.getString(buffer, "UNICODE"); 
	   res = EncodingUtils.getString(buffer, "GBK"); 
	   //依bbi.txt的编码类型选择合适的编码，如果不调整会乱码
	   in.close();            
	   }catch(Exception e){ 
	      e.printStackTrace();         
	   } 
	tv1.setText(res);//把得到的内容显示在TextView上
	tv1.setTextColor(Color.BLACK);
	tv1.scrollTo(0, line);
	tv1.setMovementMethod(ScrollingMovementMethod.getInstance()); 
	//Typeface typeface = Typeface.create("宋体", Typeface.ITALIC);
	//tv1.setTypeface(typeface);
	
	
	}



public void EcrireXML(int chapter, String nameChapter ,int line, int year, int month, int day, int idname, int flag)
{
	
	FileOutputStream fOut = null;
	OutputStreamWriter osw = null;
	Format format;
	XMLOutputter outputter;
	
	Element rootElement = new Element("DESMARQUEDINAMIQUE");
	
	
	//element.setAttribute(new Attribute("wordname", ""+name+""));
	
	//element.addContent(new Element("expli").addContent(exp));
	
	
	//更改XML文件里的单词
	 
	try {
		
		String annee= String.valueOf(year);
		String mois= String.valueOf(month);
		String jour= String.valueOf(day);
		
	     File fl = getFileStreamPath ("bookmark.xml");
	     
	    System.out.println("fl="+fl.exists());
 		if(fl.exists() == false){//if the file does not exist
 			
 			
 			
	    	//Element nameElement = new Element("name");
	    	Document myDocument = new Document(rootElement);
	    	
	    	Element element = rootElement.addContent(new Element("DESMARQUE")
	    	.setAttribute("idChapter", ""+chapter+"").addContent(new Element("line").addContent(String.valueOf(line)))
	    	.addContent(new Element("time").addContent(annee+"."+mois+"."+jour))
	    	.addContent(new Element("name").addContent(nameChapter))
	    	.addContent(new Element("idname").addContent(String.valueOf(idname)))
	    	.addContent(new Element("flag").addContent(String.valueOf(flag))));
 			
 			
 			fOut = getApplicationContext().openFileOutput("bookmark.xml", MODE_PRIVATE);
 			outputter = new XMLOutputter(Format.getPrettyFormat());
 			
 			
 			osw = new OutputStreamWriter(fOut);
        	outputter.output(myDocument, osw);
        	osw.flush();
 			
 		}else{
 			
 			
 			SAXBuilder sxb = new SAXBuilder();
 			Document doc;
			try {
				
				FileInputStream fin = this.getBaseContext().openFileInput("bookmark.xml");
				doc = sxb.build(fin);
				
				Element root = doc.getRootElement();
				root.addContent(new Element("DESMARQUE")
     			.setAttribute("idChapter", ""+chapter+"").addContent(new Element("line").addContent(String.valueOf(line)))
     			.addContent(new Element("time").addContent(annee+"."+mois+"."+jour))
     			.addContent(new Element("name").addContent(nameChapter))
     			.addContent(new Element("idname").addContent(String.valueOf(idname)))
    	    	.addContent(new Element("flag").addContent(String.valueOf(flag))));
				
				
				
				/*format = Format.getCompactFormat(); 
     			format.setEncoding("UTF-8");
     			format.setOmitDeclaration(true); //omit the declaration
     			format.setOmitEncoding(true);// omit the encoding
*/		     			outputter = new XMLOutputter(Format.getPrettyFormat());
     			
     			fOut = getApplicationContext().openFileOutput("bookmark.xml", MODE_PRIVATE);
     			osw = new OutputStreamWriter(fOut);
     			outputter.output(doc,osw);
     		     //System.out.println(strout);
     			
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
 			
	}  
	     
	     
    	
	} catch (java.io.IOException e) {
	    e.printStackTrace();
	}
	/*finally { 
			try { 
				osw.close(); 
				fOut.close(); 
			} catch (IOException e) { 
				Toast.makeText(getBaseContext(), "保存书签失败 请重试",Toast.LENGTH_SHORT).show(); 
			} 
		} */
	
	

	
}  // end of EcrireXML

	


// lire un fichier XML fait la liason avec Hashmap

static Element racine = new Element("DESMARQUEDINAMIQUE");
static org.jdom.Document document = new Document(racine);

public  void LireXML(){
	
	CharSequence[] items ;
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	SimpleAdapter listItemAdapter = null;
	
	//On cree une instance de SAXBuilder
	SAXBuilder sxb = new SAXBuilder();
	try
	{
		FileInputStream fin = this.getBaseContext().openFileInput("bookmark.xml");
		//System.out.println("hi i m document123254455");
		document = sxb.build(fin);
	
		racine = document.getRootElement();

		List<Element> liststring;
		liststring = racine.getChildren("DESMARQUE");
		
//		//List listEtudiants = racine.getChildren("string");
		Iterator<Element> b = liststring.iterator();
		Element courant = racine ;
		int chapter;
		int line;
		int flag1 = 0;
		int idname=0;
		String time;
		String nameChapter;
		while(b.hasNext()){
			
			
			courant= b.next();
		//System.out.println(courant.getText());
			 chapter = Integer.parseInt(courant.getAttributeValue("idChapter"));
			 //System.out.println("wordname = "+nom);
			 line= Integer.parseInt(courant.getChildText("line"));
			 time= courant.getChildText("time");
			 nameChapter= courant.getChildText("name");
			 flag1 =Integer.parseInt(courant.getChildText("flag"));
			 idname = Integer.parseInt(courant.getChildText("idname"));
			 
			 HashMap<String, Object> map = new HashMap<String, Object>();  
			 	map.put("id", chapter);
	            map.put("name", nameChapter); 
	            map.put("line", line);  
	            map.put("time", time);  
	            map.put("flag", flag1);
	            map.put("idname", idname);
	            listItem.add(map);
	            
	           
		
	            listItemAdapter = new SimpleAdapter(AffichageActivity.this,listItem, R.layout.bookmark,//ListItem的XML实现  
	                      
	                  	//动态数组与ImageItem对应的子项          
	                      new String[] {"name", "time"},   
	                      //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
	                      new int[] {R.id.name,R.id.time}  
	                  );  
		

		
	         
			 
			 
			 
			 
	            
		} 
		
		builder.setTitle("请选择一个书签");
		 
		 builder.setSingleChoiceItems(listItemAdapter, -1, new DialogInterface.OnClickListener() {
		     public void onClick(DialogInterface dialog, int item) {
		    	 
		    	
		    	 
		    		 int id =(Integer) listItem.get(item).get("id");
		    		 int name = (Integer) listItem.get(item).get("idname");
		    		 int flag = (Integer) listItem.get(item).get("flag");
		    		 int line =  (Integer) listItem.get(item).get("line");
		    	 
		    		 Intent in = new Intent();
						Bundle bundle = new Bundle();
					    bundle.putInt("idChapter",id );
					    bundle.putInt("nameChapter", name);
					    bundle.putInt("flag", flag);
					    bundle.putInt("line", line);
						in.setClass(AffichageActivity.this, YearsActivity.class);
						in.putExtras(bundle);
						
						AffichageActivity.this.finish();
					    //  显示Activity1
					    startActivity(in);
		    	 
		     }
		 });
		 AlertDialog alert = builder.create();   
		 alert.show();
		
		 
		 
		
			 
		fin.close();
		
		

		//			}
	}
	catch(Exception e){
		e.printStackTrace();
		System.out.println("Ne pas trouver document XML");
		showToast("读取书签失败，请重试"); 	
	}
	
		
	//return null;
	
	}
// end of LireXML




@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {

	  if(keyCode == KeyEvent.KEYCODE_BACK){
		  
		  Intent in = new Intent();
		  in.setClass(AffichageActivity.this,CatalogActivity.class);
  		
  		
  		close();
  	    //  显示Activity1
  	    startActivity(in);
  	  
		  
	  }
	  
	return false;
//而当返回false时，表示并没有完全处理完该事件，更希望其他回调方法继续对其进行处理
}




/*@Override
protected void onDestroy() {
	super.onDestroy();

	
	android.os.Process.killProcess(android.os.Process.myPid());
	System.exit(0);
 
}*/

public void close()
{
  Intent intent = new Intent();
  intent.setAction("ExitApp");
    this.sendBroadcast(intent);
  super.finish();
}


private BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
{
 @Override
 public void onReceive(Context arg0, Intent arg1) {
  finish(); 
 }
};

@Override
protected void onResume() {
 super.onResume();
 IntentFilter filter = new IntentFilter();
 filter.addAction("ExitApp");
 this.registerReceiver(broadcastReceiver, filter);
// Collector.onResume(this);
}

@Override
protected void onPause(){
	super.onPause();
	
//	Collector.onPause(this);
	
}



}// END OF ACTIVITY