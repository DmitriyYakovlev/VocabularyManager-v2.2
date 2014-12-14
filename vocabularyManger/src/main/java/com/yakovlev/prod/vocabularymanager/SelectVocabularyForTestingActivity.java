package com.yakovlev.prod.vocabularymanager;

import com.yakovlev.prod.vocabularymanager.adapters.SimpleVocabCursorAdapter;
import com.yakovlev.prod.vocabularymanager.cursor_loaders.VocabulariesCursorLoader;
import com.yakovlev.prod.vocabularymanager.dialogs.AlertDialogsHolder;
import com.yakovlev.prod.vocabularymanger.R;

import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;
import android.view.View;

public class SelectVocabularyForTestingActivity extends BaseListActivity  {

	@Override
	public int setContentView() {
		return R.layout.activity_learn_list;
	}

	@Override
	public CursorAdapter setCursorAdapter(Cursor cursor) {
		return new SimpleVocabCursorAdapter(this, cursor);
	}


	@Override
	public void onItemClickWork(int id) {
		AlertDialogsHolder.openSelectWayOfTestingDialog(id, SelectVocabularyForTestingActivity.this);
	}
	
	@Override
	public void onClick(View v) {
		
	}

	@Override
	public String setActivityHeader() {
		return "Vocabularies for testing list";
	}

	@Override
	public CursorLoader setCursorLoader() {
		return new VocabulariesCursorLoader(this);
	}


}
