package com.moodbooster;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import com.moodbooster.pam.PamActivity;

public class HomeScreenActivity extends Activity {

	public final static String PREFS_NAME = "MOODBOOSTER_PREFS";
	public final static String EXTRA_NOTIF = "com.moodbooster.HomeScreenActivity.MESSAGE";
	public final static int FIRST_NOTIF_HOUR = 14;
	public final static int SECOND_NOTIF_HOUR = 20;
	
	private Button enterMoodButton;
	private PendingIntent notifPendingIntent;
	private PendingIntent wallPendingIntent;
	private AlarmManager manager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		addButtonListener();

		// Set notification to be displayed
		manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent notificationIntentOne = new Intent(this,
				NotificationReceiver.class);
		notificationIntentOne.putExtra(EXTRA_NOTIF, FIRST_NOTIF_HOUR);
		
		// Retrieve a PendingIntent that will perform a broadcast for 1st NOTIF
		notifPendingIntent = PendingIntent.getBroadcast(this, 0,
				notificationIntentOne, 0);
		
		// set first time for mood entry alarm
		Calendar moodentryOne = Calendar.getInstance();
		moodentryOne.setTimeInMillis(System.currentTimeMillis());
		moodentryOne.set(Calendar.HOUR_OF_DAY, FIRST_NOTIF_HOUR);
		moodentryOne.set(Calendar.MINUTE, 0);
		moodentryOne.set(Calendar.SECOND, 0);
		
		// how often first notification will appear (millis)
		int notifIntervalOne = 1000 * 60 * 60 * 24; // 1sec -> 1 min -> 1hr ->
													// 24hrs
		
		// schedule notification for tomorrow if the time is already up
		if (System.currentTimeMillis() > moodentryOne.getTimeInMillis()) {
			moodentryOne.add(Calendar.DATE, 1);
		}
		manager.setRepeating(AlarmManager.RTC_WAKEUP,
				moodentryOne.getTimeInMillis(), notifIntervalOne,
				notifPendingIntent);

		
		// Retrieve a PendingIntent that will perform a broadcast for 2nd NOTIF
		Intent notificationIntentTwo = new Intent(this,
				NotificationReceiver.class);
		notificationIntentTwo.putExtra(EXTRA_NOTIF, SECOND_NOTIF_HOUR);
		notifPendingIntent = PendingIntent.getBroadcast(this, 1,
				notificationIntentTwo, 0);
		
		// set second time for mood entry alarm
		Calendar moodentryTwo = Calendar.getInstance();
		moodentryTwo.set(Calendar.HOUR_OF_DAY, SECOND_NOTIF_HOUR);
		moodentryTwo.set(Calendar.MINUTE, 0);
		moodentryTwo.set(Calendar.SECOND, 0);
		
		// how often first notification will appear (millis)
		int notifIntervalTwo = 1000 * 60 * 60 * 24; // 1sec -> 1 min -> 1hr ->
													// 24hrs
		
		// schedule notification for tomorrow if the time is already up
		if (System.currentTimeMillis() > moodentryTwo.getTimeInMillis()) {
			moodentryTwo.add(Calendar.DATE, 1);
		}
		manager.setRepeating(AlarmManager.RTC_WAKEUP,
				moodentryTwo.getTimeInMillis(), notifIntervalTwo,
				notifPendingIntent);

		
		// Set WALLPAPER to change
		boolean isWallpaperAlarmSet = (PendingIntent.getBroadcast(this, 0,
				new Intent(this, WallpaperReceiver.class),
				PendingIntent.FLAG_NO_CREATE) != null);
		
		// only change wallpaper if the wallpaperIntent has not been set
		if (!isWallpaperAlarmSet) {
			Intent wallpaperIntent = new Intent(this, WallpaperReceiver.class);
			wallPendingIntent = PendingIntent.getBroadcast(this, 0,
					wallpaperIntent, 0);

			// how often the wallpaper will update (millis)
			int wallInterval = 1000 * 60 * 60 * 2; // 1sec -> 1min -> 1hr -> 2hr

			// set wallpaper to repeat
			manager.setRepeating(AlarmManager.RTC_WAKEUP,
					System.currentTimeMillis(), wallInterval, wallPendingIntent);
		}
	}

	public void addButtonListener() {
		// define button
		enterMoodButton = (Button) findViewById(R.id.enterMoodButton);

		// switch to pam when button is pressed
		enterMoodButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// move to next screen
				Intent testBed = new Intent(v.getContext(), PamActivity.class);
				startActivity(testBed);

			} // end onClick

		}); // end setOnClickListener

	} // end addButtonListener

} // end class