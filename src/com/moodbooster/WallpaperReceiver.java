package com.moodbooster;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.WindowManager;

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
		
        //get height and width of image
        imageHeight = bitmap.getHeight();
        imageWidth = bitmap.getWidth();
        
        //determine ratio of height and width
        heightRatio = displayHeight / imageHeight;
        widthRatio = displayWidth / imageWidth;
        
    	//compare ratios
    	scaleRatio = Math.max(heightRatio, widthRatio);
        
        //resize image dimensions by smaller ratio
        scaledHeight = (int) (imageHeight * scaleRatio);
        scaledWidth = (int) (imageWidth * scaleRatio);
        
		//scale the given bitmap
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
		
		return scaledBitmap;
	}
	
	public Bitmap getBitmapFromAsset(Context arg0, String strName)
			throws IOException {
		AssetManager assetManager = arg0.getAssets();
		
		//get asset and convert to bitmap
		InputStream istr = assetManager.open(strName);
		Bitmap bitmap = BitmapFactory.decodeStream(new FlushedInputStream(istr));

		return bitmap;
		
	}
	
	public String getHappyImage()
	{
		Random random = new Random();
		int choice = 0;
		String happyDir = "happy_images/";
		String happyPath = "";
		
		//get random image
		choice = random.nextInt(19) + 1;
		happyPath = happyDir + Integer.toString(choice) + ".jpg";
		
		return happyPath;
		
	}
	

	static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }   //end flushedinputstream
	
}   //end AlarmReceiver class