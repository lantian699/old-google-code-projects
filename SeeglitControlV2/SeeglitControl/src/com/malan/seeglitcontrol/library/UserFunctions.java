/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.malan.seeglitcontrol.library;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.malan.seeglitcontrol.model.DatabaseHelper;

import android.content.Context;

public class UserFunctions {
	
	private JSONParser jsonParser;
	
	private static String URL = "http://115.29.187.37/seeglit/index.php";
	
	private static String login_tag = "login";
	private static String register_tag = "register";
	private static String society_tag = "society";
	
	// constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * */
	public JSONObject loginUser(String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	
	/**
	 * function make Login Request
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject registerUser(String name, String email, String password, String society){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("society", society));
	
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		// return json
		return json;
	}
	
	
	public JSONObject getSocietyList(){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", society_tag));
	
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		// return json
		return json;
	}
	
	/**
	 * Function get Login status
	 * @throws SQLException 
	 * */
	public boolean isUserLoggedIn(Context context) throws SQLException{
		DatabaseHelper db = DatabaseHelper.getInstance(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context){
//		DatabaseHandler db = new DatabaseHandler(context);
//		db.resetTables();
		return true;
	}
	
}
