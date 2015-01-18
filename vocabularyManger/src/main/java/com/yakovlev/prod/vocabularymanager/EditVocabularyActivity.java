package com.yakovlev.prod.vocabularymanager;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.yakovlev.prod.vocabularymanager.adapters.WordsListAdapter;
import com.yakovlev.prod.vocabularymanager.constants.Const;
import com.yakovlev.prod.vocabularymanager.dialogs.AlertDialogsHolder;
import com.yakovlev.prod.vocabularymanager.dialogs.DialogAskCallback;
import com.yakovlev.prod.vocabularymanager.dialogs.DialogButtonsCallback;
import com.yakovlev.prod.vocabularymanager.exceptions.ExceptionHelper;
import com.yakovlev.prod.vocabularymanager.file_explorer.FileExplorerActivity;
import com.yakovlev.prod.vocabularymanager.file_explorer.FileWorkHelper;
import com.yakovlev.prod.vocabularymanager.ormlite.DatabaseHelper;
import com.yakovlev.prod.vocabularymanager.ormlite.Vocabulary;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;
import com.yakovlev.prod.vocabularymanager.support.ToastHelper;
import com.yakovlev.prod.vocabularymanager.support.ValidationHelper;
import com.yakovlev.prod.vocabularymanger.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class EditVocabularyActivity extends Activity implements OnClickListener, DialogButtonsCallback {

	// views
	private ListView lvWords;
	private TextView tvHeader;
	private ImageButton btnAddWords, btnSave;
	private EditText edtVocabName, edtVocabDesc;

	// adapters
	private WordsListAdapter wordsListAdapter;

	// vars
	private Dao<WordTable, Integer> wordsDao;
	private Dao<Vocabulary, Integer> vocabularyDao;
	private DatabaseHelper dbHelper;
	private Vocabulary curentVocabulary;
	private int vocabIdForEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_vocab);

		findViews();
		setOnClickListeners();
		setOnTextChangedListener();
		setVocabularyInformationInActivity();
	}

	private void setVocabularyInformationInActivity() {
		try {
			setActivityContent();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setActivityContent() throws SQLException {
		setActivityHeader(vocabIdForEdit);

		Intent intent = getIntent();
		vocabIdForEdit = intent.getIntExtra(Const.VOCAB_ID, -1);

		dbHelper = new DatabaseHelper(this);
		vocabularyDao = dbHelper.getVocabularyDao();
		setVocabularyInformationForEdit(vocabIdForEdit);
	}

	private void findViews() {
		lvWords = (ListView) findViewById(R.id.lvWords);
		TextView tvEmpty = (TextView) findViewById(R.id.empy);
		lvWords.setEmptyView(tvEmpty);
		
		tvHeader = (TextView) findViewById(R.id.tvActName);
		btnAddWords = (ImageButton) findViewById(R.id.btnSelectAddWay);
		btnSave = (ImageButton) findViewById(R.id.btnSave);
		edtVocabName = (EditText) findViewById(R.id.edtVocabName);
		edtVocabDesc = (EditText) findViewById(R.id.edtVocabDesc);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != RESULT_CANCELED) {
			String pathFromFileExp = data.getStringExtra(FileExplorerActivity.PATH_TO_DIRECTORY_AND_FILE_NAME);

			if (requestCode == Const.REQUEST_CODE_FOR_IMPORT) {
				
				if (FileWorkHelper.isExtentionFalse(pathFromFileExp, "txt")){
					ToastHelper.useLongToast(EditVocabularyActivity.this, "Invalid extension name");
					return;
				}
					
				List<WordTable> wordList = FileWorkHelper.parseVocabFile(pathFromFileExp, vocabIdForEdit);
				
				for (int i = 0; i < wordList.size(); i++) {
					try {
						wordsDao.create(wordList.get(i));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				setWordsInfo(curentVocabulary.getId());
			}
		}

	}

	private void setOnClickListeners() {
		btnAddWords.setOnClickListener(this);
		btnSave.setOnClickListener(this);
	}

	private void setOnTextChangedListener() {
		edtVocabName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String text = edtVocabName.getText().toString();
				if (text.equals(""))
					btnSave.setEnabled(false);
				else
					btnSave.setEnabled(true);
			}
		});
	}

	private void setActivityHeader(int idForEditing) {
		tvHeader.setText(getResources().getString(R.string.h_edit_vocab));
	}

	private void setVocabularyInformationForEdit(int vocabId) {
		try {
			curentVocabulary = vocabularyDao.queryForEq("_id", vocabId).get(0);
			setVocabularyInfo();
			setWordsInfo(curentVocabulary.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setVocabularyInfo() {
		String vocName = curentVocabulary.getvName();
		String vocDesc = curentVocabulary.getvDescription();

		edtVocabName.setText(vocName);
		edtVocabDesc.setText(vocDesc);
	}

	private void setWordsInfo(int vocabId) {
		try {
			wordsDao = dbHelper.getWordDao();
			List<WordTable> list = wordsDao.queryForEq("vocabularyId", vocabId);
			wordsListAdapter = new WordsListAdapter(list, dbHelper, this);
			lvWords.setAdapter(wordsListAdapter);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnSelectAddWay:
			AlertDialogsHolder.openSelectWayDialog(this, this);
			break;
		case R.id.btnSave:
			getDataForSave();
			break;

		default:
			break;
		}
	}

	private void getDataForSave() {

		try {
			ValidationHelper.isEditTextNotEmpy(edtVocabName, getString(R.string.exc_empty_field));

			String vocabName = edtVocabName.getText().toString();
			String vocabDesc = edtVocabDesc.getText().toString();

			curentVocabulary.setvName(vocabName);
			curentVocabulary.setvDescription(vocabDesc);
			curentVocabulary.setLastModdate(new Date());
			vocabularyDao.update(curentVocabulary);

			setResult(RESULT_OK);
			finish();
		} catch (ExceptionHelper ex) {
			ToastHelper.useLongToast(this, ex.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void saveWordInVocabulary(WordTable wTable) {
		try {
			wTable.setVocabularyId(vocabIdForEdit);
			dbHelper = new DatabaseHelper(this);
			wordsDao = dbHelper.getWordDao();
			wordsDao.create(wTable);
			setWordsInfo(curentVocabulary.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void possitiveButtonPress(WordTable wTable) {
		saveWordInVocabulary(wTable);
	}

	@Override
	public void negativeButtonPress(String key, String value) {
	}

	DialogAskCallback dlgAsk = new DialogAskCallback() {

		@Override
		public void onPossitiveAskButtonPress() {
			getDataForSave();
		}

		@Override
		public void onNegativeAskButtonPress() {
			finish();
		}
	};

	@Override
	public void onBackPressed() {
//		AlertDialogsHolder.openAskDialog(this, getString(R.string.h_ask_question), dlgAsk);
        getDataForSave();

    }

}
