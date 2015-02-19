package com.yakovlev.prod.vocabularymanager;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.ImageButton;

import com.yakovlev.prod.vocabularymanager.adapters.SimpleVocabCursorAdapter;
import com.yakovlev.prod.vocabularymanager.constants.Const;
import com.yakovlev.prod.vocabularymanager.cursor_loaders.VocabulariesCursorLoader;
import com.yakovlev.prod.vocabularymanager.support.ToastHelper;
import com.yakovlev.prod.vocabularymanger.R;

public class SelectVocabularyForLearnActivity  extends BaseListActivity  {

    private int vocabulariesCount;
    private ImageButton btnShowHardWords;

    @Override
	public int setContentView() {
		return R.layout.activity_learn_list;
	}

	@Override
	public CursorAdapter setCursorAdapter(Cursor cursor) {
        SimpleVocabCursorAdapter adapter =  new SimpleVocabCursorAdapter(this, cursor);
        vocabulariesCount = adapter.getCount();
        ToastHelper.doInUIThread("Vocabularies count : " + Integer.toString(vocabulariesCount), this);
		return adapter;
	}

    @Override
    protected void processOnCreateAdditionalCode() {
        btnShowHardWords = (ImageButton)findViewById(R.id.imgBtnHardWords);
        btnShowHardWords.setOnClickListener(this);
    }

    @Override
	public void onItemClickWork(int id) {
        openLearnWordsInVocabularyActivity(id);
	}

    private void openLearnWordsInVocabularyActivity(int id){
        Intent intent = new Intent(this, LearnWordsInVocabularyActivity.class);
        intent.putExtra(Const.VOCAB_ID, id);
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

	@Override
	public String setActivityHeader() {
		return "Select vocabulary";
	}

	@Override
	public CursorLoader setCursorLoader() {
		return new VocabulariesCursorLoader(this);
	}


}
