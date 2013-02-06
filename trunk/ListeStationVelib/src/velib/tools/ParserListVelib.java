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

import velib.model.DatabaseHelper;
import velib.model.InfoStation;
import velib.model.StationVelib;
import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class ParserListVelib extends DefaultHandler implements Serializable {
	
	private static final long serialVersionUID = -1153056183204156770L;
	private static final String URL_VELIB_ALL = "http://www.velib.paris.fr/service/carto";
	
	
	private Context context;
	
	private URL url = new URL(URL_VELIB_ALL);
	private StationVelib station = new StationVelib();
	private Dao<StationVelib, Integer> StationVelibDao;
	
	
	public ParserListVelib(Context context, Dao<StationVelib, Integer> StationVelibDao) throws Exception {
		
		this.context = context;
		this.StationVelibDao = StationVelibDao;
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();
		xr.setContentHandler(this);
		xr.parse(new InputSource(url.openStream()));

	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);

		if (qName.equals("marker")) {
			

			station.setName(Tools.StringUtilsSeperator(attributes.getValue("name")));
			station.setNumber(Integer.parseInt(attributes.getValue("number")));
			station.setAddress(attributes.getValue("address"));
			station.setFullAddress(attributes.getValue("fullAddress"));
			station.setLatitude(Double.parseDouble(attributes.getValue("lat")));
			station.setLongitude(Double.parseDouble(attributes.getValue("lng")));
			station.setIsPrefered(0);

			if (attributes.getValue("bonus").equals("1"))
				station.setBonus(true);
			else
				station.setBonus(false);

			if (attributes.getValue("open").equals("1"))
				station.setOpen(true);
			else
				station.setOpen(false);

			try {
				
				/*Dao<StationVelib, Integer> StationVelibDao = DatabaseHelper.getInstance(context).getDao(StationVelib.class);
				QueryBuilder<StationVelib, Integer> queryBuilder = StationVelibDao.queryBuilder();
				queryBuilder.where().eq(StationVelib.COLUMN_VELIB_NAME, station.getName());
				PreparedQuery<StationVelib> preparedQuery = queryBuilder.prepare();
				List<StationVelib> listStation = StationVelibDao.query(preparedQuery);
				
				if(listStation.size() == 0)
					StationVelibDao.create(station);
				else
					StationVelibDao.update(station);*/
				
				
				
				StationVelibDao.create(station);
				Log.i(this, "station " +station.getId()+" "+station.getName());
				
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}
	}
	
	
	


}