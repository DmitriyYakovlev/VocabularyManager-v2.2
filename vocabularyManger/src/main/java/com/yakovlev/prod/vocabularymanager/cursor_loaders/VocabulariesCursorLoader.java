package com.yakovlev.prod.vocabularymanager.cursor_loaders;

import java.sql.SQLException;

import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.yakovlev.prod.vocabularymanager.ormlite.DatabaseHelper;
import com.yakovlev.prod.vocabularymanager.ormlite.Vocabulary;

import android.content.Context;
import android.support.v4.content.CursorLoader;
import android.database.Cursor;

public class VocabulariesCursorLoader extends CursorLoader {

	private Context context;
	
	public VocabulariesCursorLoader(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public Cursor loadInBackground() {
		Cursor cursor = getCursorFromORM(context);
		return cursor;
	}
	
	public static Cursor getCursorFromORM(Context context) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		RuntimeExceptionDao<Vocabulary, Integer> simpleDao = dbHelper.getVocabularyRuntimeDataDao();
		QueryBuilder<Vocabulary, Integer> queryBuilder = simpleDao.queryBuilder();

		CloseableIterator<Vocabulary> iterator = null;
		Cursor cursor = null;
		try {
			iterator = simpleDao.iterator(queryBuilder.prepare());
			AndroidDatabaseResults results = (AndroidDatabaseResults) iterator.getRawResults();
			cursor = results.getRawCursor();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cursor;
	}

}
