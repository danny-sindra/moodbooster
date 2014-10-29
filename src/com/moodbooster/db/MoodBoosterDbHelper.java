package com.moodbooster.db;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.moodbooster.db.MoodBoosterContract.MoodBoosterEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import au.com.bytecode.opencsv.CSVWriter;

public class MoodBoosterDbHelper extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    public static final String APP_FOLDER_NAME = "MoodBooster";

    public MoodBoosterDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MoodBoosterContract.MoodBoosterEntry.SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(MoodBoosterContract.MoodBoosterEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    
    /**
     * Drop the table, delete all records
     * @param dbHelper
     */
    public static void dropTable(MoodBoosterDbHelper dbHelper) {
    	SQLiteDatabase db = dbHelper.getReadableDatabase();
    	db.execSQL("delete from "+ MoodBoosterEntry.TABLE_NAME);
    }
    
    /**
     * Insert new record with given data to table
     * @param dbHelper
     * @param wallpaper_id
     * @param wallpaper_category
     * @param pam_position
     * @param total_unlock
     * @return
     */
    public static long insertNewRecord(MoodBoosterDbHelper dbHelper, int wallpaper_id, String wallpaper_category, int pam_position, int total_unlock) {
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//	Create  a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(MoodBoosterEntry.COLUMN_NAME_WALLPAPER_ID, wallpaper_id);
		values.put(MoodBoosterEntry.COLUMN_NAME_WALLPAPER_CATEGORY, wallpaper_category);
		values.put(MoodBoosterEntry.COLUMN_NAME_PAM_SCORE, pam_position);
		values.put(MoodBoosterEntry.COLUMN_NAME_TOTAL_UNLOCK_SCREEN, total_unlock);
		values.put(MoodBoosterEntry.COLUMN_NAME_TIMESTAMP, System.currentTimeMillis());
		
		// Insert the new row, returning the primary key value of the new row
		long newRowId = db.insert(
		         MoodBoosterEntry.TABLE_NAME,
		         null,
		         values);		
		return newRowId;
    }
    
    /**
     * Insert a dummy record to table
     * @param dbHelper
     */
    public static void insertDummyRecord(MoodBoosterDbHelper dbHelper) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//	Create  a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(MoodBoosterEntry.COLUMN_NAME_WALLPAPER_ID, 123);
		values.put(MoodBoosterEntry.COLUMN_NAME_WALLPAPER_CATEGORY, "Animals");
		values.put(MoodBoosterEntry.COLUMN_NAME_PAM_SCORE, 3);
		values.put(MoodBoosterEntry.COLUMN_NAME_TOTAL_UNLOCK_SCREEN, 1);
		values.put(MoodBoosterEntry.COLUMN_NAME_TIMESTAMP, System.currentTimeMillis());
		
		// Insert the new row, returning the primary key value of the new row
		long newRowId = db.insert(
		         MoodBoosterEntry.TABLE_NAME,
		         null,
		         values);
		Log.d("SQLite READ", "New row id: " + newRowId);
    }
    
    /**
     * Print the first record data from table to LogCat
     * @param dbHelper
     */
    public static void printFirstRecordToLogcat(MoodBoosterDbHelper dbHelper) {
		// Read record in SQLite database
		// 	Gets the data repository in read mode
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		// 	Define a projection that specifies which columns from the database
		// 		you will actually use after this query.
		String[] projection = {
			    MoodBoosterEntry._ID,
			    MoodBoosterEntry.COLUMN_NAME_WALLPAPER_ID,
			    MoodBoosterEntry.COLUMN_NAME_WALLPAPER_CATEGORY,
			    MoodBoosterEntry.COLUMN_NAME_PAM_SCORE,
			    MoodBoosterEntry.COLUMN_NAME_TOTAL_UNLOCK_SCREEN,
			    MoodBoosterEntry.COLUMN_NAME_TIMESTAMP
		    };

		// 	How you want the results sorted in the resulting Cursor
//		String sortOrder =
//				MoodBoosterEntry.COLUMN_NAME_X + " DESC";

		Cursor cursor = db.query(
				MoodBoosterEntry.TABLE_NAME,  // The table to query
			    projection,                               // The columns to return
			    "",                                // The columns for the WHERE clause
			    null,                            // The values for the WHERE clause
			    "",                                     // don't group the rows
			    "",                                     // don't filter by row groups
			    ""                                 // The sort order
		    );
		
		// Display the first record's data
		cursor.moveToFirst();
		
		// Check projection by displaying all retrieved columns' names
		String[] colNames = cursor.getColumnNames();
		int i = 0;
		while (i<colNames.length) {
			Log.d("Result", "TIMESTAMP" + colNames[i]);
			i++;
		}
		
		// Check retrieved record's value per column
		int record_id = cursor.getInt(
				cursor.getColumnIndex(MoodBoosterEntry._ID)
			);
		int wallpaper_id = cursor.getInt(
				cursor.getColumnIndex(MoodBoosterEntry.COLUMN_NAME_WALLPAPER_ID)
			);
		String wallpaper_category = cursor.getString(
				cursor.getColumnIndex(MoodBoosterEntry.COLUMN_NAME_WALLPAPER_CATEGORY)
			);
		int pam_score = cursor.getInt(
				cursor.getColumnIndex(MoodBoosterEntry.COLUMN_NAME_PAM_SCORE)
			);
		int total_unlock = cursor.getInt(
				cursor.getColumnIndex(MoodBoosterEntry.COLUMN_NAME_TOTAL_UNLOCK_SCREEN)
			);
		long timestamp = cursor.getLong(
					cursor.getColumnIndex(MoodBoosterEntry.COLUMN_NAME_TIMESTAMP)
				);
		
		// Print to LogCat
		Log.d("SQLite READ", "_ID: " + record_id);
		Log.d("SQLite READ", "WALLPAPER_ID: " + wallpaper_id);
		Log.d("SQLite READ", "WALLPAPER_CATEGORY: " + wallpaper_category);
		Log.d("SQLite READ", "PAM SCORE: " + pam_score);
		Log.d("SQLite READ", "TOTAL UNLOCK: " + total_unlock);
		Log.d("SQLite READ", "TIMESTAMP: " + timestamp);
	}
    
    /**
     * Export the table's data into a CSV file in phone's storage card
     * @param outFileName
     * @return
     */
    public static boolean exportDbToCSV(MoodBoosterDbHelper dbHelper, String userId) {
    	CSVWriter writer = null;
		try 
		{
			// Check if app's folder already created
			File dir = new File(Environment.getExternalStorageDirectory() + "/" + APP_FOLDER_NAME);
			if(!dir.exists() || !dir.isDirectory()) {
				File newDirectory = new File(Environment
		                .getExternalStorageDirectory() + "/" + APP_FOLDER_NAME);
				newDirectory.mkdir();
			}
			
			long createdTimestamp = System.currentTimeMillis();
		    writer = new CSVWriter(new FileWriter(
			    		Environment.getExternalStorageDirectory() + 
			    		"/" + 
	    				APP_FOLDER_NAME + 
	    				"/" + 
	    				userId + 
	    				"_" + 
	    				createdTimestamp + 
	    				".csv"),
    				',');
		    Log.d("SQLite EXPORT", "External storage directory: " + Environment.getExternalStorageDirectory());
		    
		    // Read record in SQLite database
			// 	Gets the data repository in read mode
			SQLiteDatabase db = dbHelper.getReadableDatabase();

			// 	Define a projection that specifies which columns from the database
			// 		you will actually use after this query.
			String[] projection = {
				    MoodBoosterEntry._ID,
				    MoodBoosterEntry.COLUMN_NAME_WALLPAPER_ID,
				    MoodBoosterEntry.COLUMN_NAME_WALLPAPER_CATEGORY,
				    MoodBoosterEntry.COLUMN_NAME_PAM_SCORE,
				    MoodBoosterEntry.COLUMN_NAME_TOTAL_UNLOCK_SCREEN,
				    MoodBoosterEntry.COLUMN_NAME_TIMESTAMP
			    };

			// 	How you want the results sorted in the resulting Cursor
			String sortOrder =
					MoodBoosterEntry.COLUMN_NAME_TIMESTAMP + " ASC";

			Cursor cursor = db.query(
					MoodBoosterEntry.TABLE_NAME,  // The table to query
				    projection,                               // The columns to return
				    "",                                // The columns for the WHERE clause
				    null,                            // The values for the WHERE clause
				    "",                                     // don't group the rows
				    "",                                     // don't filter by row groups
				    sortOrder                                 // The sort order
			    );
			
			// Write the CSV header
		    String colNames[] = cursor.getColumnNames();
		    writer.writeNext(colNames);
			
		    // Write the records
		    cursor.moveToFirst();
		    while ( cursor.isAfterLast() == false ) {
		    	String record_id = cursor.getString(
						cursor.getColumnIndex(MoodBoosterEntry._ID)
					);
				String wallpaper_id = cursor.getString(
						cursor.getColumnIndex(MoodBoosterEntry.COLUMN_NAME_WALLPAPER_ID)
					);
				String wallpaper_category = cursor.getString(
						cursor.getColumnIndex(MoodBoosterEntry.COLUMN_NAME_WALLPAPER_CATEGORY)
					);
				String pam_score = cursor.getString(
						cursor.getColumnIndex(MoodBoosterEntry.COLUMN_NAME_PAM_SCORE)
					);
				String total_unlock = cursor.getString(
						cursor.getColumnIndex(MoodBoosterEntry.COLUMN_NAME_TOTAL_UNLOCK_SCREEN)
					);
				String timestamp = cursor.getString(
							cursor.getColumnIndex(MoodBoosterEntry.COLUMN_NAME_TIMESTAMP)
						);
		    	String[] record = {record_id, wallpaper_id, wallpaper_category, pam_score, total_unlock, timestamp};
		    	writer.writeNext(record);
		    	cursor.moveToNext();
		    }
		    writer.close();
		} 
		catch (IOException e)
		{
		    e.printStackTrace();
		    return false;
		}
		return true;
	}
}
