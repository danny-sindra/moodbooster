package com.moodbooster;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class NotificationReceiver extends BroadcastReceiver
{
	private PendingIntent pamPendingIntent;
	
	@Override
    public void onReceive(Context arg0, Intent arg1) {
		
		Intent pamIntent = new Intent(arg0, com.moodbooster.pam.PamActivity.class);
		pamPendingIntent = PendingIntent.getActivity(arg0, 0, pamIntent, 0);
				
		
		//set attributes for notification
		Notification noti = new Notification.Builder(arg0)
	    	.setContentTitle("moodbooster reminder")
	         .setContentText("time to enter your mood")
	         .setSmallIcon(R.drawable.moodbooster)
	         .setLargeIcon(BitmapFactory.decodeResource(arg0.getResources(),
                     R.drawable.moodbooster))
             .setContentIntent(pamPendingIntent)
             .setAutoCancel(true)
	         .build();
		
		//build notification manager	    	
	    NotificationManager mNotificationManager =
	    		    (NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);
	    
	    //issue notification
	    mNotificationManager.notify(0, noti);
	    
        
    }   //end onreceive

	
}   //end AlarmReceiver class