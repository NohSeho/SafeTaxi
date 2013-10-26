package com.safetaxik.util;

import java.util.ArrayList;

import com.safetaxik.RidingActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class Utils {
	public final static String	msgHeaer	= "[안심택시] 현재 위치는 ";
	public final static String	msgCenter	= "입니다. 혹시 ";
	public final static String	msgTail		= "분 안에 연락이 없으면 확인해주세요. [안심택시]";

	public final static void setPref(SharedPreferences pref, String Key, String Value) {
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

	public final static void readySMS(Context context) {
		SharedPreferences location_pref, setting_pref;
		location_pref = context.getSharedPreferences("LOCATION", Context.MODE_PRIVATE);
		setting_pref = context.getSharedPreferences("SETTING", Context.MODE_PRIVATE);

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
			Utils.smsSender(context, tempNum, tempMsgArray);
		} else
			Utils.showToast(context, "설정화면에서 전화번호를 먼저 설정해주세요");

		smsSender(context, tempNum, tempMsgArray);
	}

	@SuppressWarnings("deprecation")
	public final static void smsSender(Context context, String phoneNumber, String... message) {
		PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, Utils.class), 0);
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
