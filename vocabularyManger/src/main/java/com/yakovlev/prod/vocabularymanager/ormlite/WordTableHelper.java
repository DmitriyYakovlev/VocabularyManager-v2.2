package com.yakovlev.prod.vocabularymanager.ormlite;


import java.sql.SQLException;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;


public class WordTableHelper {

	public static void deleteWordFromDb(Integer position, DatabaseHelper dbDatabaseHelper ) throws SQLException{
		
		RuntimeExceptionDao<WordTable, Integer> words = dbDatabaseHelper.getWordsRuntimeDataDao();
		QueryBuilder<WordTable, Integer> qb = words.queryBuilder();
		Where where = qb.where();
		where.eq("_id", position);
		PreparedQuery<WordTable> preparedQuery = qb.prepare();
		WordTable list = words.queryForFirst(preparedQuery);
		words.delete(list);
	}
	
	public static void updateWordFromDb(WordTable wordTable, DatabaseHelper dbDatabaseHelper ) throws SQLException{
		RuntimeExceptionDao<WordTable, Integer> words = dbDatabaseHelper.getWordsRuntimeDataDao();
		words.update(wordTable);
	}
	
}
