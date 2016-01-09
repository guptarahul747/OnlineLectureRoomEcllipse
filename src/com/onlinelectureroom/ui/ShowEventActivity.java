package com.onlinelectureroom.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.onlinelectureroom.R;
import com.onlinelectureroom.adapter.ViewPagerAdapter;
import com.onlinelectureroom.widgets.PagerSlidingTabStrip;

public class ShowEventActivity extends FragmentActivity {

	private PagerSlidingTabStrip tabStrip;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.show_event_activity);
		
		initViews();
		
	}

	private void initViews() {

		tabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabStrip);
		tabStrip.setTextColor(Color.BLACK);


		
		viewPager  = (ViewPager)findViewById(R.id.pager);
		
		ViewPagerAdapter fragmentPageAdapter = new ViewPagerAdapter(this,getSupportFragmentManager());
		fragmentPageAdapter.notifyDataSetChanged();
		viewPager.setAdapter(fragmentPageAdapter);

		tabStrip.setViewPager(viewPager);
	}
}
