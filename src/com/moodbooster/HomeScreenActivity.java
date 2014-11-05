package com.moodbooster;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import com.moodbooster.pam.PamActivity;

public class HomeScreenActivity extends Activity {

	public static final String PREFS_NAME = "MOODBOOSTER_PREFS";

	private Button enterMoodButton;
	private PendingIntent notifPendingIntent;
	private PendingIntent wallPendingIntent;
	private AlarmManager manager;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {    	  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        addButtonListener();
        
        //set notification to be displayed
        // Retrieve a PendingIntent that will perform a broadcast
        Intent notificationIntent = new Intent(this, NotificationReceiver.class);
        notifPendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, 0);
        
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        
        //set first time for mood entry alarm
        Calendar moodentryOne = Calendar.getInstance();
        moodentryOne.setTimeInMillis(System.currentTimeMillis());
        moodentryOne.set(Calendar.HOUR_OF_DAY, 14);
        moodentryOne.set(Calendar.MINUTE, 0);
        
        //how often first notification will appear (millis)
        int notifIntervalOne = 1000 * 60 * 60 * 24;   //1sec -> 1 min -> 1hr -> 24hrs
        
        //set notification to repeat
        manager.setRepeating(AlarmManager.RTC_WAKEUP, moodentryOne.getTimeInMillis(), notifIntervalOne, notifPendingIntent);
        
        // Retrieve a PendingIntent that will perform a broadcast
        notifPendingIntent = PendingIntent.getBroadcast(this, 1, notificationIntent, 0);
        
        //set second time for mood entry alarm
        Calendar moodentryTwo = Calendar.getInstance();
        moodentryTwo.set(Calendar.HOUR_OF_DAY, 20);
        moodentryTwo.set(Calendar.MINUTE, 0);
        
        //how often first notification will appear (millis)
        int notifIntervalTwo = 1000 * 60 * 60 * 24;   //1sec -> 1 min -> 1hr -> 24hrs
        
        //set notification to repeat
        manager.setRepeating(AlarmManager.RTC_WAKEUP, moodentryTwo.getTimeInMillis(), notifIntervalTwo, notifPendingIntent);
        
        
        //set wallpaper to change
        Intent wallpaperIntent = new Intent(this, WallpaperReceiver.class);
        wallPendingIntent = PendingIntent.getBroadcast(this, 0, wallpaperIntent, 0);
        
        //how often the wallpaper will update (millis)
        int wallInterval = 1000 * 60 * 60;   //1sec -> 1min -> 1hr
        
        //set wallpaper to repeat
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), wallInterval, wallPendingIntent);
        
    }
        
    public void addButtonListener()
    {
    	//define button
    	enterMoodButton = (Button)findViewById(R.id.enterMoodButton);
    	
    	//switch to pam when button is pressed
    	enterMoodButton.setOnClickListener (new OnClickListener()
    	{
    		public void onClick(View v)
    		{		    	
		    	//move to next screen
		    	Intent testBed = new Intent(v.getContext(), PamActivity.class);
		    	startActivity(testBed);
		    	
    		}   //end onClick
    		
    	});  //end setOnClickListener
    	
    }   //end addButtonListener
    
    
    
}   //end class