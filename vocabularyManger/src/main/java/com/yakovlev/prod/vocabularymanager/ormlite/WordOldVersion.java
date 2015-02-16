package com.yakovlev.prod.vocabularymanager.ormlite;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class WordOldVersion implements Serializable{

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




}
