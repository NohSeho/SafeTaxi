package com.safetaxik;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.safetaxik.adapter.RidingPagerAdapter;
import com.safetaxik.util.Utils;

public class RidingActivity extends FragmentActivity implements LocationListener {
	ViewPager			pager;
	RidingPagerAdapter	adapter;

	GoogleMap			mmap;
	LocationManager		locationManager;
	String				provider;

	SharedPreferences	location_pref, setting_pref;
	Button				btn_message;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_riding);

		GooglePlayServicesUtil.isGooglePlayServicesAvailable(RidingActivity.this);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, true);

		init();
	}

	void init() {
		location_pref = getSharedPreferences("LOCATION", MODE_PRIVATE);
		setting_pref = getSharedPreferences("SETTING", MODE_PRIVATE);
		pager = (ViewPager) findViewById(R.id.riding_pager);
		adapter = new RidingPagerAdapter(this);
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int position) {
				// TODO Auto-generated method stub

				if (position == 1) {
					if (provider == null) { // 위치정보 설정이 안되어 있으면 설정하는 엑티비티로 이동합니다
						new AlertDialog.Builder(RidingActivity.this).setTitle("위치서비스 동의").setNeutralButton("이동", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
							}
						}).setOnCancelListener(new DialogInterface.OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								finish();
							}
						}).show();
					} else { // 위치 정보 설정이 되어 있으면 현재위치를 받아옵니다
						locationManager.requestLocationUpdates(provider, 1, 1, RidingActivity.this);
						setUpMapIfNeeded();
					}
					btn_message = (Button) findViewById(R.id.riding_btn_message);
					btn_message.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							String City = Utils.getPref(location_pref, "City");
							String SubCity = Utils.getPref(location_pref, "SubCity");
							String Town = Utils.getPref(location_pref, "Town");
							String Detail = Utils.getPref(location_pref, "Detail");

							String tempNum = Utils.getPref(setting_pref, "phoneNum1");
							String tempMsg = Utils.msgHeaer + City + " " + SubCity + " " + Town + " " + Detail + Utils.msgCenter + "10" + Utils.msgTail;

							ArrayList<String> tempMsgList = new ArrayList<String>();
							int z = 0;
							while (tempMsg.length() > 40) {
								Log.e("while", "" + (z + 1) + "번째");
								tempMsgList.add(tempMsg.substring(0, 40));
								tempMsg = tempMsg.substring(40, tempMsg.length());
							}

							tempMsgList.add(tempMsg.substring(0, tempMsg.length()));

							String[] tempMsgArray = new String[tempMsgList.size()];

							for (int i = 0; i < tempMsgList.size(); i++) {
								tempMsgArray[i] = tempMsgList.get(i);
							}

							if (!tempNum.equals("") && tempNum != null) {
								Utils.smsSender(RidingActivity.this, tempNum, tempMsgArray);
							} else
								Utils.showToast(RidingActivity.this, "설정화면에서 전화번호를 먼저 설정해주세요");
						}
					});
				}
			}
		});

		// mMap.setLocationSource(new ;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {// 위치설정
																					// 엑티비티
																					// 종료
																					// 후
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 0:
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			provider = locationManager.getBestProvider(criteria, true);
			if (provider == null) {// 사용자가 위치설정동의 안했을때 종료
				finish();
			} else {// 사용자가 위치설정 동의 했을때
				locationManager.requestLocationUpdates(provider, 1L, 2F, RidingActivity.this);
				setUpMapIfNeeded();
			}
			break;
		}
	}

	@Override
	public void onBackPressed() {
		this.finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (pager.getCurrentItem() == 1) {
			setUpMapIfNeeded();

		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (pager.getCurrentItem() == 1) {
			locationManager.removeUpdates(this);
		}
	}

	private void setUpMapIfNeeded() {
		if (mmap == null) {
			mmap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			if (mmap != null) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		mmap.setMyLocationEnabled(true);
		mmap.getMyLocation();

	}

	boolean	locationTag	= true;

	@Override
	public void onLocationChanged(Location location) {
		if (locationTag) {// 한번만 위치를 가져오기 위해서 tag를 주었습니다
			Log.d("myLog", "onLocationChanged: !!" + "onLocationChanged!!");
			double lat = location.getLatitude();
			double lng = location.getLongitude();

			Utils.showToast(RidingActivity.this, "위도 : " + lat + " 경도 : " + lng);
			// 주소 갖고오기
			Geocoder gc = new Geocoder(this, Locale.KOREAN);
			try {

				List<Address> addresses = gc.getFromLocation(lat, lng, 1);
				StringBuilder sb = new StringBuilder();
				if (addresses.size() > 0) {
					Address address = addresses.get(0);
					for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
						sb.append(address.getAddressLine(i)).append("\n");
					sb.append(address.getCountryName()).append(" "); // 나라코드
					sb.append(address.getLocality()).append(" "); // 시
					Utils.setPref(location_pref, "City", address.getLocality());
					sb.append(address.getSubLocality() + " "); // 구
					Utils.setPref(location_pref, "SubCity", address.getSubLocality());
					sb.append(address.getThoroughfare()).append(" "); // 동
					Utils.setPref(location_pref, "Town", address.getThoroughfare());
					sb.append(address.getFeatureName()).append(" "); // 번지
					Utils.setPref(location_pref, "Detail", address.getFeatureName());
					Utils.showToast(getApplicationContext(), "내주소 : " + sb.toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			mmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 16));
			locationTag = false;
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

	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onMapClick(LatLng point) {
		// TODO Auto-generated method stub

	}
}