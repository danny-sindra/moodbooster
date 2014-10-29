package com.moodbooster.testbedapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import edu.cornell.pam.PamActivity;


public class HomeScreenActivity extends Activity {

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
        
        //set time for mood entry alarm
        Calendar moodentryTime = Calendar.getInstance();
        moodentryTime.set(Calendar.HOUR_OF_DAY, 0);
        moodentryTime.set(Calendar.MINUTE, 19);
        
        //how often the notification will appear (millis)
        int notifInterval = 60000;
        
        //set notification to repeat
        manager.setRepeating(AlarmManager.RTC_WAKEUP, moodentryTime.getTimeInMillis(), notifInterval, notifPendingIntent);

        
        //set wallpaper to change
        Intent wallpaperIntent = new Intent(this, WallpaperReceiver.class);
        wallPendingIntent = PendingIntent.getBroadcast(this, 0, wallpaperIntent, 0);
        
        //how often the wallpaper will update (millis)
        int wallInterval = 30000;
        
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