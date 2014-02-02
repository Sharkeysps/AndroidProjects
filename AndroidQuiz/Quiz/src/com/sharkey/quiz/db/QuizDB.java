package com.sharkey.quiz.db;

import java.util.ArrayList;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.sharkey.quiz.models.Question;
import com.sharkey.quiz.models.User;


/* 
 * Class that manages the database.Uses library SqliteAssetHelper.To ensure that 
 * always one connection will be available the class uses the Singleton pattern. 
 * */
public class QuizDB extends SQLiteAssetHelper {
	
	
	private static QuizDB sInstance = null;
	
	//Database table names and columns for easier use
	private static final String DATABASE_NAME = "QuizDB";
	private static final int DATABASE_VERSION = 4;
	public static final String KEY_ROWID = "0 _id";
	public static final String KEY_USERNAME = "USERNAME";
	public static final String USERS_TABLE="USERS";
	public static final String KEY_PASSWORD = "PASSWORD";
	public static final String KEY_ISADMIN="ISADMIN";
	public static final String KEY_POINTS="POINTS";
	
	public static final String QUESTIONS_TABLE="QUESTIONS";
	public static final String KEY_QUESTION="QUESTION";
	public static final String KEY_WANSWER1="WANSWER1"; //WRONG ANSWER 
	public static final String KEY_WANSWER2="WANSWER2"; //SECOND WRONG ANSWER
	public static final String KEY_RANSWER="RANSWER";//RIGHT ANSWER
	
