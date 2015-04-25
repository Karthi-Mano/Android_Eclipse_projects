package com.assignment.newsapp.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.assignment.newsapp.model.NewsModel;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "newsDB";

	// Table Names
	private static final String TABLE_NEWS = "news";

	// Common column names
	private static final String KEY_ID = "columns_id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_DETAIL = "detail";
	private static final String KEY_DATE = "date";
	private static final String KEY_URL = "url";
	private static final String KEY_GUID = "guid";
	private static final String KEY_WEPAGE = "webpage";
	private static final String KEY_NEWS_TYPE = "news_type";
	public static final String NEWS_TYPE_TOP_STORIES = "top_stories";
	public static final String NEWS_TYPE_WORLD = "world";

	public static final String NEWS_TYPE_BUSINESS = "business";
	public static final String NEWS_TYPE_POLITICS = "politics";

	private static final String CREATE_TABLE_NEWS = "CREATE TABLE "
			+ TABLE_NEWS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DETAIL
			+ " TEXT," + KEY_DATE + " TEXT," + KEY_URL + " TEXT," + KEY_TITLE
			+ " TEXT," + KEY_GUID + " TEXT," + KEY_NEWS_TYPE + " TEXT,"
			+ KEY_WEPAGE + " TEXT" + ")";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_NEWS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);

		// create new tables
		onCreate(db);
	}

	public void addNewsItem(ArrayList<NewsModel> listData) {

		SQLiteDatabase db = this.getReadableDatabase();
		Log.e("", "");

		for (NewsModel data : listData) {
			final ContentValues values = new ContentValues();
			values.put(KEY_DETAIL, data.getDetail());
			values.put(KEY_DATE, data.getDate());
			values.put(KEY_TITLE, data.getTitle());
			values.put(KEY_URL, data.getImageUrl());
			values.put(KEY_GUID, data.getGuId());
			values.put(KEY_NEWS_TYPE, data.getNewsType());
			if (!isExists(data.getGuId(), db)) {
				Log.e("", "guid:" + data.getGuId());
				db.insert(TABLE_NEWS, null, values);
			}
		}

		if (db != null)
			db.close();

	}

	private boolean isExists(String guid, SQLiteDatabase db)

	{

		Cursor curr = null;

		try {
			curr = db.query(TABLE_NEWS, new String[] { "*" }, KEY_GUID + " = '"
					+ guid + "'", null, null, null, null);

			if (curr != null && curr.getCount() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;

	}

	public void addWebPageData(String webPage, int rowid) {

		SQLiteDatabase db = this.getReadableDatabase();
		Log.e("", "");
		final ContentValues values = new ContentValues();

		// values.put(KEY_ITEMNAME, data.getItemname());
		values.put(KEY_WEPAGE, webPage);

		db.update(TABLE_NEWS, values, KEY_ID + " = " + rowid, null);

		if (db != null)
			db.close();

	}

	public String getWebPageData( int rowid) {

		SQLiteDatabase db = this.getReadableDatabase();
		Log.e("", "");
		Cursor curr = null;
		try {
			curr = db.query(TABLE_NEWS, new String[] { "*" }, KEY_ID + " = "
					+ rowid, null, null, null, null);

			if (curr != null & curr.getCount() > 0) {
				curr.moveToFirst();
				return curr.getString(curr.getColumnIndex(KEY_WEPAGE));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (db != null)
				db.close();

			if (curr != null) {
				curr.close();
			}

		}

		return "";

	}

	public ArrayList<NewsModel> getNewsItems(String newsType) {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<NewsModel> listData = new ArrayList<NewsModel>();
		Cursor curr = null;
		try {

			curr = db.query(TABLE_NEWS, new String[] { "*" }, KEY_NEWS_TYPE
					+ " = '" + newsType + "'", null, null, null, KEY_ID
					+ " DESC");

			if (curr != null & curr.getCount() > 0) {
				curr.moveToFirst();

				for (int i = 0; i < curr.getCount(); i++) {
					NewsModel model = new NewsModel();
					model.setDate(curr.getString(curr.getColumnIndex(KEY_DATE)));
					model.setDetail(curr.getString(curr
							.getColumnIndex(KEY_DETAIL)));
					model.setGuId(curr.getString(curr.getColumnIndex(KEY_GUID)));
					model.setImageUrl(curr.getString(curr
							.getColumnIndex(KEY_URL)));

					model.setTitle(curr.getString(curr
							.getColumnIndex(KEY_TITLE)));

					model.setId(curr.getInt(curr.getColumnIndex(KEY_ID)));

					listData.add(model);
					curr.moveToNext();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				db.close();

			if (curr != null)
				curr.close();
		}

		return listData;

	}

}
