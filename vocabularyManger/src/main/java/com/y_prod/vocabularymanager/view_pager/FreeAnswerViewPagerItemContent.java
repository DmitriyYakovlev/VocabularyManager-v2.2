package com.y_prod.vocabularymanager.view_pager;

import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FreeAnswerViewPagerItemContent extends Fragment {
	Context context;
	int position;
	int size;
	WordTable wordTable;
	ViewPagerItemCallback callback;
	int testingType;
	
	public static FreeAnswerViewPagerItemContent newInstance(Context cont, WordTable wordTable,  int position, int size,  ViewPagerItemCallback callback, int testingType) {
		FreeAnswerViewPagerItemContent fragment = new FreeAnswerViewPagerItemContent();
		fragment.context = cont;
		fragment.position = position;
		fragment.wordTable = wordTable;
		fragment.size = size;
		fragment.testingType = testingType;
		fragment.callback = callback;
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return callback.onCreateViewForViewPager(inflater, container, savedInstanceState, wordTable, position, testingType);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}
