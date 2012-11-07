package com.app.capgemini.appgeneral.spreadsheet;

public class Worksheet {
	//Table name
	public static final String TABLE_NAME_PROJECT = "od6";
//	public static final String TABLE_NAME_RECOUVREMENT = "od7";
//	public static final String TABLE_NAME_CLIENT = "od4";
/*	public static final String TABLE_NAME_SYSTEM = "od5";
	public static final String TABLE_NAME_CP1 = "oda";
	public static final String TABLE_NAME_CP2 = "odb";
	public static final String TABLE_NAME_CP3 = "od8";
	public static final String TABLE_NAME_PROJECT = "od9";
	public static final String TABLE_NAME_PERSON = "ocy";
	public static final String TABLE_NAME_TASK = "ocz";
	public static final String TABLE_NAME_VISUALINSPECTION = "ocw";
	public static final String TABLE_NAME_MESUREMENT = "ocx";*/
	
	
	//Project
	public static final String TABLE_PROJECT_COLUMN_PROJECT_ID = "projectid";
	public static final String TABLE_PROJECT_COLUMN_NAME= "name";
	public static final String TABLE_PROJECT_COLUMN_START_DATE = "startdate";
	public static final String TABLE_PROJECT_COLUMN_END_DATE = "enddate";
	public static final String TABLE_PROJECT_COLUMN_LOCATION = "location";
	
	

	
	
	
	// Worksheets (tabs)
	public enum Table{
		PROJECT{
		    public String toString() {
		        return "od6";
		    }    
		}, 
		/*RECOUVREMENT{
		    public String toString() {
		        return "od7";
		    }
		}, 
		CLIENT{
		    public String toString() {
		        return "od4";
		    }
		},
		SYSTEM{
		    public String toString() {
		        return "od5";
		    }
		},
		CP1{
		    public String toString() {
		        return "oda";
		    }
		},
		CP2{
		    public String toString() {
		        return "odb";
		    }
		},
		CP3{
		    public String toString() {
		        return "od8";
		    }
		},
		PROJECT{
		    public String toString() {
		        return "od9";
		    }
		},
		
		PERSON{
		    public String toString() {
		        return "ocy";
		    }
		},
		
		TASK{
		    public String toString() {
		        return "ocz";
		    }
		}
		,
		VISUALINSPECTION{
		    public String toString() {
		        return "ocw";
		    }
		},
		MESUREMENT{
		    public String toString() {
		        return "ocx";
		    }
		}*/
	}

}
