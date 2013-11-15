package com.example.stoobekta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SiteComments {

	public static SiteCommentsFragment returnCommentFragment() {
		return new SiteCommentsFragment();
	} 
	
	public static class SiteCommentsFragment extends Fragment {

		 @Override
		    public View onCreateView(LayoutInflater inflater, ViewGroup container,
		            Bundle savedInstanceState) {
		 
		        View rootView = inflater.inflate(R.layout.fragment_site_comments, container, false);
		         
		        return rootView;
		    }

	}
}
