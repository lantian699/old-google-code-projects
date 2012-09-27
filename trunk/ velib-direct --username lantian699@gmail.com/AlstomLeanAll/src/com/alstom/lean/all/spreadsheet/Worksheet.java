package com.alstom.lean.all.spreadsheet;

public class Worksheet {
	//Table name
	public static final String TABLE_NAME_PLANT = "od6";
	public static final String TABLE_NAME_BLOCK = "od7";
	public static final String TABLE_NAME_UNIT = "od4";
	public static final String TABLE_NAME_SYSTEM = "od5";
	public static final String TABLE_NAME_CP1 = "oda";
	public static final String TABLE_NAME_CP2 = "odb";
	public static final String TABLE_NAME_CP3 = "od8";
	public static final String TABLE_NAME_PROJECT = "od9";
	public static final String TABLE_NAME_PERSON = "ocy";
	public static final String TABLE_NAME_TASK = "ocz";
	public static final String TABLE_NAME_VISUALINSPECTION = "ocw";
	public static final String TABLE_NAME_MESUREMENT = "ocx";
	
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
	public static final String TABLE_TASK_COLUMN_WORK_INSTRUCTION = "workinstruction";
	public static final String TABLE_TASK_COLUMN_RECORD_SHEET = "recordsheet";
	public static final String TABLE_TASK_COLUMN_BEGIN = "begin";
	public static final String TABLE_TASK_COLUMN_END = "end";
	public static final String TABLE_TASK_COLUMN_STATUS = "status";
	public static final String TABLE_TASK_COLUMN_REQUIRE_WITNESS_POINT = "requireswitnesspoint";
	public static final String TABLE_TASK_COLUMN_PROJECT_NAME = "projectname";
	//visual inspection
	public static final String TABLE_INSPECTION_COLUMN_DESCRIPTION = "description";
	public static final String TABLE_INSPECTION_COLUMN_KEY = "key";
	public static final String TABLE_INSPECTION_COLUMN_VALUE = "value";
	public static final String TABLE_INSPECTION_COLUMN_ATTACHMENT = "attachment";
	//mesurement
	public static final String TABLE_MESUREMENT_COLUMN_DESCRIPTION = "description";
	public static final String TABLE_MESUREMENT_COLUMN_KEY = "key";
	public static final String TABLE_MESUREMENT_COLUMN_LABEL = "label";
	public static final String TABLE_MESUREMENT_COLUMN_VALUE = "value";
	public static final String TABLE_MESUREMENT_COLUMN_UNIT = "unit";
	public static final String TABLE_MESUREMENT_COLUMN_ATTACHMENT = "attachment";
	public static final String TABLE_MESUREMENT_COLUMN_RULE = "rule";
	public static final String TABLE_MESUREMENT_COLUMN_HIGH = "high";
	public static final String TABLE_MESUREMENT_COLUMN_LOW = "low";
	
	

	
	
	
	// Worksheets (tabs)
	public enum Table{
		PLANT{
		    public String toString() {
		        return "od6";
		    }    
		}, 
		BLOCK{
		    public String toString() {
		        return "od7";
		    }
		}, 
		UNIT{
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
		}
	}

}
