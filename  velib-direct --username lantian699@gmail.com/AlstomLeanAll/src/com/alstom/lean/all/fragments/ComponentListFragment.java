package com.alstom.lean.all.fragments;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ComponentListFragment extends Fragment implements OnItemClickListener, OnClickListener{
	
	private List<String> listModelName;
	private ModelObject model;
	private ArrayAdapter<String> adapter;
	private DatabaseHelper dataHelper;
	private List<ModelObject> listModel;
	private ListView listViewComponent;
	private TextView title_component;
	private Button btn_component_info;
	
	ComponentListFragment (ModelObject model, DatabaseHelper dataHelper){
		
		this.model = model;
		this.dataHelper = dataHelper;
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
		btn_component_info = (Button) view.findViewById(R.id.btn_component_info);
		btn_component_info.setOnClickListener(this);
		setListData();
		
		listViewComponent = (ListView) view.findViewById(R.id.list_component);
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, listModelName);
		listViewComponent.setAdapter(adapter);
		listViewComponent.setOnItemClickListener(this);
		
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
		ComponentListFragment fragment = new ComponentListFragment(listModel.get(position), dataHelper);
		
		if(listModel.get(position) instanceof Plant){
			getActivity().getSupportFragmentManager().beginTransaction()
			.replace(R.id.activity_detail_sub_container_2, fragment).commit();
		}else if(listModel.get(position) instanceof Block){
			getActivity().getSupportFragmentManager().beginTransaction()
			.replace(R.id.activity_detail_sub_container_3, fragment).commit();
		}else if(listModel.get(position) instanceof Unit){
			getActivity().getSupportFragmentManager().beginTransaction()
			.replace(R.id.activity_detail_sub_container_4, fragment).commit();
		}else if(listModel.get(position) instanceof Systems){
			getActivity().getSupportFragmentManager().beginTransaction()
			.replace(R.id.activity_detail_sub_container_5, fragment).commit();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_component_info:
		
			
			final CharSequence[] items = {"2D Plan", "GT26 Sectional View", "GT26 model view", "3D Model"};
			
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("DOCUMENTS");
			builder.setItems(items, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			        Toast.makeText(getActivity(), items[item], Toast.LENGTH_SHORT).show();
			        
			        switch (item) {
					case 0:
						
						boolean save = MyProjectListFragment.copyFile(MyProjectListFragment.PDF_GT26_PLAN_2D, Environment.getExternalStorageDirectory().getPath()+"/"+MyProjectListFragment.PDF_GT26_PLAN_2D, getResources());
						Uri uri = Uri.parse("file:///mnt/sdcard/"+MyProjectListFragment.PDF_GT26_PLAN_2D);
//						Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.gt26_plan_2d);
						if(save && Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){

							 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
							 intent.setClass(getActivity(), PdfViewerActivity.class);
							 startActivity(intent);
					 
						}
						
						break;
						
					case 1:
						
						Intent intent = new Intent();
						intent.setClass(getActivity(), ImageDisplayActivity.class);
						intent.putExtra(MyProjectListFragment.RESOURCE_ID, R.drawable.gt26_vue_en_coupe);
						startActivity(intent);
						break;

					case 2:
						
						Intent intent_model = new Intent();
						intent_model.setClass(getActivity(), ImageDisplayActivity.class);
						intent_model.putExtra(MyProjectListFragment.RESOURCE_ID, R.drawable.gt26_model);
						startActivity(intent_model);
						
						break;
						
					case 3:
						
						Intent in = new Intent();
						in.setClass(getActivity(), Model3DTurbineActivity.class);
						startActivity(in);
						
						break;
						
					default:
						break;
					}
			        
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
			
			break;

		default:
			break;
		}
		
	}

}
