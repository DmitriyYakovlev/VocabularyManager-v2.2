package com.yakovlev.prod.vocabularymanager.adapters;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yakovlev.prod.vocabularymanager.ormlite.CursorHelper;
import com.yakovlev.prod.vocabularymanger.R;

public class LearnWordsCursorAdapter extends CursorAdapter{

	private LayoutInflater inflater;
	private TextView tvKey, tvValue;
	public Set<Integer> checkedItemsList = new HashSet<Integer>();

	
	public LearnWordsCursorAdapter(Context context, Cursor cursor) {
		super(context, cursor);
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, final Context context, Cursor cursor) {
		final int id = CursorHelper.getNumberByField(cursor, "_id");
		String key = CursorHelper.getStringByField(cursor, "wKey");
		String value = CursorHelper.getStringByField(cursor, "wValue");
		
		tvKey = (TextView) view.findViewById(R.id.tvKey);
		tvValue = (TextView) view.findViewById(R.id.tvValue);
		
		tvKey.setText(key);
		tvValue.setText(value);

		final View hideView =  (View) view.findViewById(R.id.hideView);

		if (checkedItemsList.contains(id))
			hideView.setVisibility(View.INVISIBLE);
		else
			hideView.setVisibility(View.VISIBLE);
		
		RelativeLayout rightRl = (RelativeLayout) view.findViewById(R.id.rlRightHolder);
		rightRl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (hideView.getVisibility() == View.VISIBLE){
					hideView.setVisibility(View.INVISIBLE);
					checkedItemsList.add(id);
				}
				else{
					hideView.setVisibility(View.VISIBLE);
					checkedItemsList.remove(id);
				}
			}
		});
	}

	public static String getFirstNSymbols(String s, int n){
		return s.substring(0, Math.min(s.length(), n));
	}
	
	@Override
	public View newView(Context arg0, Cursor cursor, ViewGroup parent) {
		return inflater.inflate(R.layout.item_learn_words_adapter, parent, false);
	}

}
