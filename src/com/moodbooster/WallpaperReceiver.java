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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
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
	    	Bitmap bitmapToDisplay = scaleBitmap(arg0, getBitmapFromAsset(arg0, getHappyImage()));
	    	
	    	//set scaled bitmap as wallpaper
	        myWallpaperManager.setBitmap(bitmapToDisplay);
	    }
	    catch (IOException e)
	    {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
        
	    
    }   //end onreceive
	
	public Bitmap scaleBitmap (Context con, Bitmap bitmap)
	{
		double displayHeight;
		double displayWidth;
		double imageHeight;
		double imageWidth;
		double heightRatio;
		double widthRatio;
		double scaleRatio;
		int scaledHeight;
		int scaledWidth;
		
		//get height and width of display
		DisplayMetrics metrics = new DisplayMetrics ();
		WindowManager windowManager = (WindowManager)con.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(metrics);
		displayHeight = metrics.heightPixels;
		displayWidth = metrics.widthPixels;
        Log.v("DISPLAY", displayHeight + " " + displayWidth);
		
        //get height and width of image
        imageHeight = bitmap.getHeight();
        imageWidth = bitmap.getWidth();
        Log.v("IMAGE", imageHeight + " " + imageWidth);
        
        //determine ratio of height and width
        heightRatio = displayHeight / imageHeight;
        widthRatio = displayWidth / imageWidth;
        Log.v("RATIO", heightRatio + " " + widthRatio);
        
    	//compare ratios
    	scaleRatio = Math.max(heightRatio, widthRatio);
        Log.v("SCALERATIO", Double.toString(scaleRatio));
        
        //resize image dimensions by smaller ratio
        scaledHeight = (int) (imageHeight * scaleRatio);
        scaledWidth = (int) (imageWidth * scaleRatio);
        Log.v("SCALED", scaledHeight + " " + scaledWidth);
        
		//scale the given bitmap
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
		
		return scaledBitmap;
	}
	
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
		Log.v("CHOICE", Integer.toString(choice));
		happyPath = happyDir + Integer.toString(choice) + ".jpg";
		
		return happyPath;
		
	}
	

	
}   //end AlarmReceiver class