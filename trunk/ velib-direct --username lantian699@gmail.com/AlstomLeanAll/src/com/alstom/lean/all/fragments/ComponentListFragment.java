package com.alstom.lean.all.fragments;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alstom.lean.all.R;
import com.alstom.lean.all.activities.ImageDisplayActivity;
import com.alstom.lean.all.model3d.Model3DTurbineActivity;
import com.alstom.lean.all.models.Block;
import com.alstom.lean.all.models.ComponentLevel1;
import com.alstom.lean.all.models.ComponentLevel2;
import com.alstom.lean.all.models.ComponentLevel3;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.ModelObject;
import com.alstom.lean.all.models.Plant;
import com.alstom.lean.all.models.Project;
import com.alstom.lean.all.models.Systems;
import com.alstom.lean.all.models.Task;
import com.alstom.lean.all.models.Unit;
import com.alstom.lean.all.pdfviewer.PdfViewerActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ComponentListFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener{
	
	public static final String IMAGE_RESSOURCE_URI = "image_ressource_uri";
	private List<String> listModelName;
	private ModelObject model;
	private ArrayAdapter<String> adapter;
	private DatabaseHelper dataHelper;
	private List<ModelObject> listModel;
	private ListView listViewComponent;
	private TextView title_component;
	private Button btn_component_info;
	private Project myProject;
	
	public ComponentListFragment (ModelObject model, DatabaseHelper dataHelper){
		
		this.model = model;
		this.dataHelper = dataHelper;
	}
	
	public ComponentListFragment (ModelObject model, DatabaseHelper dataHelper, Project myProject){
		
		this.model = model;
		this.dataHelper = dataHelper;
		this.myProject = myProject;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
			
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_component_list, container, false);
		
		title_component = (TextView)view.findViewById(R.id.title_component_list);
		setListData();
		
		listViewComponent = (ListView) view.findViewById(R.id.list_component);
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, listModelName);
		listViewComponent.setAdapter(adapter);
		listViewComponent.setOnItemClickListener(this);
		listViewComponent.setOnItemLongClickListener(this);
		
		return view;
	}
	
	private void setListData(){
	
		listModelName = new ArrayList<String>();
		listModelName.clear();
		listModel = new ArrayList<ModelObject>();
		listModel.clear();
		
		if(model instanceof Project){
			
			try {	
				Project project = (Project) model;
				this.myProject = project;
				Dao<Plant, ?> plantDao = dataHelper.getDao(Plant.class);
				QueryBuilder<Plant, ?> queryBuilder = plantDao.queryBuilder();
				queryBuilder.where().eq(Plant.TABLE_PLANT_COLUMN_PROJECT_NAME, project.getName());
				PreparedQuery<Plant> preparedQuery = queryBuilder.prepare();
				List<Plant> listPlant = plantDao.query(preparedQuery);
				
				for(Plant plant : listPlant){
					listModelName.add(plant.getName());
					listModel.add(plant);
				}
				title_component.setText("PLANT");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(model instanceof Plant){
			try {	
				Plant plant = (Plant) model;
				Dao<Block, ?> blockDao = dataHelper.getDao(Block.class);
				QueryBuilder<Block, ?> queryBuilder = blockDao.queryBuilder();
				queryBuilder.where().eq(Block.TABLE_BLOCK_COLUMN_PLANT_ID, plant.getPlantId());
				PreparedQuery<Block> preparedQuery = queryBuilder.prepare();
				List<Block> listBlock = blockDao.query(preparedQuery);
				
				for(Block block : listBlock){
					listModelName.add(block.getName());
					listModel.add(block);
				}
				title_component.setText("BLOCK");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(model instanceof Block){
			try {	
				Block block = (Block) model;
				Dao<Unit, ?> unitDao = dataHelper.getDao(Unit.class);
				QueryBuilder<Unit, ?> queryBuilder = unitDao.queryBuilder();
				queryBuilder.where().eq(Unit.TABLE_UNIT_COLUMN_BLOCK_ID, block.getBlockId());
				PreparedQuery<Unit> preparedQuery = queryBuilder.prepare();
				List<Unit> listUnit = unitDao.query(preparedQuery);
				
				for(Unit unit : listUnit){
					listModelName.add(unit.getName());
					listModel.add(unit);
				}
				title_component.setText("UNIT");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(model instanceof Unit){
			try {	
				Unit Unit = (Unit) model;
				Dao<Systems, ?> systemDao = dataHelper.getDao(Systems.class);
				QueryBuilder<Systems, ?> queryBuilder = systemDao.queryBuilder();
				queryBuilder.where().eq(Systems.TABLE_SYSTEM_COLUMN_UNIT_ID, Unit.getUntiId());
				PreparedQuery<Systems> preparedQuery = queryBuilder.prepare();
				List<Systems> listSystem = systemDao.query(preparedQuery);
				
				for(Systems system : listSystem){
					listModelName.add(system.getName());
					listModel.add(system);
				}
				title_component.setText("SYSTEM");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if( model instanceof Systems ){
			try {	
				Systems system = (Systems) model;
				Dao<ComponentLevel1, ?> cp1Dao = dataHelper.getDao(ComponentLevel1.class);
				QueryBuilder<ComponentLevel1, ?> queryBuilder = cp1Dao.queryBuilder();
				queryBuilder.where().eq(ComponentLevel1.TABLE_CP1_COLUMN_SYSTEM_ID, system.getSystemId());
				PreparedQuery<ComponentLevel1> preparedQuery = queryBuilder.prepare();
				List<ComponentLevel1> listCp1 = cp1Dao.query(preparedQuery);
				
				for(ComponentLevel1 cp1 : listCp1){
					listModelName.add(cp1.getName());
					listModel.add(cp1);
				}
				title_component.setText("COMPONENT LEVEL");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		
		
		
	}
	


	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		// TODO Auto-generated method stub
		ComponentListFragment fragment = new ComponentListFragment(listModel.get(position), dataHelper, myProject);
		
		if(listModel.get(position) instanceof Plant){
			
			
			Fragment fb = getFragmentManager().findFragmentById(R.id.activity_detail_sub_container_2);
			Fragment fu = getFragmentManager().findFragmentById(R.id.activity_detail_sub_container_3);
			Fragment fs = getFragmentManager().findFragmentById(R.id.activity_detail_sub_container_4);
			Fragment fc = getFragmentManager().findFragmentById(R.id.activity_detail_sub_container_5);
			if(fb != null)
			getFragmentManager().beginTransaction().remove(fb).commit();
			if(fu != null)
			getFragmentManager().beginTransaction().remove(fu).commit();
			if(fs != null)
			getFragmentManager().beginTransaction().remove(fs).commit();
			if(fc != null)
			getFragmentManager().beginTransaction().remove(fc).commit();
			
			getActivity().getSupportFragmentManager().beginTransaction()
			.replace(R.id.activity_detail_sub_container_2, fragment).commit();
			
		}else if(listModel.get(position) instanceof Block){
			

			Fragment fu = getFragmentManager().findFragmentById(R.id.activity_detail_sub_container_3);
			Fragment fs = getFragmentManager().findFragmentById(R.id.activity_detail_sub_container_4);
			Fragment fc = getFragmentManager().findFragmentById(R.id.activity_detail_sub_container_5);
			if(fu != null)
			getFragmentManager().beginTransaction().remove(fu).commit();
			if(fs != null)
			getFragmentManager().beginTransaction().remove(fs).commit();
			if(fc != null)
			getFragmentManager().beginTransaction().remove(fc).commit();
			
			getActivity().getSupportFragmentManager().beginTransaction()
			.replace(R.id.activity_detail_sub_container_3, fragment).commit();
		}else if(listModel.get(position) instanceof Unit){
			
			Fragment fs = getFragmentManager().findFragmentById(R.id.activity_detail_sub_container_4);
			Fragment fc = getFragmentManager().findFragmentById(R.id.activity_detail_sub_container_5);

			if(fs != null)
			getFragmentManager().beginTransaction().remove(fs).commit();
			if(fc != null)
			getFragmentManager().beginTransaction().remove(fc).commit();
			
			getActivity().getSupportFragmentManager().beginTransaction()
			.replace(R.id.activity_detail_sub_container_4, fragment).commit();
		}else if(listModel.get(position) instanceof Systems){

			Fragment fc = getFragmentManager().findFragmentById(R.id.activity_detail_sub_container_5);
			if(fc != null)
			getFragmentManager().beginTransaction().remove(fc).commit();
			
			getActivity().getSupportFragmentManager().beginTransaction()
			.replace(R.id.activity_detail_sub_container_5, fragment).commit();
		}
	}
	
	
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
		ArrayList<String> listFileName = new ArrayList<String>();
		final String fileDir = Environment.getExternalStorageDirectory()+"/alstom/Project "+myProject.getName()+"/"+title_component.getText().toString();
		File file = new File(fileDir);
		for (File childFile : file.listFiles()) {
			listFileName.add(childFile.getName());
		}
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1,listFileName);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("DOCUMENTS");

		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        
		        
		        String fileName = adapter.getItem(item);
		        Uri uri = Uri.parse(fileDir+"/"+fileName);
		        System.out.println(uri.getPath());
		        if(fileName.endsWith("png")){	
		        	Intent intent = new Intent();
					intent.setClass(getActivity(), ImageDisplayActivity.class);
					intent.putExtra(IMAGE_RESSOURCE_URI, uri);
					startActivity(intent);
		        }else if(fileName.endsWith("pdf")){
		        	
		        	 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					 intent.setClass(getActivity(), PdfViewerActivity.class);
					 startActivity(intent);
		        }
		        
		        else{
		        	
		        	Toast.makeText(getActivity(), "File type non recognized !", Toast.LENGTH_SHORT).show();
		        }
		        
		        
		        
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
		
		return false;
	}
	

	
	
	

}
