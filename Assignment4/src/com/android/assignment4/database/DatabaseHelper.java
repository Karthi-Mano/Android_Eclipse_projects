package com.android.assignment4.database;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "blog";

	// Table Names
	private static final String TABLE_BLOG = "blog_data";

	// Common column names
	private static final String KEY_ID = "columns_id";
	private static final String KEY_BLOG = "blog";
	private static final String KEY_TIMESTAMP = "timestamp";

	private static final String CREATE_TABLE_BLOG = "CREATE TABLE "
			+ TABLE_BLOG + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_BLOG
			+ " TEXT," + KEY_TIMESTAMP + " TEXT" + ")";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_BLOG);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOG);

		// create new tables
		onCreate(db);
	}

	public void insertBlog(String blog) {

		SQLiteDatabase db = this.getReadableDatabase();
		Log.e("", "");
		final ContentValues values = new ContentValues();

		values.put(KEY_BLOG, blog);

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy-hh:mm a");
		// Ouput "Wed Sep 26 14:23:28 EST 2012"

		String formatted = format1.format(cal.getTime());

		values.put(KEY_TIMESTAMP, formatted);
		db.insert(TABLE_BLOG, null, values);

		if (db != null)
			db.close();

	}

	public String[][] getBlog() {
		String[][] data = null;
		SQLiteDatabase db = this.getReadableDatabase();
		final Cursor curr = db.query(TABLE_BLOG, new String[] { "*" }, null,
				null, null, null, null);

		if (curr != null && curr.getCount() > 0) {

			data = new String[curr.getCount()][2];
			curr.moveToFirst();
			for (int i = 0; i < curr.getCount(); i++) {

				data[i][0] = curr.getString(curr.getColumnIndex(KEY_BLOG));
				data[i][1] = curr.getString(curr.getColumnIndex(KEY_TIMESTAMP));
				curr.moveToNext();
			}
		}
		if (db != null)
			db.close();

		if (curr != null) {
			curr.close();
		}
		return data;

	}

}
