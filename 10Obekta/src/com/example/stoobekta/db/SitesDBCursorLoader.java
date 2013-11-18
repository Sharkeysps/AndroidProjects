package com.example.stoobekta.db;

import com.example.stoobekta.models.CoordinatesModel;


import android.content.Context;
import android.database.Cursor;

//change to static if mistakes occur
//loading the cursor in different thread
public class SitesDBCursorLoader extends SimpleCursorLoader {

	private SitesDB sitesDB;
	private int loaderId;
	private String searchedNumber;
	private CoordinatesModel coordinates;
	
	public SitesDBCursorLoader(Context context, SitesDB sitesDB,
			int loaderId,String searchedNumber,CoordinatesModel coordinates) {
		super(context);
		this.sitesDB = sitesDB;
		this.loaderId=loaderId;
		this.searchedNumber=searchedNumber;
		this.coordinates=coordinates;
	}

	@Override
	public Cursor loadInBackground() {
		if(loaderId==0){
		Cursor loaderCursor = sitesDB.getObekti();
		return loaderCursor;
		}else if(loaderId==1){
			Cursor loaderCursor = sitesDB.getDetailedInfoAboutObekt(searchedNumber);
			return loaderCursor;
		}else if(loaderId==2){
			Cursor loaderCursor=sitesDB.getSearchResult(searchedNumber);
			return loaderCursor;
		}else if(loaderId==3){
			Cursor loaderCursor=sitesDB.getNearSites(coordinates);
			return loaderCursor;
		}
		return null;
		}
	
	}

