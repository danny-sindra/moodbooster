package com.moodbooster;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

public class NotificationReceiver extends BroadcastReceiver {
	
	public final static int FIRST_NOTIF_HOUR = 14;
	public final static int SECOND_NOTIF_HOUR = 20;
	
	private PendingIntent pamPendingIntent;

	@Override
	public void onReceive(Context arg0, Intent arg1) {

		Intent pamIntent = new Intent(arg0,
				com.moodbooster.pam.PamActivity.class);
		pamPendingIntent = PendingIntent.getActivity(arg0, 0, pamIntent, 0);

		// set attributes for notification
		int notifTime = arg1.getIntExtra(BootReceiver.EXTRA_NOTIF, 0);
		String notifTicker = getTimelyMessage(notifTime);
		String notifMessage = "Time to enter your mood";
		Notification noti = new Notification.Builder(arg0)
				.setContentTitle(
						arg0.getResources().getString(R.string.app_name)
								+ " reminder")
				.setContentText(notifMessage)
				.setTicker(notifTicker)
				.setSmallIcon(R.drawable.moodbooster)
				.setLargeIcon(
						BitmapFactory.decodeResource(arg0.getResources(),
								R.drawable.moodbooster))
				.setContentIntent(pamPendingIntent).setAutoCancel(true).build();

		// build notification manager
		NotificationManager mNotificationManager = (NotificationManager) arg0
				.getSystemService(Context.NOTIFICATION_SERVICE);

		// issue notification
		mNotificationManager.notify(0, noti);

	} // end onreceive

	/**
	 * Customize the Ticker message based on notification time
	 * @param notifTime
	 * @return
	 */
	private String getTimelyMessage(int notifTime) {
		if (notifTime==FIRST_NOTIF_HOUR) {
			return "Howdy! it is already " + FIRST_NOTIF_HOUR + ":00. Let us know how do you feel today!";
		}
		else if (notifTime==SECOND_NOTIF_HOUR) {
			return "Good evening, it is " + SECOND_NOTIF_HOUR + ":00 now! Time to enter your mood";
		}
		else {
			return "How do you feel today? ";
		}
	}
	
} // end AlarmReceiver class