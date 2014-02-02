package com.sharkey.quiz;

import com.sharkey.quiz.db.QuizDB;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PlayerPanel extends Activity implements OnClickListener{

	TextView playerUsername;
	TextView playerPoints;
	Button backBtn;
	Button playQuiz;
	QuizDB db;
	String usernameString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_panel);
		Intent playerIntent=getIntent();

		usernameString=playerIntent.getStringExtra("username");
		db=QuizDB.getInstance(this);
		int points=db.GetUserPoints(usernameString);
		
		playerUsername=(TextView)findViewById(R.id.playerUsrname);
		playerPoints=(TextView)findViewById(R.id.playerPoints);
		
		playerUsername.setText(usernameString);
		playerPoints.setText(String.valueOf(points));
		
		playQuiz=(Button)findViewById(R.id.playQuiz);
		playQuiz.setOnClickListener(this);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player_panel, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Intent playQuiz=new Intent(this,QuestionPanel.class);
		playQuiz.putExtra("username", 
				usernameString);
		startActivity(playQuiz);
		
	}

}
