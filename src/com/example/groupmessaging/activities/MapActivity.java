package com.example.groupmessaging.activities;

import java.io.ByteArrayOutputStream;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.groupmessaging.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {

	GoogleMap mMap;
	LocationManager locationManager;
	LocationListener locationListener;
	Location tempLocation;
	Bitmap localBitmap;
	byte[] localBytes;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Share Location");

		loadMap();
		getCurrentLocation();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_send_map:
			onSendMap();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void onSendMap() {
		// if bitmap is not null -- send image 
		if(localBytes != null){
			// Prepare data intent 
			Intent data = new Intent();
			// Pass relevant data back as a result
			if(tempLocation != null){
				data.putExtra("lat", tempLocation.getLatitude());
				data.putExtra("lon", tempLocation.getLongitude());
			}
			data.putExtra("locationSnapshot", localBytes);
			// Activity finished ok, return the data
			setResult(RESULT_OK, data); // set result code and bundle data for response

		}
		else{
			// send reverse geo coded address

		}
		finish(); // closes the activity, pass data to parent
	}


	private void getCurrentLocation() {
		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location provider.
				tempLocation = location;
				makeUseOfNewLocation(location);
			}

			public void onStatusChanged(String provider, int status, Bundle extras) {}

			public void onProviderEnabled(String provider) {}

			public void onProviderDisabled(String provider) {}
		};

		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	}

	public void makeUseOfNewLocation(Location l){
		LatLng latlon = new LatLng(l.getLatitude(), l.getLongitude());

		Log.d("DEBUG", "New location: " + l.getLatitude() + " " + l.getLongitude());
		CameraPosition cameraPosition = new CameraPosition.Builder()
		.target(latlon)      // Sets the center of the map to location
		.zoom(17)                   // Sets the zoom
		.bearing(0)                // Sets the orientation of the camera to east
		.tilt(30)                   // Sets the tilt of the camera to 30 degrees
		.build();                   // Creates a CameraPosition from the builder
		mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		mMap.addMarker(new MarkerOptions().position(latlon));


		locationManager.removeUpdates(locationListener);

		mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
			public void onMapLoaded() {
				mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
					public void onSnapshotReady(Bitmap bitmap) {


						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
						localBitmap = bitmap;
						localBytes = stream.toByteArray();
						Log.d("DEBUG", "screenshot saved --- " + bitmap.toString());

					}
				});
			}
		});

	}

	private void loadMap(){

		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
					.getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				// The Map is verified. It is now safe to manipulate the map.

			}
		}
		mMap.setMyLocationEnabled(true);
		mMap.setBuildingsEnabled(true);


	}

}
