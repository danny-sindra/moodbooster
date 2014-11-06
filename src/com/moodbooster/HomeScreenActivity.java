package com.moodbooster;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import java.io.File;

import com.moodbooster.db.MoodBoosterDbHelper;
import com.moodbooster.pam.PamActivity;

public class HomeScreenActivity extends Activity {

	public final static String PREFS_NAME = "MOODBOOSTER_PREFS";
	private final static String EMAIL_RECEIVER = "als478@cornell.edu";

	private Button enterMoodButton;
	private TextView homeTitle;
	private int count = 0;
	private long startMillis = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		// Add listener to buttons
		addPamButtonListener();
		addEmailButtonListener();

		// Notify BootReceiver to start Wallpaper and Notif Receivers
		Intent intent = new Intent("com.moodbooster.HomeScreenActivity");
		sendBroadcast(intent);
	}

	public void addPamButtonListener() {
		// define button
		enterMoodButton = (Button) findViewById(R.id.enterMoodButton);

		// switch to pam when button is pressed
		enterMoodButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// move to next screen
				Intent testBed = new Intent(v.getContext(), PamActivity.class);
				startActivity(testBed);

			} // end onClick
		}); // end setOnClickListener
	} // end addButtonListener

	
	/**
	 * To attach and send data e-mail, click Title 10 times in 3 seconds
	 */
	private void addEmailButtonListener() {
		// define entity
		homeTitle = (TextView) findViewById(R.id.homeName);

		// set listener when button is pressed
		homeTitle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// get system current milliseconds
				long time = System.currentTimeMillis();
				// if it is the first time, or if it has been more than 3
				// seconds since the first tap ( so it is like a new try), we
				// reset everything
				if (startMillis == 0 || (time - startMillis > 2000)) {
					startMillis = time;
					count = 1;
				}
				// it is not the first, and it has been less than 3 seconds
				// since the first
				else { // time-startMillis< 2000
					count++;
				}

				// compose e-mail if user manage to click 5x in 2 seconds
				if (count == 5) {
					File file = new File(MoodBoosterDbHelper
							.getLatestLogFilePath());

					Intent emailIntent = new Intent(
							android.content.Intent.ACTION_SEND);
					emailIntent.setType("message/rfc822");
					emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
							new String[] { EMAIL_RECEIVER });
					emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
							"MoodBooster usage data");
					emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
							"Usage data");
					Log.d(getClass().getSimpleName(),
							"fileURI=" + Uri.fromFile(file));
					emailIntent.putExtra(Intent.EXTRA_STREAM,
							Uri.fromFile(file));
					
					startActivity(Intent.createChooser(emailIntent,
							"Send mail with:"));
				}
			}
		});
	}

} // end class