package com.cf360.adapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

public class ProDetailAdapter extends FragmentStatePagerAdapter {
	private ArrayList<Fragment> lists;

	public ProDetailAdapter(FragmentManager fm, ArrayList<Fragment> fgLists) {
		super(fm);
		this.lists = fgLists;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Fragment getItem(int arg0) {
		return lists.get(arg0);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final Object fragment = super.instantiateItem(container, position);
		try {
			final Field saveFragmentStateField = Fragment.class
					.getDeclaredField("mSavedFragmentState");
			saveFragmentStateField.setAccessible(true);
			final Bundle savedFragmentState = (Bundle) saveFragmentStateField
					.get(fragment);
			if (savedFragmentState != null) {
				savedFragmentState.setClassLoader(Fragment.class
						.getClassLoader());
			}
		} catch (Exception e) {
			Log.w("CustomFragmentStatePagerAdapter",
					"Could not get mSavedFragmentState field: " + e);
		}
		return fragment;
	}

}
