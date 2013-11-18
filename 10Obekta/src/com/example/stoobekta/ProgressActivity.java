package com.example.stoobekta;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.stoobekta.db.SitesDB;
import com.example.stoobekta.db.SitesDBCursorLoader;
import com.example.stoobekta.helpers.HttpPersister;
import com.example.stoobekta.models.CommentModel;
import com.example.stoobekta.models.CommentsModel;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.R.integer;
import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.view.Menu;
import android.widget.TextView;
import android.widget.ProgressBar;

public class ProgressActivity extends Activity implements
		LoaderCallbacks<Cursor> {

	ProgressBar progressBar;
	TextView visitedSites;
	TextView currentRank;
	String androidId;
	private int VisitedSitesCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress);

		progressBar = (ProgressBar) findViewById(R.id.visitedSitesProgress);
		visitedSites = (TextView) findViewById(R.id.progresText);
		currentRank = (TextView) findViewById(R.id.currentRank);

		getLoaderManager().restartLoader(4, null, this);

	}

	private void FillProgress(int count) {
		this.VisitedSitesCount = count;
		progressBar.setProgress(count);
		visitedSites.setText("Посетени обекти:" + count + "/100");
		SendTouristData();
	}

	private void GetCurrentUserRank() {
		// androidId=getDeviceID(this);
		androidId = "123abc";
		HttpPersister.Get(this, "tourist/?androidId=" + androidId, null,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {

						int count = Integer.valueOf(response)+1;
						if (count == (-1)) {
							SendTouristData();
						}else{
							currentRank.setText("Вашата позиция в класацията е:"+count);
						}

					}

				});
	}

	private void SendTouristData() {
		StringEntity serializedEntity = null;

		JSONObject jdata = new JSONObject();

		try {
			jdata.put("AndroidID", androidId);
			jdata.put("Username", "");
			jdata.put("VisitedSites", VisitedSitesCount);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			serializedEntity = new StringEntity(jdata.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpPersister.Post(this, "tourist", serializedEntity,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						GetCurrentUserRank();

					}
				});

	}

	private String getDeviceID(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId;
		if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
			// Tablet
			deviceId = Secure.getString(this.getContentResolver(),
					Secure.ANDROID_ID);

		} else {
			// Mobile
			deviceId = manager.getDeviceId();

		}
		return deviceId;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.progress, menu);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new SitesDBCursorLoader(this, SitesDB.getInstance(this), 4,
				null, null);

	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		int visitedCount = arg1.getCount();
		FillProgress(visitedCount);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}

}
