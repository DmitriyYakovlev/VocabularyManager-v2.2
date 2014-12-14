package com.yakovlev.prod.vocabularymanager.support;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class InitViewsHelper {

	public static void setOnClickListenersForTextViewArray(View[] allButtonsInActivity, Context activity) {
		for (int i = 0; i < allButtonsInActivity.length; i++)
			allButtonsInActivity[i].setOnClickListener((OnClickListener) activity);
	}

	public static void enableViewsArray(View[] allButtonsInActivity, Context activity) {
		setEnabledForViewsArray(allButtonsInActivity, activity, true);
		setAlphaForViewsArray(allButtonsInActivity, activity, 1);
	}

	public static void disableViewsArray(View[] allButtonsInActivity, Context activity) {
		setEnabledForViewsArray(allButtonsInActivity, activity, false);
		setAlphaForViewsArray(allButtonsInActivity, activity, 0.7f);
	}

	private static void setEnabledForViewsArray(View[] allButtonsInActivity, Context activity, Boolean value) {
		for (int i = 0; i < allButtonsInActivity.length; i++)
			allButtonsInActivity[i].setEnabled(value);
	}

	private static void setAlphaForViewsArray(View[] allButtonsInActivity, Context activity, float value) {
		for (int i = 0; i < allButtonsInActivity.length; i++)
			allButtonsInActivity[i].setAlpha(value);
	}

	public static void setTextInTv(TextView tv, String text) {
		tv.setText(text);
	}

	public static void insertNumberInTextView(TextView tv, int Data) {
		tv.setText(Integer.toString(Data));
	}

	public static void insertStringInTextView(TextView tv, String Data) {
		tv.setText((Data));
	}

	public static void insertTextWithFormatingInTextView(TextView tv, String fromResources, double stat) {
		DecimalFormat dformat = new DecimalFormat("#.##");
		tv.setText(fromResources + " : " + dformat.format(stat) + " %");
	}

	public static void insertPercentInTextView(TextView tv, double stat) {
		DecimalFormat dformat = new DecimalFormat("#.##");
		tv.setText(dformat.format(stat) + " %");
	}

	public static void disableButtonsArray(Button[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i].setEnabled(false);
		}
	}

	public static List<String> getButtonNamesArray(Button[] array) {
		List<String> answersForButtons = new ArrayList<String>();

		for (int i = 0; i < array.length; i++) 
			answersForButtons.add(array[i].getText().toString());
		
		return answersForButtons;
	}
	
	public static void setButtonNames(Button[] array, List<String> textForButtons) {
		for (int i = 0; i < array.length; i++) 
			array[i].setText(textForButtons.get(i));
	}
	
	public static String trimLeft(String s) {
	    return s.replaceAll("^\\s+", "");
	}
	 
	public static String trimRight(String s) {
	    return s.replaceAll("\\s+$", "");
	}
	
	public static String trimLeftRight(String str){
		String leftTrimedStr = trimLeft(str);
		leftTrimedStr = trimRight(leftTrimedStr);
		
		return leftTrimedStr;
	}
	
}
