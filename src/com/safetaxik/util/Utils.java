package com.safetaxik.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Utils {
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
}
