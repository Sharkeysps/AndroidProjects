package com.example.stoobekta.db;

import android.content.Context;
import android.database.Cursor;

public class SitesDBDetailedCursorLoader extends SimpleCursorLoader {

	private SitesDB sitesDB;
	private String searchedNumber;
	
	public SitesDBDetailedCursorLoader(Context context, SitesDB sitesDB,String number) {
		super(context);
		this.sitesDB = sitesDB;
		this.searchedNumber=number;
	}

	@Override
	public Cursor loadInBackground() {
		Cursor loaderCursor = sitesDB.getDetailedInfoAboutObekt(searchedNumber);
		return loaderCursor;
	}

}