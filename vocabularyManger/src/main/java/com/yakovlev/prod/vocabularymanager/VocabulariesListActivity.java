package com.yakovlev.prod.vocabularymanager;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.yakovlev.prod.vocabularymanager.adapters.VocabCursorAdapter;
import com.yakovlev.prod.vocabularymanager.constants.Const;
import com.yakovlev.prod.vocabularymanager.cursor_loaders.VocabulariesCursorLoader;
import com.yakovlev.prod.vocabularymanager.dialogs.AlertDialogsHolder;
import com.yakovlev.prod.vocabularymanager.dialogs.DialogButtonsCallback;
import com.yakovlev.prod.vocabularymanager.dialogs.DialogPositiveBtnCallBack;
import com.yakovlev.prod.vocabularymanager.file_explorer.FileExplorerActivity;
import com.yakovlev.prod.vocabularymanager.file_explorer.FileWorkHelper;
import com.yakovlev.prod.vocabularymanager.ormlite.DatabaseHelper;
import com.yakovlev.prod.vocabularymanager.ormlite.Vocabulary;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;
import com.yakovlev.prod.vocabularymanager.support.IntentHelper;
import com.yakovlev.prod.vocabularymanager.support.ToastHelper;
import com.yakovlev.prod.vocabularymanger.R;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class VocabulariesListActivity extends FragmentActivity implements LoaderCallbacks<Cursor>, OnClickListener, DialogPositiveBtnCallBack {

	// views
	private ListView lvVocabs;
	private TextView tvHeader;
	private ImageButton ibtnAddVocab, imBtnLoadAllFromFolder;

	private VocabCursorAdapter vocabCursorAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_vocabs);
		findViews();
		setOnClickListeners();

		lvVocabs = (ListView) findViewById(R.id.lvVocabs);
		getSupportLoaderManager().initLoader(0, null, this);
	}

	private void findViews() {
		tvHeader = (TextView) findViewById(R.id.tvActName);
		ibtnAddVocab = (ImageButton) findViewById(R.id.imgAddVocab);
        imBtnLoadAllFromFolder = (ImageButton) findViewById(R.id.imgLoadAllFromFolder);
		tvHeader.setText("Vocabularies list");
	}

	private void setOnClickListeners() {
		ibtnAddVocab.setOnClickListener(this);
        imBtnLoadAllFromFolder.setOnClickListener(this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new VocabulariesCursorLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		vocabCursorAdapter = new VocabCursorAdapter(this, cursor, this, this);
		lvVocabs.setAdapter(vocabCursorAdapter);
		lvVocabs.setOnItemClickListener(itemListener);
	}

	OnItemClickListener itemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			Integer id = (int) vocabCursorAdapter.getItemId(position);
			IntentHelper.openEditVocabularyActivity(id, VocabulariesListActivity.this);
		}
	};


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            String pathFromFileExp = data.getStringExtra(FileExplorerActivity.PATH_TO_DIRECTORY_AND_FILE_NAME);
            if (requestCode == Const.REQUEST_CODE_FOR_DIRECTORY_SELECTION) {
                safeDirectoryWithVocabsProcessing(pathFromFileExp);
            }
        }
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    private void safeDirectoryWithVocabsProcessing(String pathFromFileExp){
        try {
            processFilesFromDirectoryAfterSelection(pathFromFileExp);
        }
        catch (Exception ex){
            ToastHelper.doInUIThread(ex.toString(), this);
        }
    }

    private void processFilesFromDirectoryAfterSelection(String pathToDirectory) {
        File folder = new File(pathToDirectory);
        File[] listOfFiles = folder.listFiles();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        if (listOfFiles.length > 0) {
            int countWords = 0;
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {

                    String fileName = listOfFiles[i].getName();
                    String nameWithOutExtension = fileName.substring(0, fileName.lastIndexOf('.'));

                    Vocabulary vocabulary = new Vocabulary(nameWithOutExtension, "", 1, new Date(), new Date());
                    dbHelper.getVocabularyRuntimeDataDao().create(vocabulary);

                    String pathWithFileName = pathToDirectory + "/" +nameWithOutExtension + "." + Const.EXTENTION_EXD;
                    List<WordTable> wordList = FileWorkHelper.parseVocabFile(pathWithFileName , vocabulary.getId());
                    for (int j = 0; j < wordList.size(); j++) {
                        dbHelper.getWordsRuntimeDataDao().create(wordList.get(j));
                        countWords++;
                    }
                }
            }
            ToastHelper.doInUIThread("Imported " + Integer.toString(countWords) + " words", this);
        }
    }

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
	}

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imgAddVocab:
                openAddEditVocabActivity();
                break;
            case R.id.imgLoadAllFromFolder:
                openFileExplorerForSelectingDirectoryWithVocabs();
                break;
            default:
                break;
        }
    }

    private void openFileExplorerForSelectingDirectoryWithVocabs(){
        IntentHelper.openFileExplorerForChoiceDirectory(this);
    }

	private void openAddEditVocabActivity() {
		AlertDialogsHolder.openCreateVocabularyDialog(this, this);
	}

	@Override
	public void onPossitiveBtnPress(String key, String value) {
		DatabaseHelper dbHelper = new DatabaseHelper(this);
		Vocabulary voca = new Vocabulary(key, value, 1, new Date(), new Date());
		dbHelper.getVocabularyRuntimeDataDao().create(voca);

		getSupportLoaderManager().restartLoader(0, null, this);
		IntentHelper.openEditVocabularyActivity(voca.getId(), VocabulariesListActivity.this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
}
