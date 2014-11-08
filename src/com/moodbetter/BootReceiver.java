package com.moodbetter;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

	public final static String EXTRA_NOTIF = "com.moodbooster.BootReceiver.MESSAGE";
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// Re-set AlarmManager for Wallpaper and Notif after phone reboot OR opened
		PendingIntent notifPendingIntent;
		PendingIntent wallPendingIntent;
		AlarmManager manager;
		
		// Set notification to be displayed
		manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent notificationIntentOne = new Intent(context,
				NotificationReceiver.class);
		notificationIntentOne.putExtra(EXTRA_NOTIF, NotificationReceiver.FIRST_NOTIF_HOUR);
		
		// Retrieve a PendingIntent that will perform a broadcast for 1st NOTIF
		notifPendingIntent = PendingIntent.getBroadcast(context, 0,
				notificationIntentOne, 0);
		
		// set first time for mood entry alarm
		Calendar moodentryOne = Calendar.getInstance();
		moodentryOne.set(Calendar.HOUR_OF_DAY, NotificationReceiver.FIRST_NOTIF_HOUR);
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
		Intent notificationIntentTwo = new Intent(context,
				NotificationReceiver.class);
		notificationIntentTwo.putExtra(EXTRA_NOTIF, NotificationReceiver.SECOND_NOTIF_HOUR);
		notifPendingIntent = PendingIntent.getBroadcast(context, 1,
				notificationIntentTwo, 0);
		
		// set second time for mood entry alarm
		Calendar moodentryTwo = Calendar.getInstance();
		moodentryTwo.set(Calendar.HOUR_OF_DAY, NotificationReceiver.SECOND_NOTIF_HOUR);
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
		boolean isWallpaperAlarmSet = (PendingIntent.getBroadcast(context, 0,
				new Intent(context, WallpaperReceiver.class),
				PendingIntent.FLAG_NO_CREATE) != null);
		
		// only change wallpaper if the wallpaperIntent has not been set
		if (!isWallpaperAlarmSet) {
			Intent wallpaperIntent = new Intent(context, WallpaperReceiver.class);
			wallPendingIntent = PendingIntent.getBroadcast(context, 0,
					wallpaperIntent, 0);

			// how often the wallpaper will update (millis)
			int wallInterval = 1000 * 60 * 60 * 2; // 1sec -> 1min -> 1hr -> 2hr

			// set wallpaper to repeat
			manager.setRepeating(AlarmManager.RTC_WAKEUP,
					System.currentTimeMillis(), wallInterval, wallPendingIntent);
		}
	}

}
