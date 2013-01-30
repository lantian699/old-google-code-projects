package ciel.equipe.years;



import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class CatalogActivity extends Activity {
	
	ListView mainlist1;
    ArrayList<HashMap<String, Object>> listItem1;
    String nameChapter;
    ArrayList<HashMap<String, Object>> listItem;

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.catalog);
    
        mainlist1 = (ListView)findViewById(R.id.list1);
        listItem1 = new ArrayList<HashMap<String, Object>>();  //ArrayList<HashMap<String, Object>> 
       // System.out.println(mainlist1+"***********");
        
        
        AddCatalog(getResources().getString(R.string.di0) ); // 前言
        AddCatalog(getResources().getString(R.string.di1) );//失恋第一天
        AddCatalog(getResources().getString(R.string.di2) );
        AddCatalog(getResources().getString(R.string.di3) );
        AddCatalog(getResources().getString(R.string.di4) );
        AddCatalog(getResources().getString(R.string.di5) );
        AddCatalog(getResources().getString(R.string.di6) );
        AddCatalog(getResources().getString(R.string.di7) );
        AddCatalog(getResources().getString(R.string.di8) );
        AddCatalog(getResources().getString(R.string.di9) );
        AddCatalog(getResources().getString(R.string.dia) );
        AddCatalog(getResources().getString(R.string.dib) );
        AddCatalog(getResources().getString(R.string.dic) );
        AddCatalog(getResources().getString(R.string.did) );
        AddCatalog(getResources().getString(R.string.die) );
        AddCatalog(getResources().getString(R.string.dif) );
        AddCatalog(getResources().getString(R.string.dig) );
        AddCatalog(getResources().getString(R.string.dih) );
        AddCatalog(getResources().getString(R.string.dii) );
        AddCatalog(getResources().getString(R.string.dij) );
        AddCatalog(getResources().getString(R.string.dik) );
        AddCatalog(getResources().getString(R.string.dil) );
        AddCatalog(getResources().getString(R.string.dim) );
        AddCatalog(getResources().getString(R.string.din) );
        AddCatalog(getResources().getString(R.string.dio) );
        AddCatalog(getResources().getString(R.string.dip) );
        AddCatalog(getResources().getString(R.string.diq) );
     
        
        //生成适配器的Item和动态数组对应的元素  
        SimpleAdapter listItemAdapter = new SimpleAdapter(CatalogActivity.this,listItem1, R.layout.list_content,//ListItem的XML实现  
            
        	//动态数组与ImageItem对应的子项          
            new String[] {"ItemImage","ItemTitle"},   
            //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
            new int[] {R.id.ItemImage,R.id.ItemTitle}  
        );  
        mainlist1.setAdapter (listItemAdapter);
    	
        
        mainlist1.setOnItemClickListener(new OnItemClickListener() {  
      	  
            public void onItemClick(AdapterView<?> ada, View view,  
                    int index, final long longIndex) {  
                
            	
            	
            	
            	if(index == 0){
            		Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.di0);
            	    bundle.putInt("nameChapter", R.string.di0);
            	    bundle.putInt("flag", 0);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
                    // Collector.setAppClickCount("Catalog");
               
                    //  显示Activity1
                    startActivity(in);
            		
            	}
            	
            	if(index == 1){
            		
            		Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.di1);
            	    bundle.putInt("nameChapter", R.string.di1);
            	    bundle.putInt("flag", 1);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
                    // Collector.setAppClickCount("Catalog");
                    //  显示Activity1
                    startActivity(in);
            		
            	}
            	
				if(index == 2){
					// Collector.setAppClickCount("Catalog");
				            		
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.di2);
            	    bundle.putInt("nameChapter", R.string.di2);
            	    bundle.putInt("flag", 2);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				                    //  显示Activity1
				                    startActivity(in);
				            		
				            	}
				
				if(index == 3){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.di3);
            	    bundle.putInt("nameChapter", R.string.di3);
            	    bundle.putInt("flag", 3);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 4){
					// Collector.setAppClickCount("Catalog");
					
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.di4);
            	    bundle.putInt("nameChapter", R.string.di4);
            	    bundle.putInt("flag", 4);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				
				if(index == 5){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.di5);
            	    bundle.putInt("nameChapter", R.string.di5);
            	    bundle.putInt("flag", 5);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				
				if(index == 6){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.di6);
            	    bundle.putInt("nameChapter", R.string.di6);
            	    bundle.putInt("flag", 6);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 7){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.di7);
            	    bundle.putInt("nameChapter", R.string.di7);
            	    bundle.putInt("flag", 7);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 8){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.di8);
            	    bundle.putInt("nameChapter", R.string.di8);
            	    bundle.putInt("flag", 8);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 9){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.di9);
            	    bundle.putInt("nameChapter", R.string.di9);
            	    bundle.putInt("flag", 9);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 10){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.dia);
            	    bundle.putInt("nameChapter", R.string.dia);
            	    bundle.putInt("flag", 10);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 11){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.dib);
            	    bundle.putInt("nameChapter", R.string.dib);
            	    bundle.putInt("flag", 11);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 12){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.dic);
            	    bundle.putInt("nameChapter", R.string.dic);
            	    bundle.putInt("flag", 12);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 13){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.did);
            	    bundle.putInt("nameChapter", R.string.did);
            	    bundle.putInt("flag", 13);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 14){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.die);
            	    bundle.putInt("nameChapter", R.string.die);
            	    bundle.putInt("flag", 14);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 15){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.dif);
            	    bundle.putInt("nameChapter",R.string.dif);
            	    bundle.putInt("flag", 15);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 16){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.dig);
            	    bundle.putInt("nameChapter", R.string.dij);
            	    bundle.putInt("flag", 16);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 17){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.dih);
            	    bundle.putInt("nameChapter", R.string.dih);
            	    bundle.putInt("flag", 17);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 18){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.dii);
            	    bundle.putInt("nameChapter", R.string.dii);
            	    bundle.putInt("flag", 18);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 19){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.dij);
            	    bundle.putInt("nameChapter", R.string.dij);
            	    bundle.putInt("flag", 19);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 20){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.dik);
            	    bundle.putInt("nameChapter", R.string.dik);
            	    bundle.putInt("flag", 20);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 21){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.dil);
            	    bundle.putInt("nameChapter", R.string.dil);
            	    bundle.putInt("flag", 21);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 22){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.dim);
            	    bundle.putInt("nameChapter", R.string.dim);
            	    bundle.putInt("flag", 22);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 23){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.din);
            	    bundle.putInt("nameChapter", R.string.din);
            	    bundle.putInt("flag", 23);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 24){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.dio);
            	    bundle.putInt("nameChapter", R.string.dio);
            	    bundle.putInt("flag", 24);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 25){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.dip);
            	    bundle.putInt("nameChapter", R.string.dip);
            	    bundle.putInt("flag", 25);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
				if(index == 26){
					// Collector.setAppClickCount("Catalog");
					Intent in = new Intent();
            		Bundle bundle = new Bundle();
            	    bundle.putInt("idChapter", R.raw.diq);
            	    bundle.putInt("nameChapter", R.string.diq);
            	    bundle.putInt("flag", 26);
            		in.setClass(CatalogActivity.this, YearsActivity.class);
                    in.putExtras(bundle);
				    //  显示Activity1
				    startActivity(in);
					
				}
				
			

            	
            	
            }
            
        });
        
     
                    	
             
                
                                                       
        
    
    }// end of onCreate

    
    

    
    
    public void AddCatalog(String catalog ){
    	
    	 HashMap<String, Object> map = new HashMap<String, Object>();  
        map.put("ItemImage", null);//图像资源的ID  
        map.put("ItemTitle", catalog);  
      
         
        listItem1.add(map);
        }
    
    
    
    

    /**
     * Pour créer un menu
     */
    @Override
	public boolean onCreateOptionsMenu (Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.xml.menu_catalog, menu);
		
		return true;
	}
	
	void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
    	
      

    	
        switch (item.getItemId()) {
        
           
        case R.id.menu_mark:
        	
        	

        	final CharSequence[] items = {"读取书签", "取消"};

        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setTitle("书签操作");
        	builder.setItems(items, new DialogInterface.OnClickListener() {
        	    public void onClick(DialogInterface dialog, int item) {
        	    	
        	    	
        	    	
        	    	if(item == 0){
        	    	
        	    		
        	    		LireXML();
        	    	}
        	    	
        	       
        	    }
        	});
        	AlertDialog alert = builder.create();
        	
        	alert.show();
            
            return true;
            
            
        case R.id.menu_auther:
        	
      
        	new HelpDialog(CatalogActivity.this).show();
        	
        	
        	
            return true;
            
            
       
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    

 // lire un fichier XML fait la liason avec Hashmap

 static Element racine = new Element("DESMARQUEDINAMIQUE");
 static org.jdom.Document document = new Document(racine);
    
    public  void LireXML(){
    	
    	CharSequence[] items ;
    	final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	SimpleAdapter listItemAdapter = null;
    	
    	//On cree une instance de SAXBuilder
    	SAXBuilder sxb = new SAXBuilder();
    	try
    	{
    		FileInputStream fin = this.getBaseContext().openFileInput("bookmark.xml");
    		//System.out.println("hi i m document123254455");
    		document = sxb.build(fin);
    		listItem = new ArrayList<HashMap<String, Object>>();
    		racine = document.getRootElement();

    		List<Element> liststring;
    		liststring = racine.getChildren("DESMARQUE");
    		
//    		//List listEtudiants = racine.getChildren("string");
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
    	            
    	           
    		
    	            listItemAdapter = new SimpleAdapter(CatalogActivity.this,listItem, R.layout.bookmark,//ListItem的XML实现  
    	                      
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
    						in.setClass(CatalogActivity.this, YearsActivity.class);
    						in.putExtras(bundle);
    						
    						
    						
    					    //  显示Activity1
    					    startActivity(in);
    					    CatalogActivity.this.onDestroy();
    					   // System.out.println("je suis hahahahaha");
    					  
    					 
    		    	 
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
    		showToast("您还没有添加书签"); 	
    	}
    	
    		
    	//return null;
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	}
    // end of LireXML
    
    
    
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	
		  if(keyCode == KeyEvent.KEYCODE_BACK){
	    	   
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
			  
		  }
		  
		return false;
	//而当返回false时，表示并没有完全处理完该事件，更希望其他回调方法继续对其进行处理
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
	   }
	   
	   
	   public void close()
	   {
	     Intent intent = new Intent();
	     intent.setAction("ExitApp");
	       this.sendBroadcast(intent);
	     super.finish();
	   }
	   
    

}//end of Activity
