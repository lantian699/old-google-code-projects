package com.alstom.lean.all.tools;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import com.alstom.lean.all.R;
import com.alstom.lean.all.models.Block;
import com.alstom.lean.all.models.ComponentLevel1;
import com.alstom.lean.all.models.ComponentLevel2;
import com.alstom.lean.all.models.ComponentLevel3;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Factory;
import com.alstom.lean.all.models.Mesurement;
import com.alstom.lean.all.models.Person;
import com.alstom.lean.all.models.Plant;
import com.alstom.lean.all.models.Project;
import com.alstom.lean.all.models.Systems;
import com.alstom.lean.all.models.Task;
import com.alstom.lean.all.models.Unit;
import com.alstom.lean.all.models.VisualInspection;
import com.alstom.lean.all.spreadsheet.SpreadsheetAndroidRequestInitializer;
import com.alstom.lean.all.spreadsheet.Worksheet;
import com.alstom.lean.all.spreadsheet.Worksheet.Table;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.services.spreadsheet.client.SpreadsheetClient;
import com.google.api.services.spreadsheet.model.ListEntry;
import com.google.api.services.spreadsheet.model.ListFeed;
import com.google.api.services.spreadsheet.url.ListUrl;

import com.j256.ormlite.dao.Dao;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Tools {
	
	private static final String PREF_SPREADSHEET_KEY = "spreadsheetKey";
	public static final String _UPDATED = "updated";
    public static final String KEY_SIEBEL_ID = "siebelid";
    public static final String TAG = "Tools";
	
    
	public static void  DrawOneStationOnMap(Activity context,Factory factory , MapView mapView) {

		List<Overlay> mapOverlays = mapView.getOverlays();
			
		
		Drawable drawable = context.getResources().getDrawable(R.drawable.pointer);
		VelibItemizedOverlay velibItemizedOverlay = new VelibItemizedOverlay(drawable, context, mapView);

				GeoPoint StationPos = new GeoPoint((int) (factory.getLatitude() * 1E6),(int) (factory.getLongitude() * 1E6));
		
				
				OverlayItem overlayitem = new OverlayItem(StationPos, factory.getName(), factory.getAddress());

				velibItemizedOverlay.addOverlay(overlayitem);
				
				mapOverlays.add(velibItemizedOverlay);
			
				mapView.postInvalidate();
			
				MapController mapController = mapView.getController();
				mapController.animateTo(StationPos);
				mapController.setZoom(10);
			



	}
	
	
	public static void getAll(DatabaseHelper dataHelper, SpreadsheetAndroidRequestInitializer requestInitializer, Table table){
		
		
		try {
		String spreadsheetKey = requestInitializer.settings.getString(PREF_SPREADSHEET_KEY, null);
		SpreadsheetClient client = new SpreadsheetClient(requestInitializer.createRequestFactory());
		ListUrl listUrl = ListUrl.forListFeedByKey(spreadsheetKey, table.toString());
//		java.lang.System.out.println("url = "+listUrl);
		ListFeed listFeed = client.listFeed().list().execute(listUrl);
		
		Dao<Plant, ?> plantDao = dataHelper.getDao(Plant.class);
		Dao<Block, ?> blockDao = dataHelper.getDao(Block.class);
		Dao<Unit, ?> unitDao = dataHelper.getDao(Unit.class);
		Dao<Systems, ?> systemDao = dataHelper.getDao(Systems.class);
		Dao<ComponentLevel1, ?> cp1Dao = dataHelper.getDao(ComponentLevel1.class);
		Dao<ComponentLevel2, ?> cp2Dao = dataHelper.getDao(ComponentLevel2.class);
		Dao<ComponentLevel3, ?> cp3Dao = dataHelper.getDao(ComponentLevel3.class);
		Dao<Project, ?> projectDao = dataHelper.getDao(Project.class);
		Dao<Person, ?> personDao = dataHelper.getDao(Person.class);
		Dao<Task, ?> taskDao = dataHelper.getDao(Task.class);
		Dao<VisualInspection, ?> inspectionDao = dataHelper.getDao(VisualInspection.class);
		Dao<Mesurement, ?> mesurementDao = dataHelper.getDao(Mesurement.class);

		
		for (ListEntry listEntry:listFeed.getEntries()){
					
			Map<String, String> content = listEntry.customElements;
					
			
			if(table.toString().equals(Worksheet.TABLE_NAME_PLANT)){
				Plant plant = new Plant();
				
				plant.setName(content.get(Worksheet.TABLE_PLANT_COLUMN_NAME));
				plant.setPlantId(content.get(Worksheet.TABLE_PLANT_COLUMN_ID));
				plant.setProjectName(content.get(Worksheet.TABLE_PLANT_COLUMN_PROJECT_NAME));
				
				plantDao.create(plant);
				Log.i(TAG, "Plant created !" );
			}else if(table.toString().equals(Worksheet.TABLE_NAME_BLOCK)){

				Block block = new Block();
				
				block.setName(content.get(Worksheet.TABLE_BLOCK_COLUMN_NAME));
				block.setBlockId(content.get(Worksheet.TABLE_BLOCK_COLUMN_ID));
				block.setPlantId(content.get(Worksheet.TABLE_PLANT_COLUMN_ID));
				
				blockDao.create(block);	
				Log.i(TAG, "Block created !" );
			}else if(table.toString().equals(Worksheet.TABLE_NAME_UNIT)){
				Unit unit = new Unit();
						
				unit.setName(content.get(Worksheet.TABLE_UNIT_COLUMN_NAME));
				unit.setUntiId(content.get(Worksheet.TABLE_UNIT_COLUMN_ID));
				unit.setBlockId(content.get(Worksheet.TABLE_BLOCK_COLUMN_ID));
						
				unitDao.create(unit);		
				Log.i(TAG, "Unit created !" );
			}else if(table.toString().equals(Worksheet.TABLE_NAME_SYSTEM)){	
				Systems system = new Systems();
				
				system.setName(content.get(Worksheet.TABLE_SYSTEM_COLUMN_NAME));
				system.setSystemId(content.get(Worksheet.TABLE_SYSTEM_COLUMN_ID));
				system.setType(content.get(Worksheet.TABLE_SYSTEM_COLUMN_TYPE));
				system.setUnitId(content.get(Worksheet.TABLE_UNIT_COLUMN_ID));
				
				systemDao.create(system);
				Log.i(TAG, "System created !" );
			}else if(table.toString().equals(Worksheet.TABLE_NAME_CP1)){	
				ComponentLevel1 cp1 = new ComponentLevel1();
				
				cp1.setName(content.get(Worksheet.TABLE_CP1_COLUMN_NAME));
				cp1.setCP1Id(content.get(Worksheet.TABLE_CP1_COLUMN_ID));
				cp1.setSystemId(content.get(Worksheet.TABLE_SYSTEM_COLUMN_ID));
				
				cp1Dao.create(cp1);
				Log.i(TAG, "Component level 1 created !" );
			}else if(table.toString().equals(Worksheet.TABLE_NAME_CP2)){	
				ComponentLevel2 cp2 = new ComponentLevel2();
				
				cp2.setName(content.get(Worksheet.TABLE_CP2_COLUMN_NAME));
				cp2.setCP1Id(content.get(Worksheet.TABLE_CP2_COLUMN_ID));
				cp2.setCP1Id(content.get(Worksheet.TABLE_CP1_COLUMN_ID));
				
				cp2Dao.create(cp2);
				Log.i(TAG, "component level 2 created !" );
			}else if(table.toString().equals(Worksheet.TABLE_NAME_CP3)){	
				ComponentLevel3 cp3 = new ComponentLevel3();
				
				cp3.setName(content.get(Worksheet.TABLE_CP3_COLUMN_NAME));
				cp3.setCP3Id(content.get(Worksheet.TABLE_CP3_COLUMN_ID));
				cp3.setCP2Id(content.get(Worksheet.TABLE_CP2_COLUMN_ID));
				
				cp3Dao.create(cp3);
				Log.i(TAG, "Component level 3 created !" );
			}else if(table.toString().equals(Worksheet.TABLE_NAME_PROJECT)){	
				Project project = new Project();
				
				project.setLocation(content.get(Worksheet.TABLE_PROJECT_COLUMN_LOCATION));
				project.setName(content.get(Worksheet.TABLE_PROJECT_COLUMN_NAME));
				
				projectDao.create(project);
				Log.i(TAG, "Project created !" );
			}else if(table.toString().equals(Worksheet.TABLE_NAME_PERSON)){	
				Person person = new Person();
				
				person.setName(content.get(Worksheet.TABLE_PERSON_COLUMN_NAME));
				person.setProfile(content.get(Worksheet.TABLE_PERSON_COLUMN_PROFILE));
				person.setTelNumber(content.get(Worksheet.TABLE_PERSON_COLUMN_TEL));
				person.setEmail(content.get(Worksheet.TABLE_PERSON_COLUMN_EMAIL));
				person.setParent(content.get(Worksheet.TABLE_PERSON_COLUMN_PARENT));
				personDao.create(person);
				Log.i(TAG, "Person created !" );
			}else if(table.toString().equals(Worksheet.TABLE_NAME_TASK)){	
				Task task = new Task();
				
				task.setSelfUrl(listEntry.getSelfLink());
				task.setUpdateTime(listEntry.updated);
				task.setBegin(content.get(Worksheet.TABLE_TASK_COLUMN_BEGIN));
				task.setEnd(content.get(Worksheet.TABLE_TASK_COLUMN_END));
				task.setName(content.get(Worksheet.TABLE_TASK_COLUMN_NAME));
				task.setStatus(content.get(Worksheet.TABLE_TASK_COLUMN_STATUS));
				task.setParentProject(content.get(Worksheet.TABLE_TASK_COLUMN_PROJECT_NAME));
				task.setType(content.get(Worksheet.TABLE_TASK_COLUMN_TYPE));
				task.setAttachment(content.get(Worksheet.TABLE_TASK_COLUMN_ATTACHMENT));
				task.setDescription(content.get(Worksheet.TABLE_TASK_COLUMN_DESCRIPTION));
				task.setRelatedObject(content.get(Worksheet.TABLE_TASK_COLUMN_RELATED_OBJECT));
				task.setRelatedTask(content.get(Worksheet.TABLE_TASK_COLUMN_RELATED_TASK));
				task.setResponsible(content.get(Worksheet.TABLE_TASK_COLUMN_RESPONSIBLE));
				task.setSignature(content.get(Worksheet.TABLE_TASK_COLUMN_SIGNATURE));
				task.setCustomerName(content.get(Worksheet.TABLE_TASK_COLUMN_CUSTOMER_NAME));
				taskDao.create(task);
				Log.i(TAG, "Task created !" );
			}else if(table.toString().equals(Worksheet.TABLE_NAME_VISUALINSPECTION)){	
				VisualInspection inspection = new VisualInspection();
				
				inspection.setSelfUrl(listEntry.getSelfLink());
				inspection.setUpdateTime(listEntry.updated);
				inspection.setKey(content.get(Worksheet.TABLE_INSPECTION_COLUMN_KEY));
				inspection.setDescription(content.get(Worksheet.TABLE_INSPECTION_COLUMN_DESCRIPTION));
				inspection.setValue(content.get(Worksheet.TABLE_INSPECTION_COLUMN_VALUE));
				inspection.setParent(content.get(Worksheet.TABLE_INSPECTION_COLUMN_PARENT));
				
				inspectionDao.create(inspection);
				Log.i(TAG, "VI created !" );
			}else if(table.toString().equals(Worksheet.TABLE_NAME_MESUREMENT)){	
				Mesurement mesurement = new Mesurement();
				
				mesurement.setSelfUrl(listEntry.getSelfLink());
				mesurement.setUpdateTime(listEntry.updated);
				mesurement.setDescription(content.get(Worksheet.TABLE_MESUREMENT_COLUMN_DESCRIPTION));
				mesurement.setKey(content.get(Worksheet.TABLE_MESUREMENT_COLUMN_KEY));
				mesurement.setLabel(content.get(Worksheet.TABLE_MESUREMENT_COLUMN_LABEL));
				mesurement.setUnit(content.get(Worksheet.TABLE_MESUREMENT_COLUMN_UNIT));
				mesurement.setValue(content.get(Worksheet.TABLE_MESUREMENT_COLUMN_VALUE));
				mesurement.setRule(content.get(Worksheet.TABLE_MESUREMENT_COLUMN_RULE));
				mesurement.setParent(content.get(Worksheet.TABLE_MESUREMENT_COLUMN_PARENT));
				mesurement.setHigh(content.get(Worksheet.TABLE_MESUREMENT_COLUMN_HIGH));
				mesurement.setLow(content.get(Worksheet.TABLE_MESUREMENT_COLUMN_LOW));
				mesurement.setTimeStamp(content.get(Worksheet.TABLE_MESUREMENT_COLUMN_TIME));
				mesurement.setType(content.get(Worksheet.TABLE_MESUREMENT_COLUMN_TYPE));
				
				mesurementDao.create(mesurement);
				Log.i(TAG, "Mesurement created !" );
				
				/*ListEntry listEntryModified = client.listEntry().get().execute(new ListUrl(mesurement.getSelfUrl()));
				listEntryModified.setCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_DESCRIPTION, "this is a test");
				client.listFeed().update().execute(listEntryModified);*/
			}
			
		
		}
		
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	
	public static void sendAll(DatabaseHelper dataHelper, SpreadsheetAndroidRequestInitializer requestInitializer, Table table){
		
		try {
		Dao<Task, ?> taskDao = dataHelper.getDao(Task.class);
		Dao<VisualInspection, ?> inspectionDao = dataHelper.getDao(VisualInspection.class);
		Dao<Mesurement, ?> mesurementDao = dataHelper.getDao(Mesurement.class);
		
//		String spreadsheetKey = requestInitializer.settings.getString(PREF_SPREADSHEET_KEY, null);
		SpreadsheetClient client = new SpreadsheetClient(requestInitializer.createRequestFactory());
		String spreadsheetKey = requestInitializer.settings.getString(PREF_SPREADSHEET_KEY, null);
		ListUrl listUrl = ListUrl.forListFeedByKey(spreadsheetKey, table.toString());
		
		if(table.toString().equals(Worksheet.TABLE_NAME_TASK)){
			List<Task> listTask = taskDao.queryForAll();
			
			for (Task task : listTask) {
				
				ListEntry listEntryModified = null;
				if(task.getSelfUrl() != null){
				listEntryModified = client.listEntry().get().execute(new ListUrl(task.getSelfUrl()));
				
				
				listEntryModified.setCustomElement(Worksheet.TABLE_TASK_COLUMN_NAME, task.getName());
				listEntryModified.setCustomElement(Worksheet.TABLE_TASK_COLUMN_BEGIN, task.getBegin());
				listEntryModified.setCustomElement(Worksheet.TABLE_TASK_COLUMN_END, task.getEnd());
//				listEntryModified.setCustomElement(Worksheet.TABLE_TASK_COLUMN_PROJECT_NAME, task.getParentProject());
//				listEntryModified.setCustomElement(Worksheet.TABLE_TASK_COLUMN_REQUIRE_WITNESS_POINT, task.isRequiresWitnessPoint());
				listEntryModified.setCustomElement(Worksheet.TABLE_TASK_COLUMN_STATUS, task.getStatus());
				listEntryModified.setCustomElement(Worksheet.TABLE_TASK_COLUMN_TYPE, task.getType());
				if(task.getType().equals("finding")){
					listEntryModified.setCustomElement(Worksheet.TABLE_TASK_COLUMN_STATUS, task.getStatus());
					listEntryModified.setCustomElement(Worksheet.TABLE_TASK_COLUMN_REQUIRE_WITNESS_POINT, task.getRequiresWitnessPoint());
					listEntryModified.setCustomElement(Worksheet.TABLE_TASK_COLUMN_RELATED_TASK, task.getRelatedTask());
					listEntryModified.setCustomElement(Worksheet.TABLE_TASK_COLUMN_RESPONSIBLE, task.getResponsible());
				}
				
				if(task.getAttachment() != null )
				listEntryModified.setCustomElement(Worksheet.TABLE_TASK_COLUMN_ATTACHMENT, task.getAttachment());
				
				client.listFeed().update().execute(listEntryModified);
				}else{
		
					ListEntry entry = new ListEntry();
					entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_NAME, task.getName());
					entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_BEGIN, task.getBegin());
					entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_END, task.getEnd());
//					entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_PROJECT_NAME, task.getParentProject());
//					entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_REQUIRE_WITNESS_POINT, task.isRequiresWitnessPoint());
					entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_STATUS, task.getStatus());
					entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_TYPE, task.getType());
					if(task.getType().equals("finding")){
						entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_STATUS, task.getStatus());
						entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_REQUIRE_WITNESS_POINT, task.getRequiresWitnessPoint());
						entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_RELATED_TASK, task.getRelatedTask());
						entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_RESPONSIBLE, task.getResponsible());
					}
					
					if(task.getAttachment() != null )
						entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_ATTACHMENT, task.getAttachment());

					ListEntry entryInsert = client.listFeed().insert().execute(listUrl, entry);
					task.setSelfUrl(entryInsert.getSelfLink());
					taskDao.update(task);
				}
				
				
				
				
				
			}
		}else if(table.toString().equals(Worksheet.TABLE_NAME_VISUALINSPECTION)){
			
			List<VisualInspection> listVi = inspectionDao.queryForAll();
			
			/*for (VisualInspection vi : listVi) {
				ListEntry listEntryModified = null;
				if(vi.getSelfUrl() != null){
				listEntryModified = client.listEntry().get().execute(new ListUrl(vi.getSelfUrl()));
				
//				listEntryModified.setCustomElement(Worksheet.TABLE_INSPECTION_COLUMN_DESCRIPTION, vi.getDescription());
//				listEntryModified.setCustomElement(Worksheet.TABLE_INSPECTION_COLUMN_KEY, vi.getKey());
//				listEntryModified.setCustomElement(Worksheet.TABLE_INSPECTION_COLUMN_PARENT, vi.getDescription());
//				listEntryModified.setCustomElement(Worksheet.TABLE_INSPECTION_COLUMN_VALUE, vi.getValue());
				}
				client.listFeed().update().execute(listEntryModified);
			    
			}*/
			
		}else if(table.toString().equals(Worksheet.TABLE_NAME_MESUREMENT)){
			List<Mesurement> listMesure = mesurementDao.queryForAll();
			
			for (Mesurement mesure : listMesure) {
				ListEntry listEntryModified = null;
				if(mesure.getSelfUrl() != null){
				listEntryModified = client.listEntry().get().execute(new ListUrl(mesure.getSelfUrl()));
				listEntryModified.setCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_VALUE, mesure.getValue());
				listEntryModified.setCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_TIME, mesure.getTimeStamp());
				
				client.listFeed().update().execute(listEntryModified);
				}else {
					
					ListEntry entry = new ListEntry();
					entry.addCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_DESCRIPTION, mesure.getDescription());
					entry.addCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_VALUE, mesure.getValue());
					entry.addCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_TIME, mesure.getTimeStamp());
					
				
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
		
		
		
			
		
	}
	

}
