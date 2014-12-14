package com.yakovlev.prod.vocabularymanager.ormlite;

import java.sql.SQLException;

import android.database.Cursor;

import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.yakovlev.prod.vocabularymanager.VocabulariesListActivity;

public class VocabularyDbHelp {

	public void addVocabulary(Vocabulary vocabulary, RuntimeExceptionDao<Vocabulary, Integer> simpleDao) {
		simpleDao.create(vocabulary);
	}

	
	


}
