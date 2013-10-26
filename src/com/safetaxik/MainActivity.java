package com.safetaxik;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
		btn_exit.setOnClickListener(this);

		location_pref = getSharedPreferences("LOCATION", MODE_PRIVATE);
		setting_pref = getSharedPreferences("SETTING", MODE_PRIVATE);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.main_btn_ride) {
			startActivity(new Intent(MainActivity.this, SelectActivity.class));
		}
		if (v.getId() == R.id.main_btn_settings) {
			startActivity(new Intent(MainActivity.this, SettingsActivity.class));
		}
		if (v.getId() == R.id.main_btn_exit) {
			if (Utils.getPref(ing_pref, "ING").equals("on")) {
				Utils.setPref(ing_pref, "ING", "off");
				Utils.showToast(getApplicationContext(), "하차하였습니다.");
				Utils.smsSender(getApplicationContext(), Utils.getPref(setting_pref, "phoneNum1"),
						"[안심택시]안전하게 하차하였습니다.");
			} else {
				Utils.showToast(getApplicationContext(), "탑승후 시도해주세요");
			}
		}
		if (v.getId() == R.id.main_btn_search) {
			showDialog(1);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder;
		AlertDialog alertDialog;

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.write_dialog, (ViewGroup) findViewById(R.id.layout_root));
		final EditText sex = (EditText) layout.findViewById(R.id.edit_carno);
		builder = new AlertDialog.Builder(MainActivity.this);
		builder.setView(layout);
		builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent mIntent = new Intent(MainActivity.this, SearchActivity.class);
				mIntent.putExtra("CARNO", sex.getText().toString());
				startActivity(mIntent);
				finish();
			}
		});
		alertDialog = builder.create();
		return alertDialog;

	}
}
