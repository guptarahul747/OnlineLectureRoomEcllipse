package com.onlinelectureroom.adapter;

import com.example.onlinelectureroom.R;
import com.onlinelectureroom.ui.EventListFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	private Context context;
	private String[] tabs;

	public ViewPagerAdapter(Context context, FragmentManager fm) {
		super(fm);

		this.context = context;
		tabs = context.getResources().getStringArray(R.array.tabs);
	}

	@Override
	public Fragment getItem(int pos) {

		switch (pos) {

		case 0:

			return EventListFragment.newInstance(tabs[pos]);
		case 1:

			return EventListFragment.newInstance(tabs[pos]);
		default:
			break;
		}

		return null;
	}

	@Override
	public int getCount() {

		return tabs.length;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		
		return tabs[position];
	}

}
