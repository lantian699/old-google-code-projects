package velib.tools;

import java.io.Serializable;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.j256.ormlite.dao.Dao;

import velib.model.DatabaseHelper;
import velib.model.InfoStation;

import android.content.Context;

public class ParserInfoStation extends DefaultHandler implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -255659017452893205L;
	
	
	private StringBuffer current;
	private int available;
	private int free;
	private int total;
	private static final String URL_INFO = "http://www.velib.paris.fr/service/stationdetails/";
	private InfoStation infoStation = new InfoStation();
	private boolean ticket;
	private boolean open;
	private Context context;


	private Dao<InfoStation, ?> infoStationDao;
	
	
	public ParserInfoStation(Context context, Dao<InfoStation, ?> dao, int num) {
		try {
			this.infoStationDao = dao;
			this.context = context;
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			xr.setContentHandler(this);
			xr.parse(new InputSource(new URL(URL_INFO+num).openStream()));
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
			infoStation.setAvailable(available);
		}
		if (qName.equals("free")) {
			free = Integer.parseInt(current.toString());
			infoStation.setFree(free);
		}
		if (qName.equals("total")) {
			total = Integer.parseInt(current.toString());
			infoStation.setTotal(total);
		}
		if (qName.equals("ticket")) {
			if(Integer.parseInt(current.toString()) == 1)
				ticket = true;
				else
					ticket = false;
			infoStation.setTicket(ticket);
		}
		if (qName.equals("open")) {
			if(Integer.parseInt(current.toString()) == 1)
				open = true;
				else
					open = false;
			infoStation.setOpen(open);
			
			try {
				infoStationDao.create(infoStation);
				//Log.i(this, "une infoStation est cree --->  velib.db");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		/*
		 * if(qName.equals("ticket")){ ticket = current.toString() != null;
		 * }
		 */

	}
}