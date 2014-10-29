package com.moodbooster.testbedapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.util.Calendar;

import edu.cornell.pam.PamActivity;


public class TestbedAppActivity extends Activity {
    public TestbedAppActivity()
    {
    	
    }
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
	    
        
        
        
        //***CHANGE WALLPAPER***
        
        
	    //create button and associate with layout xml definition
	    Button changeButton = (Button)findViewById(R.id.change_button);
	    
	    //tie event handler to button	
	    changeButton.setOnClickListener(new Button.OnClickListener()
	    {
	    	
		    public void onClick(View arg0)
		    {
			    // TODO Auto-generated method stub
			    WallpaperManager myWallpaperManager 
			    = WallpaperManager.getInstance(getApplicationContext());
			    try
			    {
			    	//set image to wallpaper
			        myWallpaperManager.setResource(R.drawable.cornell_s);
			    }
			    catch (IOException e)
			    {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    }
		   	}   //end onclick
	    });   //end event handler
	    
        
	    
	    
	    
	    //***GET TIME***
	    
	    Calendar currentTime = Calendar.getInstance();
	    int currentMinute = currentTime.get(Calendar.MINUTE);
	    
	    if (currentMinute % 5 == 0)
	    {
	    	//set label to current minute
	    	((TextView)findViewById(R.id.label)).setText(Integer.toString(currentMinute));
	    }
	    else
	    {
	    	//set label to no-go message
	    	((TextView)findViewById(R.id.label)).setText("353");
	    }
	    
	    setAlarm(this);
	    

    }   //end oncreate
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }
    
    public void setAlarm(Context context)
    {
    	AlarmManager alarmMgr;
    	PendingIntent alarmIntent;
    	
    	alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, PamActivity.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        
        // Set the alarm to start at approximately 2:00 p.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 42);
        
        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
             AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_pam:
                openPAM();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * Open the PAM activity
     */
    public void openPAM() {
    	Intent intent = new Intent(this, PamActivity.class);
        startActivity(intent);
    }
    
    public void changeWallpaper()
    {
    	WallpaperManager myWallpaperManager 
	    = WallpaperManager.getInstance(getApplicationContext());
	    try
	    {
	    	//set image to wallpaper
	        myWallpaperManager.setResource(R.drawable.cornell_s);
	    }
	    catch (IOException e)
	    {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
    }
    
}   //end class