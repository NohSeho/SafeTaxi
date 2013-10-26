package com.safetaxik;

import java.io.IOException;
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

public class SearchActivity extends FragmentActivity {

	Intent	mIntent;
	String	carNo;

	String	c_name, d_name, c_num, d_num;
	TextView	company_name, driver_name, company_num, driver_num;
	ImageView	img_driver, img_left, img_right;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_fragment2);

		mIntent = new Intent(this.getIntent());
		carNo = mIntent.getStringExtra("CARNO");

		init();
	}

	void init() {
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
		}
		Utils.showToast(getApplicationContext(), midCarNo);
		if ((!midCarNo.equals("아")) && (!midCarNo.equals("바")) && (!midCarNo.equals("사")) && (!midCarNo.equals("자"))) {
			c_name = "회사명 : 불법택시";
		}
		Log.d("fuck", c_name);
		company_name.setText(c_name);

	}

}