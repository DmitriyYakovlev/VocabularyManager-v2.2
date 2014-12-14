package com.yakovlev.prod.vocabularymanager.asynk_tasks;

import java.util.List;

import com.yakovlev.prod.vocabularymanager.connection.HttpHelper;
import com.yakovlev.prod.vocabularymanager.connection.NetworkException;
import com.yakovlev.prod.vocabularymanager.connection.NetworkResponse;
import com.yakovlev.prod.vocabularymanager.ormlite.Vocabulary;
import com.yakovlev.prod.vocabularymanager.support.ToastHelper;

import android.content.Context;
import android.os.AsyncTask;

public class GetVocabularyListAT extends AsyncTask<Void, Void, Void>{

	
	private Context context;
	private ConectionCallback conectionCallback;
	private List<Vocabulary> vocabList;
	String json;
	
	public GetVocabularyListAT(ConectionCallback conectionCallback, Context context) {
		this.conectionCallback = conectionCallback;
		this.context = context;
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
			netResp = httpHelper.doGetRequest("get_vocab_list/");
			json = netResp.getResponse();
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
