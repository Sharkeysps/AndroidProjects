package com.example.stoobekta;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stoobekta.db.SitesDB;
import com.example.stoobekta.db.SitesDBCursorLoader;
import com.example.stoobekta.helpers.HttpPersister;
import com.example.stoobekta.models.ProgressModel;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class ProgressActivity extends Activity implements
		LoaderCallbacks<Cursor> {

	ProgressBar progressBar;
	TextView visitedSites;
	TextView currentRank;
	TextView messageToUser;
	String androidId;
	private int VisitedSitesCount;
	private String userName;
	private boolean refreshedRanking = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress);

		progressBar = (ProgressBar) findViewById(R.id.visitedSitesProgress);
		visitedSites = (TextView) findViewById(R.id.progresText);
		currentRank = (TextView) findViewById(R.id.currentRank);
		messageToUser=(TextView)findViewById(R.id.messageToUser);
		messageToUser.setText("");
		currentRank.setText("");
		getLoaderManager().restartLoader(4, null, this);

	}

	private void FillProgress(int count) {
		this.VisitedSitesCount = count;
		progressBar.setProgress(count);
		visitedSites.setText("Посетени обекти:" + count + "/100");
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);
		userName=settings.getString("userName", "");
		boolean receiveRankingData = settings.getBoolean("receiveRankingData",
				true);
		if (receiveRankingData) {
			GetCurrentUserRank();
		} else {
			Toast.makeText(this, "Класирането е спряно от настройки", Toast.LENGTH_LONG).show();
		}
	}

	private void GetCurrentUserRank() {
		// androidId=getDeviceID(this);
		androidId = "123abc";
		HttpPersister.Get(this, "Tourist/GetProgress/?androidId=" + androidId, null,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						
						Gson gson = new Gson();
						ProgressModel receivedCommentsModel = gson
								.fromJson(response, ProgressModel.class);
						
						int count = receivedCommentsModel.ProgressNumber+1;
						if (refreshedRanking == false) {
							SendTouristData();
						}
						currentRank.setText("Вашата позиция в класацията е:"
								+ count);
						if(receivedCommentsModel.Message!=null || receivedCommentsModel.Message.trim().length()>0){
							messageToUser.setText("Имате съобщение от администраторите:"+receivedCommentsModel.Message);
						}
					}

				});
	}
	

	private void SendTouristData() {
		StringEntity serializedEntity = null;

		JSONObject jdata = new JSONObject();

		try {
			jdata.put("AndroidID", androidId);
			jdata.put("Username", userName);
			jdata.put("VisitedSites", VisitedSitesCount);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			serializedEntity = new StringEntity(jdata.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HttpPersister.Post(this, "tourist", serializedEntity,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						refreshedRanking = true;
						GetCurrentUserRank();

					}
				});

	}

	@SuppressWarnings("unused")
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
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new SitesDBCursorLoader(this, SitesDB.getInstance(this), SitesDBCursorLoader.VISITED_SITES_LOADER,
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
