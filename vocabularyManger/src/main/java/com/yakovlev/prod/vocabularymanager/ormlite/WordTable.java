package com.yakovlev.prod.vocabularymanager.ormlite;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class WordTable implements Serializable{

	@DatabaseField(generatedId = true, columnName = "_id")
    private int id;

	@DatabaseField(index = true)
    @SerializedName("key")
	private String wKey;

	@DatabaseField
    @SerializedName("value")
    private String wValue;

    @DatabaseField
    @SerializedName("transcription")
    private String wTranscription;

	@DatabaseField
    @SerializedName("vocab_id")
    private int vocabularyId;

    @DatabaseField
    @SerializedName("word_stat")
    private int wordStatus;

	public WordTable() { }

	public WordTable(String wKey, String wValue, int vocabularyId) {
		this.setwKey(wKey);
		this.setwValue(wValue);
		this.setVocabularyId(vocabularyId);
	}

    public WordTable(int id, String wKey, String wValue, String wTranscription, int vocabularyId, int wordStatus) {
        this.id = id;
        this.wKey = wKey;
        this.wValue = wValue;
        this.wTranscription = wTranscription;
        this.vocabularyId = vocabularyId;
        this.wordStatus = wordStatus;
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

    public String getwTranscription() {
        return wTranscription;
    }

    public void setwTranscription(String wTranscription) {
        this.wTranscription = wTranscription;
    }

    public int getWordStatus() {
        return wordStatus;
    }

    public void setWordStatus(int wordStatus) {
        this.wordStatus = wordStatus;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }

    public static WordTable fromJson(String jsonString){
        WordTable wordTable = new Gson().fromJson(jsonString, WordTable.class);
        return wordTable;
    }

}