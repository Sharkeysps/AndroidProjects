package com.example.stoobekta.db;

import com.example.stoobekta.models.CoordinatesModel;


import android.content.Context;
import android.database.Cursor;

//change to static if mistakes occur
//loading the cursor in different thread
public class SitesDBCursorLoader extends SimpleCursorLoader {

	private SitesDB sitesDB;
	private String loaderId;
	private String searchParameter;
	private CoordinatesModel coordinates;
	public static final String NATIONAL_SITES_LOADER="nationalSites";
	public static final String DETAILED_INFO_LOADER="detailInfo";
	public static final String SEARCH_SITES_LOADER="searchSites";
	public static final String VISITED_SITES_LOADER="visitedSites";
	public static final String NEAR_SITES_LOADER="nearSites";
	
	
	public SitesDBCursorLoader(Context context, SitesDB sitesDB,
			String loader,String searchedNumber,CoordinatesModel coordinates) {
		super(context);
		this.sitesDB = sitesDB;
		this.loaderId=loader;
		this.searchParameter=searchedNumber;
		this.coordinates=coordinates;
	}

	@Override
	public Cursor loadInBackground() {
		if(loaderId.equals(NATIONAL_SITES_LOADER)){
		Cursor loaderCursor = sitesDB.getNationalSites();
		return loaderCursor;
		}else if(loaderId.equals(DETAILED_INFO_LOADER)){
			Cursor loaderCursor = sitesDB.getDetailedInfoAboutObekt(searchParameter);
			return loaderCursor;
		}else if(loaderId.equals(SEARCH_SITES_LOADER)){
			Cursor loaderCursor=sitesDB.getSearchResult(searchParameter);
			return loaderCursor;
		}else if(loaderId.equals(NEAR_SITES_LOADER)){
			Cursor loaderCursor=sitesDB.getNearSites(coordinates);
			return loaderCursor;
		}else if(loaderId.equals(VISITED_SITES_LOADER)){
			Cursor loaderCursor=sitesDB.getVisitedSites();
			return loaderCursor;
		}
		return null;
		}
	}

