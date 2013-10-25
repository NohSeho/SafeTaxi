package com.safetaxik;

import com.safetaxik.util.Utils;

import group.pals.android.lib.ui.lockpattern.LockPatternActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;

public class PatternLockActivity extends Activity {
	private static final int REQ_CREATE_PATTERN = 1;
	private static final int REQ_ENTER_PATTERN = 2;
	char[] pattern;
	char[] savedPattern;

	SharedPreferences pattern_pref;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pattern_lock);

		init();

		String temp_pattern = Utils.getPref(pattern_pref, "PATTERN");
 
		savedPattern = new char[temp_pattern.length()];

		for (int i = 0; i < temp_pattern.length(); i++) {
			savedPattern[i] = temp_pattern.charAt(i);
		}

		// Intent create = new Intent(LockPatternActivity.ACTION_CREATE_PATTERN,
		// null, this, LockPatternActivity.class);
		// startActivityForResult(create, REQ_CREATE_PATTERN);

		Intent intent = new Intent(LockPatternActivity.ACTION_COMPARE_PATTERN,
				null, this, LockPatternActivity.class);
		intent.putExtra(LockPatternActivity.EXTRA_PATTERN, savedPattern);
		startActivityForResult(intent, REQ_ENTER_PATTERN);
	}

	void init() {
		pattern_pref = getSharedPreferences("PATTERN", MODE_PRIVATE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.pattern_lock, menu);
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQ_CREATE_PATTERN:
			if (resultCode == RESULT_OK) {
				char[] pattern = data
						.getCharArrayExtra(LockPatternActivity.EXTRA_PATTERN);
				StringBuilder patternBuilder = new StringBuilder();
				for (int i = 0; i < pattern.length; i++) {
					patternBuilder.append(pattern[i]);
					System.out.println(pattern[i]);
				}

				Utils.setPref(pattern_pref, "PATTERN",
						patternBuilder.toString());
				Utils.showToast(this, "패턴이 저장되었습니다");
			}
			break;

		case REQ_ENTER_PATTERN:
			/*
			 * NOTE that there are 4 possible result codes!!!
			 */
			switch (resultCode) {
			case RESULT_OK:
				// The user passed
				break;
			case RESULT_CANCELED:
				// The user cancelled the task
				break;
			case LockPatternActivity.RESULT_FAILED:
				// The user failed to enter the pattern
				break;
			case LockPatternActivity.RESULT_FORGOT_PATTERN:
				// The user forgot the pattern and invoked your recovery
				// Activity.
				break;
			}

			/*
			 * In any case, there's always a key EXTRA_RETRY_COUNT, which holds
			 * the number of tries that the user did.
			 */
			int retryCount = data.getIntExtra(
					LockPatternActivity.EXTRA_RETRY_COUNT, 0);

			break;
		}
	}
}
