package com.moodbooster;

<<<<<<< HEAD
import java.io.File;
=======
import java.io.FilterInputStream;
>>>>>>> f3e00e6ddb38ba43a2b9fbf0ad81d67e429be9bc
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

<<<<<<< HEAD
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

=======
>>>>>>> f3e00e6ddb38ba43a2b9fbf0ad81d67e429be9bc
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.WindowManager;
<<<<<<< HEAD
import android.view.View;;
=======
>>>>>>> f3e00e6ddb38ba43a2b9fbf0ad81d67e429be9bc

public class WallpaperReceiver extends BroadcastReceiver {

	public static final String PREFS_WALLPAPER_ID = "WALLPAPER_ID";
	public static final int PREFS_WALLPAPER_ID_DEFAULTVAL = 1;
	public static final String PREFS_WALLPAPER_CATEGORY = "WALLPAPER_CATEGORY";
	public static final String PREFS_WALLPAPER_CATEGORY_DEFAULTVAL = "animal";

	int currentWallpaperID;
	String currentWallpaperCategory;
	static ImageLoader imageLoader;
	
	@Override
<<<<<<< HEAD
	public void onReceive(Context arg0, Intent arg1) {
		final WallpaperManager myWallpaperManager = WallpaperManager
				.getInstance(arg0);
		
		// Create global configuration and initialize ImageLoader
		if (imageLoader==null) {
			File cacheDir = StorageUtils.getCacheDirectory(arg0);
	        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(arg0.getApplicationContext())
	        	.denyCacheImageMultipleSizesInMemory()
	        	.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
	        	.diskCache(new UnlimitedDiscCache(cacheDir))
	        	.build();
	        imageLoader = ImageLoader.getInstance();
	        imageLoader.init(config);
		}
		
		// Change the wallpaper
		try {
			String imageUri = "assets://" + getHappyImage();
			imageLoader.loadImage(imageUri, new SimpleImageLoadingListener() {
			    @Override
			    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			    	try {
			    		myWallpaperManager.setBitmap(loadedImage);
					} catch (IOException e) {
						e.printStackTrace();
					}
			    }
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Saved the current wallpaper id & category to SharedPreferences
		SharedPreferences savedData = arg0.getSharedPreferences(HomeScreenActivity.PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = savedData.edit();
		editor.putInt(PREFS_WALLPAPER_ID, currentWallpaperID);
		editor.putString(PREFS_WALLPAPER_CATEGORY, currentWallpaperCategory);
		editor.commit();
=======
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
>>>>>>> f3e00e6ddb38ba43a2b9fbf0ad81d67e429be9bc
	
	} // end onreceive

	public Bitmap scaleBitmap(Context con, Bitmap bitmap) {
		double displayHeight;
		double displayWidth;
		double imageHeight;
		double imageWidth;
		double heightRatio;
		double widthRatio;
		double scaleRatio;
		int scaledHeight;
		int scaledWidth;

		// get height and width of display
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) con
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(metrics);
		displayHeight = metrics.heightPixels;
		displayWidth = metrics.widthPixels;
<<<<<<< HEAD
		Log.v("DISPLAY", displayHeight + " " + displayWidth);

		// get height and width of image
		imageHeight = bitmap.getHeight();
		imageWidth = bitmap.getWidth();
		Log.v("IMAGE", imageHeight + " " + imageWidth);

		// determine ratio of height and width
		heightRatio = displayHeight / imageHeight;
		widthRatio = displayWidth / imageWidth;
		Log.v("RATIO", heightRatio + " " + widthRatio);

		// compare ratios
		scaleRatio = Math.max(heightRatio, widthRatio);
		Log.v("SCALERATIO", Double.toString(scaleRatio));

		// resize image dimensions by smaller ratio
		scaledHeight = (int) (imageHeight * scaleRatio);
		scaledWidth = (int) (imageWidth * scaleRatio);
		Log.v("SCALED", scaledHeight + " " + scaledWidth);

		// scale the given bitmap
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth,
				scaledHeight, false);

=======
		
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
		
>>>>>>> f3e00e6ddb38ba43a2b9fbf0ad81d67e429be9bc
		return scaledBitmap;
	}

	public Bitmap getBitmapFromAsset(Context arg0, String strName)
			throws IOException {
		AssetManager assetManager = arg0.getAssets();
		
		//get asset and convert to bitmap
		InputStream istr = assetManager.open(strName);
<<<<<<< HEAD
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inPreferredConfig = Config.RGB_565;
		Bitmap bitmap = BitmapFactory.decodeStream(istr, null, options);
=======
		Bitmap bitmap = BitmapFactory.decodeStream(new FlushedInputStream(istr));
>>>>>>> f3e00e6ddb38ba43a2b9fbf0ad81d67e429be9bc

		return bitmap;
	}

	public String getHappyImage() {
		Random random = new Random();
		int choice = 0;
		String happyDir = "happy_images/";
		String happyPath = "";
<<<<<<< HEAD

=======
		
		//get random image
>>>>>>> f3e00e6ddb38ba43a2b9fbf0ad81d67e429be9bc
		choice = random.nextInt(19) + 1;
		happyPath = happyDir + Integer.toString(choice) + ".jpg";
		
		// Save the current wallpaper info
		currentWallpaperID = choice;
		currentWallpaperCategory = getHappyImageCategory(choice);

		return happyPath;
	}
	
	/**
	 * Get the image's category based on the ID
	 * @param wallpaper_id
	 * @return
	 */
	public String getHappyImageCategory(int wallpaper_id) {
		switch (wallpaper_id) {
			case 1:
				return "animal";
			default:
				return "none";
		}
	}

<<<<<<< HEAD
} // end AlarmReceiver class
=======
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
>>>>>>> f3e00e6ddb38ba43a2b9fbf0ad81d67e429be9bc
