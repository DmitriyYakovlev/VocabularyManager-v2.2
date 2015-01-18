package com.yakovlev.prod.vocabularymanager.export_import_functionality;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.yakovlev.prod.vocabularymanager.ormlite.Vocabulary;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;
import java.io.Serializable;
import java.util.List;

public class ExportVocabulariesContainer implements Serializable{

    @SerializedName("all_vocabs")
    private Vocabulary[] allVocabs;

    @SerializedName("all_words")
    private WordTable[] allWords;

    public ExportVocabulariesContainer(){

    }

    public void setAllVocabs(Vocabulary[] allVocabs) {
        this.allVocabs = allVocabs;
    }

    public void setAllWords(WordTable[] allWords) {
        this.allWords = allWords;
    }

    public Vocabulary[] getAllVocabs() {
        return allVocabs;
    }

    public WordTable[] getAllWords() {
        return allWords;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }

    public static ExportVocabulariesContainer fromJson(String jsonString){
        ExportVocabulariesContainer wordTable = new Gson().fromJson(jsonString, ExportVocabulariesContainer.class);
        return wordTable;
    }



}
