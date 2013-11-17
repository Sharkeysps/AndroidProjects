package com.example.stoobekta.db;

import android.content.Context;
import android.database.Cursor;

//change to static if mistakes occur
//loading the cursor in different thread
public class SitesDBCursorLoader extends SimpleCursorLoader {

	private SitesDB sitesDB;
	private int loaderId;
	private String searchedNumber;

	public SitesDBCursorLoader(Context context, SitesDB sitesDB,
			int loaderId,String searchedNumber) {
		super(context);
		this.sitesDB = sitesDB;
		this.loaderId=loaderId;
		this.searchedNumber=searchedNumber;
	}

	@Override
	public Cursor loadInBackground() {
		if(loaderId==0){
		Cursor loaderCursor = sitesDB.getObekti();
		return loaderCursor;
		}else if(loaderId==1){
			Cursor loaderCursor = sitesDB.getDetailedInfoAboutObekt(searchedNumber);
			return loaderCursor;
		}else{
			Cursor loaderCursor=sitesDB.getSearchResult(searchedNumber);
			return loaderCursor;
		}
		}
	}

