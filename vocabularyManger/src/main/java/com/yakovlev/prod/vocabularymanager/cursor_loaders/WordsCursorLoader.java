package com.yakovlev.prod.vocabularymanager.cursor_loaders;

import java.sql.SQLException;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.yakovlev.prod.vocabularymanager.ormlite.DatabaseHelper;
import com.yakovlev.prod.vocabularymanager.ormlite.Vocabulary;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;

public class WordsCursorLoader extends CursorLoader {

	private Context context;
	private static int vocabularyId;
	
	public WordsCursorLoader(int vocabularyId,Context context) {
		super(context);
		this.context = context;
		this.vocabularyId = vocabularyId;
	}

	@Override
	public Cursor loadInBackground() {
		Cursor cursor = getCursorFromORM(context);
		return cursor;
	}
	
	public static Cursor getCursorFromORM(Context context)  {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		RuntimeExceptionDao<WordTable, Integer> simpleDao = dbHelper.getWordsRuntimeDataDao();
		QueryBuilder<WordTable, Integer> queryBuilder = simpleDao.queryBuilder();
		
		Where where = queryBuilder.where();
		try {
			where.eq("vocabularyId", vocabularyId);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
//		PreparedQuery<WordTable> preparedQuery = queryBuilder.prepare();
//		SimpleData list = simpleDao.queryForFirst(preparedQuery);

		CloseableIterator<WordTable> iterator = null;
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
