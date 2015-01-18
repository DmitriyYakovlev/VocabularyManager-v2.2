package com.yakovlev.prod.vocabularymanager.export_import_functionality;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.yakovlev.prod.vocabularymanager.ormlite.DatabaseHelper;
import com.yakovlev.prod.vocabularymanager.ormlite.Vocabulary;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;
import com.yakovlev.prod.vocabularymanager.support.FileHelper;
import com.yakovlev.prod.vocabularymanager.support.ProgressBarHellper;
import com.yakovlev.prod.vocabularymanager.support.ToastHelper;
import java.util.List;


public class ExportFullBackUpInJsonAT extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progressBar;
    private Context context;
    private String pathToFile;


    public ExportFullBackUpInJsonAT(Context context, String pathToFile) {
        this.context = context;
        this.pathToFile = pathToFile;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = ProgressBarHellper.getProgressBarDialogAndStartWithoutCancelListner(progressBar, context);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            ExportVocabulariesContainer exportVocabulariesContainer = new ExportVocabulariesContainer();

            DatabaseHelper dbHelper = new DatabaseHelper(context);
            List<Vocabulary> allVocabsList = dbHelper.getVocabularyRuntimeDataDao().queryForAll();
            Vocabulary[] vocabularies = allVocabsList.toArray(new Vocabulary[allVocabsList.size()]);

            List<WordTable> allWordsList = dbHelper.getWordsRuntimeDataDao().queryForAll();
            WordTable[] words = allWordsList.toArray(new WordTable[allWordsList.size()]);

            exportVocabulariesContainer.setAllVocabs(vocabularies);
            exportVocabulariesContainer.setAllWords(words);

            String json = exportVocabulariesContainer.toJson();
            FileHelper.saveStringInFile(json, pathToFile);
            ToastHelper.doInUIThread("Full back up completed successfully" , context);
        } catch (Exception ex) {
            ToastHelper.doInUIThread("Full back up exception", context);
        } catch (Error error){
            ToastHelper.doInUIThread("Full back up error", context);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void integer) {
        super.onPostExecute(integer);
        ProgressBarHellper.stopProgressBar(progressBar);
    }

}
