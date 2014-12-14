package com.yakovlev.prod.vocabularymanager.support;

import java.util.Iterator;
import java.util.Set;

import com.yakovlev.prod.vocabularymanger.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class ToastHelper {

	public static void useLongToast(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);

//		View view = toast.getView();
//		view.setBackgroundResource(R.color.col_blue_my);

		toast.show();
	}

	public static void useShortToast(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);

//		View view = toast.getView();
//		view.setBackgroundResource(R.color.col_blue_my);

		toast.show();
	}

	public static void useShortToastGood(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);

		View view = toast.getView();
		view.setBackgroundResource(R.color.col_green);

		toast.show();
	}
	
	public static void useShortToastBad(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);

		View view = toast.getView();
		view.setBackgroundResource(R.color.col_red);

		toast.show();
	}
	
	public static void doInUIThread(final String message, final Context context) {
		Activity act = (Activity) context;
		act.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ToastHelper.useLongToast(context, message);
			}
		});
	}

	public static void doInUIThreadShort(final String message, final Context context) {
		Activity act = (Activity) context;
		act.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ToastHelper.useShortToast(context, message);
			}
		});
	}

	public static void useLongToastForIntegerSet(Set<Integer> ourSet, Context context) {

		String pringStr = "Set : ";

		Iterator itr = ourSet.iterator();
		while (itr.hasNext()) {
			Object element = itr.next();
			pringStr += element.toString() + ", ";
		}

		useLongToast(context, pringStr);
	}

}
