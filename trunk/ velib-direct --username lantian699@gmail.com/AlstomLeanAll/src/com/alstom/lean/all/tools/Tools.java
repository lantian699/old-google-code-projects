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
		java.lang.System.out.println("url = "+listUrl);
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
			}else if(table.toString().equals(Worksheet.TABLE_NAME_BLOCK)){

				Block block = new Block();
				
				block.setName(content.get(Worksheet.TABLE_BLOCK_COLUMN_NAME));
				block.setBlockId(content.get(Worksheet.TABLE_BLOCK_COLUMN_ID));
				block.setPlantId(content.get(Worksheet.TABLE_PLANT_COLUMN_ID));
				
				blockDao.create(block);				
			}else if(table.toString().equals(Worksheet.TABLE_NAME_UNIT)){
				Unit unit = new Unit();
						
				unit.setName(content.get(Worksheet.TABLE_UNIT_COLUMN_NAME));
				unit.setUntiId(content.get(Worksheet.TABLE_UNIT_COLUMN_ID));
				unit.setBlockId(content.get(Worksheet.TABLE_BLOCK_COLUMN_ID));
						
				unitDao.create(unit);				
			}else if(table.toString().equals(Worksheet.TABLE_NAME_SYSTEM)){	
				Systems system = new Systems();
				
				system.setName(content.get(Worksheet.TABLE_SYSTEM_COLUMN_NAME));
				system.setSystemId(content.get(Worksheet.TABLE_SYSTEM_COLUMN_ID));
				system.setType(content.get(Worksheet.TABLE_SYSTEM_COLUMN_TYPE));
				system.setUnitId(content.get(Worksheet.TABLE_UNIT_COLUMN_ID));
				
				systemDao.create(system);
			}else if(table.toString().equals(Worksheet.TABLE_NAME_CP1)){	
				ComponentLevel1 cp1 = new ComponentLevel1();
				
				cp1.setName(content.get(Worksheet.TABLE_CP1_COLUMN_NAME));
				cp1.setCP1Id(content.get(Worksheet.TABLE_CP1_COLUMN_ID));
				cp1.setSystemId(content.get(Worksheet.TABLE_SYSTEM_COLUMN_ID));
				
				cp1Dao.create(cp1);
			}else if(table.toString().equals(Worksheet.TABLE_NAME_CP2)){	
				ComponentLevel2 cp2 = new ComponentLevel2();
				
				cp2.setName(content.get(Worksheet.TABLE_CP2_COLUMN_NAME));
				cp2.setCP1Id(content.get(Worksheet.TABLE_CP2_COLUMN_ID));
				cp2.setCP1Id(content.get(Worksheet.TABLE_CP1_COLUMN_ID));
				
				cp2Dao.create(cp2);
			}else if(table.toString().equals(Worksheet.TABLE_NAME_CP3)){	
				ComponentLevel3 cp3 = new ComponentLevel3();
				
				cp3.setName(content.get(Worksheet.TABLE_CP3_COLUMN_NAME));
				cp3.setCP3Id(content.get(Worksheet.TABLE_CP3_COLUMN_ID));
				cp3.setCP2Id(content.get(Worksheet.TABLE_CP2_COLUMN_ID));
				
				cp3Dao.create(cp3);
			}else if(table.toString().equals(Worksheet.TABLE_NAME_PROJECT)){	
				Project project = new Project();
				
				project.setLocation(content.get(Worksheet.TABLE_PROJECT_COLUMN_LOCATION));
				project.setName(content.get(Worksheet.TABLE_PROJECT_COLUMN_NAME));
				
				projectDao.create(project);
			}else if(table.toString().equals(Worksheet.TABLE_NAME_PERSON)){	
				Person person = new Person();
				
				person.setName(content.get(Worksheet.TABLE_PERSON_COLUMN_NAME));
				person.setProfile(content.get(Worksheet.TABLE_PERSON_COLUMN_PROFILE));
				person.setTelNumber(content.get(Worksheet.TABLE_PERSON_COLUMN_TEL));
				person.setEmail(content.get(Worksheet.TABLE_PERSON_COLUMN_EMAIL));
				
				personDao.create(person);
			}else if(table.toString().equals(Worksheet.TABLE_NAME_TASK)){	
				Task task = new Task();
				
				task.setBegin(content.get(Worksheet.TABLE_TASK_COLUMN_BEGIN));
				task.setEnd(content.get(Worksheet.TABLE_TASK_COLUMN_END));
				task.setName(content.get(Worksheet.TABLE_TASK_COLUMN_NAME));
				task.setStatus(content.get(Worksheet.TABLE_TASK_COLUMN_STATUS));
				task.setParentProject(content.get(Worksheet.TABLE_TASK_COLUMN_PROJECT_NAME));
				task.setType(content.get(Worksheet.TABLE_TASK_COLUMN_TYPE));
				taskDao.create(task);
			
			}else if(table.toString().equals(Worksheet.TABLE_NAME_VISUALINSPECTION)){	
				VisualInspection inspection = new VisualInspection();
				
				inspection.setKey(content.get(Worksheet.TABLE_INSPECTION_COLUMN_KEY));
				inspection.setDescription(content.get(Worksheet.TABLE_INSPECTION_COLUMN_DESCRIPTION));
				inspection.setValue(content.get(Worksheet.TABLE_INSPECTION_COLUMN_VALUE));
				inspection.setParent(content.get(Worksheet.TABLE_INSPECTION_COLUMN_PARENT));
				
				inspectionDao.create(inspection);
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
				
				mesurementDao.create(mesurement);
				
				
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
	

}
