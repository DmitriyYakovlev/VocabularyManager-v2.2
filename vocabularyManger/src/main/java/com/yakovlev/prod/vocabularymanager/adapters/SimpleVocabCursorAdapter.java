package com.yakovlev.prod.vocabularymanager.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yakovlev.prod.vocabularymanager.ormlite.CursorHelper;
import com.yakovlev.prod.vocabularymanager.support.SharedPreferencesHelper;
import com.yakovlev.prod.vocabularymanger.R;

public class SimpleVocabCursorAdapter extends CursorAdapter{

	private LayoutInflater inflater;
	private TextView tvName, tvDescription, tvDate;
	private int lastVocabId;

	public SimpleVocabCursorAdapter(Context context, Cursor cursor) {
		super(context, cursor);
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, final Context context, Cursor cursor) {
		final String id = CursorHelper.getStringByField(cursor, "_id");
		String name = CursorHelper.getStringByField(cursor, "vName");
		String desc = CursorHelper.getStringByField(cursor, "vDescription");
		String date = CursorHelper.getStringByField(cursor, "date");

        this.lastVocabId = SharedPreferencesHelper.loadNumberFromSharedPreferences(context, SharedPreferencesHelper.KEY_LAST_USED_VOCABULARY_ID);
        LinearLayout llParent = (LinearLayout)view.findViewById(R.id.itemWordLearn);
        int idNum = Integer.parseInt(id);
        if (idNum == lastVocabId)
            llParent.setBackgroundColor(context.getResources().getColor(R.color.col_red_dark));
        else
            llParent.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));

        tvName = (TextView) view.findViewById(R.id.tvVocabName);
		tvDescription = (TextView) view.findViewById(R.id.tvVocabDesc);
		tvDate = (TextView) view.findViewById(R.id.tvDate);
		
		tvName.setText(name);
		tvDescription.setText(desc);
		tvDate.setText(getFirstNSymbols(date, 10));
		
	}

	public static String getFirstNSymbols(String s, int n){
		return s.substring(0, Math.min(s.length(), n));
	}
	
	@Override
	public View newView(Context arg0, Cursor cursor, ViewGroup parent) {
		return inflater.inflate(R.layout.item_simple_vocab_cursor_adapter, parent, false);
	}

}
