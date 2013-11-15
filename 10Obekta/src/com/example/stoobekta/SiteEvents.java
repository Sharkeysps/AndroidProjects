package com.example.stoobekta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SiteEvents {

	public static SiteEventsFragment getSiteEventsFragment(){
		return new SiteEventsFragment();
	}
	
	public static class SiteEventsFragment extends Fragment{

		 public View onCreateView(LayoutInflater inflater, ViewGroup container,
		            Bundle savedInstanceState) {
		 
		        View rootView = inflater.inflate(R.layout.fragment_site_events, container, false);
		         
		        return rootView;
		    }

	}
}
