package com.yakovlev.prod.vocabularymanager.asynk_tasks;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.yakovlev.prod.vocabularymanager.connection.HttpHelper;
import com.yakovlev.prod.vocabularymanager.connection.NetworkException;
import com.yakovlev.prod.vocabularymanager.connection.NetworkResponse;
import com.yakovlev.prod.vocabularymanager.ormlite.Vocabulary;
import com.yakovlev.prod.vocabularymanager.support.ToastHelper;

public class GetWordsByVocabularyIdAT extends AsyncTask<Void, Void, Void>{

	
	private Context context;
	private ConectionCallback conectionCallback;
	private List<Vocabulary> vocabList;
	private Integer vocabId;
	String jsone;
	
	public GetWordsByVocabularyIdAT(Integer vocabId, ConectionCallback conectionCallback, Context context) {
		this.conectionCallback = conectionCallback;
		this.context = context;
		this.vocabId = vocabId;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		conectionCallback.beforeConect();
	}
	
	private void doResponceGetDevicesForCurentUser(Context context) {
		HttpHelper httpHelper = new HttpHelper(context);
		NetworkResponse netResp = null;
		
		try {
			netResp = httpHelper.doGetRequest("get_vocab_by_id?id=" + Integer.toString(vocabId));
			jsone = netResp.getResponse();
		} catch (NetworkException e) {
			e.printStackTrace();
			ToastHelper.doInUIThread(e.getMessage(), context);
		}
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		doResponceGetDevicesForCurentUser(context);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		conectionCallback.afterConect(vocabList);
	}

}