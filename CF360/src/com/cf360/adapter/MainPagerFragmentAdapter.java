package com.cf360.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerFragmentAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> fgLists;

	public MainPagerFragmentAdapter(FragmentManager fm,
			ArrayList<Fragment> fgLists) {
		super(fm);
	}
	
	public void setData(ArrayList<Fragment> fgLists){
		this.fgLists = fgLists;
	}
	
	@Override
	public Fragment getItem(int position) {
		return fgLists.get(position);
	}

	@Override
	public int getCount() {
		return fgLists.size();
	}

	
}
