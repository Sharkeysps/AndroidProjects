package com.sharkey.quiz;


import com.sharkey.quiz.db.QuizDB;
import com.sharkey.quiz.db.QuizDbAsyncLoader;

import android.os.Bundle;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class AllQuestionsBrowser extends Activity  implements 
LoaderCallbacks<Cursor>,OnItemClickListener {

	SimpleCursorAdapter dataAdapter;
	QuizDB db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_questions_browser);
		db=QuizDB.getInstance(this);
		FillListView();	
	}

	//Creates the ListView with the data from the cursor that will be loaded in the loader
	public void FillListView(){
		//Bindings
		String[] dataBindFrom = new String[] { QuizDB.KEY_QUESTION, QuizDB.KEY_RANSWER };

		int[] dataBindTo = new int[] { R.id.questionName,
				R.id.rightAnswer, };

	
		dataAdapter = new SimpleCursorAdapter(this,
				R.layout.questions_model, null, dataBindFrom, dataBindTo,
				0);

		ListView listView = (ListView) findViewById(R.id.listViewQuestions);
		listView.setAdapter(dataAdapter);
		
		listView.setOnItemClickListener(this);

		getLoaderManager().initLoader(0, null, this);
	}
	
	//Gets the data about the clicked item in the listview and opens a new detailed activity 
	@Override
	public void onItemClick(AdapterView<?> listView, View view, int position,
			long id) {
		Cursor cursor = (Cursor) listView.getItemAtPosition(position);
		String question = cursor.getString(cursor
				.getColumnIndexOrThrow(QuizDB.KEY_QUESTION));
		Intent intent=new Intent(this, EditQuestion.class);
		intent.putExtra("question", question);
		startActivity(intent);

	}

	//Creates the loader in different thread
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new QuizDbAsyncLoader(this);
	}
	
	//Upon loading the cursor swaps the loaded data with the existing cursor in the ListView
	public void onLoadFinished(Loader<Cursor> loader, Cursor loadedCursor) {
			dataAdapter.swapCursor(loadedCursor);

	}
	
	//If loader is reset or some error happens swap the cursor in the listview with null
	public void onLoaderReset(Loader<Cursor> arg0) {
		dataAdapter.swapCursor(null);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.all_questions_browser, menu);
		return true;
	}

}
