package com.yakovlev.prod.vocabularymanager.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.yakovlev.prod.vocabularymanager.EditVocabularyActivity;
import com.yakovlev.prod.vocabularymanager.MenuActivity;
import com.yakovlev.prod.vocabularymanager.SelectVocabularyForTestingActivity;
import com.yakovlev.prod.vocabularymanager.ServerConnectionActivity;
import com.yakovlev.prod.vocabularymanager.TestingActivity;
import com.yakovlev.prod.vocabularymanager.constants.Const;
import com.yakovlev.prod.vocabularymanager.constants.TestingType;
import com.yakovlev.prod.vocabularymanager.file_explorer.FileExplorerActivity;

public class IntentHelper {

	public static void openEditVocabularyActivity(Integer id, Context context) {
		Intent intent = new Intent(context, EditVocabularyActivity.class);
		intent.putExtra(Const.VOCAB_ID, id);
		((Activity) context).startActivityForResult(intent, Const.REQUEST_CODE_EDIT_VOCAB);
	}

	public static void openFileExplorerForChoiceFileForImporting(Context context) {
		Intent intent = new Intent(context, FileExplorerActivity.class);
		intent.putExtra(Const.GETTING_FILE_FOR_IMPORT, 1);
		((Activity) context).startActivityForResult(intent, Const.REQUEST_CODE_FOR_IMPORT);
	}

	public static void sendIntentFromExplorer(String pathForSendResult, String key, Context context) {
		Intent resultIntent = new Intent();
		resultIntent.putExtra(key, pathForSendResult);
		((Activity) context).setResult(Activity.RESULT_OK, resultIntent);
	}

	public static void sendIntentForSeverConnection(Context context) {
		Intent intent = new Intent(context, ServerConnectionActivity.class);
		((Activity) context).startActivityForResult(intent, Const.REQUEST_CODE_FOR_SERVER_CONNECTION);
	}
	
	public static void startActivity( Class<?> cls, Activity context) {
		Intent intent = new Intent(context, cls);
		context.startActivity(intent);
	}
	
	public static void goToMainMenuIntent(Activity context){
		Intent intent = new Intent(context, MenuActivity.class);
		context.startActivity(intent);
	}
	
	public static void openTestingActivity(int vocabularyId, int testingType, Activity context){
		Intent intent = new Intent(context, TestingActivity.class);
		intent.putExtra(Const.VOCAB_ID, vocabularyId);
		intent.putExtra(Const.PUT_EXTRA_TESTING_TYPE, testingType);
		context.startActivity(intent);
	}
}
