package com.yakovlev.prod.vocabularymanager;

import com.yakovlev.prod.vocabularymanager.adapters.SimpleVocabCursorAdapter;
import com.yakovlev.prod.vocabularymanager.cursor_loaders.VocabulariesCursorLoader;
import com.yakovlev.prod.vocabularymanager.dialogs.AlertDialogsHolder;
import com.yakovlev.prod.vocabularymanger.R;

import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.ImageButton;

public class SelectVocabularyForTestingActivity extends BaseListActivity  {

    private ImageButton btnShowHardWords;

	@Override
	public int setContentView() {
		return R.layout.activity_learn_list;
	}

	@Override
	public CursorAdapter setCursorAdapter(Cursor cursor) {
		return new SimpleVocabCursorAdapter(this, cursor);
	}

    @Override
    protected void processOnCreateAdditionalCode() {
        btnShowHardWords = (ImageButton)findViewById(R.id.imgBtnHardWords);
        btnShowHardWords.setVisibility(View.GONE);
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
		return "Select vocabulary";
	}

	@Override
	public CursorLoader setCursorLoader() {
		return new VocabulariesCursorLoader(this);
	}


}
