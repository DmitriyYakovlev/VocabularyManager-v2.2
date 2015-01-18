package com.yakovlev.prod.vocabularymanager.export_import_functionality;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.BaseAdapter;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.yakovlev.prod.vocabularymanager.ormlite.DatabaseHelper;
import com.yakovlev.prod.vocabularymanager.ormlite.Vocabulary;
import com.yakovlev.prod.vocabularymanager.ormlite.VocabularyDbHelp;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTableHelper;
import com.yakovlev.prod.vocabularymanager.support.FileHelper;
import com.yakovlev.prod.vocabularymanager.support.ProgressBarHellper;
import com.yakovlev.prod.vocabularymanager.support.ToastHelper;
import com.yakovlev.prod.vocabularymanger.R;

public class ImportContentFromFileAT extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progressBar;
    private Context context;
    private String pathToFile;


    public ImportContentFromFileAT(Context context, String pathToFile) {
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
            String json = FileHelper.loadStringFromFile(pathToFile);
            ExportVocabulariesContainer dataFromFile = ExportVocabulariesContainer.fromJson(json);

            DatabaseHelper dbHelper = new DatabaseHelper(context);
            RuntimeExceptionDao<Vocabulary, Integer> vocabularyDao = dbHelper.getVocabularyRuntimeDataDao();
            Vocabulary[] allVocabs = dataFromFile.getAllVocabs();
            int allVocabsCount = allVocabs.length;

            RuntimeExceptionDao<WordTable, Integer> wordsRuntimeDataDao = dbHelper.getWordsRuntimeDataDao();
            WordTable[] allWords = dataFromFile.getAllWords();
            int wordsCount = allWords.length;

            for (int i = 0; i < allVocabs.length; i++) {
                Vocabulary vocabulary  = allVocabs[i];
                int oldVocId = vocabulary.getId();
                vocabularyDao.create(vocabulary);
                int newVocId = vocabulary.getId();

                for (int j = 0; j < allWords.length; j++) {
                    WordTable currentWord = allWords[j];
                    int currentWordId = currentWord.getVocabularyId();

                    if (currentWordId == oldVocId) {
                        currentWord.setVocabularyId(newVocId);
                        wordsRuntimeDataDao.create(currentWord);
                    }
                }

            }


            ToastHelper.doInUIThread("Imported successfully. Vocabularies : " + Integer.toString(allVocabsCount) + " Words : " + wordsCount, context);
        } catch (Exception ex) {
            ToastHelper.doInUIThread("Import error", context);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void integer) {
        super.onPostExecute(integer);
        ProgressBarHellper.stopProgressBar(progressBar);
    }


}
