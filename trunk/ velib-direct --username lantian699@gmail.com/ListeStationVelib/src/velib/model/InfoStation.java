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

import velib.tools.ParserInfoStation;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import android.R.bool;
import android.content.Context;

import essai.cnam.ListeStationVelibActivity;

@DatabaseTable()
public class InfoStation extends ModelObject {

	private static final long serialVersionUID = 4526974254086931419L;
	
	public static final String COLUMN_INFO_ID = "id";
	public static final String COLUMN_INFO_FREE = "free";
	public static final String COLUMN_INFO_OPEN = "open";
	public static final String COLUMN_INFO_TICKET = "ticket";
	public static final String COLUMN_INFO_AVAILABLE = "available";
	public static final String COLUMN_INFO_TOTAL = "total";
	public static final String COLUMN_INFO_UPDATED = "updated";
	public static final String COLUMN_INFO_ID_STATION = "StationVelibId";
	public static final String COLUMN_INFO_ISPREFERED= "isPrefered";
	
	private static Context context;
	
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField()
	private int StationVelibId;
	@DatabaseField
	private long time;
	@DatabaseField
	private int available;
	@DatabaseField
	private int free;
	@DatabaseField
	private int total;
	@DatabaseField
	private boolean ticket;
	@DatabaseField
	private boolean open;
	@DatabaseField
	private int updated;
	@DatabaseField
	private int isPrefered;
	
	

	public int getIsPrefered() {
		return isPrefered;
	}

	public void setIsPrefered(int isPrefered) {
		this.isPrefered = isPrefered;
	}


	
	public InfoStation(){
		
	}
	
	public InfoStation(int numeroDeStation, Context context) {
		this.time = System.currentTimeMillis();
	

	}

	public int getAvailable() {
		return available;
	}

	public int getFree() {
		return free;
	}

	public int getTotal() {
		return total;
	}

	public boolean getTicket() {
		return ticket;
	}
	
	public boolean getOpen(){
		return open;
	}

	public int getUpdated(){
		return updated;
	}
	
	public int getStationVelibId(){
		return this.StationVelibId;
		
	}
	
	public void setStationVelibId(int stationId){
		this.StationVelibId = stationId;
		
	}
	
	public void setAvailable(int available) {
		this.available = available;
	}

	public void setFree(int free) {
		this.free = free;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setTicket(boolean ticket) {
		this.ticket = ticket;
	}

	public void setOpen(boolean open){
		this.open = open;
	}
	
	public void setUpdated(int updated){
		this.updated = updated;
	}
	
	public String toString() {
		return "<" + getAvailable() + "," + getFree() + ">";
	}

	/*private class ParserXML extends DefaultHandler implements Serializable {
		private StringBuffer current;

		public ParserXML(InputStream in) {
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

		public void characters(char[] ch, int start, int length)
				throws SAXException {
			super.characters(ch, start, length);
			current.append(new String(ch, start, length));
		}

		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			super.endElement(uri, localName, qName);

			if (qName.equals("available")) {
				available = Integer.parseInt(current.toString());
			}
			if (qName.equals("free")) {
				free = Integer.parseInt(current.toString());
			}
			if (qName.equals("total")) {
				total = Integer.parseInt(current.toString());
			}
			
			 * if(qName.equals("ticket")){ ticket = current.toString() != null;
			 * }
			 

		}
	}*/

}
