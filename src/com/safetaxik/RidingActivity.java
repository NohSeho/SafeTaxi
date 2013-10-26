package com.safetaxik;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

	SharedPreferences	location_pref, setting_pref, ing_pref;
	Button				btn_message;
	Button				btn_cancel;

	Intent				mIntent;
	String				carNo;

	String				c_name, d_name, c_num, d_num;
	TextView			company_name, driver_name, company_num, driver_num;
	ImageView			img_driver, img_left, img_right;

	NotificationManager	mNM		= null;
	Notification		mNoti	= null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_riding);

		GooglePlayServicesUtil.isGooglePlayServicesAvailable(RidingActivity.this);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, true);
		mIntent = new Intent(this.getIntent());
		carNo = mIntent.getStringExtra("CARNO");

		init();
	}

	void init() {
		PendingIntent mPedingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), RidingActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
		mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNoti = new NotificationCompat.Builder(getApplicationContext()).setContentTitle("알림택시").setContentText("탑승중").setSmallIcon(R.drawable.ic_launcher).setTicker("탑승!!").setAutoCancel(true)
				.setContentIntent(mPedingIntent).build();
		mNoti.flags = Notification.FLAG_ONGOING_EVENT;
		mNM.notify(0000, mNoti);
		location_pref = getSharedPreferences("LOCATION", MODE_PRIVATE);
		setting_pref = getSharedPreferences("SETTING", MODE_PRIVATE);
		ing_pref = getSharedPreferences("ING", MODE_PRIVATE);
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
				if (position == 0) {
					img_right = (ImageView) findViewById(R.id.img_right);
					img_right.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							pager.setCurrentItem(1);
						}
					});
					company_name = (TextView) findViewById(R.id.company_name);
					driver_name = (TextView) findViewById(R.id.driver_name);
					company_name = (TextView) findViewById(R.id.company_name);
					driver_name = (TextView) findViewById(R.id.driver_name);
					img_driver = (ImageView) findViewById(R.id.img_driver);

					carNo = carNo.trim();
					if (carNo.length() < 5) {
						carNo = "34아2095";
					}
					String firstCarNo = carNo.substring(0, 2);
					String midCarNo = carNo.substring(2, 3);
					String lastCarNo = carNo.substring(3, 5);

					if (lastCarNo.equals("12")) {
						c_name = "삼안통상";
					} else if (lastCarNo.equals("87")) {
						c_name = "성진운수";
					} else if (lastCarNo.equals("53")) {
						c_name = "상연기업";
					} else if (lastCarNo.equals("10")) {
						c_name = "국제운수";
					} else if (lastCarNo.equals("92")) {
						c_name = "성진운수";
					} else if (lastCarNo.equals("81")) {
						c_name = "삼덕상운";
					} else if (lastCarNo.equals("83")) {
						c_name = "한일택시";
					} else if (lastCarNo.equals("45")) {
						c_name = "통운산업";
					} else if (lastCarNo.equals("69")) {
						c_name = "불루택시";
					} else if (lastCarNo.equals("81")) {
						c_name = "천진교통";
					} else if (lastCarNo.equals("32")) {
						c_name = "삼일운수";
					} else {
						c_name = "진일운수";
					}

					if ((!midCarNo.equals("아")) || (!midCarNo.equals("바")) || (!midCarNo.equals("사")) || (!midCarNo.equals("자"))) {
						c_name = "회사명 : 불법택시";
					}
					Log.d("fuck", c_name);
					company_name.setText(c_name);

					Utils.setPref(ing_pref, "ING", "on");
				} else if (position == 1) {
					img_left = (ImageView) findViewById(R.id.img_left);
					img_left.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							pager.setCurrentItem(0);
						}
					});
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
							Utils.readySMS(RidingActivity.this);
						}
					});
					btn_cancel = (Button) findViewById(R.id.riding_btn_cancel);
					btn_cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (Utils.getPref(ing_pref, "ING").equals("on")) {
								Utils.setPref(ing_pref, "ING", "off");
								Utils.showToast(getApplicationContext(), "하차하였습니다.");
								Utils.smsSender(getApplicationContext(), Utils.getPref(setting_pref, "Phone1"), "[안심택시]안전하게 하차하였습니다.");
								finish();
							}
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

			// Utils.showToast(RidingActivity.this, "위도 : " + lat + " 경도 : " +
			// lng);
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