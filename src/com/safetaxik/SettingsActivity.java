package com.safetaxik;

import com.safetaxik.util.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity implements OnClickListener {
	SharedPreferences pref;
	EditText edit_phoneNum1;
	Button btn_apply;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		init();
	}

	void init() {
		edit_phoneNum1 = (EditText) findViewById(R.id.settings_edit_phone_1);
		btn_apply = (Button) findViewById(R.id.settings_btn_apply);

		pref = getSharedPreferences("SETTING", MODE_PRIVATE);

		edit_phoneNum1.setText(Utils.getPref(pref, "phoneNum1"));
		btn_apply.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Utils.setPref(pref, "phoneNum1", edit_phoneNum1.getText().toString());
	}

	@Override
	public void onPause() {
		super.onPause();
		Utils.setPref(pref, "phoneNum1", edit_phoneNum1.getText().toString());
	}

	@Override
	protected void onResume() {
		super.onResume();
		edit_phoneNum1.setText(Utils.getPref(pref, "phoneNum1"));
	}
}
