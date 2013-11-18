package com.example.stoobekta;

import com.example.stoobekta.db.SitesDB;
import com.example.stoobekta.db.SitesDBCursorLoader;
import com.example.stoobekta.helpers.GPSLocationListener;
import com.example.stoobekta.models.CoordinatesModel;
import com.example.stoobekta.models.DetailedSiteInfoModel;
import com.google.gson.Gson;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.R.bool;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListObektiActivity<D> extends Activity implements
		OnItemClickListener, LoaderCallbacks<Cursor>, OnClickListener {

	private SimpleCursorAdapter dataAdapter;
	private Context context;
	private CoordinatesModel coordinates;
	private Button getNearSitesButton;
	GPSLocationListener locationListener;
	private LocationManager locationManager;
	private Button getVisitedSitesButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_obekti);
		super.onCreate(savedInstanceState);

		this.context = this;

		// bindings
		FillObektList();

		this.locationListener = new GPSLocationListener();
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,
				1000, 500, locationListener);

		getNearSitesButton = (Button) findViewById(R.id.getNearSites);
		getVisitedSitesButton=(Button)findViewById(R.id.visitedSites);
		getVisitedSitesButton.setOnClickListener(this);
		getNearSitesButton.setOnClickListener(this);

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

	private void showGPSDisabledAlertToUser() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setMessage(
						"За да използвате тази опция трябва да включите GPS")
				.setCancelable(false)
				.setPositiveButton("Добре(ще ви изпрати към настройки)",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent callGPSSettingIntent = new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivity(callGPSSettingIntent);

							}
						});
		alertDialogBuilder.setNegativeButton("Не мерси",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

	private void FillObektList() {
		String[] dataBindFrom = new String[] { SitesDB.KEY_NUMBER,
				SitesDB.KEY_CITY, SitesDB.KEY_NAME, };

		int[] dataBindTo = new int[] { R.id.textID, R.id.textCity,
				R.id.textName, };

		dataAdapter = new SimpleCursorAdapter(this,
				R.layout.sites_info_layout_lv, null, dataBindFrom, dataBindTo,
				0);

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
			// All sites
			return new SitesDBCursorLoader(this, SitesDB.getInstance(this), 0,
					"", null);
		} else if (id == 1) {
			// Specific site search query
			return new SitesDBCursorLoader(this, SitesDB.getInstance(this), 1,
					args.getString("number"), null);
		} else if (id == 2) {
			// Search functionality
			return new SitesDBCursorLoader(this, SitesDB.getInstance(this), 2,
					args.getString("number"), null);
		} else if (id == 3) {
			// Sites near you
			return new SitesDBCursorLoader(this, SitesDB.getInstance(this), 3,
					null, coordinates);
		}else if(id==4){
			//Visited sites
			return new SitesDBCursorLoader(this, SitesDB.getInstance(this), 4,
					null, null);
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor loadedCursor) {
		int loaderId = loader.getId();

		if (loaderId == 0 || loaderId == 2 || loaderId == 3 || loaderId==4) {
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

			selectedSite.setLatitude(loadedCursor.getDouble(loadedCursor
					.getColumnIndex(SitesDB.KEY_LATITUDE)));
			selectedSite.setLongitude(loadedCursor.getDouble(loadedCursor
					.getColumnIndex(SitesDB.KEY_LONGITUDE)));

			double lat = locationListener.Latitude;
			double longitude = locationListener.Longitude;

			PassToDetailedActivity(selectedSite, lat, longitude);
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		dataAdapter.swapCursor(null);

	}

	private void PassToDetailedActivity(DetailedSiteInfoModel model,
			double latitude, double longitude) {
		Intent i = new Intent(this, DetailedObektActivity.class);
		String serializedString = new Gson().toJson(model);
		i.putExtra("lat", latitude);
		i.putExtra("long", longitude);
		i.putExtra("model", serializedString);
		startActivity(i);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.getNearSites:
			boolean isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			if (isGPSEnabled) {
				double lat = locationListener.Latitude;
				double longitude = locationListener.Longitude;
				if (lat != 0) {
					coordinates = new CoordinatesModel(lat, longitude);
					getLoaderManager().restartLoader(3, null, this);
				} else {
					Toast.makeText(this, "Моля включете GPS локацията",
							Toast.LENGTH_LONG).show();
				}
			} else {
				showGPSDisabledAlertToUser();
			}
			break;
		case R.id.visitedSites:
			getLoaderManager().restartLoader(4, null, this);
			break;
		
		}

	}

}
