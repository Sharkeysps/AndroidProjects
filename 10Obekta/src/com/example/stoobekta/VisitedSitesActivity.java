package com.example.stoobekta;

import com.example.stoobekta.db.SitesDB;
import com.example.stoobekta.models.CoordinatesModel;


import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.inputmethod.CorrectionInfo;

public class VisitedSitesActivity extends Activity implements LoaderCallbacks<Cursor> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visited_sites);
		Cursor testCursor;
		
		CoordinatesModel gpsCoordinatesSearchModel=
				new CoordinatesModel(42.42, 23.20);
		testCursor=SitesDB.getInstance(this).getNearSites(gpsCoordinatesSearchModel);
			int count=testCursor.getCount();
		
		int t=5;
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.visited_sites, menu);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}

}
