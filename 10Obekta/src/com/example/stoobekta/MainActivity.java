package com.example.stoobekta;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	Button button1;
	Button button2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);

		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
		
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
			Intent intent = new Intent(MainActivity.this,
					ListObektiActivity.class);
			startActivity(intent);
			break;
		case R.id.button2:
			try{
			Intent intent1 = new Intent(MainActivity.this,
					ProgressActivity.class);
			startActivity(intent1);
			}catch(Exception ex){
				Log.d("d", ex.getMessage());
			}
			break;
		}
		
	}


}
