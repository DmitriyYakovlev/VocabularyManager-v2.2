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
import com.yakovlev.prod.vocabularymanager.ormlite.Vocabulary;
import com.yakovlev.prod.vocabularymanager.ormlite.VocabularyDbHelp;
import com.yakovlev.prod.vocabularymanager.support.HardWordMode;
import com.yakovlev.prod.vocabularymanager.support.SharedPreferencesHelper;
import com.yakovlev.prod.vocabularymanager.support.ToastHelper;
import com.yakovlev.prod.vocabularymanger.R;

import java.sql.SQLException;
import java.util.ArrayList;

public class LearnWordsInVocabularyActivity extends FragmentActivity implements
        BaseActivityStructure, LoaderManager.LoaderCallbacks<Cursor>, DialogAskCallback {

	private LearnWordsCursorAdapter adapter;
    private ImageButton btnSwitchMode, btnPrevious, btnNext, btnShowHardWordsFirstRank, btnShowHardWordsSecondRank, btnShowAllWords;
    private ListView listContent;
    private TextView tvHeader, tvVocabName;
    private int vocabularyId;
    private CursorAdapter baseCursorAdapter;
    private boolean isOpenHardWordsModeActive = false;
    private ArrayList<Integer> vocabIsList;
    private Vocabulary currentVocabulary;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_learn_select_vocab);

        findAllViews();
        setOnClickListeners();
        tvHeader.setText("Learn : ");

        Intent intent = getIntent();
        vocabularyId = intent.getIntExtra(Const.VOCAB_ID_POSITION_IN_LIST, -1);
        if (vocabularyId == Const.OPEN_LEARN_WORDS_ACTIVITY_FOR_HARD_WORDS){
            isOpenHardWordsModeActive = true;
        }
        else {
            vocabIsList = intent.getIntegerArrayListExtra(Const.VOCAB_IDS_LIST);
            currentVocabulary = initVocabularyByPosition(vocabularyId);
            tvVocabName.setText(currentVocabulary.getvName());
        }

        processLastVocabularyIdFromPreferences(vocabularyId);

        listContent = (ListView) findViewById(R.id.lvVocabs);
        getSupportLoaderManager().initLoader(0, null, this);

        if (isOpenHardWordsModeActive){
            btnPrevious.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
        }

    }

    private Vocabulary initVocabularyByPosition(int position){
        try {
            return VocabularyDbHelp.getVocabularyById(position, this);
        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
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
        tvVocabName = (TextView) findViewById(R.id.tvVocabName);
        btnPrevious = (ImageButton)findViewById(R.id.btnPreliminaryVocabulary);
        btnNext = (ImageButton)findViewById(R.id.btnNextVocabulary);

        btnShowHardWordsFirstRank = (ImageButton)findViewById(R.id.btnHardWordsFirstRank);
        btnShowHardWordsSecondRank = (ImageButton)findViewById(R.id.btnHardWordsSecondRank);
        btnShowAllWords = (ImageButton)findViewById(R.id.btnLearnedWord);
    }

    @Override
    public void setOnClickListeners() {
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        btnShowHardWordsFirstRank.setOnClickListener(this);
        btnShowHardWordsSecondRank.setOnClickListener(this);
        btnShowAllWords.setOnClickListener(this);
    }

    private HardWordMode hardWordsMode = HardWordMode.ALL_HARD_WORDS;
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {

        if (isOpenHardWordsModeActive)
            return new HardWordsCursorLoader(this,hardWordsMode);
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

	public CursorAdapter  setCursorAdapter(Cursor cursor) {
		adapter = new LearnWordsCursorAdapter(this, cursor, this, vocabularyId, hardWordsMode);
        int wordsCount = adapter.getCount();
        String message = "Number of words in vocabulary : " + Integer.toString(wordsCount);
        ToastHelper.doInUIThreadShort(message, this);
		return adapter;
	}

    private void processVocabularyChanging(){
        try {
            getSupportLoaderManager().restartLoader(0, null, this);
            processLastVocabularyIdFromPreferences(vocabularyId);
            currentVocabulary = initVocabularyByPosition(vocabularyId);
            tvVocabName.setText(currentVocabulary.getvName());
        }catch (Exception ex){
            ToastHelper.doInUIThreadShort("Toggle vocabulary exception", this);
        }
    }

	@Override
	public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBtnSwitch:
                adapter.changeSwitcher();
                break;
            case R.id.btnNextVocabulary:
                vocabularyId++;
                processVocabularyChanging();
                break;
            case R.id.btnPreliminaryVocabulary:
                vocabularyId--;
                if (vocabularyId > 0 ) {
                    processVocabularyChanging();
                }
                else {
                    ToastHelper.doInUIThread("Vocabulary id ought be bigger than 0", this);
                    vocabularyId++;
                }
                break;
            case R.id.btnHardWordsFirstRank:
                hardWordsMode = HardWordMode.ONLY_HARDEST_WORDS;
                getSupportLoaderManager().restartLoader(0, null, this);
                break;
            case R.id.btnHardWordsSecondRank:
                hardWordsMode = HardWordMode.ONLY_SECOND_RANG;
                getSupportLoaderManager().restartLoader(0, null, this);
                break;
            case R.id.btnLearnedWord:
                hardWordsMode = HardWordMode.ALL_HARD_WORDS;
                getSupportLoaderManager().restartLoader(0, null, this);

                break;
        }
	}

}