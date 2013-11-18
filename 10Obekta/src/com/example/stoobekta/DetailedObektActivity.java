package com.example.stoobekta;

import com.example.stoobekta.adapters.TabsPagerAdapter;
import com.example.stoobekta.helpers.GPSCoordinateChecker;
import com.example.stoobekta.models.DetailedSiteInfoModel;
import com.google.gson.Gson;

import android.R.integer;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class DetailedObektActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	
	//Names of tabs
	private static final String DETAILED_INFO_TAB="Инфо";
	private static final String EVENTS_TAB="Събития";
	private static final String COMMENTS_TAB="Коментари";

	
	// Tab titles
	private String[] tabs = { DETAILED_INFO_TAB,  COMMENTS_TAB , EVENTS_TAB};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_obekt);
		
		Context context=getApplicationContext();
		
		String jsonStr = getIntent().getStringExtra("model");
		DetailedSiteInfoModel model=new Gson().fromJson(jsonStr, DetailedSiteInfoModel.class);		
		
		double lat=getIntent().getDoubleExtra("lat", 0);
		double longitude=getIntent().getDoubleExtra("long", 0);
		
		if(lat!=0 || longitude!=0){
			GPSCoordinateChecker.CheckDistance(lat, longitude,
					model.Latitude, model.Longitude,
					model.Number,context);
		}
	
		
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		//Create all tab names in the Action Bar
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

}