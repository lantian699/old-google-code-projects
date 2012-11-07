package com.app.capgemini.appgeneral.models.tools;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;


import com.app.capgemini.appgeneral.models.DatabaseHelper;
import com.app.capgemini.appgeneral.models.Project;
import com.app.capgemini.appgeneral.spreadsheet.SpreadsheetAndroidRequestInitializer;
import com.app.capgemini.appgeneral.spreadsheet.Worksheet;
import com.app.capgemini.appgeneral.spreadsheet.Worksheet.Table;
import com.google.api.services.spreadsheet.client.SpreadsheetClient;
import com.google.api.services.spreadsheet.model.ListEntry;
import com.google.api.services.spreadsheet.model.ListFeed;
import com.google.api.services.spreadsheet.url.ListUrl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class Tools {

	public static final String PREF_SPREADSHEET_KEY = "spreadsheetKey";
	public static final String PREF_SPREADSHEET_KEY_TOTO = "spreadsheetKey_toto";
	public static final String PREF_SPREADSHEET_KEY_QIQI = "spreadsheetKey_qiqi";
	public static final String PREF_SPREADSHEET_KEY_MIMI = "spreadsheetKey_mimi";
	public static final String _UPDATED = "updated";
	public static final String KEY_SIEBEL_ID = "siebelid";
	public static final String TAG = "Tools";
	private static Dao<Project, ?> clientDao;

	/*public static void DrawOneStationOnMap(Context context, MapView mapView,
			Recouvrement recouvert, DatabaseHelper dataHelper) {

		try {
			clientDao = dataHelper.getDao(Client.class);
			QueryBuilder<Client, ?> queryBuilder = clientDao.queryBuilder();
			queryBuilder.where().eq(Client.COLUMN_NAME_CLIENT_ID,
					recouvert.getClientId());
			PreparedQuery<Client> preparedQuery = queryBuilder.prepare();
			List<Client> listClient = clientDao.query(preparedQuery);
			
			if (listClient.size() > 0) {
				Client client = listClient.get(0);
				
				GeoPoint point = getLatFromAddress(context, client.getAdresse());
				System.out.println("point = " + point.getLatitudeE6()+ " addr= "+client.getAdresse());
				List<Overlay> mapOverlays = mapView.getOverlays();

				Drawable drawable = context.getResources().getDrawable(R.drawable.pin);
				MapItemizedOverlay velibItemizedOverlay = new MapItemizedOverlay(drawable, context, mapView);


				OverlayItem overlayitem = new OverlayItem(point,client.getNom(), client.getAdresse());

				velibItemizedOverlay.addOverlay(overlayitem);

				mapOverlays.add(velibItemizedOverlay);

				mapView.postInvalidate();

				MapController mapController = mapView.getController();
				mapController.animateTo(point);
				mapController.setZoom(10);
			}

			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public static  GeoPoint getLatFromAddress(Context context, String strAddress){
		
		Geocoder coder = new Geocoder(context);
		List<Address> address;

		try {
		    address = coder.getFromLocationName(strAddress,5);
		    if (address == null) {
		        return null;
		    }
		    Address location = address.get(0);
		    location.getLatitude();
		    location.getLongitude();

		    GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6),
		                      (int) (location.getLongitude() * 1E6));

		     return point;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
*/
	public static void getAll(DatabaseHelper dataHelper,
			SpreadsheetAndroidRequestInitializer requestInitializer,
			Table table) {
		try {
			String spreadsheetKey = requestInitializer.settings.getString(PREF_SPREADSHEET_KEY, null);
			SpreadsheetClient spreadsheetClient = new SpreadsheetClient(requestInitializer.createRequestFactory());
			ListUrl listUrl = ListUrl.forListFeedByKey(spreadsheetKey,table.toString());

			ListFeed listFeed = spreadsheetClient.listFeed().list().execute(listUrl);


			Dao<Project, ?> projectDao = dataHelper.getDao(Project.class);
			for (ListEntry listEntry : listFeed.getEntries()) {

				Map<String, String> content = listEntry.customElements;

				if (table.toString().equals(Worksheet.TABLE_NAME_PROJECT)) {
					Project project = new Project();
					
					project.setProjectId(content.get(Worksheet.TABLE_PROJECT_COLUMN_PROJECT_ID));
					project.setStartDate(content.get(Worksheet.TABLE_PROJECT_COLUMN_START_DATE));
					project.setEndDate(content.get(Worksheet.TABLE_PROJECT_COLUMN_END_DATE));
					project.setName(content.get(Worksheet.TABLE_PROJECT_COLUMN_NAME));
					project.setLocation(content.get(Worksheet.TABLE_PROJECT_COLUMN_LOCATION));
				
					projectDao.create(project);
					
					
					Log.i(TAG, "Client created !");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/*public static void sendAll(DatabaseHelper dataHelper,
			SpreadsheetAndroidRequestInitializer requestInitializer, Table table, User user) {

		try {
			Dao<Task, ?> taskDao = dataHelper.getDao(Task.class);
			Dao<VisualInspection, ?> inspectionDao = dataHelper
					.getDao(VisualInspection.class);
			Dao<Mesurement, ?> mesurementDao = dataHelper
					.getDao(Mesurement.class);

			// String spreadsheetKey =
			// requestInitializer.settings.getString(PREF_SPREADSHEET_KEY,
			// null);
			SpreadsheetClient client = new SpreadsheetClient(
					requestInitializer.createRequestFactory());
			String spreadsheetKey = requestInitializer.settings.getString(
					user.toString(), null);
			ListUrl listUrl = ListUrl.forListFeedByKey(spreadsheetKey,
					table.toString());

			if (table.toString().equals(Worksheet.TABLE_NAME_TASK)) {
				List<Task> listTask = taskDao.queryForAll();

				for (Task task : listTask) {

					ListEntry listEntryModified = null;
					if (task.getSelfUrl() != null) {
						listEntryModified = client.listEntry().get()
								.execute(new ListUrl(task.getSelfUrl()));

						listEntryModified.setCustomElement(
								Worksheet.TABLE_TASK_COLUMN_NAME,
								task.getName());
						listEntryModified.setCustomElement(
								Worksheet.TABLE_TASK_COLUMN_BEGIN,
								task.getBegin());
						listEntryModified.setCustomElement(
								Worksheet.TABLE_TASK_COLUMN_END, task.getEnd());
						// listEntryModified.setCustomElement(Worksheet.TABLE_TASK_COLUMN_PROJECT_NAME,
						// task.getParentProject());
						// listEntryModified.setCustomElement(Worksheet.TABLE_TASK_COLUMN_REQUIRE_WITNESS_POINT,
						// task.isRequiresWitnessPoint());
						listEntryModified.setCustomElement(
								Worksheet.TABLE_TASK_COLUMN_STATUS,
								task.getStatus());
						listEntryModified.setCustomElement(
								Worksheet.TABLE_TASK_COLUMN_TYPE,
								task.getType());
						if (task.getType().equals("finding")) {
							listEntryModified.setCustomElement(
									Worksheet.TABLE_TASK_COLUMN_STATUS,
									task.getStatus());
							listEntryModified
									.setCustomElement(
											Worksheet.TABLE_TASK_COLUMN_REQUIRE_WITNESS_POINT,
											task.getRequiresWitnessPoint());
							listEntryModified.setCustomElement(
									Worksheet.TABLE_TASK_COLUMN_RELATED_TASK,
									task.getRelatedTask());
							listEntryModified.setCustomElement(
									Worksheet.TABLE_TASK_COLUMN_RESPONSIBLE,
									task.getResponsible());
						}

						if (task.getAttachment() != null)
							listEntryModified.setCustomElement(
									Worksheet.TABLE_TASK_COLUMN_ATTACHMENT,
									task.getAttachment());

						client.listFeed().update().execute(listEntryModified);
					} else {

						ListEntry entry = new ListEntry();
						entry.addCustomElement(
								Worksheet.TABLE_TASK_COLUMN_NAME,
								task.getName());
						entry.addCustomElement(
								Worksheet.TABLE_TASK_COLUMN_BEGIN,
								task.getBegin());
						entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_END,
								task.getEnd());
						// entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_PROJECT_NAME,
						// task.getParentProject());
						// entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_REQUIRE_WITNESS_POINT,
						// task.isRequiresWitnessPoint());
						entry.addCustomElement(
								Worksheet.TABLE_TASK_COLUMN_STATUS,
								task.getStatus());
						entry.addCustomElement(
								Worksheet.TABLE_TASK_COLUMN_TYPE,
								task.getType());
						if (task.getType().equals("finding")) {
							entry.addCustomElement(
									Worksheet.TABLE_TASK_COLUMN_STATUS,
									task.getStatus());
							entry.addCustomElement(
									Worksheet.TABLE_TASK_COLUMN_REQUIRE_WITNESS_POINT,
									task.getRequiresWitnessPoint());
							entry.addCustomElement(
									Worksheet.TABLE_TASK_COLUMN_RELATED_TASK,
									task.getRelatedTask());
							entry.addCustomElement(
									Worksheet.TABLE_TASK_COLUMN_RESPONSIBLE,
									task.getResponsible());
						}

						if (task.getAttachment() != null)
							entry.addCustomElement(
									Worksheet.TABLE_TASK_COLUMN_ATTACHMENT,
									task.getAttachment());

						ListEntry entryInsert = client.listFeed().insert()
								.execute(listUrl, entry);
						task.setSelfUrl(entryInsert.getSelfLink());
						taskDao.update(task);
					}

				}
			} else if (table.toString().equals(
					Worksheet.TABLE_NAME_VISUALINSPECTION)) {

				List<VisualInspection> listVi = inspectionDao.queryForAll();

				
				 * for (VisualInspection vi : listVi) { ListEntry
				 * listEntryModified = null; if(vi.getSelfUrl() != null){
				 * listEntryModified = client.listEntry().get().execute(new
				 * ListUrl(vi.getSelfUrl()));
				 * 
				 * // listEntryModified.setCustomElement(Worksheet.
				 * TABLE_INSPECTION_COLUMN_DESCRIPTION, vi.getDescription()); //
				 * listEntryModified
				 * .setCustomElement(Worksheet.TABLE_INSPECTION_COLUMN_KEY,
				 * vi.getKey()); //
				 * listEntryModified.setCustomElement(Worksheet.
				 * TABLE_INSPECTION_COLUMN_PARENT, vi.getDescription()); //
				 * listEntryModified
				 * .setCustomElement(Worksheet.TABLE_INSPECTION_COLUMN_VALUE,
				 * vi.getValue()); }
				 * client.listFeed().update().execute(listEntryModified);
				 * 
				 * }
				 

			} else if (table.toString().equals(Worksheet.TABLE_NAME_MESUREMENT)) {
				List<Mesurement> listMesure = mesurementDao.queryForAll();

				for (Mesurement mesure : listMesure) {
					ListEntry listEntryModified = null;
					if (mesure.getSelfUrl() != null) {
						listEntryModified = client.listEntry().get()
								.execute(new ListUrl(mesure.getSelfUrl()));
						listEntryModified.setCustomElement(
								Worksheet.TABLE_MESUREMENT_COLUMN_VALUE,
								mesure.getValue());
						listEntryModified.setCustomElement(
								Worksheet.TABLE_MESUREMENT_COLUMN_TIME,
								mesure.getTimeStamp());

						client.listFeed().update().execute(listEntryModified);
					} else {

						ListEntry entry = new ListEntry();
						entry.addCustomElement(
								Worksheet.TABLE_MESUREMENT_COLUMN_DESCRIPTION,
								mesure.getDescription());
						entry.addCustomElement(
								Worksheet.TABLE_MESUREMENT_COLUMN_VALUE,
								mesure.getValue());
						entry.addCustomElement(
								Worksheet.TABLE_MESUREMENT_COLUMN_TIME,
								mesure.getTimeStamp());
						entry.addCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_UNIT, mesure.getUnit());
						entry.addCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_RULE, mesure.getRule());
						entry.addCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_LOW, mesure.getLow());
						entry.addCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_HIGH, mesure.getHigh());
						entry.addCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_TYPE, mesure.getType());
						entry.addCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_PARENT, mesure.getParent());
						
						ListEntry entryInsert = client.listFeed().insert()
						.execute(listUrl, entry);
						mesure.setSelfUrl(entryInsert.getSelfLink());
						mesurementDao.update(mesure);

					}
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/

}
