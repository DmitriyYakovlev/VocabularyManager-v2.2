package com.yakovlev.prod.vocabularymanager;

import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;
import android.view.View;

import com.yakovlev.prod.vocabularymanager.adapters.LearnWordsCursorAdapter;
import com.yakovlev.prod.vocabularymanager.adapters.SimpleVocabCursorAdapter;
import com.yakovlev.prod.vocabularymanager.cursor_loaders.WordsCursorLoader;
import com.yakovlev.prod.vocabularymanger.R;

public class LearnListActivity extends BaseListActivity {

	LearnWordsCursorAdapter adapter;

	@Override
	public int setContentView() {
		return R.layout.activity_learn_list;
	}

	@Override
	public CursorAdapter setCursorAdapter(Cursor cursor) {
		adapter = new LearnWordsCursorAdapter(this, cursor);
		return adapter;
	}

	@Override
	public void onItemClickWork(int id) {
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public String setActivityHeader() {
		return "Learn";
	}

	@Override
	public CursorLoader setCursorLoader() {
		return new WordsCursorLoader(getId(), this);
	}

}