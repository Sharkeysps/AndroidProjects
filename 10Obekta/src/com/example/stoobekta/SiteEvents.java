package com.example.stoobekta;

import android.app.Activity;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class SiteEvents {

	public static SiteEventsFragment getSiteEventsFragment() {
		return new SiteEventsFragment();
	}

	public static class SiteEventsFragment extends Fragment implements
			LocationListener, SensorEventListener {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mSensorManager = (SensorManager) this.getActivity()
					.getSystemService(Activity.SENSOR_SERVICE);
		}

		private LocationManager locationManager;
		private Location targetLocation;
		GeomagneticField geoField;

		private SensorManager mSensorManager;
		private Sensor mOrientation;

		private final int sensorType = Sensor.TYPE_ROTATION_VECTOR;
		float[] rotMat = new float[9];
		float[] vals = new float[3];

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_site_events,
					container, false);

//			mSensorManager = (SensorManager) getActivity().getSystemService(
//					Context.SENSOR_SERVICE);
//			mOrientation = mSensorManager
//					.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
//			mSensorManager.registerListener(this, mOrientation,
//					SensorManager.SENSOR_DELAY_NORMAL);
//
//			targetLocation = new Location("newLocation");
//			targetLocation
//					.setLatitude(GPSCoordinateChecker.CurrentSiteLatitude);
//			targetLocation
//					.setLongitude(GPSCoordinateChecker.CurrentSiteLongitude);
//
//			locationManager = (LocationManager) getActivity().getSystemService(
//					Context.LOCATION_SERVICE);
//			locationManager.requestLocationUpdates(
//					LocationManager.GPS_PROVIDER, 1000, 500, this);

			return rootView;
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			Toast.makeText(getActivity(), "Sensor event", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		public void setMenuVisibility(boolean menuVisible) {
			super.setMenuVisibility(menuVisible);

			// First starts (gets called before everything else)
			if (mSensorManager == null) {
				return;
			}

			if (menuVisible) {
				this.registerSensorListener();
			} else {
				this.unregisterSensorListener();
			}
		}

		@Override
		public void onStart() {
			super.onStart();

			if (this.getUserVisibleHint()) {
				this.registerSensorListener();
			}
		}

		@Override
		public void onStop() {
			super.onStop();
			this.unregisterSensorListener();
		}

		private void registerSensorListener() {
			mSensorManager.registerListener(this,
					mSensorManager.getSensorList(Sensor.TYPE_ROTATION_VECTOR)
							.get(0), SensorManager.SENSOR_DELAY_FASTEST);
		}

		private void unregisterSensorListener() {
			mSensorManager.unregisterListener(this);
		}

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub

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
}

//
//
//
//
// private LocationManager locationManager;
// private Location targetLocation;
// GeomagneticField geoField;
//
// private SensorManager mSensorManager;
// private Sensor mOrientation;
//
// private final int sensorType = Sensor.TYPE_ROTATION_VECTOR;
// float[] rotMat = new float[9];
// float[] vals = new float[3];
//
// public View onCreateView(LayoutInflater inflater, ViewGroup container,
// Bundle savedInstanceState) {
//
// View rootView = inflater.inflate(R.layout.fragment_site_events,
// container, false);
//
//
// mSensorManager =
// (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
// mOrientation = mSensorManager
// .getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
// mSensorManager.registerListener(this, mOrientation,
// SensorManager.SENSOR_DELAY_NORMAL);
//
// targetLocation = new Location("newLocation");
// targetLocation
// .setLatitude(GPSCoordinateChecker.CurrentSiteLatitude);
// targetLocation
// .setLongitude(GPSCoordinateChecker.CurrentSiteLongitude);
//
// locationManager = (LocationManager) getActivity().getSystemService(
// Context.LOCATION_SERVICE);
// locationManager.requestLocationUpdates(
// LocationManager.GPS_PROVIDER, 1000, 500, this);
//
// return rootView;
// }
//
// @Override
// public void onLocationChanged(Location location) {
// geoField = new GeomagneticField(Double.valueOf(
// location.getLatitude()).floatValue(), Double.valueOf(
// location.getLongitude()).floatValue(), Double.valueOf(
// location.getAltitude()).floatValue(),
// System.currentTimeMillis());
// double heading = geoField.getDeclination();
// // Toast.makeText(getActivity(), String.valueOf("Geofiled:"+heading),
// // Toast.LENGTH_LONG).show();
//
// }
//
// @Override
// public void onProviderDisabled(String provider) {
// // TODO Auto-generated method stub
//
// }
//
// @Override
// public void onProviderEnabled(String provider) {
// // TODO Auto-generated method stub
//
// }
//
// @Override
// public void onStatusChanged(String provider, int status, Bundle extras) {
// // TODO Auto-generated method stub
//
// }
//
// @Override
// public void onAccuracyChanged(Sensor sensor, int accuracy) {
// // TODO Auto-generated method stub
//
// }
//
// @Override
// public void onSensorChanged(SensorEvent event) {
// // It is good practice to check that we received the proper sensor event
// if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
// {
// // Convert the rotation-vector to a 4x4 matrix.
// SensorManager.getRotationMatrixFromVector(rotMat,
// event.values);
// SensorManager
// .remapCoordinateSystem(rotMat,
// SensorManager.AXIS_X, SensorManager.AXIS_Z,
// rotMat);
// SensorManager.getOrientation(rotMat, vals);
//
// // Optionally convert the result from radians to degrees
// vals[0] = (float) Math.toDegrees(vals[0]);
// vals[1] = (float) Math.toDegrees(vals[1]);
// vals[2] = (float) Math.toDegrees(vals[2]);
//
//
// Toast.makeText(getActivity(),
// String.valueOf("Azimuth"+vals[0]+"Test:"+vals[1]), Toast.LENGTH_LONG).show();
//
// }
//
// }
