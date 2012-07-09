package velib.model;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;

import java.io.Serializable;
import java.io.File;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;
import org.xml.sax.Attributes;

import velib.tools.ParserListVelib;

import com.j256.ormlite.dao.Dao;

import android.content.Context;
import android.location.Location;
import essai.cnam.ListeStationVelibActivity;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

import java.net.URL;

public class ListeDesStationsVelib{
  

  private static final String URL_VELIB_ALL = "http://www.velib.paris.fr/service/carto";
  private static final String URL_VELIB_INFO= "http://www.velib.paris.fr/service/stationdetails/"; //  /number
  
  HashMap<Integer, Object> stations = new HashMap<Integer, Object>();  
  HashMap<String, Integer> noms = new HashMap<String, Integer>();  
  HashMap<String, String> names = new HashMap<String, String>();  
  ArrayList<String> listItem= new ArrayList<String>();
  InfoStation infostation;
  String [] stNoms= new String[1300];
  Location [] GareVelib = new Location[7000];
  double [] lats = new double[7000];
  double [] longs = new double [7000];
  String[] stAddr =new String[1300];
  int [] stNum = new int [7000];
  int i=0;
  private Context context;
  Dao<StationVelib, Integer> dao;
  // ---------------------------------------------------------------------------
  
 
  public ListeDesStationsVelib(InputStream in, Context context) throws Exception{
	
	 // new ParserXML(in);
	  this.context = context;
	  new ParserListVelib(context,dao);
	}
  
  
  public  ArrayList<String> getList(){
	  
	  
	  
	 // System.out.println("celection = "+names);
	return listItem;
	  
  }
 public StationVelib lireStation(String name){
	  
	 
	  
	  return  (StationVelib) stations.get(noms.get(name));
		 }
  public String[] getAddr(){
	  
	  return stAddr;
  }
 
 public String[] getNoms(){
	 
	 return stNoms;
 }
 public double[] getLats(){
	 
	 return lats;
 }
 
 public double[] getLongs(){
	 return longs;
 }
  
 public Location[] getGareVelib(){
	 
	 return GareVelib;
	 
 }
 
 public int getLength(){
	 
	 
	 return i;
	 
 }
 
 public int[] getNum(){ 
	 
	 return stNum;
 }
 
 
  public InfoStation info(StationVelib st){
	  
	  infostation = new InfoStation(st.getNumber(), context);
	  
	return infostation;
	  
  }
  
  
  
  
  
/*  private class ParserXML  extends DefaultHandler implements Serializable{
    
	  *//**
	 * 
	 *//*
	private static final long serialVersionUID = -1153056183204156770L;
	URL url = new URL(URL_VELIB_ALL);
    public ParserXML(InputStream in) throws Exception{
    //	public ParserXML() throws Exception{
      SAXParserFactory spf = SAXParserFactory.newInstance();
		  SAXParser sp = spf.newSAXParser();
		  XMLReader xr = sp.getXMLReader();
		  xr.setContentHandler(this);
		 // xr.parse(new InputSource(url.openStream()));
		  xr.parse(new InputSource(in));
    }
    
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
  		super.startElement(uri, localName, qName, attributes);
  		
  		if(qName.equals("marker")){
  			StationVelib station = new StationVelib();
  		
  			station.setName(attributes.getValue("name"));
  			station.setNumber(Integer.parseInt(attributes.getValue("number")));
  			station.setAddress(attributes.getValue("address"));
  			station.setLatitude(Double.parseDouble(attributes.getValue("lat")));
  			station.setLongitude(Double.parseDouble(attributes.getValue("lng")));
  			
  			if(attributes.getValue("bonus").equals("1"))
  				station.setBonus(true);
  			 else
  				station.setBonus(false);
  			
  			if(attributes.getValue("open").equals("1"))
  				station.setOpen(true);
  			 else
  				station.setOpen(false);
  			
  			try {
				Dao<StationVelib, Integer> VelibStationDao = DatabaseHelper.getInstance(context).getDao(StationVelib.class);
				//List<StationVelib> list = VelibStationDao.queryForAll();
				VelibStationDao.create(station);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  			
			  stations.put(station.getNumber(),station);
			  noms.put(station.getName(),station.getNumber());
			
			  //listItem = new ArrayList<String>(); 
			  
			  listItem.add(station.getName());
			  //System.out.println("listitem = "+listItem);
		//	  System.out.println("latsdqsdqsdqsd  = "+station.getLatitude()+"   "+i);
			  
			  //GareVelib[i].
			 // GareVelib[i].setLatitude(station.getLatitude());
			 // GareVelib[i++].setLongitude(station.getLongitude());
			  
			  //stationSelectionne[i].setName(station.getName());
			  stAddr[i]=station.getAddress();
		      stNoms[i]=station.getName(); 
			  lats[i]=station.getLatitude();
			  stNum[i] = station.getNumber();
		       longs[i++]=station.getLongitude();
		      // System.out.println("i="+i);
		       
  		}
	  }
    
  }//end of parseXML
  
  */
  
  
}
