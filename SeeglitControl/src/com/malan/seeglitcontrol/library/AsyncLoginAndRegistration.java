package com.malan.seeglitcontrol.library;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iubiquity.spreadsheets.model.DatabaseHelper;
import com.iubiquity.spreadsheets.model.User;
import com.malan.seeglitcontrol.ActivityDiscovery;
import com.malan.seeglitcontrol.RegisterActivity;

import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class AsyncLoginAndRegistration extends AsyncTask<String, String, JSONObject>{

	
	// JSON Response node names
		private static String KEY_SUCCESS = "success";
		private static String KEY_ERROR = "error";
		private static String KEY_ERROR_MSG = "error_msg";
		private static String KEY_UID = "uid";
		private static String KEY_NAME = "name";
		private static String KEY_EMAIL = "email";
		private static String KEY_CREATED_AT = "created_at";
		
		private static String KEY_NAT_CID="cid";
		private static String KEY_NAT_PROTOCOLE="protocole_type";
		private static String KEY_NAT_EXT_PORT="external_port";
		private static String KEY_NAT_DEST_PORT="dest_port";
		private static String KEY_NAT_DEST_IP="dest_ip";
		private static String KEY_NAT_NIC_VENDOR="nic_vendor";
		private static String KEY_NAT_MAC_ADDR="mac_address";
		private static String KEY_NAT_DEV_TYPE="device_type";
		private static String KEY_NAT_SID="sid";

		private TextView errorMsgTextView;
		private Context context;
		private UserFunctions userFunction;
		private ProgressDialog dialog;
		private String tag;
		private DatabaseHelper db;
		private Spinner spinner;
		private ArrayList<String> listSociety;
		private ArrayList<HashMap<String, String >> listSid ;
		
	
	public AsyncLoginAndRegistration(TextView errorMsgTextView, Context context, 
			String tag, Spinner spinner){
			
		this.context = context;
		this.errorMsgTextView = errorMsgTextView;
		this.tag = tag;
		this.spinner = spinner;
		}
	
	
	@Override
	protected void onPreExecute() {
	// TODO Auto-generated method stub
	super.onPreExecute();
	
		if(tag == "register"){
			dialog = ProgressDialog.show(context, "", "正在注册，请稍后。。。");
		}else if(tag == "login"){
			dialog = ProgressDialog.show(context, "", "正在登录，请稍后。。。");
		}else if(tag == "society"){
			dialog = ProgressDialog.show(context, "", "获取信息，请稍后。。。");
		}
	}
	
	@Override
	protected JSONObject doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		
		
		userFunction = new UserFunctions();
		
		if(tag == "register"){
			String name = arg0[0];
			String email = arg0[1];
			String password = arg0[2];
			String scSelected = arg0[3];
			
			JSONObject json = userFunction.registerUser(name, email, password,scSelected);
			return json;
		}else if(tag == "login"){
			
			String email = arg0[0];
			String password = arg0[1];

			JSONObject json = userFunction.loginUser(email, password);
			return json;
		}else if(tag == "society"){
			
			JSONObject json = userFunction.getSocietyList();
			return json;
		}
		
		return null;
	}
	
	
	@Override
	protected void onPostExecute(JSONObject json) {
		
		super.onPostExecute(json);
		
		dialog.dismiss();
		System.out.println(json);
		
		listSid = new ArrayList<HashMap<String,String>>();
		
		if(tag == "register"){
		// check for login response
		try {
			if (json.getString(KEY_SUCCESS) != null) {
				errorMsgTextView.setText("");
				String res = json.getString(KEY_SUCCESS); 
				if(Integer.parseInt(res) == 1){
					// user successfully registred
					// Store user details in SQLite Database
//					DatabaseHandler db = new DatabaseHandler(context);
					db = DatabaseHelper.getInstance(context);
					JSONObject json_user = json.getJSONObject("user");
					JSONArray json_nat = json.getJSONArray("nat");
					// Clear all previous data in database
					userFunction.logoutUser(context);
					db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));
					
					
					for(int i=0; i<json_nat.length();i++){
						JSONObject json_nat_object = json_nat.getJSONObject(i);
						db.addNat(json_nat_object.getString(KEY_NAT_CID), json_nat_object.getString(KEY_NAT_PROTOCOLE),null, json_nat_object.getString(KEY_NAT_EXT_PORT),
								json_nat_object.getString(KEY_NAT_DEST_IP), json_nat_object.getString(KEY_NAT_DEST_PORT), json_nat_object.getString(KEY_NAT_NIC_VENDOR), json_nat_object.getString(KEY_NAT_DEV_TYPE)
								,json_nat_object.getString(KEY_NAT_SID));
						
					}
					System.out.println(json_nat.getJSONObject(0).getString(KEY_NAT_DEST_IP));
					// Launch Dashboard Screen
					Intent dashboard = new Intent(context, ActivityDiscovery.class);
					// Close all views before launching Dashboard
					dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(dashboard);
					// Close Registration Screen
					((Activity) context).finish();
				}else{
					// Error in registration
					errorMsgTextView.setText("注册错误");
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}else if(tag == "login"){
			

					// check for login response
					try {
						if (json.getString(KEY_SUCCESS) != null) {
							errorMsgTextView.setText("");
							String res = json.getString(KEY_SUCCESS); 
							if(Integer.parseInt(res) == 1){
								// user successfully logged in
								// Store user details in SQLite Database
//								DatabaseHandler db = new DatabaseHandler(context);
								JSONObject json_user = json.getJSONObject("user");
								JSONArray json_nat = json.getJSONArray("nat");
								db = DatabaseHelper.getInstance(context);
								// Clear all previous data in database
								userFunction.logoutUser(context);
								db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));						
								
								// Launch Dashboard Screen
								Intent dashboard = new Intent(context, ActivityDiscovery.class);
								
								// Close all views before launching Dashboard
								dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								context.startActivity(dashboard);
								
								// Close Login Screen
								((Activity) context).finish();
							}else{
								// Error in login
								errorMsgTextView.setText("登录错误");
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			
			
			
		}else if(tag == "society"){
			// check for login response
			try {
				listSociety = new ArrayList<String>();
				if (json.getString(KEY_SUCCESS) != null) {
					//errorMsgTextView.setText("");
					String res = json.getString(KEY_SUCCESS); 
					if(Integer.parseInt(res) == 1){

						// user successfully registred
						// Store user details in SQLite Database
//						DatabaseHandler db = new DatabaseHandler(context);
//						db = DatabaseHelper.getInstance(context);
						 JSONArray json_society = json.getJSONArray("society");
						 
//						System.out.println(json_society.length());
						
						for(int i=0; i< json_society.length();i++){
							listSociety.add(json_society.getJSONObject(i).getString("name")) ;
							HashMap<String, String> map = new HashMap<String, String>();
							map.put(json_society.getJSONObject(i).getString("name"), json_society.getJSONObject(i).getString("sid"));
							listSid.add(map);
						}
						
						((RegisterActivity)context).listSid = listSid;
						
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, listSociety);
						
						// Specify the layout to use when the list of choices appears
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						// Apply the adapter to the spinner
						spinner.setAdapter(adapter);
						
						
						
						
					}else{
						// Error in registration
						//errorMsgTextView.setText("ע��ʱ�������");
						
						listSociety.add("无法获取注册企业列表");
						
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, listSociety);
						
						// Specify the layout to use when the list of choices appears
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						// Apply the adapter to the spinner
						spinner.setAdapter(adapter);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			}
		
	}
		
	

}
