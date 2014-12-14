package com.yakovlev.prod.vocabularymanager.dialogs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.yakovlev.prod.vocabularymanager.TestingActivity;
import com.yakovlev.prod.vocabularymanager.adapters.VocabularyDialogAdapter;
import com.yakovlev.prod.vocabularymanager.constants.Const;
import com.yakovlev.prod.vocabularymanager.constants.TestingType;
import com.yakovlev.prod.vocabularymanager.ormlite.Vocabulary;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;
import com.yakovlev.prod.vocabularymanager.support.IntentHelper;
import com.yakovlev.prod.vocabularymanager.support.ToastHelper;
import com.yakovlev.prod.vocabularymanger.R;

public class AlertDialogsHolder {

	public static void openCreateNewWord(final Context context, final DialogButtonsCallback callback) {
		WordTable wordTable = new WordTable("", "", 1);
		openEditWordDialog(context, callback, wordTable);
	}

	public static void openEditWord(final Context context, final DialogButtonsCallback callback, WordTable wordTable) {
		openEditWordDialog(context, callback, wordTable);
	}

	public static void openEditWordDialog(final Context context, final DialogButtonsCallback callback,
			final WordTable wordTable) {

		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_edit_word);

		TextView tvHeader = (TextView) dialog.findViewById(R.id.tvDialogHeader);

		String key = wordTable.getwKey();
		String value = wordTable.getwValue();

		if (key.equals("") && value.equals(""))
			tvHeader.setText("Enter new words");
		else
			tvHeader.setText("Edit word");

		final EditText edtKey = (EditText) dialog.findViewById(R.id.edtKey);
		edtKey.setText(key);

		final EditText edtValue = (EditText) dialog.findViewById(R.id.edtValue);
		edtValue.setText(value);

		Button btnPossitive = (Button) dialog.findViewById(R.id.btnPossitive);
		btnPossitive.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				WordTable wNew = wordTable;
				String edtKeyValue = edtKey.getText().toString();
				String edtValueText = edtValue.getText().toString();

				if (edtKeyValue == null || edtKeyValue.equals("")) {
					ToastHelper.useShortToast(context, "Please, enter text in top field");
					return;
				}
				if (edtValueText == null || edtValueText.equals("")) {
					ToastHelper.useShortToast(context, "Please, enter text in bottom field");
					return;
				}

				wNew.setwKey(edtKeyValue);
				wNew.setwValue(edtValueText);

				callback.possitiveButtonPress(wNew);

				cloaseKeyBoardAndDismiss(context, edtValue, dialog);
			}
		});

		Button btnNegative = (Button) dialog.findViewById(R.id.btnNegative);
		btnNegative.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cloaseKeyBoardAndDismiss(context, edtValue, dialog);
			}
		});

		dialog.show();

	}

	private static void cloaseKeyBoardAndDismiss(Context context, EditText edtValue, Dialog dialog) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edtValue.getWindowToken(), 0);
		dialog.dismiss();
	}

	public static void openSelectWayDialog(final Activity context, final DialogButtonsCallback callback) {

		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_select_way);

		TextView tvHeader = (TextView) dialog.findViewById(R.id.tvDialogHeader);
		tvHeader.setText(context.getApplicationContext().getString(R.string.s_select_way));

		Button btnFromFile = (Button) dialog.findViewById(R.id.btnFromFile);
		btnFromFile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				IntentHelper.openFileExplorerForChoiceFileForImporting(context);
				dialog.dismiss();
			}
		});

		Button btnByHand = (Button) dialog.findViewById(R.id.btnByHand);
		btnByHand.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openCreateNewWord(context, callback);
				dialog.dismiss();
			}
		});

		Button btnFromServer = (Button) dialog.findViewById(R.id.btnFromServer);
		btnFromServer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				IntentHelper.sendIntentForSeverConnection(context);
				dialog.dismiss();
			}
		});

		Button btnFromHolder = (Button) dialog.findViewById(R.id.btnFromHolder);
		btnFromHolder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	public static void openCreateVocabularyDialog(final Context context, final DialogPositiveBtnCallBack callback) {

		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_enter_vocab_info);

		TextView tvHeader = (TextView) dialog.findViewById(R.id.tvDialogHeader);
		tvHeader.setText(context.getString(R.string.h_add_vocab_dialog));

		final EditText edtKey = (EditText) dialog.findViewById(R.id.edtKey);
		final EditText edtValue = (EditText) dialog.findViewById(R.id.edtValue);

		final Button btnPossitive = (Button) dialog.findViewById(R.id.btnPossitive);
		btnPossitive.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String key = edtKey.getText().toString();
				String value = edtValue.getText().toString();

				callback.onPossitiveBtnPress(key, value);
				cloaseKeyBoardAndDismiss(context, edtValue, dialog);
			}
		});

		Button btnNegative = (Button) dialog.findViewById(R.id.btnNegative);
		btnNegative.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cloaseKeyBoardAndDismiss(context, edtValue, dialog);
			}
		});

		edtKey.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String text = edtKey.getText().toString();
				if (text.equals(""))
					btnPossitive.setEnabled(false);
				else
					btnPossitive.setEnabled(true);
			}
		});

		dialog.show();

	}

	public static void openAskDialog(final Context context, String message, final DialogAskCallback callback) {

		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_ask);

		TextView tvHeader = (TextView) dialog.findViewById(R.id.tvDialogHeader);
		tvHeader.setText(message);

		final Button btnPossitive = (Button) dialog.findViewById(R.id.btnOk);
		btnPossitive.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.onPossitiveAskButtonPress();
				dialog.dismiss();
			}
		});

		Button btnNegative = (Button) dialog.findViewById(R.id.btnCancel);
		btnNegative.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.onNegativeAskButtonPress();
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	public static void openSelectWayOfTestingDialog(final int vocabularyId, final Activity context) {

		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_select_testing_way);

		TextView tvHeader = (TextView) dialog.findViewById(R.id.tvDialogHeader);
		tvHeader.setText(context.getApplicationContext().getString(R.string.s_select_testing_way));

		Button btnFreeAnswer = (Button) dialog.findViewById(R.id.btnFreeAnswer);
		btnFreeAnswer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				IntentHelper.openTestingActivity(vocabularyId, TestingType.FREE_ANSWER.ordinal(), context);
				dialog.dismiss();
			}
		});

		Button btnEstablishCom = (Button) dialog.findViewById(R.id.btnEstablishCompliance);
		btnEstablishCom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		Button btnChooseVariant = (Button) dialog.findViewById(R.id.btnChooseVariant);
		btnChooseVariant.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				IntentHelper.openTestingActivity(vocabularyId, TestingType.CHOOSE_ONE_VARIANT.ordinal(), context);
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	public static void openSelectVocabularyDialog(List<Vocabulary> vocabList, final Activity context,
			OnItemClickCallback callback) {

		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_select_vocab_from_list);

		ListView lvVocabs = (ListView) dialog.findViewById(R.id.lvVocabsOnServer);
		VocabularyDialogAdapter vocabularyDialogAdapter = new VocabularyDialogAdapter(vocabList, context);
		lvVocabs.setAdapter(vocabularyDialogAdapter);

		dialog.show();
	}

}
