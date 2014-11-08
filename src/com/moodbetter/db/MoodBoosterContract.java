package com.moodbetter.db;

import android.provider.BaseColumns;

public class MoodBoosterContract {
	// To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public MoodBoosterContract() {}

    /* Inner class that defines the table contents */
    public static abstract class MoodBoosterEntry implements BaseColumns {
        public static final String TABLE_NAME = "mood_better";
        public static final String COLUMN_NAME_ROW_ID = "row_id";
        public static final String COLUMN_NAME_WALLPAPER_ID = "wallpaper_id";
        public static final String COLUMN_NAME_WALLPAPER_CATEGORY = "wallpaper_category";
        public static final String COLUMN_NAME_PAM_SCORE = "pam_score";
        public static final String COLUMN_NAME_TOTAL_UNLOCK_SCREEN = "total_unlock_screen";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        
        /**
         * CREATE & DROP DATABASE
         */
        private static final String TEXT_TYPE = " TEXT";
        private static final String INTEGER_TYPE = " INTEGER";
        private static final String COMMA_SEP = ",";
        
        public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MoodBoosterEntry.TABLE_NAME + " (" +
            MoodBoosterEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MoodBoosterEntry.COLUMN_NAME_WALLPAPER_ID + INTEGER_TYPE + COMMA_SEP +
            MoodBoosterEntry.COLUMN_NAME_WALLPAPER_CATEGORY + TEXT_TYPE + COMMA_SEP +
            MoodBoosterEntry.COLUMN_NAME_PAM_SCORE + INTEGER_TYPE + COMMA_SEP +
            MoodBoosterEntry.COLUMN_NAME_TOTAL_UNLOCK_SCREEN + INTEGER_TYPE + COMMA_SEP +
            MoodBoosterEntry.COLUMN_NAME_TIMESTAMP + INTEGER_TYPE +
            " )";

        public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MoodBoosterEntry.TABLE_NAME;
        
        
    }
    
}
