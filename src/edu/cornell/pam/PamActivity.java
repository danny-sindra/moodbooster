package edu.cornell.pam;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.moodbooster.db.MoodBoosterDbHelper;
import com.moodbooster.db.MoodBoosterContract.MoodBoosterEntry;
import com.moodbooster.testbedapp.R;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class PamActivity extends Activity {

	public static final String PAM_SELECTION = PamActivity.class.getPackage().getName() + "PAM_SELECTION";
	public static final String PAM_PHOTO_ID = PamActivity.class.getPackage().getName() + "PAM_PHOTO_ID";
	
	private String[] filenames;
	private Random random= new Random();
	private Button reload;
	private GridView gridview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pam);
		
		gridview = (GridView) findViewById(R.id.pam_grid);
		reload= (Button) findViewById(R.id.pam_reload);
		reload.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				setupPAM();
			}

		});
		
		// Button export to storage card
		Button button_export = (Button) findViewById(R.id.pam_export);
        button_export.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
            	exportUsageData("userId");
            }
        });

		// set up pam
		setupPAM();
	}

	/** setup PAM */
	private void setupPAM() {
		try {
			String[] files= {
					"pam_images/1_afraid/" + this.getResources().getAssets().list("pam_images/1_afraid")[random.nextInt(3)],
					"pam_images/2_tense/" + this.getResources().getAssets().list("pam_images/2_tense")[random.nextInt(3)],
					"pam_images/3_excited/" + this.getResources().getAssets().list("pam_images/3_excited")[random.nextInt(3)],
					"pam_images/4_delighted/" + this.getResources().getAssets().list("pam_images/4_delighted")[random.nextInt(3)],
					"pam_images/5_frustrated/" + this.getResources().getAssets().list("pam_images/5_frustrated")[random.nextInt(3)],
					"pam_images/6_angry/" + this.getResources().getAssets().list("pam_images/6_angry")[random.nextInt(3)],
					"pam_images/7_happy/" + this.getResources().getAssets().list("pam_images/7_happy")[random.nextInt(3)],
					"pam_images/8_glad/" + this.getResources().getAssets().list("pam_images/8_glad")[random.nextInt(3)],
					"pam_images/9_miserable/" + this.getResources().getAssets().list("pam_images/9_miserable")[random.nextInt(3)],
					"pam_images/10_sad/" + this.getResources().getAssets().list("pam_images/10_sad")[random.nextInt(3)],
					"pam_images/11_calm/" + this.getResources().getAssets().list("pam_images/11_calm")[random.nextInt(3)],
					"pam_images/12_satisfied/" + this.getResources().getAssets().list("pam_images/12_satisfied")[random.nextInt(3)],
					"pam_images/13_gloomy/" + this.getResources().getAssets().list("pam_images/13_gloomy")[random.nextInt(3)],
					"pam_images/14_tired/" + this.getResources().getAssets().list("pam_images/14_tired")[random.nextInt(3)],
					"pam_images/15_sleepy/" + this.getResources().getAssets().list("pam_images/15_sleepy")[random.nextInt(3)],
					"pam_images/16_serene/" + this.getResources().getAssets().list("pam_images/16_serene")[random.nextInt(3)]
			};
			
			filenames= files;
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Start PAM 
		gridview.setAdapter(new BaseAdapter() {
			private int width= getWindowManager().getDefaultDisplay().getWidth();

			@Override
			public int getCount() {
				return filenames.length;
			}

			@Override
			public Object getItem(int arg0) {
				return null;
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@SuppressWarnings("finally")
			@Override
			public View getView(int position, View convertView,
					ViewGroup parent) {
				ImageView imageView;
				if (null == convertView) {
					imageView= new ImageView(PamActivity.this);
					imageView.setLayoutParams(new GridView.LayoutParams(width/4,width/4));
					imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				} else {
					imageView= (ImageView) convertView;
				}
				try {
					imageView.setImageBitmap(getBitmapFromAsset(filenames[position]));
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					return imageView;
				}
			}

			private Bitmap getBitmapFromAsset(String strName) throws IOException
			{
				AssetManager assetManager = getAssets();

				InputStream istr = assetManager.open(strName);
				Bitmap bitmap = BitmapFactory.decodeStream(istr);

				return bitmap;
			}

		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Intent result = new Intent();
				int pam_photo_id = 0;
				Pattern p= Pattern.compile("\\d+_\\d+");
				Matcher matcher= p.matcher(filenames[position]);
				if (matcher.find()) {
					String sub= matcher.group();
					String[] parts= sub.split("_");
					pam_photo_id= (Integer.parseInt(parts[0])-1)*3 + (Integer.parseInt(parts[1]));
				}
				
				result.putExtra(PAM_SELECTION, position);
				result.putExtra(PAM_PHOTO_ID, pam_photo_id);
				setResult(Activity.RESULT_OK, result);
				
				// Put new record to SQLite database
				MoodBoosterDbHelper dbHelper = new MoodBoosterDbHelper(getApplicationContext());
				long newRowId = MoodBoosterDbHelper.insertNewRecord(
						dbHelper, 
						12, // curr_wallpaper_id
						"Animal", // curr_wallpaper_category
						position, // pam_score
						145 // total_unlock
					);
				Log.d("Result","new row id: " + newRowId);
				
				// DEBUGGING
				//MoodBoosterDbHelper.dropTable(dbHelper); //drop table
				//MoodBoosterDbHelper.insertDummyRecord(dbHelper); //insert fake data
				//MoodBoosterDbHelper.printFirstRecordToLogcat(dbHelper); //print first record
				//MoodBoosterDbHelper.exportDbToCSV(dbHelper, "danny"); //create a CSV file in storage card
				finish();
			}
		});
	}
	
	/**
	 * Export usage data to CSV file in storage card
	 * @param userId
	 */
	public void exportUsageData(String userId) {
		MoodBoosterDbHelper dbHelper = new MoodBoosterDbHelper(getApplicationContext());
		MoodBoosterDbHelper.exportDbToCSV(dbHelper, userId);
		finish();
	}
}
