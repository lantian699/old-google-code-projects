package com.malan.seeglitcontrol.spreadsheet.client;

import com.iubiquity.spreadsheets.model.Feed;

public interface AsyncSpreadsheetCaller {
	
	public void onSpreadsheetResult(int requestCode, Feed feed);
	public void onExceptionResponse(Exception e);

}
