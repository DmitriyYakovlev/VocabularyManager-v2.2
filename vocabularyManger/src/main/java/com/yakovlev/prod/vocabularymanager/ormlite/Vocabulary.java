package com.yakovlev.prod.vocabularymanager.ormlite;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class Vocabulary implements Serializable{

	@DatabaseField(generatedId = true, columnName = "_id")
	@SerializedName("voc_id")
	private int id;
	
	@DatabaseField(index = true)
	@SerializedName("voc_name")
	private String vName;
	
	@DatabaseField
	@SerializedName("voc_desc")
	private String vDescription;
	
	@DatabaseField
	@SerializedName("voc_author_id")
	private int authorId;
	
	@DatabaseField
	@SerializedName("voc_date")
	private Date date;
	
	@DatabaseField
    @SerializedName("voc_last_mode_date")
    private Date lastModdate;
	
	public Vocabulary() { 	}

	public Vocabulary(String name, String description, int authorId, Date date, Date lastModdate ) {
		this.setvName(name);
		this.setvDescription(description);
		this.setAuthorId(authorId);
		this.setDate(date);
		this.setLastModdate(lastModdate);
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

	public void setId(int id) {
		this.id = id;
	}
	
	public String getvName() {
		return vName;
	}

	public void setvName(String vName) {
		this.vName = vName;
	}

	public String getvDescription() {
		return vDescription;
	}

	public void setvDescription(String vDescription) {
		this.vDescription = vDescription;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getLastModdate() {
		return lastModdate;
	}

	public void setLastModdate(Date lastModdate) {
		this.lastModdate = lastModdate;
	}

    public String toJson(){
        return new Gson().toJson(this);
    }

    public static Vocabulary fromJson(String jsonString){
        Vocabulary wordTable = new Gson().fromJson(jsonString, Vocabulary.class);
        return wordTable;
    }

}
