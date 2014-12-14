package com.yakovlev.prod.vocabularymanager.ormlite;

import android.database.Cursor;

public class CursorHelper {

	
	public static String getStringByField(Cursor cursor, String fieldName) {
		return cursor.getString(cursor.getColumnIndex(fieldName));
	}
	
	public static Integer getNumberByField(Cursor cursor, String fieldName) {
		return cursor.getInt(cursor.getColumnIndex(fieldName));
	}
	
}
