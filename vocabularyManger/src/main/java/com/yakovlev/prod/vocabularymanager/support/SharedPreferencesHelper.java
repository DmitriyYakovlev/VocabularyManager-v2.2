package com.yakovlev.prod.vocabularymanager.support;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesHelper {

	final public static String GENERATED_DEVICE_ID = "genDevId";
	final public static String KEY_LOGIN_PREFERENCES = "login";
	final public static String EMPTY_VALUE = "";

	public static void saveTextInPreferences(String value, Activity cont, String key) {
		SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(cont);
		SharedPreferences.Editor ed = sPref.edit();
		ed.putString(key, value);
		ed.commit();
	}

	public static String loadText(Context cont, String key) {
		SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(cont);
		String str = sPref.getString(key, "");

		return str;
	}

	public static String getDeviceIdFromPreferences(Context mContext) {
		return SharedPreferencesHelper.loadText(mContext, SharedPreferencesHelper.GENERATED_DEVICE_ID);
	}

}
