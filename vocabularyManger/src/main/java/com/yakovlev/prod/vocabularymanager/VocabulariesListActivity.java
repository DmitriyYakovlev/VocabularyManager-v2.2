package com.yakovlev.prod.vocabularymanager;

import java.util.Date;

import com.yakovlev.prod.vocabularymanager.adapters.VocabCursorAdapter;
import com.yakovlev.prod.vocabularymanager.constants.Const;
import com.yakovlev.prod.vocabularymanager.cursor_loaders.VocabulariesCursorLoader;
import com.yakovlev.prod.vocabularymanager.dialogs.AlertDialogsHolder;
import com.yakovlev.prod.vocabularymanager.dialogs.DialogButtonsCallback;
import com.yakovlev.prod.vocabularymanager.dialogs.DialogPositiveBtnCallBack;
import com.yakovlev.prod.vocabularymanager.ormlite.DatabaseHelper;
import com.yakovlev.prod.vocabularymanager.ormlite.Vocabulary;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;
import com.yakovlev.prod.vocabularymanager.support.IntentHelper;
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
	private ImageButton ibtnAddVocab;

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
		tvHeader.setText("Vocabularies list");
	}

	private void setOnClickListeners() {
		ibtnAddVocab.setOnClickListener(this);
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

	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		getSupportLoaderManager().restartLoader(0, null, this);
	};

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.imgAddVocab:
			openAddEditVocabActivity();
			break;
		default:
			break;
		}
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
