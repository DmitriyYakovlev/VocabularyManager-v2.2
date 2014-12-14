package com.yakovlev.prod.vocabularymanager.file_explorer;

import java.io.File;

import com.yakovlev.prod.vocabularymanager.constants.Const;
import com.yakovlev.prod.vocabularymanager.support.IntentHelper;
import com.yakovlev.prod.vocabularymanager.support.ToastHelper;
import com.yakovlev.prod.vocabularymanger.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FileExplorerActivity extends Activity implements IFolderItemListener, OnClickListener {

	private FolderLayout localFolders;
	private static String pathToDirectory;
	int isForExportOpened, isForImportOpened;
	String pathToFolder = null;
	String newFileName = null;
	private Button btnExportInCurrentDirectory;
	public static String PATH_TO_DIRECTORY_AND_FILE_NAME = "path_export";
	public static boolean isForImportBuckUpStarted = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_explorer_layout);

		btnExportInCurrentDirectory = (Button) findViewById(R.id.btnSaveInCurrentDirectory);
		btnExportInCurrentDirectory.setOnClickListener(this);

		File sdPath = Environment.getExternalStorageDirectory();
		pathToDirectory = sdPath.getAbsolutePath() + "/";

		localFolders = (FolderLayout) findViewById(R.id.localfolders);
		localFolders.setIFolderItemListener(this);
		localFolders.setDir(pathToDirectory);

		Intent intent = getIntent();
		isForExportOpened = intent.getIntExtra(Const.GETTING_FILE_FROM_FILE_EX, -1);
		if (isForExportOpened != -1) {
			btnExportInCurrentDirectory.setVisibility(View.VISIBLE);
		}

		isForImportOpened = intent.getIntExtra(Const.GETTING_FILE_FOR_IMPORT, -1);
		if (isForImportOpened != -1) {
			isForImportBuckUpStarted = true;
		}
	}

	@Override
	// Your stuff here for Cannot open Folder
	public void OnCannotFileRead(File file) {
		new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher)
				.setTitle("[" + file.getName() + "] " + getResources().getString(R.string.txt_cannot_read_folder))
				.setPositiveButton(getResources().getString(R.string.txt_OK_allert), new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
	}

	@Override
	public void OnFileClicked(File file) {
		if (isForExportOpened == -1) {
			IntentHelper.sendIntentFromExplorer(file.getAbsolutePath(), PATH_TO_DIRECTORY_AND_FILE_NAME, this);
			finish();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnSaveInCurrentDirectory:
			openADSaveFile(this);
			break;
		default:
			break;
		}
	}

	private void openADSaveFile(final Context cont) {

		final AlertDialog.Builder alertDialogBuilder = FileExplorerAlertDialogSupport.createAlertDialogForEnterFileName(this);
		final EditText edtFileName = FileExplorerAlertDialogSupport.addEditViewToAlertBuilder(cont, alertDialogBuilder);

		final AlertDialog alertDialog = alertDialogBuilder.create();

		alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				positiveButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						onButtonPositiveClick(edtFileName, cont, alertDialog);
					}
				});
			}
		});

		try {
			alertDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onButtonPositiveClick(EditText edtFileName, Context cont, AlertDialog alertDialog) {
		newFileName = edtFileName.getText().toString();

		try {
			FileExplorerAlertDialogSupport.checkFileNameForEmpty(newFileName, FileExplorerActivity.this);

			String directory = localFolders.getPathFromFolderLayout() + "/" + newFileName;
			if (FileExplorerAlertDialogSupport.isFileNotExistInThisDirectory(newFileName, directory)) {
				IntentHelper.sendIntentFromExplorer(directory, PATH_TO_DIRECTORY_AND_FILE_NAME, cont);
				finish();
			} else {
				setAlertDialogForChossingRewriting(cont);
				alertDialog.dismiss();
			}
		} catch (Exception e) {
			ToastHelper.useLongToast(cont, e.getMessage());
		}
	}

	private void setAlertDialogForChossingRewriting(final Context cont) {
		final AlertDialog.Builder alertDialogBuilder = FileExplorerAlertDialogSupport.createAlertDialogForChossingRewriting(cont);

		alertDialogBuilder.setNegativeButton(getResources().getString(R.string.txt_Cancell_allert), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
				openADSaveFile(FileExplorerActivity.this);
			}
		});

		alertDialogBuilder.setPositiveButton(getResources().getString(R.string.txt_OK_allert), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String path = localFolders.getPathFromFolderLayout() + "/" + newFileName;
				IntentHelper.sendIntentFromExplorer(path, PATH_TO_DIRECTORY_AND_FILE_NAME, cont);
				FileExplorerAlertDialogSupport.deleteFileByPath(path);
				finish();
			}
		});

		FileExplorerAlertDialogSupport.openDialogFromBuilder(alertDialogBuilder);
	}

}