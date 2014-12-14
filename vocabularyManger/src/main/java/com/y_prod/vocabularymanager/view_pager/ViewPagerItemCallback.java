package com.y_prod.vocabularymanager.view_pager;

import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface ViewPagerItemCallback {

	public View onCreateViewForViewPager(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, WordTable wordTable, int position, int testingType);
	
}
