package com.yakovlev.prod.vocabularymanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.y_prod.vocabularymanager.data_holders.TestAnswersHolder;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;

import android.app.Application;

public class VocabularyApp extends Application {

	public static float testPersentValue;
	public static int testTrueAnswersCount;
	public static int testAllAnswersCount;

	public static List<WordTable> wrongAnswersList = new ArrayList<WordTable>();
	public static List<Integer> blockedWordsIdsList = new ArrayList<Integer>();
	public static Map<Integer, TestAnswersHolder> answersMap = new HashMap<Integer, TestAnswersHolder>();
	
	public static void clearAllData() {
		testPersentValue = 0;
		testTrueAnswersCount = 0;
		testAllAnswersCount = 0;
		wrongAnswersList = new ArrayList<WordTable>();
		blockedWordsIdsList = new ArrayList<Integer>();
	}

	public static void clearWrongAnswersData() {
		testPersentValue = 0;
		testTrueAnswersCount = 0;
		testAllAnswersCount = 0;
		wrongAnswersList = new ArrayList<WordTable>();
		answersMap = new HashMap<Integer, TestAnswersHolder>();
	}
	
}
