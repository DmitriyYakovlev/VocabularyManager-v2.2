package com.yakovlev.prod.vocabularymanager.support;

import java.text.DecimalFormat;

import android.widget.TextView;

import com.yakovlev.prod.vocabularymanager.VocabularyApp;

public class PersentageCalculateHelper {
	
	
	public static float countPersent(int all, int correctly){
		float one = 100f / all;
		return one * correctly;
	}
	
	public static void setPersentageInTv(TextView tvPersent){
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		String str = df.format(VocabularyApp.testPersentValue);
		tvPersent.setText(str + " % ");
	}

}
