package com.example.stoobekta.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.stoobekta.SiteComments;
import com.example.stoobekta.SiteInfo;


public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm){
		super(fm);
	}
	
	public Fragment getItem(int index){
		switch (index) {
		case 0:
			return SiteInfo.getSiteInfoFragment();
			
		case 1:
			return SiteComments.returnCommentFragment();
		}
		return null;
	}

	@Override
	public int getCount(){
		return 2;
	}

}
