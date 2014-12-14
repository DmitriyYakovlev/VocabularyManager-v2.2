package com.yakovlev.prod.vocabularymanager;

import java.text.DecimalFormat;
import java.util.List;

import com.yakovlev.prod.vocabularymanager.adapters.WrongAnswersListAdapter;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;
import com.yakovlev.prod.vocabularymanager.support.IntentHelper;
import com.yakovlev.prod.vocabularymanger.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TestingResultActivity extends Activity implements OnClickListener {

	TextView tvHeader, tvPersentage, tvRatio, tvWall;
	Button btnGoToMenu;
	ListView lvWrongView;
	RelativeLayout rlLeftContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testing_result);

		findViews();
		setOnClickListeners();
		setData();
	}

	private void findViews() {
		tvHeader = (TextView) findViewById(R.id.tvActName);
		tvHeader.setText("Testing results");

		tvPersentage = (TextView) findViewById(R.id.tvTestPersentage);
		tvRatio = (TextView) findViewById(R.id.tvTestNumber);

		tvWall = (TextView) findViewById(R.id.empyWall);
		lvWrongView = (ListView) findViewById(R.id.lvWrong);
		btnGoToMenu = (Button) findViewById(R.id.btnGoToMainManu);

		rlLeftContent = (RelativeLayout) findViewById(R.id.rlLeftResultContent);
	}

	private void setOnClickListeners() {
		btnGoToMenu.setOnClickListener(this);
	}

	private void setData() {
		List<WordTable> wrongAnswers = VocabularyApp.wrongAnswersList;

		if (wrongAnswers.size() == 0) {
			rlLeftContent.setVisibility(View.GONE);
		} else {
			WrongAnswersListAdapter wAdapter = new WrongAnswersListAdapter(wrongAnswers, this);
			lvWrongView.setAdapter(wAdapter);
		}
		String strPersentage = getResources().getString(R.string.txt_persentage);
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		String str = strPersentage + df.format(VocabularyApp.testPersentValue) + " % ";
		tvPersentage.setText(str);

		String strRation = getResources().getString(R.string.txt_corect_num);
		strRation = strRation + Integer.toString(VocabularyApp.testTrueAnswersCount) + " / "
				+ Integer.toString(VocabularyApp.testAllAnswersCount);
		tvRatio.setText(strRation);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		VocabularyApp.clearAllData();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnGoToMainManu:
			IntentHelper.goToMainMenuIntent(this);
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		IntentHelper.goToMainMenuIntent(this);
		finish();
	}

}
