package com.example.stoobekta;

import com.example.stoobekta.db.SitesDB;
import com.example.stoobekta.db.SitesDBCursorLoader;
import com.example.stoobekta.models.DetailedSiteInfoModel;
import com.google.gson.Gson;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListObektiActivity<D> extends Activity implements
		OnItemClickListener, LoaderCallbacks<Cursor> {

	private SimpleCursorAdapter dataAdapter;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_obekti);
		super.onCreate(savedInstanceState);

		this.context = this;
		
		//bindings
		FillObektList();

		EditText myFilter = (EditText) findViewById(R.id.myFilter);
		myFilter.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				String searchedNumber = String.valueOf(s);
				Bundle bundle = new Bundle();

				bundle.putString("number", searchedNumber);

				getLoaderManager().restartLoader(2, bundle,
						(LoaderCallbacks<D>) context);

			}
		});

	}
	
	private void FillObektList(){
		String[] dataBindFrom = new String[] { SitesDB.KEY_NUMBER,
				SitesDB.KEY_CITY, SitesDB.KEY_NAME, };

		int[] dataBindTo = new int[] { R.id.textID, R.id.textCity,
				R.id.textName, };
		
			dataAdapter = new SimpleCursorAdapter(this,
					R.layout.sites_info_layout_lv, null, dataBindFrom,
					dataBindTo, 0);

			ListView listView = (ListView) findViewById(R.id.listView1);
			listView.setAdapter(dataAdapter);
			listView.setOnItemClickListener(this);
			
			getLoaderManager().initLoader(0, null, this);
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

		Bundle bundle = new Bundle();
		bundle.putString("number", obektNumber);
		getLoaderManager().restartLoader(1, bundle, this);

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if (id == 0) {
			return new SitesDBCursorLoader(this, SitesDB.getInstance(this), 0,
					"");
		} else if (id == 1) {
			return new SitesDBCursorLoader(this, SitesDB.getInstance(this), 1,
					args.getString("number"));
			// return new SitesDBDetailedCursorLoader(this,
			// SitesDB.getInstance(this),args.getString("number"));
		} else {
			return new SitesDBCursorLoader(this, SitesDB.getInstance(this), 2,
					args.getString("number"));
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor loadedCursor) {
		int loaderId = loader.getId();

		if (loaderId == 0 || loaderId == 2) {
			dataAdapter.swapCursor(loadedCursor);
		} else {
			DetailedSiteInfoModel selectedSite = new DetailedSiteInfoModel();
			selectedSite.setCity(loadedCursor.getString(loadedCursor
					.getColumnIndex(SitesDB.KEY_CITY)));

			selectedSite.setName(loadedCursor.getString(loadedCursor
					.getColumnIndex(SitesDB.KEY_NAME)));

			selectedSite.setNumber(loadedCursor.getInt(loadedCursor
					.getColumnIndex(SitesDB.KEY_NUMBER)));

			selectedSite.setDescription(loadedCursor.getString(loadedCursor
					.getColumnIndex(SitesDB.KEY_DESCRIPTION)));

			selectedSite.setTicketAdult(loadedCursor.getString(loadedCursor
					.getColumnIndex(SitesDB.KEY_TICKETADULT)));

			selectedSite.setTicketChild(loadedCursor.getString(loadedCursor
					.getColumnIndex(SitesDB.KEY_TICKETCHILD)));

			selectedSite.setWorkingHours(loadedCursor.getString(loadedCursor
					.getColumnIndex(SitesDB.KEY_WORKINGHOURS)));

			PassToDetailedActivity(selectedSite);
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		dataAdapter.swapCursor(null);

	}

	private void PassToDetailedActivity(DetailedSiteInfoModel model) {
		Intent i = new Intent(this, DetailedObektActivity.class);
		String serializedString = new Gson().toJson(model);
		i.putExtra("model", serializedString);
		startActivity(i);
	}

}
