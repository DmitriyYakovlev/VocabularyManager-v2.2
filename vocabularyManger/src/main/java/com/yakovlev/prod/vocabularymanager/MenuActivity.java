package com.yakovlev.prod.vocabularymanager;

import com.yakovlev.prod.vocabularymanager.support.IntentHelper;
import com.yakovlev.prod.vocabularymanger.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends Activity implements BaseActivityStructure {

	TextView tvLearn, tvTest,tvVocabulary, tvActHeader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		findAllViews();
		setOnClickListeners();

		tvActHeader.setText("Main Menu");
	}

	@Override
	public void findAllViews() {
		tvTest = (TextView) findViewById(R.id.tvTest);
		tvLearn = (TextView) findViewById(R.id.tvLearn);
		tvVocabulary = (TextView) findViewById(R.id.tvEditVocabs);
		tvActHeader = (TextView) findViewById(R.id.tvActName);
	}

	@Override
	public void setOnClickListeners() {
		tvTest.setOnClickListener(this);
		tvLearn.setOnClickListener(this);
		tvVocabulary.setOnClickListener(this);
		tvActHeader.setOnClickListener(this);
	}
 
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvTest:
			IntentHelper.startActivity(SelectVocabularyForTestingActivity.class, this);
			break;
		case R.id.tvLearn:
			IntentHelper.startActivity(SelectVocabularyForLearnActivity.class, this);
			break;
		case R.id.tvEditVocabs:
			IntentHelper.startActivity(VocabulariesListActivity.class, this);
			break;

		default:
			break;
		}
	}

}