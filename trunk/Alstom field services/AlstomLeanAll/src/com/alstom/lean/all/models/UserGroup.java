package com.alstom.lean.all.models;

import com.alstom.lean.all.tools.Tools;

public class UserGroup {
	
	public enum User{
		TOTO{
		    public String toString() {
		        return Tools.PREF_SPREADSHEET_KEY_TOTO;
		    }    
		}, 
		QIQI{
		    public String toString() {
		        return Tools.PREF_SPREADSHEET_KEY_QIQI;
		    }
		}
		, 
		MIMI{
		    public String toString() {
		        return Tools.PREF_SPREADSHEET_KEY_MIMI;
		    }
		}
	}

}
