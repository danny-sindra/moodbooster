package com.moodbooster;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.Toast;


public class NotificationReceiver extends BroadcastReceiver
{
	
	@Override
    public void onReceive(Context arg0, Intent arg1) {

	    	Notification noti = new Notification.Builder(arg0)
	    	.setContentTitle("moodbooster reminder")
	         .setContentText("time to enter your mood")
	         .setSmallIcon(R.drawable.cornell_s)
	         .setLargeIcon(BitmapFactory.decodeResource(arg0.getResources(),
                     R.drawable.cornell_s))
	         .build();
	    	
	    	
	    	NotificationManager mNotificationManager =
	    		    (NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);
	    	
	    	mNotificationManager.notify(0, noti); 

        
    }   //end onreceive

	
}   //end AlarmReceiver class