package com.yakovlev.prod.vocabularymanager;

import com.yakovlev.prod.vocabularymanager.constants.Const;
import com.yakovlev.prod.vocabularymanager.dialogs.ExportImportButtonsCallback;
import com.yakovlev.prod.vocabularymanager.dialogs.SelectExportImportDialog;
import com.yakovlev.prod.vocabularymanager.export_import_functionality.ExportFullBackUpInJsonAT;
import com.yakovlev.prod.vocabularymanager.export_import_functionality.ImportContentFromFileAT;
import com.yakovlev.prod.vocabularymanager.file_explorer.FileExplorerActivity;
import com.yakovlev.prod.vocabularymanager.file_explorer.FileWorkHelper;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;
import com.yakovlev.prod.vocabularymanager.support.IntentHelper;
import com.yakovlev.prod.vocabularymanager.support.ToastHelper;
import com.yakovlev.prod.vocabularymanger.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

public class MenuActivity extends Activity implements BaseActivityStructure, ExportImportButtonsCallback {

	private TextView tvLearn, tvTest,tvVocabulary, tvActHeader, tvExportImport;

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
        tvExportImport = (TextView) findViewById(R.id.tvExportImport);
	}

	@Override
	public void setOnClickListeners() {
		tvTest.setOnClickListener(this);
		tvLearn.setOnClickListener(this);
		tvVocabulary.setOnClickListener(this);
		tvActHeader.setOnClickListener(this);
		tvExportImport.setOnClickListener(this);
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
        case R.id.tvExportImport:
            processExportImportButtonsPressing();
            break;
		default:
			break;
		}
	}

    private void processExportImportButtonsPressing(){
        SelectExportImportDialog dialog = new SelectExportImportDialog(this, this);
        dialog.show();
    }

    @Override
    public void exportButtonPressed() {
        IntentHelper.openFileExplorerForExport(this);
    }

    @Override
    public void importButtonPressed() {
        IntentHelper.openFileExplorerForChoiceFileForImporting(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            String pathFromFileExp = data.getStringExtra(FileExplorerActivity.PATH_TO_DIRECTORY_AND_FILE_NAME);

            if (requestCode == Const.REQUEST_CODE_FOR_IMPORT) {
                if (FileWorkHelper.isExtentionFalse(pathFromFileExp, Const.EXPORT_IMPORT_EXTENTION)){
                    ToastHelper.useLongToast(this, "Invalid extension name");
                    return;
                }

                ImportContentFromFileAT importContentFromFileAT = new ImportContentFromFileAT(this, pathFromFileExp);
                importContentFromFileAT.execute();

            }else if (requestCode == Const.REQUEST_CODE_FOR_EXPORT){

                pathFromFileExp += "." + Const.EXPORT_IMPORT_EXTENTION;

                ExportFullBackUpInJsonAT exportFullBackUpInJsonAT = new ExportFullBackUpInJsonAT(this, pathFromFileExp);
                exportFullBackUpInJsonAT.execute();

            }
        }
    }

}