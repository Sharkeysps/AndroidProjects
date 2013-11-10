package com.example.stoobekta;

import java.util.ArrayList;
import java.util.UUID;

import com.example.stoobekta.db.SitesDB;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.widget.ListAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Cursor nationalSites;
    private SitesDB db;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        super.onCreate(savedInstanceState);

        db = SitesDB.getInstance(this);
        nationalSites = db.getObekti(); // you would not typically call this on the main thread
        try{
        	
        	 int count= nationalSites.getCount();
             Toast.makeText(this, String.valueOf(count), Toast.LENGTH_LONG).show();
        }catch(Exception ex){
        	  Toast.makeText(this,"garmi" , Toast.LENGTH_LONG).show();
        }
       
        
        
//        ListAdapter adapter = new SimpleCursorAdapter(this, 
//                        android.R.layout.simple_list_item_1, 
//                        employees, 
//                        new String[] {"city"}, 
//                        new int[] {android.R.id.text1});
//
//        getListView().setAdapter(adapter);
        
//        String id=getAndroidId(getApplicationContext());
//        Toast.makeText(this, id, Toast.LENGTH_LONG).show();
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
