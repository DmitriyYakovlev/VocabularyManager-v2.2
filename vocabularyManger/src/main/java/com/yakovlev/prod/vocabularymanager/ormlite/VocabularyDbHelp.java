package com.yakovlev.prod.vocabularymanager.ormlite;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.yakovlev.prod.vocabularymanager.VocabulariesListActivity;

public class VocabularyDbHelp {

	public static void addVocabulary(Vocabulary vocabulary, RuntimeExceptionDao<Vocabulary, Integer> simpleDao) {
		simpleDao.create(vocabulary);
	}

    public List<Vocabulary> getAllVocabs( RuntimeExceptionDao<Vocabulary, Integer> simpleDao) {
        List<Vocabulary> allVocabs = simpleDao.queryForAll();
        return allVocabs;
    }

    public static Vocabulary getVocabularyById(Integer vocabId, Context context) throws SQLException{
        DatabaseHelper dbDatabaseHelper = new DatabaseHelper(context);
        RuntimeExceptionDao<Vocabulary, Integer> vocabularies = dbDatabaseHelper.getVocabularyRuntimeDataDao();
        QueryBuilder<Vocabulary, Integer> qb = vocabularies.queryBuilder();
        Where where = qb.where();
        where.eq("_id", vocabId);
        PreparedQuery<Vocabulary> preparedQuery = qb.prepare();
        Vocabulary v = vocabularies.queryForFirst(preparedQuery);
        return v;
    }

}
