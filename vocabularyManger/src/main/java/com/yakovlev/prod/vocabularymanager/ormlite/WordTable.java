package com.yakovlev.prod.vocabularymanager.ormlite;

import com.j256.ormlite.field.DatabaseField;

public class WordTable {

	@DatabaseField(generatedId = true, columnName = "_id")
	private int id;

	@DatabaseField(index = true)
	private String wKey;

	@DatabaseField
	private String wValue;

	@DatabaseField
	private int vocabularyId;

	public WordTable() { }

	public WordTable(String wKey, String wValue, int vocabularyId) {
		this.setwKey(wKey);
		this.setwValue(wValue);
		this.setVocabularyId(vocabularyId);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id);
		return sb.toString();
	}

	
	public int getId() {
		return id;
	}
	
	public String getwKey() {
		return wKey;
	}

	public void setwKey(String wKey) {
		this.wKey = wKey;
	}

	public String getwValue() {
		return wValue;
	}

	public void setwValue(String wValue) {
		this.wValue = wValue;
	}

	public int getVocabularyId() {
		return vocabularyId;
	}

	public void setVocabularyId(int vocabularyId) {
		this.vocabularyId = vocabularyId;
	}
}