package com.yakovlev.prod.vocabularymanager.support;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.LinearLayout.LayoutParams;

public class ProgressBarHelper {

	public static ProgressDialog getProgressDialog(Context context, OnCancelListener canselListner) {
		ProgressDialog progressBar = new ProgressDialog(context);
		progressBar.setCancelable(true);
		progressBar.setOnCancelListener(canselListner);
		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		progressBar.show();

		LinearLayout ll = new LinearLayout(context);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		ProgressBar progB = new ProgressBar(context);
		progB.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		ll.addView(progB);

		progressBar.setContentView(ll);

		return progressBar;

	}

	public static ProgressDialog startProgressBar(ProgressDialog progress, Context context, OnCancelListener canselListner) {
		progress = ProgressBarHelper.getProgressDialog(context, canselListner);
		return progress;
	}

	public static void stopProgressBar(ProgressDialog progress) {
		progress.dismiss();
	}

	
	
}
