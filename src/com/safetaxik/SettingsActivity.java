package com.safetaxik;

import com.safetaxik.util.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity implements OnClickListener {
	SharedPreferences	pref;
	Button				btn_phone, btn_phone2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		init();
	}

	void init() {
		btn_phone = (Button) findViewById(R.id.setting_btn_phone);
		btn_phone2 = (Button) findViewById(R.id.setting_btn_phone2);

		pref = getSharedPreferences("SETTING", MODE_PRIVATE);

		btn_phone.setOnClickListener(this);
		btn_phone2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_btn_phone:
			showDialog(1);
			break;

		case R.id.setting_btn_phone2:
			showDialog(2);
			break;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 1:
			AlertDialog.Builder builder;
			AlertDialog alertDialog;
			final SharedPreferences pref = getSharedPreferences("SETTING", MODE_PRIVATE);

			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.phone_set, null);
			final EditText edit_phone = (EditText) layout.findViewById(R.id.setting_edit_phone);
			edit_phone.setText(Utils.getPref(pref, "Phone1"));
			builder = new AlertDialog.Builder(SettingsActivity.this);
			builder.setView(layout);
			builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Utils.setPref(pref, "Phone1", edit_phone.getText().toString());
				}
			});
			alertDialog = builder.create();
			return alertDialog;

		case 2:
			AlertDialog.Builder builder2;
			AlertDialog alertDialog2;
			final SharedPreferences pref2 = getSharedPreferences("SETTING", MODE_PRIVATE);

			LayoutInflater inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout2 = inflater2.inflate(R.layout.phone_set, null);
			final EditText edit_phone2 = (EditText) layout2.findViewById(R.id.setting_edit_phone);
			edit_phone2.setText(Utils.getPref(pref2, "Phone2"));
			builder2 = new AlertDialog.Builder(SettingsActivity.this);
			builder2.setView(layout2);
			builder2.setPositiveButton("확인", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Utils.setPref(pref2, "Phone2", edit_phone2.getText().toString());
				}
			});
			alertDialog2 = builder2.create();
			return alertDialog2;
		}

		return null;

	}

}
