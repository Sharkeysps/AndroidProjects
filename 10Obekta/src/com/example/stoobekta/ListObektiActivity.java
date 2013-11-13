package com.example.stoobekta;

import com.example.stoobekta.db.SitesDB;
import com.example.stoobekta.db.SitesDBCursorLoader;
import com.example.stoobekta.db.SitesDBDetailedCursorLoader;
import com.example.stoobekta.models.DetailedSiteInfoModel;
import com.google.gson.Gson;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListObektiActivity extends Activity implements
		OnItemClickListener, LoaderCallbacks<Cursor> {

	private SimpleCursorAdapter dataAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_obekti);
		super.onCreate(savedInstanceState);
		
		// The columns to be bound
		String[] dataBindFrom = new String[] { SitesDB.KEY_NUMBER,
				SitesDB.KEY_CITY, SitesDB.KEY_NAME, };

		// the XML defined views which the data will be bound to
		int[] dataBindTo = new int[] { R.id.textID, R.id.textCity,
				R.id.textName, };
		try {
			dataAdapter = new SimpleCursorAdapter(this,
					R.layout.sites_info_layout_lv, null, dataBindFrom,
					dataBindTo, 0);

			ListView listView = (ListView) findViewById(R.id.listView1);
			// Assign adapter to ListView
			listView.setAdapter(dataAdapter);

			listView.setOnItemClickListener(this);
			// Loader in another thread
			getLoaderManager().initLoader(0, null, this);

		} catch (Exception ex) {
			Log.d("ex", ex.getMessage());
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// // Inflate the menu; this adds items to the action bar if it is
		// present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> listView, View view, int position,
			long id) {
		Cursor cursor = (Cursor) listView.getItemAtPosition(position);
		String obektNumber = cursor.getString(cursor
				.getColumnIndexOrThrow("number"));
		
		Bundle bundle=new Bundle();
		bundle.putString("number",obektNumber);
		getLoaderManager().initLoader(1, bundle, this);

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if(id==0){
			return new SitesDBCursorLoader(this, SitesDB.getInstance(this));
		}
		else{
			return new SitesDBDetailedCursorLoader(this,
					SitesDB.getInstance(this),args.getString("number"));
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor loadedCursor) {
		int loaderId=loader.getId();
		
		if(loaderId==0){
			dataAdapter.swapCursor(loadedCursor);
		}else{
			DetailedSiteInfoModel selectedSite=new DetailedSiteInfoModel();
			selectedSite.setCity(loadedCursor
					.getString(loadedCursor.getColumnIndex(SitesDB.KEY_CITY)));
			
			selectedSite.setName(loadedCursor
					.getString(loadedCursor.getColumnIndex(SitesDB.KEY_NAME)));
			
			selectedSite.setNumber(loadedCursor
					.getInt(loadedCursor.getColumnIndex(SitesDB.KEY_NUMBER)));
			
			selectedSite.setDescription(loadedCursor
					.getString(loadedCursor.getColumnIndex(SitesDB.KEY_DESCRIPTION)));
			
			selectedSite.setTicketAdult(loadedCursor
					.getString(loadedCursor.getColumnIndex(SitesDB.KEY_TICKETADULT)));
			
			selectedSite.setTicketChild(loadedCursor
					.getString(loadedCursor.getColumnIndex(SitesDB.KEY_TICKETCHILD)));
			
			selectedSite.setWorkingHours(loadedCursor
					.getString(loadedCursor.getColumnIndex(SitesDB.KEY_WORKINGHOURS)));
			
			PassToDetailedActivity(selectedSite);
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		dataAdapter.swapCursor(null);

	}
	
	private void PassToDetailedActivity(DetailedSiteInfoModel model){
		Intent i = new Intent(this,DetailedObektActivity.class);
		String serializedString=new Gson().toJson(model);
		i.putExtra("model", serializedString);
		startActivity(i);
	}


}
