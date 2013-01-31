package com.ciel.equipe.word.search.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Chinese extends ModelObjet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5105828955372060914L;

	@DatabaseField
	private String character;
	@DatabaseField
	private String meaning;
	@DatabaseField
	private String numStroke;
	
	
	public String getCharacter() {
		return character;
	}
	public void setCharacter(String character) {
		this.character = character;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	public String getNumStroke() {
		return numStroke;
	}
	public void setNumStroke(String numStroke) {
		this.numStroke = numStroke;
	}
	
	
	
}
