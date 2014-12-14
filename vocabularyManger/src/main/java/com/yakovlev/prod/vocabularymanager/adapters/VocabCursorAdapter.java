package com.yakovlev.prod.vocabularymanager.adapters;


import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.yakovlev.prod.vocabularymanager.ormlite.CursorHelper;
import com.yakovlev.prod.vocabularymanager.ormlite.DatabaseHelper;
import com.yakovlev.prod.vocabularymanager.ormlite.Vocabulary;
import com.yakovlev.prod.vocabularymanger.R;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;


public class VocabCursorAdapter extends CursorAdapter{

	private LayoutInflater inflater;
	private TextView tvName, tvDescription, tvDate;
	private Button deleteVocab;
	private Cursor cursor;
	private LoaderCallbacks<Cursor> callback;
	private FragmentActivity fragment;
	
	public VocabCursorAdapter(Context context, Cursor cursor, FragmentActivity fragment, LoaderCallbacks<Cursor> callback) {
		super(context, cursor);
		this.inflater = LayoutInflater.from(context);
		this.cursor = cursor;
		this.callback = callback;
		this.fragment = fragment;
	}

	@Override
	public void bindView(View view, final Context context, Cursor cursor) {
		final String id = CursorHelper.getStringByField(cursor, "_id");
		String name = CursorHelper.getStringByField(cursor, "vName");
		String desc = CursorHelper.getStringByField(cursor, "vDescription");
		String date = CursorHelper.getStringByField(cursor, "date");
		
		tvName = (TextView) view.findViewById(R.id.tvVocabName);
		tvDescription = (TextView) view.findViewById(R.id.tvVocabDesc);
		tvDate = (TextView) view.findViewById(R.id.tvDate);
		deleteVocab = (Button)view.findViewById(R.id.btnDeleteVocab);
		
		tvName.setText(name);
		tvDescription.setText(desc);
		tvDate.setText(getFirstNSymbols(date, 10));
		
		deleteVocab.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				deleteVocabulary(id, context);
			}
		});
		
	}

	private void deleteVocabulary(String id ,Context context){
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		try {
			Dao<Vocabulary, Integer> vocabDao = dbHelper.getVocabularyDao();
			DeleteBuilder<Vocabulary, Integer> deleteBuilder = vocabDao.deleteBuilder();
			deleteBuilder.where().eq("_id", id);
			deleteBuilder.delete();
			fragment.getSupportLoaderManager().restartLoader(0, null, callback);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String getFirstNSymbols(String s, int n){
		return s.substring(0, Math.min(s.length(), n));
	}
	
	@Override
	public View newView(Context arg0, Cursor cursor, ViewGroup parent) {
		return inflater.inflate(R.layout.item_vocab_cursor_adapter, parent, false);
	}

	public void deleteItem(int position) {
		swapCursor(cursor);
	}
	
}
