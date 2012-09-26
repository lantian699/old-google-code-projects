package com.alstom.lean.all.spreadsheet;

public class Worksheet {
	//Table name
	public static final String TABLE_NAME_PLANT = "PLANT";
	public static final String TABLE_NAME_BLOCK = "BLOCK";
	public static final String TABLE_NAME_UNIT = "UNIT";
	public static final String TABLE_NAME_SYSTEM = "SYSTEM";
	public static final String TABLE_NAME_CP1 = "CP1";
	public static final String TABLE_NAME_CP2 = "CP2";
	public static final String TABLE_NAME_CP3 = "CP3";
	public static final String TABLE_NAME_PROJECT = "PROJECT";
	public static final String TABLE_NAME_TASK = "TASK";
	public static final String TABLE_NAME_PERSON = "PERSON";
	public static final String TABLE_NAME_VISUALINSPECTION = "VISUALINSPECTION";
	public static final String TABLE_NAME_MESUREMENT = "MESUREMENT";
	
	//PLANT
	public static final String TABLE_PLANT_COLUMN_NAME = "plantname";
	public static final String TABLE_PLANT_COLUMN_ID = "plantid";
	public static final String TABLE_PLANT_COLUMN_PROJECT_NAME = "projectname";
	//BLOCK
	public static final String TABLE_BLOCK_COLUMN_NAME = "blockname";
	public static final String TABLE_BLOCK_COLUMN_ID= "blockid";
	
	//UNIT
	public static final String TABLE_UNIT_COLUMN_NAME = "unitname";
	public static final String TABLE_UNIT_COLUMN_ID = "unitid";
	//SYSTEM
	public static final String TABLE_SYSTEM_COLUMN_NAME = "systemname";
	public static final String TABLE_SYSTEM_COLUMN_TYPE = "systemtype";
	public static final String TABLE_SYSTEM_COLUMN_ID = "systemid";
	//COMPONENT LEVEL 1
	public static final String TABLE_CP1_COLUMN_NAME = "componentname";
	public static final String TABLE_CP1_COLUMN_ID = "cp1id";
	//COMPONENT LEVEL 2
	public static final String TABLE_CP2_COLUMN_NAME = "componentname";
	public static final String TABLE_CP2_COLUMN_ID = "cp2id";
	//COMPONENT LEVEL 3
	public static final String TABLE_CP3_COLUMN_NAME = "componentname";
	public static final String TABLE_CP3_COLUMN_ID = "cp3id";
	//project
	public static final String TABLE_PROJECT_COLUMN_NAME = "name";
	public static final String TABLE_PROJECT_COLUMN_LOCATION = "location";
	//person
	public static final String TABLE_PERSON_COLUMN_NAME = "name";
	public static final String TABLE_PERSON_COLUMN_PROFILE = "profile";
	public static final String TABLE_PERSON_COLUMN_TEL = "tel";
	public static final String TABLE_PERSON_COLUMN_EMAIL = "email";
	//task
	public static final String TABLE_TASK_COLUMN_NAME = "name";
	
	
	
	

	
	
	
	// Worksheets (tabs)
	public enum Table{
		PLANT{
		    public String toString() {
		        return "od6";
		    }
		    public String getName(){
		    	return TABLE_NAME_PLANT;
		    }
		    
		}, 
		BLOCK{
		    public String toString() {
		        return "od7";
		    }
		    public String getName(){
		    	return "BLOCK";
		    }
		}, 
		UNIT{
		    public String toString() {
		        return "od4";
		    }
		    public String getName(){
		    	return "UNIT";
		    }
		},
		SYSTEM{
		    public String toString() {
		        return "od5";
		    }
		    public String getName(){
		    	return "SYSTEM";
		    }
		},
		CP1{
		    public String toString() {
		        return "oda";
		    }
		    public String getName(){
		    	return "CP1";
		    }
		},
		CP2{
		    public String toString() {
		        return "odb";
		    }
		    public String getName(){
		    	return "CP2";
		    }
		},
		CP3{
		    public String toString() {
		        return "od8";
		    }
		    public String getName(){
		    	return "CP3";
		    }
		},
		PROJECT{
		    public String toString() {
		        return "od9";
		    }
		    public String getName(){
		    	return "PROJECT";
		    }
		},
		
		PERSON{
		    public String toString() {
		        return "ocy";
		    }
		    public String getName(){
		    	return "PERSON";
		    }
		},
		
		TASK{
		    public String toString() {
		        return "ocz";
		    }
		    public String getName(){
		    	return "TASK";
		    }
		}
		,
		VISUALINSPECTION{
		    public String toString() {
		        return "ocw";
		    }
		    public String getName(){
		    	return "VISUALINSPECTION";
		    }
		},
		MESUREMENT{
		    public String toString() {
		        return "ocx";
		    }
		    public String getName(){
		    	return "MESUREMENT";
		    }
		}
	}

}
