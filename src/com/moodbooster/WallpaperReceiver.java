package com.moodbooster;

import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

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
import android.util.Log;
import android.view.WindowManager;
import android.view.View;;

public class WallpaperReceiver extends BroadcastReceiver {

	public static final String PREFS_WALLPAPER_ID = "WALLPAPER_ID";
	public static final int PREFS_WALLPAPER_ID_DEFAULTVAL = 1;
	public static final String PREFS_WALLPAPER_CATEGORY = "WALLPAPER_CATEGORY";
	public static final String PREFS_WALLPAPER_CATEGORY_DEFAULTVAL = "animal";

	int currentWallpaperID;
	String currentWallpaperCategory;
	static ImageLoader imageLoader;
	
	@Override
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
		
		return scaledBitmap;
	}

	public Bitmap getBitmapFromAsset(Context arg0, String strName)
			throws IOException {
		AssetManager assetManager = arg0.getAssets();
		
		//get asset and convert to bitmap
		InputStream istr = assetManager.open(strName);

		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inPreferredConfig = Config.RGB_565;
		Bitmap bitmap = BitmapFactory.decodeStream(istr, null, options);

		return bitmap;
	}

	public String getHappyImage() {
		Random random = new Random();
		int choice = 0;
		String happyDir = "happy_images/";
		String happyPath = "";
		
		//get random image
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

} // end WallapaperReceiver class