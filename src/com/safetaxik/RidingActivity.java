package com.safetaxik;

import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MapController;
import com.safetaxik.adapter.RidingPagerAdapter;
import com.safetaxik.util.Utils;

public class RidingActivity extends FragmentActivity implements OnMapClickListener, LocationListener, OnMarkerClickListener {
	ViewPager			pager;
	RidingPagerAdapter	adapter;

	GoogleMap			mMap;
	LocationManager		locationManager;
	String				provider;
	Location			location;
	double				latitude, longitude;
	LatLng				latLng;
	Marker				marker;
	TextView			mTapTextView;
	Button				pinRemove, dropPin;
	MapController		mapController;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		provider = LocationManager.NETWORK_PROVIDER;
		locationManager.requestLocationUpdates(provider, 0, 0, this);
		GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		setContentView(R.layout.activity_riding);

		init();
	}

	void init() {
		pager = (ViewPager) findViewById(R.id.riding_pager);
		adapter = new RidingPagerAdapter(this);
		pager.setAdapter(adapter);

		if (pager.getCurrentItem() == 1) {
			setUpMapIfNeeded();

			pinRemove = (Button) findViewById(R.id.pinremove);
			dropPin = (Button) findViewById(R.id.droppin);

			pinRemove.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mMap.clear();
				}
			});

			dropPin.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Display display = getWindowManager().getDefaultDisplay();

					int width = display.getWidth();
					int height = display.getHeight();

					Point point = new Point(width / 2, height / 2);
					LatLng latLng = mMap.getProjection().fromScreenLocation(point);
					System.out.println("+++++++++++++" + width / 2 + "+++" + height / 2);
					marker = mMap.addMarker(new MarkerOptions().position(latLng).title("").snippet("").draggable(true));
					marker.getPosition();
					marker.showInfoWindow();
				}
			});
		}
		// mMap.setLocationSource(new ;
	}

	@Override
	public void onPause() {
		super.onPause();
		locationManager.removeUpdates(RidingActivity.this);
	}

	@Override
	public void onResume() {
		super.onResume();

		if (pager.getCurrentItem() == 1)
			setUpMapIfNeeded();
	}

	void setUpMapIfNeeded() {
		if (pager.getCurrentItem() == 1)
			if (mMap == null) {
				mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
				if (mMap != null) {
					setUpMap();
				}
			}
	}

	void setUpMap() {
		mMap.setOnMapClickListener(this);
		mMap.setMyLocationEnabled(true);
		mMap.getMyLocation();
		mMap.setOnMarkerClickListener(this);
		mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker arg0) {
				Utils.showToast(RidingActivity.this, "You clicked on InfoWindow ...");
			}
		});
	}

	@Override
	public void onMapClick(LatLng arg0) {

	}

	@Override
	public void onLocationChanged(Location location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		marker.getPosition();
		marker.showInfoWindow();

		marker.setTitle("Longitude = " + marker.getPosition().longitude);
		marker.setSnippet("Latitude = " + marker.getPosition().latitude);

		return true;
	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}
}
