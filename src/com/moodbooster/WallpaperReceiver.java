package com.moodbooster;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import com.moodbooster.pam.PamActivity;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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
	        //myWallpaperManager.setResource(R.drawable.cornell_s);
	    	//myWallpaperManager.suggestDesiredDimensions(1080, 1920);
	        myWallpaperManager.setBitmap(getBitmapFromAsset(arg0, getHappyImage()));
	    }
	    catch (IOException e)
	    {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
        
	    
    }   //end onreceive
	
	public Bitmap getBitmapFromAsset(Context arg0, String strName)
			throws IOException {
		AssetManager assetManager = arg0.getAssets();

		InputStream istr = assetManager.open(strName);
		Bitmap bitmap = BitmapFactory.decodeStream(istr);

		return bitmap;
		
	}
	
	public String getHappyImage()
	{
		Random random = new Random();
		int choice = 0;
		String happyDir = "happy_images/";
		String happyPath = "";
		
		choice = random.nextInt(19) + 1;
		happyPath = happyDir + Integer.toString(choice) + ".jpg";
		
		return happyPath;
		
	}
	

	
}   //end AlarmReceiver class