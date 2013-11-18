package com.example.stoobekta.helpers;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class GPSLocationListener implements LocationListener {

	public double Longitude;
	public double Latitude;
	@Override
	public void onLocationChanged(Location location) {
		if(location!=null){
			this.Latitude=location.getLatitude();
			this.Longitude=location.getLongitude();
		}
		
	}

	
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
