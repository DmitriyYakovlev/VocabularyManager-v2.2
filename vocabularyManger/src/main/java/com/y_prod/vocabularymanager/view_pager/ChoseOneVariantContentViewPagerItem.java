package com.y_prod.vocabularymanager.view_pager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;

public class ChoseOneVariantContentViewPagerItem extends Fragment {
	private int position;
	private	WordTable wordTable;
	public static List<Integer> blockedWordsIdsList = new ArrayList<Integer>();
	private ViewPagerItemCallback callback;
	int testingType;
	
	public static ChoseOneVariantContentViewPagerItem newInstance(Context cont, WordTable wordTable,  int position, int size, ViewPagerItemCallback callback, int testingType) {

		ChoseOneVariantContentViewPagerItem fragment = new ChoseOneVariantContentViewPagerItem();
		fragment.position = position;
		fragment.wordTable = wordTable;
		fragment.callback = callback;
		fragment.testingType = testingType;
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
		blockedWordsIdsList.clear();
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
