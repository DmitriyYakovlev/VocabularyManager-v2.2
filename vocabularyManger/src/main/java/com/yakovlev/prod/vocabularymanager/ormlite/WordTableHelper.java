package com.yakovlev.prod.vocabularymanager.ormlite;


import android.content.Context;
import android.database.Cursor;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;


public class WordTableHelper {

	public static void deleteWordFromDb(Integer position, DatabaseHelper dbDatabaseHelper ) throws SQLException{

		RuntimeExceptionDao<WordTable, Integer> words = dbDatabaseHelper.getWordsRuntimeDataDao();
		WordTable list = getWordById(position, dbDatabaseHelper);
		words.delete(list);
	}
	
	public static void updateWordFromDb(WordTable wordTable, DatabaseHelper dbDatabaseHelper ) throws SQLException{
		RuntimeExceptionDao<WordTable, Integer> words = dbDatabaseHelper.getWordsRuntimeDataDao();
		words.update(wordTable);
	}

    public static List<WordTable> getHardWords(DatabaseHelper dbDatabaseHelper) throws SQLException{
        RuntimeExceptionDao<WordTable, Integer> words = dbDatabaseHelper.getWordsRuntimeDataDao();
        QueryBuilder<WordTable, Integer> qb = words.queryBuilder();
        Where where = qb.where();
        where.eq("wordStatus", WordStatusEnum.getHard());
        PreparedQuery<WordTable> preparedQuery = qb.prepare();
        List<WordTable> hardWords = words.query(preparedQuery);
        return hardWords;
    }

    public static WordTable getWordById(Integer position, DatabaseHelper dbDatabaseHelper ) throws SQLException{
        RuntimeExceptionDao<WordTable, Integer> words = dbDatabaseHelper.getWordsRuntimeDataDao();
        QueryBuilder<WordTable, Integer> qb = words.queryBuilder();
        Where where = qb.where();
        where.eq("_id", position);
        PreparedQuery<WordTable> preparedQuery = qb.prepare();
        WordTable word = words.queryForFirst(preparedQuery);
        return word;
    }

    public static void changeWordStatus(Integer wordId, DatabaseHelper dbDatabaseHelper) throws SQLException {
        WordTable word = getWordById(wordId, dbDatabaseHelper);

        if (word.getWordStatus() == WordStatusEnum.getNormal()){
            word.setWordStatus(WordStatusEnum.getHard());
        }
        else {
            word.setWordStatus(WordStatusEnum.getNormal());
        }

        updateWordFromDb(word, dbDatabaseHelper);
    }

    public static Cursor getHardWordsCursorFromORM(Context context)  {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        RuntimeExceptionDao<WordTable, Integer> simpleDao = dbHelper.getWordsRuntimeDataDao();
        QueryBuilder<WordTable, Integer> queryBuilder = simpleDao.queryBuilder();

        Where where = queryBuilder.where();
        try {
            where.eq("wordStatus", WordStatusEnum.getHard());
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

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
