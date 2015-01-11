package com.yakovlev.prod.vocabularymanager.export_import_functionality;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.codevog.android.mobileorder.R;
import com.codevog.android.mobileorder.local_network_support.Server;
import com.codevog.android.mobileorder.support_code.ProgressBarHellper;
import com.codevog.android.mobileorder.support_code.ToastHelper;
import com.codevog.android.mobileorder.ui.file_explorer.FileWorkSupport;

public class ExportFullBackUpInJsonAT extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progressBar;
    private Context context;
    private String pathToFile;


    public ExportFullBackUpInJsonAT(Context context, String pathToFile) {
        this.context = context;
        this.pathToFile = pathToFile;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = ProgressBarHellper.getProgressBarDialogAndStartWithoutCancelListner(progressBar, context);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            String json = Server.createJSONForFullExport(context);
            FileWorkSupport.saveStringInFile(json, pathToFile);
            ToastHelper.doInUIThread(context.getString(R.string.txt_toast_full_back_compl) , context);
        } catch (Exception ex) {
            ToastHelper.doInUIThread(context.getString(R.string.txt_toast_full_back_error), context);
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void integer) {
        super.onPostExecute(integer);
        ProgressBarHellper.stopProgressBar(progressBar);
    }

}
