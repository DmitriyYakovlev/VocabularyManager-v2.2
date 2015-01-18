package com.yakovlev.prod.vocabularymanager.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.yakovlev.prod.vocabularymanger.R;

public class SelectExportImportDialog extends Dialog implements View.OnClickListener{

    private ExportImportButtonsCallback exportImportButtonsCallback;
    private Button btnExport, btnImport;

    public SelectExportImportDialog(Context context, ExportImportButtonsCallback exportImportButtonsCallback) {
        super(context);
        this.exportImportButtonsCallback = exportImportButtonsCallback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_export_import_layout);
        findViews();
    }

    private void findViews(){
        btnExport = (Button)findViewById(R.id.btnExportVocabsIntoFile);
        btnImport = (Button)findViewById(R.id.btnImportVocabsFromFile);

        btnExport.setOnClickListener(this);
        btnImport.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnExportVocabsIntoFile:
                exportImportButtonsCallback.exportButtonPressed();
                dismiss();
                break;
            case R.id.btnImportVocabsFromFile:
                exportImportButtonsCallback.importButtonPressed();
                dismiss();
                break;

        }
    }
}
