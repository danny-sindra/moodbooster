package com.moodbetter;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class UnlockPhoneReceiver extends BroadcastReceiver {

	public static final String PREFS_NAME = "MOODBOOSTER_PREFS";
	public static final String PREFS_TOTAL_UNLOCK = "TOTAL_UNLOCK";
	public static final int PREFS_TOTAL_UNLOCK_DEFAULTVAL = 0;
	public static final String PREFS_DATE = "CURRENT_DATE";
	public static final int PREFS_DATE_DEFAULTVAL = 1;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {

		}
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

		}
		if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
			SharedPreferences savedData = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = savedData.edit();
			
			// Get current day
			Calendar cal = Calendar.getInstance();
			int currentDay = cal.get(Calendar.DAY_OF_MONTH);
			
			// Get old day from SharedPreferences
			int oldDay = savedData.getInt(PREFS_DATE, PREFS_DATE_DEFAULTVAL);
			
			// Reset total unlock in SharedPreferences if the day changed, else increase total unlock value
			if (currentDay != oldDay) {
				// Reset total unlock to 0
				editor.putInt(PREFS_TOTAL_UNLOCK, 1);
				
				// Write new day to PREFS
				editor.putInt(PREFS_DATE, currentDay);
				
				editor.commit();
			}
			else {
				// Get old total_unlock value from PREFS
				savedData = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
				int oldTotalUnlock = savedData.getInt(PREFS_TOTAL_UNLOCK, PREFS_TOTAL_UNLOCK_DEFAULTVAL);
				
				// Write new total_unlock value to PREFS
				editor.putInt(PREFS_TOTAL_UNLOCK, oldTotalUnlock+1);
				editor.commit();
			}
		}
	}
}
