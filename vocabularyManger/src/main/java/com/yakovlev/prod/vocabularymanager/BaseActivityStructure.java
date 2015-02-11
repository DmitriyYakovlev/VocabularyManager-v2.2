package com.yakovlev.prod.vocabularymanager;

import android.view.View;
import android.view.View.OnClickListener;

public interface BaseActivityStructure extends OnClickListener{

 	public void findAllViews();
	
	public void setOnClickListeners();
	
	@Override
	public void onClick(View v);
	
	/*
		switch (v.getVocabularyId()) {
		case R.id.tvLearn:
			break;
		case R.id.tvEditVocabs:
			break;
		default:
			break;
		}
	
	 */
}
