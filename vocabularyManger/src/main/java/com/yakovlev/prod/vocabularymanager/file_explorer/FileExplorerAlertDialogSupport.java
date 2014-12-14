package com.yakovlev.prod.vocabularymanager.file_explorer;

import java.io.File;
import java.io.IOException;

import com.yakovlev.prod.vocabularymanager.constants.Const;
import com.yakovlev.prod.vocabularymanager.exceptions.ExceptionHelper;
import com.yakovlev.prod.vocabularymanger.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FileExplorerAlertDialogSupport {

	public static AlertDialog.Builder createAlertDialogForEnterFileName(final Context context) {

		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		LinearLayout layout = new LinearLayout(context);
		LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(parms);

		layout.setGravity(Gravity.CLIP_VERTICAL);
		layout.setPadding(2, 2, 2, 2);

		TextView tv = new TextView(context);
		tv.setText(context.getResources().getString(R.string.txt_enter_name_for_new_file));
		tv.setPadding(40, 40, 40, 40);
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(20);

		final EditText et = new EditText(context);
		LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		tv1Params.bottomMargin = 5;
		layout.addView(et, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		alertDialogBuilder.setView(layout);
		alertDialogBuilder.setCustomTitle(tv);
		alertDialogBuilder.setCancelable(false);

		alertDialogBuilder.setNegativeButton(context.getResources().getString(R.string.txt_Cancell_allert),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});

		alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.txt_OK_allert),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		return alertDialogBuilder;
	}

	public static AlertDialog.Builder createAlertDialogForChossingRewriting(final Context cont) {
		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cont);

		LinearLayout layout = new LinearLayout(cont);
		LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(parms);

		layout.setGravity(Gravity.CLIP_VERTICAL);
		layout.setPadding(2, 2, 2, 2);

		TextView tv = new TextView(cont);
		tv.setText(cont.getResources().getString(R.string.txt_exception_if_file_exist));
		tv.setPadding(40, 40, 40, 40);
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(20);

		alertDialogBuilder.setView(layout);
		alertDialogBuilder.setCustomTitle(tv);
		alertDialogBuilder.setCancelable(false);

		return alertDialogBuilder;
	}

	public static void openDialogFromBuilder(AlertDialog.Builder alertDialogBuilder) {
		final AlertDialog alertDialogForChoosing = alertDialogBuilder.create();
		try {
			alertDialogForChoosing.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void checkFileNameForEmpty(String newFileName, Context context) throws ExceptionHelper {
		if (newFileName.equals("")) {
			String message = context.getResources().getString(R.string.txt_exception_if_file_name_is_null);
			throw new ExceptionHelper(message);
		}
	}

	public static boolean isFileNotExistInThisDirectory(String newFileName, String direct) {
		direct += "." + Const.EXTENTION_EXD;
		File myFile = new File(direct);
		if (myFile.exists())
			return false;
		return true;
	}

	public static void deleteFileByPath(String path) {
		File file = new File(path);
		if (file.exists()) {
			String deleteCmd = "rm -r " + path;
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec(deleteCmd);
			} catch (IOException e) {
			}
		}
	}

	public static EditText addEditViewToAlertBuilder(Context cont, AlertDialog.Builder alertDialogBuilder) {
		final EditText edtFileName = new EditText(cont);
		LinearLayout layout = new LinearLayout(cont);
		layout.addView(edtFileName, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		alertDialogBuilder.setView(layout);
		return edtFileName;
	}
}
