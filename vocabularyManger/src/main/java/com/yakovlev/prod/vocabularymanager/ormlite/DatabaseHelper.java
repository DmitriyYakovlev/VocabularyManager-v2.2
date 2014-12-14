package com.yakovlev.prod.vocabularymanager.ormlite;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yakovlev.prod.vocabularymanger.R;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "vocabManager.db";
	private static final int DATABASE_VERSION = 1;

	private Dao<Vocabulary, Integer> vocabularyDao = null;
	private RuntimeExceptionDao<Vocabulary, Integer> vocabularyRuntimeDao = null;

	private Dao<WordTable, Integer> wordsDao = null;
	private RuntimeExceptionDao<WordTable, Integer> wordsRuntimeDao = null;

	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Vocabulary.class);
			TableUtils.createTable(connectionSource, WordTable.class);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, Vocabulary.class, true);
			TableUtils.dropTable(connectionSource, WordTable.class, true);

			onCreate(db, connectionSource);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/* ================================================================================= */
	public Dao<Vocabulary, Integer> getVocabularyDao() throws SQLException {
		if (vocabularyDao == null)
			vocabularyDao = getDao(Vocabulary.class);
		return vocabularyDao;
	}

	public RuntimeExceptionDao<Vocabulary, Integer> getVocabularyRuntimeDataDao() {
		if (vocabularyRuntimeDao == null) {
			vocabularyRuntimeDao = getRuntimeExceptionDao(Vocabulary.class);
		}
		return vocabularyRuntimeDao;
	}
	
	/* ================================================================================= */

	public Dao<WordTable, Integer> getWordDao() throws SQLException {
		if (wordsDao == null)
			wordsDao = getDao(WordTable.class);
		return wordsDao;
	}

	public RuntimeExceptionDao<WordTable, Integer> getWordsRuntimeDataDao() {
		if (wordsRuntimeDao == null) {
			wordsRuntimeDao = getRuntimeExceptionDao(WordTable.class);
		}
		return wordsRuntimeDao;
	}
	/* ================================================================================= */

	@Override
	public void close() {
		super.close();
		vocabularyDao = null;
		vocabularyRuntimeDao = null;
		wordsDao = null;
		wordsRuntimeDao = null;
	}
}
