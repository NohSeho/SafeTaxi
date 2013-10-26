package com.safetaxik;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.safetaxik.util.Utils;

public class MainActivity extends Activity implements OnClickListener {
	SharedPreferences	setting_pref, location_pref;
	Button				btn_ride, btn_exit, btn_search, btn_setting;
	SharedPreferences	ing_pref;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
	}

	void init() {
		ing_pref = getSharedPreferences("ING", MODE_PRIVATE);
		btn_ride = (Button) findViewById(R.id.main_btn_ride);
		btn_exit = (Button) findViewById(R.id.main_btn_exit);
		btn_search = (Button) findViewById(R.id.main_btn_search);
		btn_setting = (Button) findViewById(R.id.main_btn_settings);

		btn_ride.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		btn_setting.setOnClickListener(this);

		location_pref = getSharedPreferences("LOCATION", MODE_PRIVATE);
		setting_pref = getSharedPreferences("SETTING", MODE_PRIVATE);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.main_btn_ride) {
			startActivity(new Intent(MainActivity.this, SelectActivity.class));
		} else if (v.getId() == R.id.main_btn_settings) {
			startActivity(new Intent(MainActivity.this, SettingsActivity.class));
		} else if (v.getId() == R.id.main_btn_exit) {
			if (Utils.getPref(ing_pref, "ING").equals("on")) {
				Utils.setPref(ing_pref, "ING", "off");
				Utils.showToast(getApplicationContext(), "하차하였습니다.");
				Utils.smsSender(getApplicationContext(), Utils.getPref(setting_pref, "phoneNum1"),
						"[안심택시]안전하게 하차하였습니다.");
			} else {
				Utils.showToast(getApplicationContext(), "탑승후 시도해주세요");
			}
		}
	}

}
