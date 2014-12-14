package com.y_prod.vocabularymanager.view_pager;

import java.util.List;

import com.yakovlev.prod.vocabularymanager.constants.TestingType;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ContentForViewPagerAdapter extends FragmentPagerAdapter  {

	private Context cont;
	private int contentCount;
	private List<WordTable> wordsList;
	private int testingType;
	private ViewPagerItemCallback callback;

	public ContentForViewPagerAdapter(FragmentManager fm, List<WordTable> wordsList, int testingType, ViewPagerItemCallback callback, Context cont) {
		super(fm);
		this.cont = cont;
		this.contentCount = wordsList.size();
		this.wordsList = wordsList;
		this.testingType = testingType;
		this.callback = callback;
	}

	public void refreshPersents(int position) {
		getItem(position);
	}

	@Override
	public Fragment getItem(int position) {
		Fragment itemFragment = null;

		if (testingType == TestingType.FREE_ANSWER.ordinal()) {
			itemFragment = FreeAnswerViewPagerItemContent.newInstance(cont, wordsList.get(position), position, wordsList.size(), callback, testingType);
		} else if (testingType == TestingType.CHOOSE_ONE_VARIANT.ordinal()) {
			itemFragment = ChoseOneVariantContentViewPagerItem.newInstance(cont, wordsList.get(position), position, wordsList.size(), callback, testingType);
		}

		return itemFragment;
	}

	@Override
	public int getCount() {
		return contentCount;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "";
	}

}