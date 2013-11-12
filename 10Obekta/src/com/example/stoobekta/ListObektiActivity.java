package com.example.stoobekta;

import com.example.stoobekta.db.SimpleCursorLoader;
import com.example.stoobekta.db.SitesDB;

import android.os.Bundle;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
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

	// LoaderManager loadermanager;
	// CursorLoader cursorLoader;

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
		String countryCode = cursor.getString(cursor
				.getColumnIndexOrThrow("number"));
		Toast.makeText(getApplicationContext(), String.valueOf(countryCode),
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new SitesDBCursorLoader(this, SitesDB.getInstance(this));
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor loadedCursor) {
		dataAdapter.swapCursor(loadedCursor);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		dataAdapter.swapCursor(null);

	}

	// Class needs to be static to work,but i can`t have static in outer class
	public static final class SitesDBCursorLoader extends SimpleCursorLoader {

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

}
