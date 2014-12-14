package com.yakovlev.prod.vocabularymanager.adapters;

import java.sql.SQLException;
import java.util.List;

import com.yakovlev.prod.vocabularymanager.dialogs.AlertDialogsHolder;
import com.yakovlev.prod.vocabularymanager.dialogs.DialogButtonsCallback;
import com.yakovlev.prod.vocabularymanager.ormlite.DatabaseHelper;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTableHelper;
import com.yakovlev.prod.vocabularymanger.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class WordsListAdapter extends BaseAdapter implements DialogButtonsCallback {

	Context context;
	List<WordTable> words;
	LayoutInflater lInflater;
	DatabaseHelper dbDatabaseHelper;

	public WordsListAdapter(List<WordTable> words, DatabaseHelper dbHelper, Context context) {
		this.context = context;
		this.words = words;
		this.dbDatabaseHelper = dbHelper;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = lInflater.inflate(R.layout.item_words, parent, false);
		}

		TextView tvKey = (TextView) view.findViewById(R.id.tvKey);
		TextView tvVal = (TextView) view.findViewById(R.id.tvVal);
		Button btnEditWord = (Button) view.findViewById(R.id.btnEditWord);
		Button btnDeleteWord = (Button) view.findViewById(R.id.btnDeleteWord);

		final WordTable word = words.get(position);

		tvKey.setText(word.getwKey());
		tvVal.setText(word.getwValue());

		btnEditWord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialogsHolder.openEditWord(context, WordsListAdapter.this, word);
			}
		});

		btnDeleteWord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				deleteWord(word);
			}
		});

		return view;
	}

	
	@Override
	public void possitiveButtonPress(WordTable wordT) {
		try {
			WordTableHelper.updateWordFromDb(wordT, dbDatabaseHelper);
			notifyDataSetChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void negativeButtonPress(String key, String value) {

	}

	private void deleteWord(WordTable word) {
		try {
			WordTableHelper.deleteWordFromDb(word.getId(), dbDatabaseHelper);
			words.remove(word);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		notifyDataSetChanged();
	}

}
