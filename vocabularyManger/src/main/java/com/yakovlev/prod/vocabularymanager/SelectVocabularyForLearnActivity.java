package com.yakovlev.prod.vocabularymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.yakovlev.prod.vocabularymanager.adapters.SimpleVocabCursorAdapter;
import com.yakovlev.prod.vocabularymanager.constants.Const;
import com.yakovlev.prod.vocabularymanager.cursor_loaders.VocabulariesCursorLoader;
import com.yakovlev.prod.vocabularymanager.dialogs.AlertDialogsHolder;
import com.yakovlev.prod.vocabularymanager.dialogs.DialogAskCallback;
import com.yakovlev.prod.vocabularymanager.ormlite.CursorHelper;
import com.yakovlev.prod.vocabularymanager.support.SharedPreferencesHelper;
import com.yakovlev.prod.vocabularymanager.support.ToastHelper;
import com.yakovlev.prod.vocabularymanger.R;

import java.util.ArrayList;
import java.util.List;

public class SelectVocabularyForLearnActivity   extends FragmentActivity implements
        BaseActivityStructure, LoaderManager.LoaderCallbacks<Cursor>, DialogAskCallback {

    private int vocabulariesCount;
    private ImageButton btnShowHardWords;
    private ListView listContent;
    private TextView tvHeader;
    private CursorAdapter baseCursorAdapter;
    private List<Integer> vocabIdsList;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_learn_list);

        processLastVocabularyIdFromPreferences();

        findAllViews();
        setOnClickListeners();
        tvHeader.setText("Select vocabulary");

        listContent = (ListView) findViewById(R.id.lvVocabs);
        getSupportLoaderManager().initLoader(0, null, this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (baseCursorAdapter != null) {
            baseCursorAdapter.notifyDataSetChanged();
            listContent.refreshDrawableState();
        }
    }

    private int lastVocabularyId;
    private void processLastVocabularyIdFromPreferences() {
            lastVocabularyId = SharedPreferencesHelper.loadNumberFromSharedPreferences(this, SharedPreferencesHelper.KEY_LAST_USED_VOCABULARY_ID);
            if (lastVocabularyId != -1)
                AlertDialogsHolder.openAskDialog(this, "Restart last vocabulary ?", this);
    }

    @Override
    public void onNegativeAskButtonPress() {

    }

    @Override
    public void onPossitiveAskButtonPress() {
        openLearnWordsInVocabularyActivity(lastVocabularyId);
    }

    @Override
    public void findAllViews() {
        tvHeader = (TextView) findViewById(R.id.tvActName);
        btnShowHardWords = (ImageButton)findViewById(R.id.imgBtnHardWords);
    }

    @Override
    public void setOnClickListeners() {
        btnShowHardWords.setOnClickListener(this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        return new VocabulariesCursorLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        baseCursorAdapter = setCursorAdapter(cursor);
        fillVocabularyIdsList(cursor);
        listContent.setAdapter(baseCursorAdapter);
        listContent.setOnItemClickListener(itemListener);
    }

    private void fillVocabularyIdsList(Cursor cursor){
        vocabIdsList = new ArrayList<Integer>();
        while (cursor.moveToNext()) {
            Integer id = CursorHelper.getNumberByField(cursor, "_id");
            vocabIdsList.add(id);
        }
    }

    AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
            Integer id = (int) baseCursorAdapter.getItemId(position);
            openLearnWordsInVocabularyActivity(id);
        }
    };

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
        SimpleVocabCursorAdapter adapter =  new SimpleVocabCursorAdapter(this, cursor);
        vocabulariesCount = adapter.getCount();
        ToastHelper.doInUIThread("Vocabularies count : " + Integer.toString(vocabulariesCount), this);
		return adapter;
	}

    private void openLearnWordsInVocabularyActivity(int id){
        Intent intent = new Intent(this, LearnWordsInVocabularyActivity.class);
        intent.putExtra(Const.VOCAB_ID_POSITION_IN_LIST, id);
        intent.putIntegerArrayListExtra(Const.VOCAB_IDS_LIST, (ArrayList<Integer>) vocabIdsList);
        startActivity(intent);
    }
	
	@Override
	public void onClick(View v) {
		switch (v.getId()){
            case R.id.imgBtnHardWords:
                openLearnWordsInVocabularyActivity(Const.OPEN_LEARN_WORDS_ACTIVITY_FOR_HARD_WORDS);
                break;
        }
	}

}
