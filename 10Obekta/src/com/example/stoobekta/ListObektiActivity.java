package com.example.stoobekta;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stoobekta.db.SitesDB;
import com.example.stoobekta.db.SitesDBCursorLoader;
import com.example.stoobekta.helpers.GPSCoordinateChecker;
import com.example.stoobekta.helpers.GPSLocationListener;
import com.example.stoobekta.models.CoordinatesModel;
import com.example.stoobekta.models.DetailedSiteInfoModel;
import com.google.gson.Gson;

public class ListObektiActivity<D> extends Activity implements
		OnItemClickListener, LoaderCallbacks<Cursor>, OnClickListener {

	private SimpleCursorAdapter dataAdapter;
	private Context context;
	private CoordinatesModel coordinates;
	private Button getNearSitesButton;
	GPSLocationListener locationListener;
	private LocationManager locationManager;
	private Button getVisitedSitesButton;
	private boolean guideToSite;

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
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				200, 500, locationListener);

		getNearSitesButton = (Button) findViewById(R.id.getNearSites);
		getVisitedSitesButton = (Button) findViewById(R.id.visitedSites);
		getVisitedSitesButton.setOnClickListener(this);
		getNearSitesButton.setOnClickListener(this);

		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);
		guideToSite = settings.getBoolean("guide", false);

		EditText myFilter = (EditText) findViewById(R.id.myFilter);
		myFilter.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@SuppressWarnings("unchecked")
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

	private void showChoiceForGuideOrInfoDialog(
			final DetailedSiteInfoModel model, final double latitude,
			final double longitude) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setMessage("Къде искате да отидете?")
				.setCancelable(false)
				.setPositiveButton("Информацията за обекта",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								PassToDetailedActivity(model, latitude,
										longitude);

							}
						});
		alertDialogBuilder.setNegativeButton("Насочване към обекта",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						PassToGuideActivity(latitude, longitude);
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
		getMenuInflater().inflate(R.menu.list_obekti, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.home_button:
			Intent homeIntent = new Intent(this, MainActivity.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
			return new SitesDBCursorLoader(this, SitesDB.getInstance(this),
					SitesDBCursorLoader.NATIONAL_SITES_LOADER, "", null);
		} else if (id == 1) {
			// Specific site search query
			return new SitesDBCursorLoader(this, SitesDB.getInstance(this),
					SitesDBCursorLoader.DETAILED_INFO_LOADER,
					args.getString("number"), null);
		} else if (id == 2) {
			// Search functionality
			return new SitesDBCursorLoader(this, SitesDB.getInstance(this),
					SitesDBCursorLoader.SEARCH_SITES_LOADER,
					args.getString("number"), null);
		} else if (id == 3) {
			// Sites near you
			return new SitesDBCursorLoader(this, SitesDB.getInstance(this),
					SitesDBCursorLoader.NEAR_SITES_LOADER, null, coordinates);
		} else if (id == 4) {
			// Visited sites
			return new SitesDBCursorLoader(this, SitesDB.getInstance(this),
					SitesDBCursorLoader.VISITED_SITES_LOADER, null, null);
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor loadedCursor) {
		int loaderId = loader.getId();

		if (loaderId == 0 || loaderId == 2 || loaderId == 3 || loaderId == 4) {
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

			boolean isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			
			if (isGPSEnabled && guideToSite) {
				showChoiceForGuideOrInfoDialog(selectedSite, lat, longitude);
			} else {
				PassToDetailedActivity(selectedSite, lat, longitude);
			}

		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		dataAdapter.swapCursor(null);

	}

	private void PassToGuideActivity(double latitude, double longitude) {
		GPSCoordinateChecker.CurrentSiteLatitude = latitude;
		GPSCoordinateChecker.CurrentSiteLongitude = longitude;

		Intent guideActivityIntent = new Intent(this, GuideActivity.class);
		startActivity(guideActivityIntent);
	}

	private void PassToDetailedActivity(DetailedSiteInfoModel model,
			double latitude, double longitude) {
		Intent detailedSiteActivity = new Intent(this,
				DetailedObektActivity.class);
		String serializedString = new Gson().toJson(model);
		detailedSiteActivity.putExtra("lat", latitude);
		detailedSiteActivity.putExtra("long", longitude);
		detailedSiteActivity.putExtra("model", serializedString);
		startActivity(detailedSiteActivity);
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
