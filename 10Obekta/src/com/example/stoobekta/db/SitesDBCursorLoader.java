package com.example.stoobekta.db;

import android.content.Context;
import android.database.Cursor;

//change to static if mistakes occur
//loading the cursor in different thread
public class SitesDBCursorLoader extends SimpleCursorLoader {

	private SitesDB sitesDB;

	public SitesDBCursorLoader(Context context, SitesDB sitesDB) {
		super(context);
		this.sitesDB = sitesDB;
	}

	@Override
	public Cursor loadInBackground() {
		Cursor loaderCursor = sitesDB.getObekti();
		return loaderCursor;
	}

}