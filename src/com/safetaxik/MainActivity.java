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
	SharedPreferences	settings_pref;
	Button				btn_ride, btn_exit, btn_search, btn_settings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
	}

	void init() {
		btn_ride = (Button) findViewById(R.id.main_btn_ride);
		btn_exit = (Button) findViewById(R.id.main_btn_exit);
		btn_search = (Button) findViewById(R.id.main_btn_search);
		btn_settings = (Button) findViewById(R.id.main_btn_settings);

		btn_ride.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		btn_settings.setOnClickListener(this);

		settings_pref = getSharedPreferences("SETTING", MODE_PRIVATE);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.main_btn_ride) {
			startActivity(new Intent(MainActivity.this, RidingActivity.class));
		} else if (v.getId() == R.id.main_btn_search) {
			String tempNum = Utils.getPref(settings_pref, "phoneNum1");
			String tempMsg = Utils.msgHeaer + "니 마음속" + Utils.msgCenter + "10" + Utils.msgTail;

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
				Utils.smsSender(MainActivity.this, tempNum, tempMsgArray);
			} else
				Utils.showToast(MainActivity.this, "설정화면에서 전화번호를 먼저 설정해주세요");
		} else if (v.getId() == R.id.main_btn_settings) {
			startActivity(new Intent(MainActivity.this, SettingsActivity.class));
		}
	}
}
