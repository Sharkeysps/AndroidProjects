package com.example.stoobekta.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;


import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class SitesDB extends SQLiteAssetHelper {

	private static SitesDB sInstance = null;

	public static final String KEY_ROWID = "0 _id";
	public static final String KEY_CITY = "city";
	public static final String KEY_NAME = "name";
	public static final String KEY_NUMBER = "number";

	private static final String DATABASE_NAME = "national_sites_android";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME="Obekti";

	public static SitesDB getInstance(Context context) {

		// Use the application context, which will ensure that you
		// don't accidentally leak an Activity's context.
		// See this article for more information: http://bit.ly/6LRzfx
		if (sInstance == null) {
			sInstance = new SitesDB(context.getApplicationContext());
		}
		return sInstance;
	}

	private SitesDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// you can use an alternate constructor to specify a database location
		// (such as a folder on the sd card)
		// you must ensure that this folder is available and you have permission
		// to write to it
		// super(context, DATABASE_NAME,
		// context.getExternalFilesDir(null).getAbsolutePath(), null,
		// DATABASE_VERSION);

	}

	public Cursor getObekti() {

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String[] sqlSelect = { KEY_ROWID,KEY_NUMBER, KEY_CITY, KEY_NAME };
		String sqlTables = TABLE_NAME;

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);

		c.moveToFirst();
		return c;

	}

}
