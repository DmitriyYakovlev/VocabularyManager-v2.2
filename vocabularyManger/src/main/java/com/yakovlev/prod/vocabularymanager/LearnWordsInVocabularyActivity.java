package com.yakovlev.prod.vocabularymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.yakovlev.prod.vocabularymanager.adapters.LearnWordsCursorAdapter;
import com.yakovlev.prod.vocabularymanager.constants.Const;
import com.yakovlev.prod.vocabularymanager.cursor_loaders.HardWordsCursorLoader;
import com.yakovlev.prod.vocabularymanager.cursor_loaders.WordsCursorLoader;
import com.yakovlev.prod.vocabularymanager.dialogs.AlertDialogsHolder;
import com.yakovlev.prod.vocabularymanager.dialogs.DialogAskCallback;
import com.yakovlev.prod.vocabularymanager.support.SharedPreferencesHelper;
import com.yakovlev.prod.vocabularymanager.support.ToastHelper;
import com.yakovlev.prod.vocabularymanger.R;

import java.util.ArrayList;

public class LearnWordsInVocabularyActivity extends FragmentActivity implements
        BaseActivityStructure, LoaderManager.LoaderCallbacks<Cursor>, DialogAskCallback {

	private LearnWordsCursorAdapter adapter;
    private ImageButton btnSwitchMode, btnPrevious, btnNext;
    private ListView listContent;
    private TextView tvHeader;
    private int vocabularyId;
    private CursorAdapter baseCursorAdapter;
    private boolean isOpenHardWordsModeActive = false;
    private ArrayList<Integer> vocabIsList;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_learn_select_vocab);

        Intent intent = getIntent();
        vocabularyId = intent.getIntExtra(Const.VOCAB_ID_POSITION_IN_LIST, -1);
        if (vocabularyId == Const.OPEN_LEARN_WORDS_ACTIVITY_FOR_HARD_WORDS){
            isOpenHardWordsModeActive = true;
        }
        else {
            vocabIsList = intent.getIntegerArrayListExtra(Const.VOCAB_IDS_LIST);
        }

        processLastVocabularyIdFromPreferences(vocabularyId);

        findAllViews();
        setOnClickListeners();
        tvHeader.setText("Learn");

        listContent = (ListView) findViewById(R.id.lvVocabs);
        getSupportLoaderManager().initLoader(0, null, this);

        if (isOpenHardWordsModeActive){
            btnPrevious.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (baseCursorAdapter != null) {
            baseCursorAdapter.notifyDataSetChanged();
            listContent.refreshDrawableState();
        }
        btnSwitchMode = (ImageButton)findViewById(R.id.imgBtnSwitch);
        btnSwitchMode.setOnClickListener(this);
    }

    private void processLastVocabularyIdFromPreferences(int vocabularyId) {
        if (!isOpenHardWordsModeActive) {
                SharedPreferencesHelper.saveNumberInSharedPreferences(vocabularyId, this, SharedPreferencesHelper.KEY_LAST_USED_VOCABULARY_ID);
        }
    }

    @Override
    public void onNegativeAskButtonPress() {

    }

    @Override
    public void onPossitiveAskButtonPress() {

    }

    @Override
    public void findAllViews() {
        tvHeader = (TextView) findViewById(R.id.tvActName);
        btnPrevious = (ImageButton)findViewById(R.id.btnPreliminaryVocabulary);
        btnNext = (ImageButton)findViewById(R.id.btnNextVocabulary);
    }

    @Override
    public void setOnClickListeners() {
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
//        int fromList = vocabIsList.get(vocabularyId-1);
//        ToastHelper.doInUIThread(Integer.toString(vocabularyId) + " - " + Integer.toString(fromList), LearnWordsInVocabularyActivity.this);

        if (isOpenHardWordsModeActive)
            return new HardWordsCursorLoader(this);
        else
            return new WordsCursorLoader(vocabularyId, this);


    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        baseCursorAdapter = setCursorAdapter(cursor);
        listContent.setAdapter(baseCursorAdapter);
    }

    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        getSupportLoaderManager().restartLoader(0, null, this);
        baseCursorAdapter.notifyDataSetChanged();
    };

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // some code :)
    }

	public CursorAdapter setCursorAdapter(Cursor cursor) {
		adapter = new LearnWordsCursorAdapter(this, cursor, this, vocabularyId);
        int wordsCount = adapter.getCount();
//        String message = "Number of words in vocabulary : " + Integer.toString(wordsCount);
//        ToastHelper.doInUIThreadShort(message, this);
		return adapter;
	}




	@Override
	public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBtnSwitch:
                adapter.changeSwitcher();
                break;
            case R.id.btnNextVocabulary:
                vocabularyId++;
                getSupportLoaderManager().restartLoader(0, null, this);
                processLastVocabularyIdFromPreferences(vocabularyId);
                break;
            case R.id.btnPreliminaryVocabulary:
                vocabularyId--;
                if (vocabularyId > 0 ) {
                    getSupportLoaderManager().restartLoader(0, null, this);
                    processLastVocabularyIdFromPreferences(vocabularyId);
                }
                else {
                    ToastHelper.doInUIThread("Vocabulary id ought be bigger than 0", this);
                    vocabularyId++;
                }
                break;
        }
	}

}