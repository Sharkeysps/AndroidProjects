package com.example.stoobekta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

	Button button1;
	Button button2;
	Button optionsButton;
	EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);

		// editText=(EditText)findViewById(R.id.editText1);
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);

		button2 = (Button) findViewById(R.id.rankingButton);
		button2.setOnClickListener(this);

		optionsButton = (Button) findViewById(R.id.optionsButton);
		optionsButton.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Intent sitesActivityIntent = new Intent(MainActivity.this,
					ListObektiActivity.class);
			startActivity(sitesActivityIntent);
			break;
		case R.id.rankingButton:
			Intent progressActivity = new Intent(MainActivity.this,
					ProgressActivity.class);
			startActivity(progressActivity);
			break;
		case R.id.optionsButton:
			Intent settingsActivity = new Intent(MainActivity.this,
					SettingsActivity.class);
			startActivity(settingsActivity);
			break;

		}

	}

}
