package velib.model;


import java.net.URL;
import java.io.*;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;

import essai.cnam.ListeStationVelibActivity;


public class InfoStation implements Serializable{
  private static final long serialVersionUID = 5263116456578091144L;
  private long time;
  private int available;
  private int free;
  private int total;
  private boolean ticket;
  
  private static Context context;
  
  public InfoStation(StationVelib st){
    this(st.getNumber());
  }
  
  public InfoStation(int numeroDeStation){
    this.time = System.currentTimeMillis();
    try{
    	URL url = new	 URL("http://www.velib.paris.fr/service/stationdetails/"+numeroDeStation);
      new ParserXML(url.openStream());
    }catch(Exception e){
       // en mode d�grad�
      
    }
     
  }
  
   
  public int getAvailable(){
    return available;
  }
  public int getFree(){
    return free;
  }
  public int getTotal(){
    return total;
  }
  public boolean getTicket(){
    return ticket;
  }
  
  public void setAvailable(int available){
    this.available = available;
  }
  
  public void setFree(int free){
    this.free = free;
  }
  public void setTotal(int total){
    this.total = total;
  }
  
  public void setTicket(boolean ticket){
    this.ticket = ticket;
  }
  
  public String toString(){
	  return "<" + getAvailable() + "," + getFree() + ">";
	 }
  
  private class ParserXML extends DefaultHandler implements Serializable {
    private StringBuffer current;
    	
    public ParserXML(InputStream in){
    	try {
    		SAXParserFactory spf = SAXParserFactory.newInstance();
    		SAXParser sp = spf.newSAXParser();
    		XMLReader xr = sp.getXMLReader();
    		xr.setContentHandler(this);
    		xr.parse(new InputSource(in));
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		  super.startElement(uri, localName, qName, attributes);
		  current = new StringBuffer();
	  }
	

	  public void characters(char[] ch, int start, int length) throws SAXException {
		  super.characters(ch, start, length);
		  current.append(new String(ch, start, length));
	  }

    public void endElement(String uri, String localName, String qName)
    		throws SAXException {
    	super.endElement(uri, localName, qName);
    	
    	if(qName.equals("available")){
    		available = Integer.parseInt(current.toString());
    	}
    	if(qName.equals("free")){
    		free = Integer.parseInt(current.toString());
    	}
    	if(qName.equals("total")){
    		total = Integer.parseInt(current.toString());
    	}
    	/*if(qName.equals("ticket")){
    		ticket = current.toString() != null;
    	}*/
  	

    }
  }
 
}
