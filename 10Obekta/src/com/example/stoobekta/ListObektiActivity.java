package com.example.stoobekta;

import com.example.stoobekta.db.SitesDB;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListObektiActivity extends Activity implements OnItemClickListener {

	private Cursor nationalSites;
	private SitesDB db;
	private SimpleCursorAdapter dataAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_obekti);
		super.onCreate(savedInstanceState);

		db = SitesDB.getInstance(this);
		nationalSites = db.getObekti();
	//	String[] names = nationalSites.getColumnNames();

		// The desired columns to be bound
		String[] columns = new String[] {
				SitesDB.KEY_NUMBER, SitesDB.KEY_CITY, SitesDB.KEY_NAME, };

		// the XML defined views which the data will be bound to
		int[] to = new int[] { R.id.textID, R.id.textCity, R.id.textName, };
		try {
			// create the adapter using the cursor pointing to the desired data
			// as well as the layout information
			dataAdapter = new SimpleCursorAdapter(this,
					R.layout.sites_info_layout_lv, nationalSites, columns, to,
					0);

			ListView listView = (ListView) findViewById(R.id.listView1);
			// Assign adapter to ListView
			listView.setAdapter(dataAdapter);

			listView.setOnItemClickListener(this);

		} catch (Exception ex) {
			Log.d("d", "Greshka");
		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public String getAndroidId(Context context) {
		String androidId = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
		return androidId;

	}

	@Override
	public void onItemClick(AdapterView<?> listView, View view, int position,
			long id) {
		// Get the cursor, positioned to the corresponding row in the
		// result set
		Cursor cursor = (Cursor) listView.getItemAtPosition(position);

		// Get the state's capital from this row in the database.
		String countryCode = cursor.getString(cursor
				.getColumnIndexOrThrow("number"));
		Toast.makeText(getApplicationContext(), String.valueOf(countryCode), Toast.LENGTH_SHORT)
				.show();

	}
}
