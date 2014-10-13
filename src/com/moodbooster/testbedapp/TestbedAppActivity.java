package com.moodbooster.testbedapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.WallpaperManager;

import java.io.IOException;
import java.util.Calendar;


public class TestbedAppActivity extends Activity {
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
	    	((TextView)findViewById(R.id.label)).setText("new contender");
	    }
	    

    }   //end oncreate
    
}   //end class