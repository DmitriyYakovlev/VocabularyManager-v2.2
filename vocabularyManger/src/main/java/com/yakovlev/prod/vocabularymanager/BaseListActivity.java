package com.yakovlev.prod.vocabularymanager;

import com.yakovlev.prod.vocabularymanager.constants.Const;
import com.yakovlev.prod.vocabularymanager.dialogs.AlertDialogsHolder;
import com.yakovlev.prod.vocabularymanager.dialogs.DialogAskCallback;
import com.yakovlev.prod.vocabularymanager.support.SharedPreferencesHelper;
import com.yakovlev.prod.vocabularymanger.R;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public abstract class BaseListActivity extends FragmentActivity implements
		BaseActivityStructure, LoaderCallbacks<Cursor>, DialogAskCallback {

	private ListView listContent;
	private TextView tvHeader;
	private int vocabularyId;
	private CursorAdapter baseCursorAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(setContentView());

        Intent intent = getIntent();
        vocabularyId = intent.getIntExtra(Const.VOCAB_ID, -1);

        processLastVocabularyIdFromPreferences(vocabularyId);

        findAllViews();
        setOnClickListeners();
        tvHeader.setText(setActivityHeader());

        listContent = (ListView) findViewById(R.id.lvVocabs);
        getSupportLoaderManager().initLoader(0, null, this);

        processOnCreateAdditionalCode();
	}

    protected abstract void processOnCreateAdditionalCode();

    @Override
    protected void onResume() {
        super.onResume();
        if (baseCursorAdapter != null) {
            baseCursorAdapter.notifyDataSetChanged();
            listContent.refreshDrawableState();
        }
    }

    private int lastVocabularyId;
    private void processLastVocabularyIdFromPreferences(int vocabularyId) {
        if (vocabularyId != -1 ){
            SharedPreferencesHelper.saveNumberInSharedPreferences(vocabularyId, this, SharedPreferencesHelper.KEY_LAST_USED_VOCABULARY_ID);
        }
        else 
        {
            lastVocabularyId = SharedPreferencesHelper.loadNumberFromSharedPreferences(this, SharedPreferencesHelper.KEY_LAST_USED_VOCABULARY_ID);
            if (lastVocabularyId != -1 && lastVocabularyId != vocabularyId)
                AlertDialogsHolder.openAskDialog(this, "Restart last vocabulary ?", this);
        }
    }

    @Override
    public void onNegativeAskButtonPress() {

    }

    @Override
    public void onPossitiveAskButtonPress() {
        onItemClickWork(lastVocabularyId);
    }

    public int getVocabularyId() {
		return vocabularyId;
	}

	@Override
	public void findAllViews() {
		tvHeader = (TextView) findViewById(R.id.tvActName);
	}

	@Override
	public void setOnClickListeners() {

	}

	public abstract int setContentView();

	public abstract String setActivityHeader();

	public abstract CursorAdapter setCursorAdapter(Cursor cursor);

	public abstract void onItemClickWork(int id);

	public abstract CursorLoader setCursorLoader();

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return setCursorLoader();
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		baseCursorAdapter = setCursorAdapter(cursor);
		listContent.setAdapter(baseCursorAdapter);
		listContent.setOnItemClickListener(itemListener);
	}

	OnItemClickListener itemListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position,
				long arg3) {
			Integer id = (int) baseCursorAdapter.getItemId(position);
			onItemClickWork(id);
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

}
