package com.yakovlev.prod.vocabularymanager.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yakovlev.prod.vocabularymanager.ormlite.Vocabulary;
import com.yakovlev.prod.vocabularymanger.R;

public class VocabularyDialogAdapter extends BaseAdapter {

	Context context;
	List<Vocabulary> vocabs;
	LayoutInflater lInflater;

	public VocabularyDialogAdapter(List<Vocabulary> vocabs, Context context) {
		this.context = context;
		this.vocabs = vocabs;
		lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return vocabs.size();
	}

	@Override
	public Object getItem(int position) {
		return vocabs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return vocabs.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = lInflater.inflate(R.layout.item_vocab_from_server_adapter, parent, false);
		}

		TextView tvKey = (TextView) view.findViewById(R.id.tvVocabName);
		TextView tvVal = (TextView) view.findViewById(R.id.tvVocabDesc);

		final Vocabulary vocabulary = vocabs.get(position);

		tvKey.setText(vocabulary.getvName());
		tvVal.setText(vocabulary.getvDescription());

		return view;
	}

}
