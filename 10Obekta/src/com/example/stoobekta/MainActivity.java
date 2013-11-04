package com.example.stoobekta;

import java.util.UUID;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String id=getAndroidId(getApplicationContext());
        Toast.makeText(this, id, Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public static String getAndroidId(Context context)
    {
        String androidId =Secure.getString(context.getContentResolver(),
        		Secure.ANDROID_ID);
        return androidId;
 
    }
    
}
