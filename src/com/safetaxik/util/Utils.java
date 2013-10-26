package com.safetaxik.util;

import java.util.ArrayList;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class Utils {
	public final static String msgHeaer = "[안심택시] 현재 위치는 ";
	public final static String msgCenter = "입니다. 혹시 ";
	public final static String msgTail = "분 안에 연락이 없으면 확인해주세요.";

	public final static void setPref(SharedPreferences pref, String Key,
			String Value) {
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(Key, Value);
		editor.commit();
	}

	public final static String getPref(SharedPreferences pref, String Key) {
		return pref.getString(Key, "");
	}

	public final static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	@SuppressWarnings("deprecation")
	public final static void smsSender(Context context, String phoneNumber,
			String... message) {
		PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(
				context, Utils.class), 0);
		SmsManager sms = SmsManager.getDefault();

		ArrayList<String> tempMsg = new ArrayList<String>();

		for (int i = 0; i < message.length; i++) {
			tempMsg.add(message[i]);
			Log.i("UTILS", message[i]);
		}

		for (int i = 0; i < tempMsg.size(); i++)
			sms.sendTextMessage(phoneNumber, null, tempMsg.get(i), pi, null);
	}
}
