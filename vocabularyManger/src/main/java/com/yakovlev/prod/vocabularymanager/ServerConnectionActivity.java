package com.yakovlev.prod.vocabularymanager;


import java.util.List;

import com.yakovlev.prod.vocabularymanager.asynk_tasks.ConectionCallback;
import com.yakovlev.prod.vocabularymanager.asynk_tasks.GetVocabularyListAT;
import com.yakovlev.prod.vocabularymanager.dialogs.AlertDialogsHolder;
import com.yakovlev.prod.vocabularymanager.dialogs.OnItemClickCallback;
import com.yakovlev.prod.vocabularymanager.ormlite.Vocabulary;
import com.yakovlev.prod.vocabularymanager.support.ProgressBarHellper;
import com.yakovlev.prod.vocabularymanger.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ServerConnectionActivity extends Activity implements ConectionCallback, OnItemClickCallback{
	
	Button btnGetVocabs;
	ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_connection);
		
		TextView tvHeader = (TextView) findViewById(R.id.tvActName);
		tvHeader.setText("Connection with server");
		
		btnGetVocabs = (Button)findViewById(R.id.btnGetVocabulariesList);
		
		btnGetVocabs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GetVocabularyListAT getVocabularyListAT = new GetVocabularyListAT(ServerConnectionActivity.this, ServerConnectionActivity.this);
				getVocabularyListAT.execute();
			}
		});
	}

	@Override
	public void beforeConect() {
		progressDialog = ProgressBarHellper.startProgressBar(progressDialog, this, canselListner);
	}

	OnCancelListener canselListner = new OnCancelListener() {
		@Override
		public void onCancel(DialogInterface dialog) {
			ProgressBarHellper.stopProgressBar(progressDialog);
		}
	};
	
	@Override
	public void afterConect(List<Vocabulary> vocabList) {
		ProgressBarHellper.stopProgressBar(progressDialog);
		
		if (vocabList == null || vocabList.size() == 0)
			return;
		
		AlertDialogsHolder.openSelectVocabularyDialog(vocabList, ServerConnectionActivity.this, ServerConnectionActivity.this);
	}



	@Override
	public void onVocabularyItemClick() {
		// TODO Auto-generated method stub
		
	}
	
	
}