	public static QuizDB getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new QuizDB(context.getApplicationContext());
		}
		return sInstance;
	}
	
	
	private QuizDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//this command removes caching problems that may occur
		setForcedUpgradeVersion(4);

	}
	
	//Get all info about specific question from the database
	public Question GetSpecificQuestion(String searhedQuestion){
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String[] sqlSelect = { KEY_QUESTION,KEY_RANSWER,KEY_WANSWER1, KEY_WANSWER2};
		String sqlTables = QUESTIONS_TABLE;
		qb.setTables(sqlTables);
		Cursor cursor = qb.query(db, sqlSelect, "QUESTION=?", new String[] { searhedQuestion }, null, null, null);
		cursor.moveToFirst();
		
		String question=cursor.getString(cursor
				.getColumnIndexOrThrow(KEY_QUESTION));
		String rightAnswer=cursor.getString(cursor
				.getColumnIndexOrThrow(KEY_RANSWER));
		String wrongAnswerOne=cursor.getString(cursor
				.getColumnIndexOrThrow(KEY_WANSWER1));
		String wrongAnswerTwo=cursor.getString(cursor
				.getColumnIndexOrThrow(KEY_WANSWER2));
		
		Question resultQuestion=new Question(question, wrongAnswerOne, wrongAnswerTwo, rightAnswer);
		return resultQuestion;
		
	}
	
	//gets all questions and return it as cursor
	public Cursor ListAllQuestions(){
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		String[] sqlSelect = { KEY_ROWID, KEY_QUESTION,KEY_RANSWER};
		String sqlTables = QUESTIONS_TABLE;
		
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
		
		c.moveToFirst();
		return c;
		
	}
	
	//all users
	public Cursor ListAllUsers(){
		
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String[] sqlSelect = { KEY_ROWID, KEY_USERNAME,KEY_PASSWORD};
		String sqlTables = USERS_TABLE;

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);

		c.moveToFirst();
		return c;
	}
	
	
	public void AddUser(User newUser){
		
		SQLiteDatabase db = getWritableDatabase();

		ContentValues insertValues = new ContentValues();
		insertValues.put(KEY_USERNAME, newUser.getUsername());
		insertValues.put(KEY_PASSWORD, newUser.getPassword());
		insertValues.put(KEY_POINTS, 0);
		insertValues.put(KEY_ISADMIN,newUser.IsAdmin());

		db.insert( USERS_TABLE, null, insertValues);
		db.close();
	}
	
	//Gets up to 10 questions from the database randomly
	public ArrayList<Question> getQuestions(){
		ArrayList<Question> returnQuestions=new ArrayList<Question>();
		
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		String[] sqlSelect = { KEY_QUESTION,KEY_RANSWER,KEY_WANSWER1, KEY_WANSWER2};
		String sqlTables = QUESTIONS_TABLE;
		qb.setTables(sqlTables);
		Cursor cursor = qb.query(db, sqlSelect, null,null, null, null, "RANDOM() LIMIT 10");
		
		while(cursor.moveToNext()){
		String question=cursor.getString(cursor
				.getColumnIndexOrThrow(KEY_QUESTION));
		String rightAnswer=cursor.getString(cursor
				.getColumnIndexOrThrow(KEY_RANSWER));
		String wrongAnswerOne=cursor.getString(cursor
				.getColumnIndexOrThrow(KEY_WANSWER1));
		String wrongAnswerTwo=cursor.getString(cursor
				.getColumnIndexOrThrow(KEY_WANSWER2));
		
		Question resultQuestion=new Question(question, wrongAnswerOne, wrongAnswerTwo, rightAnswer);
		returnQuestions.add(resultQuestion);
		}
		return returnQuestions;

	}
	
	//get specific user points from game
	public int GetUserPoints(String username){
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String[] sqlSelect = { KEY_POINTS};
		String sqlTables = USERS_TABLE;

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "USERNAME=?", new String[]{username}, null, null, null);
		c.moveToFirst();
		
		int points=c.getInt(c
				.getColumnIndexOrThrow(KEY_POINTS));
		
		
		return points;
	}
	
	public void UpdateUserPoints(String username,int points){
		SQLiteDatabase db = getWritableDatabase();
		ContentValues args = new ContentValues();
		args.put(KEY_POINTS, points);

		db.update( USERS_TABLE, args,"USERNAME=?", new String[]{username});
	}
	
	public void AddQuestion(Question newQuestion){
		SQLiteDatabase db = getWritableDatabase();

		ContentValues insertValues = new ContentValues();
		insertValues.put(KEY_QUESTION, newQuestion.Question);
		insertValues.put(KEY_WANSWER1, newQuestion.WrongAnswerOne);
		insertValues.put(KEY_WANSWER2, newQuestion.WrongAnswerTwo);
		insertValues.put(KEY_RANSWER,newQuestion.RightAnswer);

		db.insert( QUESTIONS_TABLE, null, insertValues);
		db.close();
	}
	
	public void EditQuestion(String searchedQuestion,Question newQuestion){
		SQLiteDatabase db = getWritableDatabase();
		ContentValues args = new ContentValues();
		args.put(KEY_QUESTION, newQuestion.Question);
		args.put(KEY_WANSWER1, newQuestion.WrongAnswerOne);
		args.put(KEY_WANSWER2, newQuestion.WrongAnswerTwo);
		args.put(KEY_RANSWER,newQuestion.RightAnswer);
		
		db.update( QUESTIONS_TABLE, args,"QUESTION=?", new String[]{searchedQuestion});
		
	}
	
	//check if login is successfull
	public boolean CheckLogin(String username,String password){
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		String[] sqlSelect = { KEY_ROWID, KEY_USERNAME,KEY_PASSWORD};
		String sqlTables = USERS_TABLE;
		
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "USERNAME=? AND PASSWORD=?", new String[] { username,password },
				null, null, null);
		if(c.getCount()==0){
			return false;
		}
		return true;
	}
	
	//check if the logged user has admin rights
	public boolean CheckAdmin(String username,String password){
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		String[] sqlSelect = { KEY_ROWID, KEY_USERNAME,KEY_PASSWORD};
		String sqlTables = USERS_TABLE;
		
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "USERNAME=? AND PASSWORD=? AND ISADMIN=?", new String[] { username,password,"1" },
				null, null, null);
		if(c.getCount()==0){
			return false;
		}
		return true;
	}

	//check for username duplicates
	public boolean CheckIfUsernameExists(String username){
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		String[] sqlSelect = { KEY_ROWID, KEY_USERNAME};
		String sqlTables = USERS_TABLE;
		
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "USERNAME=?", new String[] { username },
				null, null, null);
		if(c.getCount()==0){
			return false;
		}
		return true;

	}
	
}
