package com.yakovlev.prod.vocabularymanager.ormlite;


import android.content.Context;
import android.database.Cursor;

import java.sql.SQLException;

import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.yakovlev.prod.vocabularymanager.support.HardWordMode;


public class WordTableHelper {

    public static WordTable createWordWithHardStatusInDb(WordTable word, Context context) throws SQLException{
        word.setWordStatus(WordStatusEnum.getNormal());
        word.setwTranscription("");
        DatabaseHelper dbDatabaseHelper = new DatabaseHelper(context);
        Dao<WordTable, Integer> wordsDao = dbDatabaseHelper.getWordDao();
        wordsDao.create(word);
        return word;
    }

	public static void deleteWordFromDb(Integer wordId, DatabaseHelper dbDatabaseHelper ) throws SQLException{

		RuntimeExceptionDao<WordTable, Integer> words = dbDatabaseHelper.getWordsRuntimeDataDao();
		WordTable list = getWordById(wordId, dbDatabaseHelper);
		words.delete(list);
	}
	
	public static void updateWordFromDb(WordTable wordTable, DatabaseHelper dbDatabaseHelper ) throws SQLException{
		RuntimeExceptionDao<WordTable, Integer> words = dbDatabaseHelper.getWordsRuntimeDataDao();
		words.update(wordTable);
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

    public static int changeWordStatus(Integer wordId, DatabaseHelper dbDatabaseHelper) throws SQLException {
        WordTable word = getWordById(wordId, dbDatabaseHelper);

        if (word.getWordStatus() == WordStatusEnum.getNormal()){
            word.setWordStatus(WordStatusEnum.getHardFirstRank());
        }
        else {
            word.setWordStatus(WordStatusEnum.getNormal());
        }

        updateWordFromDb(word, dbDatabaseHelper);
        return word.getWordStatus();
    }

    public static int setWordStatus(Integer wordId, int wordStatus,  DatabaseHelper dbDatabaseHelper) throws SQLException {
        WordTable word = getWordById(wordId, dbDatabaseHelper);
        word.setWordStatus(wordStatus);
        updateWordFromDb(word, dbDatabaseHelper);
        return word.getWordStatus();
    }

    public static long getCountOfWordsInDatabase(Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        RuntimeExceptionDao<WordTable, Integer> simpleDao = dbHelper.getWordsRuntimeDataDao();
        return simpleDao.countOf();
    }

    public static Cursor getHardWordsCursorFromORM(Context context, HardWordMode mode, String text)  {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        RuntimeExceptionDao<WordTable, Integer> simpleDao = dbHelper.getWordsRuntimeDataDao();
        QueryBuilder<WordTable, Integer> queryBuilder = simpleDao.queryBuilder();

        Where where = queryBuilder.where();
        try {

            if (text != null && !text.equals("")) {
                if (mode == HardWordMode.ALL_HARD_WORDS) {
                    where.and(where.like("wValue", "%" + text + "%").or().like("wKey", "%" + text + "%"),
                            where.eq("wordStatus", WordStatusEnum.getHardFirstRank()).or().eq("wordStatus", WordStatusEnum.getHardSecondRank()));
                } else if (mode == HardWordMode.ONLY_HARDEST_WORDS) {
                    where.and(where.like("wValue", "%" + text + "%").or().like("wKey", "%" + text + "%"),
                            where.eq("wordStatus", WordStatusEnum.getHardFirstRank()));
                } else if (mode == HardWordMode.ONLY_SECOND_RANG) {
                    where.and(where.like("wValue", "%" + text + "%").or().like("wKey", "%" + text + "%"),
                            where.eq("wordStatus", WordStatusEnum.getHardSecondRank()));
                }
            }else{
                if (mode == HardWordMode.ALL_HARD_WORDS) {
                    where.eq("wordStatus", WordStatusEnum.getHardFirstRank());
                    where.or();
                    where.eq("wordStatus", WordStatusEnum.getHardSecondRank());
                } else if (mode == HardWordMode.ONLY_HARDEST_WORDS) {
                    where.eq("wordStatus", WordStatusEnum.getHardFirstRank());
                } else if (mode == HardWordMode.ONLY_SECOND_RANG)
                    where.eq("wordStatus", WordStatusEnum.getHardSecondRank());
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        queryBuilder.orderBy("wKey", true);

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
