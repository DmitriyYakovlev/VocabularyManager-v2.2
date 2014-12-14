package com.yakovlev.prod.vocabularymanager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;
import com.y_prod.vocabularymanager.data_holders.TestAnswersHolder;
import com.y_prod.vocabularymanager.view_pager.ContentForViewPagerAdapter;
import com.y_prod.vocabularymanager.view_pager.ViewPagerItemCallback;
import com.yakovlev.prod.vocabularymanager.constants.Const;
import com.yakovlev.prod.vocabularymanager.constants.TestingType;
import com.yakovlev.prod.vocabularymanager.ormlite.DatabaseHelper;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;
import com.yakovlev.prod.vocabularymanager.support.InitViewsHelper;
import com.yakovlev.prod.vocabularymanager.support.PersentageCalculateHelper;
import com.yakovlev.prod.vocabularymanager.support.RandomizeHelper;
import com.yakovlev.prod.vocabularymanger.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class TestingActivity extends FragmentActivity implements ViewPagerItemCallback {

	ViewPager mViewPager;
	PageIndicator mPageIndicator;

	// Adapters
	private ContentForViewPagerAdapter mContentAdapter;
	private Dao<WordTable, Integer> wordsDao;
	List<WordTable> list;
	List<String[]> allVariants;
	private int vocabularyId;
	
	TextView tvPersentageGlob;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testing);

		TextView tvHeader = (TextView) findViewById(R.id.tvActName);
		tvHeader.setText("Testing . . .");
		
		tvPersentageGlob = (TextView) findViewById(R.id.tvPercentageGlobal);

		Intent intent = getIntent();
		vocabularyId = intent.getIntExtra(Const.VOCAB_ID, -1);

		if (vocabularyId == -1) {
			finish();
		} else {
			int testingType = intent.getIntExtra(Const.PUT_EXTRA_TESTING_TYPE, -1);
			initializeViewPager(testingType);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		VocabularyApp.clearWrongAnswersData();
		blockedWordsIdsList.clear();
	}

	private String[] generateWrongAnswersArray(int position) {
		int[] ex = { position };

		Random random = new Random();
		String answerVariants[] = new String[3];
		for (int j = 0; j < 3; j++) {
			int val = RandomizeHelper.getRandomWithExclusion(random, 0, list.size() - 1, ex);
			answerVariants[j] = list.get(val).getwValue();
		}

		return answerVariants;
	}

	public void initializeViewPager(int testingType) {
		DatabaseHelper dbHelper = new DatabaseHelper(this);
		try {
			wordsDao = dbHelper.getWordDao();
			list = wordsDao.queryForEq("vocabularyId", vocabularyId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		mContentAdapter = new ContentForViewPagerAdapter(getSupportFragmentManager(), list, testingType, this, this);

		mViewPager = (ViewPager) findViewById(R.id.pagerView);
		mViewPager.setAdapter(mContentAdapter);
		
		mPageIndicator = (CirclePageIndicator) findViewById(R.id.indicatorLine);
		mPageIndicator.setViewPager(mViewPager);

		mPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}

	int genNumberOfTrueButton;

	@Override
	public View onCreateViewForViewPager(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState,
			WordTable wordTable, int position, int testingType) {

		if (testingType == TestingType.CHOOSE_ONE_VARIANT.ordinal()){
			return chooseOneVariantItem(inflater, container, savedInstanceState, wordTable, position, testingType);
		}
		else if (testingType == TestingType.FREE_ANSWER.ordinal()){
			return freeAnswerVariantItem(inflater, container, savedInstanceState, wordTable, position, testingType);
		}
		return null;
	}

	
	private View chooseOneVariantItem(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState,
			WordTable wordTable, int position, int testingType){
		View view = inflater.inflate(R.layout.item_view_pager_chose_one_variant, null);

		TextView tvLeft = (TextView) view.findViewById(R.id.tvLeftKeyText);
		tvLeft.setText(wordTable.getwKey());

		Button btnFirst = (Button) view.findViewById(R.id.btnFirstVariant);
		Button btnSecond = (Button) view.findViewById(R.id.btnSecondVariant);
		Button btnThird = (Button) view.findViewById(R.id.btnThirdVariant);
		Button btnFour = (Button) view.findViewById(R.id.btnFourVariant);

		Button[] allButtons = { btnFirst, btnSecond, btnThird, btnFour };
		for (int i = 0; i < allButtons.length; i++) 
			allButtons[i].setTag(i);
		
		if (VocabularyApp.answersMap.containsKey(wordTable.getId())){
			setDataForDisabledPage(wordTable, allButtons);
			return view;
		}

		String trueAnswerStr = wordTable.getwValue();
		genNumberOfTrueButton = RandomizeHelper.randInt(0, 3);

		int countOfWrongAnswers = 0;
		String[] wrongVariants = generateWrongAnswersArray(position);
		for (int i = 0; i < allButtons.length; i++) {
			if (i == genNumberOfTrueButton) {
				allButtons[i].setText(trueAnswerStr);
			} else {
				String valueWrongAnswers = wrongVariants[countOfWrongAnswers];
				countOfWrongAnswers++;
				allButtons[i].setText(valueWrongAnswers);
			}
		}

		VariantsButtonClickListener listener = new VariantsButtonClickListener(genNumberOfTrueButton, allButtons,
				wordTable);
		for (int i = 0; i < allButtons.length; i++)
			allButtons[i].setOnClickListener(listener);

		return view;
	}

	
	public static List<Integer> blockedWordsIdsList = new ArrayList<Integer>();

	private View freeAnswerVariantItem(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState,
			final WordTable wordTable, int position, int testingType) {
		
		TextView tvLeft;
		final Button btnOk;
		final EditText edtValue;
		View view = inflater.inflate(R.layout.texting_view_pager_content, null);

		tvLeft = (TextView)view.findViewById(R.id.tvLeftKeyText);
		tvLeft.setText(wordTable.getwKey());
		
		edtValue = (EditText)view.findViewById(R.id.edtValue);

		btnOk = (Button)view.findViewById(R.id.btnGetAnswer);
		
		if (blockedWordsIdsList.contains(wordTable.getId())){
			btnOk.setEnabled(false);
			String woString = wordTable.getwValue();
			edtValue.setEnabled(true);

			edtValue.setText(woString);
			edtValue.setEnabled(false);

			return view;
		}
		
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				String leftData = wordTable.getwValue();
				String trimedLeftData = InitViewsHelper.trimLeftRight(leftData);
				
				String enteredData = edtValue.getText().toString();
				String trimedEnteredData = InitViewsHelper.trimLeftRight(enteredData);
				
				if (trimedLeftData.equals(trimedEnteredData)){
					VocabularyApp.testTrueAnswersCount++;
					edtValue.setBackgroundColor(getResources().getColor(R.color.col_green));
				}
				else
				{
					VocabularyApp.wrongAnswersList.add(wordTable);
					edtValue.setBackgroundColor(getResources().getColor(R.color.col_red));
				}
				
				edtValue.setEnabled(false);
				btnOk.setEnabled(false);
				VocabularyApp.testAllAnswersCount++;
				blockedWordsIdsList.add(wordTable.getId());
				
				if (blockedWordsIdsList.size() == list.size())
				{
					Intent intent = new Intent(TestingActivity.this, TestingResultActivity.class);
					startActivity(intent);
					((Activity)TestingActivity.this).finish();
				}
				
				calculateAndSetPersentage();
				
			}
		});
		return view;
	}
	
	private void setDataForDisabledPage(WordTable wordTable, Button[] allButtons){
		TestAnswersHolder answers = VocabularyApp.answersMap.get(wordTable.getId());
		
		int trueAns =answers.getTrueAnsPosition();
		int pressedAns = answers.getPressedAnsPosition();
		if (trueAns == pressedAns)
		{
			allButtons[trueAns].setBackground(getResources().getDrawable(R.drawable.rounded_button_true_state));
		}
		else{
			allButtons[trueAns].setBackground(getResources().getDrawable(R.drawable.rounded_button_true_state));
			allButtons[pressedAns].setBackground(getResources().getDrawable(R.drawable.rounded_button_false_state));
		}
	
		InitViewsHelper.disableButtonsArray(allButtons);
		InitViewsHelper.setButtonNames(allButtons, answers.getVariantsForButtons());
	}
	
	class VariantsButtonClickListener implements OnClickListener {

		int geneTrueP;
		Button[] buttons;
		WordTable wordTable;

		public VariantsButtonClickListener(int geneTrueP, Button[] buttons, WordTable wordTable) {
			this.geneTrueP = geneTrueP;
			this.buttons = buttons;
			this.wordTable = wordTable;
		}

		@Override
		public void onClick(View v) {
			Integer pressedButtonTag = (Integer) v.getTag();

			if (pressedButtonTag == geneTrueP) {
				VocabularyApp.testTrueAnswersCount++;

			} else {
				buttons[pressedButtonTag].setBackground(getResources().getDrawable(
						R.drawable.rounded_button_false_state));
				VocabularyApp.wrongAnswersList.add(wordTable);
			}
			buttons[geneTrueP].setBackground(getResources().getDrawable(R.drawable.rounded_button_true_state));

			VocabularyApp.testAllAnswersCount++;

			if (VocabularyApp.testAllAnswersCount == list.size()) {
				Intent intent = new Intent(TestingActivity.this, TestingResultActivity.class);
				startActivity(intent);
				finish();
			}
			
			InitViewsHelper.disableButtonsArray(buttons);
			List<String> answersForButtons = InitViewsHelper.getButtonNamesArray(buttons);
			TestAnswersHolder testAnswer = new TestAnswersHolder(geneTrueP, pressedButtonTag, answersForButtons);
			VocabularyApp.answersMap.put(wordTable.getId(), testAnswer);

			calculateAndSetPersentage();
		}

	}
	
	private void calculateAndSetPersentage(){
		if (tvPersentageGlob.getVisibility() == View.GONE){
			tvPersentageGlob.setVisibility(View.VISIBLE);
		}
		VocabularyApp.testPersentValue = PersentageCalculateHelper.countPersent(VocabularyApp.testAllAnswersCount, VocabularyApp.testTrueAnswersCount);
		PersentageCalculateHelper.setPersentageInTv(tvPersentageGlob);		
	}

}
