package com.example.stoobekta.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.example.stoobekta.models.CoordinatesModel;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class SitesDB extends SQLiteAssetHelper {

	private static SitesDB sInstance = null;

	public static final String KEY_ROWID = "0 _id";
	public static final String KEY_CITY = "city";
	public static final String KEY_NAME = "name";
	public static final String KEY_NUMBER = "number";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_TICKETADULT = "ticketadult";
	public static final String KEY_TICKETCHILD = "ticketchild";
	public static final String KEY_WORKINGHOURS = "worktimes";
	public static final String KEY_VISITED_SITES = "visited";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";

	private static final String DATABASE_NAME = "national_sites_android";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "Obekti";

	public static SitesDB getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new SitesDB(context.getApplicationContext());
		}
		return sInstance;
	}

	private SitesDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	public Cursor getObekti() {

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String[] sqlSelect = { KEY_ROWID, KEY_NUMBER, KEY_CITY, KEY_NAME };
		String sqlTables = TABLE_NAME;

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);

		c.moveToFirst();
		return c;

	}

	public Cursor getSearchResult(String searchNumber) {

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String[] sqlSelect = { KEY_ROWID, KEY_NUMBER, KEY_CITY, KEY_NAME };
		String sqlTables = TABLE_NAME;

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, KEY_NUMBER + " LIKE ?",
				new String[] { searchNumber + "%" }, null, null, null);

		c.moveToFirst();
		return c;

	}

	public Cursor getVisitedSites() {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String[] sqlSelect = { KEY_ROWID, KEY_NUMBER, KEY_CITY, KEY_NAME };
		String sqlTables = TABLE_NAME;

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "visited=?", new String[] { "1" },
				null, null, null);

		c.moveToFirst();
		return c;
	}

	public Cursor getNearSites(CoordinatesModel coordinates) {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String[] sqlSelect = { KEY_ROWID, KEY_NUMBER, KEY_CITY, KEY_NAME };
		String sqlTables = TABLE_NAME;

		qb.setTables(sqlTables);
		Cursor c = qb.query(
				db,
				sqlSelect,
				"longitude>=? AND longitude<? AND latitude>=?",
				new String[] {
						(String) String.valueOf(coordinates.SmallerLongitude)
								.subSequence(0, 5),
						(String) String.valueOf(coordinates.BiggerLongitude)
								.substring(0, 5),
						(String) String.valueOf(coordinates.SmallerLatitude)
								.substring(0, 5) }, null, null, null);

		c.moveToFirst();
		return c;
	}

	public Cursor getDetailedInfoAboutObekt(String number) {

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String[] sqlSelect = { KEY_ROWID, KEY_NUMBER, KEY_CITY, KEY_NAME,
				KEY_DESCRIPTION, KEY_TICKETADULT, KEY_TICKETCHILD,
				KEY_WORKINGHOURS, KEY_LATITUDE, KEY_LONGITUDE };
		String sqlTables = TABLE_NAME;

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "_id=?", new String[] { number },
				null, null, null);

		c.moveToFirst();
		return c;

	}

}
