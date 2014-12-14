package com.yakovlev.prod.vocabularymanager.adapters;

import java.util.List;

import com.yakovlev.prod.vocabularymanager.ormlite.DatabaseHelper;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;
import com.yakovlev.prod.vocabularymanger.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WrongAnswersListAdapter extends BaseAdapter {

	Context context;
	List<WordTable> words;
	LayoutInflater lInflater;

	public WrongAnswersListAdapter(List<WordTable> words, Context context) {
		this.context = context;
		this.words = words;
		lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return words.size();
	}

	@Override
	public Object getItem(int position) {
		return words.get(position);
	}

	@Override
	public long getItemId(int position) {
		return words.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = lInflater.inflate(R.layout.item_words_for_wrong_answers, parent, false);
		}

		TextView tvKey = (TextView) view.findViewById(R.id.tvKey);
		TextView tvVal = (TextView) view.findViewById(R.id.tvVal);

		final WordTable word = words.get(position);

		tvKey.setText(word.getwKey());
		tvVal.setText(word.getwValue());

		return view;
	}

}
