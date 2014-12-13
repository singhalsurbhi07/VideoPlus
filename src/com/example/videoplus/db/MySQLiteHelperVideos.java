package com.example.videoplus.db;



import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelperVideos  extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "VideoDB.db";
	public static String COLUMN_ID = "VIDEO_ID";

	public static final String TBL_VIDEO = "VIDEO";
	public static final String COLUMN_TYPE = "TYPE";
	public static final String COLUMN_TITLE = "TITLE";
	public static final String COLUMN_URL = "URL";
	private static final String TABLE_VIDEO_CREATE = "CREATE TABLE  IF NOT EXISTS " + TBL_VIDEO 
			+ " ( " + COLUMN_ID + " TEXT PRIMARY KEY NOT NULL, " 
			+ COLUMN_TYPE +  " TEXT DEFAULT NULL, "
			+ COLUMN_TITLE + " TEXT DEFAULT NULL, "
			+ COLUMN_URL +  " TEXT DEFAULT NULL	);"	;

	private static SQLiteDatabase db= null;
	//	CREATE TABLE  IF NOT EXISTS VIDEO ( VIDEO TEXT PRIMARY KEY NOT NULL, TYPE TEXT DEFAULT NULL, TITLE TEXT DEFAULT NULL, URL TEXT DEFAULT NULL	);

	public MySQLiteHelperVideos(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_VIDEO_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEO_CREATE);
		onCreate(db);
	}

	public void addVideoRow() throws JSONException {

		//PArse JSON object and extract the input values
		//String videoId = new JSONObject(videoJSONObj.getString("items")).getString("contentDetails").getString("resourceId").getString(""videoId);

		String[] videoId = {"https://www.youtube.com/watch?v=zWg7U0OEAoE", 
				"https://www.youtube.com/watch?v=tORLeHHtazM", 
				"https://www.youtube.com/watch?v=q0woiOp7sqU", 
				"https://www.youtube.com/watch?v=5NPv0lK9YP4", 
				"https://www.youtube.com/watch?v=UcWsDwg1XwM", 
				"https://www.youtube.com/watch?v=oo1ZZlvT2LQ", 
				"https://www.youtube.com/watch?v=2qxY859dzzQ",
				"https://www.youtube.com/watch?v=RM3Q1oiHTGU", 
		"https://www.youtube.com/watch?v=8ANuXhvKoik"}; 

		String[] url = {"https://i.ytimg.com/vi/zWg7U0OEAoE/mqdefault.jpg",
				"https://i.ytimg.com/vi/tORLeHHtazM/mqdefault.jpg", 
				"https://i.ytimg.com/vi/q0woiOp7sqU/mqdefault.jpg", 
				"https://i.ytimg.com/vi/5NPv0lK9YP4/mqdefault.jpg", 
				"https://i.ytimg.com/vi_webp/UcWsDwg1XwM/default.webp", 
				"https://i.ytimg.com/vi_webp/oo1ZZlvT2LQ/default.webp", 
				"https://i.ytimg.com/vi_webp/2qxY859dzzQ/default.webp",
				"https://i.ytimg.com/vi/RM3Q1oiHTGU/mqdefault.jpg",
		"https://i.ytimg.com/vi/8ANuXhvKoik/default.jpg"};


		String[] title = {"Lecture - 1 Introduction to Data Structures and Algorithms",
				"Lecture - 6 Trees", 
				"Lecture 16 - Trees", 
				"Lecture 01: Beginning Algebra (Math 70)", 
				"Big Picture of Calculus", 
				"The Exponential Function", 
				"Big Picture: Integrals",
				"Wifi Direct Explained: What it is, How it works & What it can do for you!",
		"SJSU Campus Tour"};

		//Open database connection
		SQLiteDatabase db = this.getWritableDatabase();
		// Define values for each field
		ContentValues values = new ContentValues();
		//values.put(COLUMN_ID, DateUtil.getYesterdayDateString()); 

		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			Log.d("MySqliteHelper","storing data to db for "+i);
			int j = random.nextInt(9);
			values.put(COLUMN_ID, videoId[j]);
			values.put(COLUMN_TITLE, title[j]); 
			values.put(COLUMN_TYPE, "like");
			values.put(COLUMN_URL, url[j]);

			// Insert Row
			long rowId=db.insert(TBL_VIDEO, null, values);
		}

		// Closing database connection
		db.close(); 
	}
	
	public String getKAddressList(String queryStr) {

		db = this.getReadableDatabase();
		Map<String, Integer> addressMap = new HashMap<String, Integer>();
		System.out.println("I came here 1");
		

		Cursor cursor = db.rawQuery(queryStr, null);
		System.out.println("I came here 2");

		System.out.println(cursor.moveToFirst());
		System.out.println("I came here 3");

		if(cursor.getCount() == 0) {
			return null;
		}
		
		JSONArray array = new JSONArray();
		
		while(!cursor.isAfterLast()) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("videoId", cursor.getString(0));
				obj.put("thumbnail", cursor.getString(1));
				obj.put("title", cursor.getString(2));
				array.put(obj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("I came here 4");
			//addressMap.put(cursor.getString(0), cursor.getInt(1));
			System.out.println("I came here 5");
			cursor.moveToNext();
		}
	
		cursor.close();
		System.out.println("json ResponseArray"+array.toString());


		if(db.isOpen()) 
			db.close();
		System.out.println("I came here 7");

		return array.toString();
	}
}