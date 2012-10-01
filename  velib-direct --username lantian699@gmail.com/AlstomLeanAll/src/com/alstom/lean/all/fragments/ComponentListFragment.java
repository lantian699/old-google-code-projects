package com.alstom.lean.all.fragments;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alstom.lean.all.R;
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
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ComponentListFragment extends ListFragment{
	
	private List<String> listModelName;
	private ModelObject model;
	private ArrayAdapter<String> adapter;
	private DatabaseHelper dataHelper;
	private List<ModelObject> listModel;
	
	
	ComponentListFragment (ModelObject model, DatabaseHelper dataHelper){
		
		this.model = model;
		this.dataHelper = dataHelper;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setListData();
		
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, listModelName);
		setListAdapter(adapter);
		
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		
		
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
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

}
