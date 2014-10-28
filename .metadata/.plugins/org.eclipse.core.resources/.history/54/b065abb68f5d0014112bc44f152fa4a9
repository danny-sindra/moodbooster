package com.moodbooster.testbedapp;

import java.io.IOException;

import edu.cornell.pam.PamActivity;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class WallpaperReceiver extends BroadcastReceiver
{
	@Override
    public void onReceive(Context arg0, Intent arg1) {
        // For our recurring task, we'll just display a message
        //Toast.makeText(arg0, "rollback", Toast.LENGTH_SHORT).show();
        
		WallpaperManager myWallpaperManager 
	    = WallpaperManager.getInstance(arg0);
	    try
	    {
	    	//set image to wallpaper
	        myWallpaperManager.setResource(R.drawable.mlake_s);
	    }
	    catch (IOException e)
	    {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
        
    }   //end onreceive

	
}   //end AlarmReceiver class