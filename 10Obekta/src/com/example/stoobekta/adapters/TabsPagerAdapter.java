package com.example.stoobekta.adapters;

import com.example.stoobekta.SiteComments;
import com.example.stoobekta.SiteEvents;
import com.example.stoobekta.SiteInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm){
		super(fm);
	}
	
	public Fragment getItem(int index){
		switch (index) {
		case 0:
			return SiteInfo.getSiteInfoFragment();
			
		case 1:
			return SiteEvents.getSiteEventsFragment();
			
		case 2:
			return SiteComments.returnCommentFragment();
		}
		return null;
	}

	@Override
	public int getCount(){
		return 3;
	}

}
