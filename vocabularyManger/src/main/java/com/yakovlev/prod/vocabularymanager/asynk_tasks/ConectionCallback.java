package com.yakovlev.prod.vocabularymanager.asynk_tasks;

import java.util.List;

import com.yakovlev.prod.vocabularymanager.ormlite.Vocabulary;

public interface ConectionCallback {

	public void beforeConect();
	
	public void afterConect(List<Vocabulary> vocabList);

	
}
