package com.example.stoobekta.adapters;

import com.example.stoobekta.DetailedObektActivity;
import com.example.stoobekta.R;
import com.example.stoobekta.models.DetailedSiteInfoModel;
import com.google.gson.Gson;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm){
		super(fm);
	}
	
	public Fragment getItem(int index){
		switch (index) {
		case 0:
			return new SiteInfoFragment();
			
		case 1:
			return new SiteEventsFragment();
			
		case 2:
			return new SiteCommentsFragment();
			
		case 3:
			return new SiteWikiFragment();
		}
		return null;
	}

	@Override
	public int getCount(){
		return 4;
	}

	public static class SiteEventsFragment extends Fragment{

		 public View onCreateView(LayoutInflater inflater, ViewGroup container,
		            Bundle savedInstanceState) {
		 
		        View rootView = inflater.inflate(R.layout.fragment_site_events, container, false);
		         
		        return rootView;
		    }

	}
	
	public static class SiteCommentsFragment extends Fragment {

		 @Override
		    public View onCreateView(LayoutInflater inflater, ViewGroup container,
		            Bundle savedInstanceState) {
		 
		        View rootView = inflater.inflate(R.layout.fragment_site_comments, container, false);
		         
		        return rootView;
		    }

	}
	
	
	public static class SiteInfoFragment extends Fragment {

		 public View onCreateView(LayoutInflater inflater, ViewGroup container,
		            Bundle savedInstanceState) {
		 
		        View rootView = inflater.inflate(R.layout.fragment_site_info, container, false);
		         
		        String jsonStr = getActivity().getIntent().getStringExtra("model");
				DetailedSiteInfoModel model=new Gson().fromJson(jsonStr, DetailedSiteInfoModel.class);		
					
		        return rootView;
		    }

	}
	
	public static class SiteWikiFragment extends Fragment{

		 @Override
		    public View onCreateView(LayoutInflater inflater, ViewGroup container,
		            Bundle savedInstanceState) {
		 
		        View rootView = inflater.inflate(R.layout.fragment_site_wiki, container, false);
		         
		        return rootView;
		    }

	}

	

}
