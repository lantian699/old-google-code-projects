package com.alstom.lean.all.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

public class InspectionTask extends Task{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3089907682256484140L;
	
	private VisualInspection inspection;

	public VisualInspection getInspection() {
		return inspection;
	}

	public void setInspection(VisualInspection inspection) {
		this.inspection = inspection;
	}
	
	

}
