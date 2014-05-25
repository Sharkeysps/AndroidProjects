package com.example.stoobekta.helpers;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.example.stoobekta.db.SitesDB;

public class GPSCoordinateChecker {

	public static void CheckDistance(double phoneLatitude,double phoneLongitude,
			double siteLatitude,double siteLongitude,
			int siteNumber,Context context) {
		Location originLocation = new Location("gps");
		Location destinationLocation = new Location("gps");
		originLocation.setLatitude(phoneLatitude);
		originLocation.setLongitude(phoneLongitude);
		destinationLocation.setLatitude(siteLatitude);
		destinationLocation.setLongitude(siteLongitude);
		float distance = originLocation.distanceTo(destinationLocation);
		
		String compare=String.valueOf(Float.compare(distance,(float) 100.0));
		
		if(compare.equals("-1")){
			try{
			SitesDB.getInstance(context).updateVisitedSite(String.valueOf(siteNumber));
			}catch(Exception ex){
				Log.d("d", ex.getMessage());
			}
			Toast.makeText(context,"Вие успешно посетихте този обект", Toast.LENGTH_LONG).show();
		}
		
	}
}
