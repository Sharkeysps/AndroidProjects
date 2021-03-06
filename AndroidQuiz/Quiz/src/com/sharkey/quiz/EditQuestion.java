package com.sharkey.quiz;

import com.sharkey.quiz.db.QuizDB;
import com.sharkey.quiz.models.Admin;
import com.sharkey.quiz.models.Question;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditQuestion extends Activity implements OnClickListener {

	EditText questionText;
	EditText wrongAnswerOne;
	EditText wrongAnswerTwo;
	EditText rightAnswer;
	Question questionData;
	QuizDB db;
	private Button backToLogin;
	private Button saveQuestion;
	String selectedQuestion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_question);
		
		questionText=(EditText)findViewById(R.id.questionText);
		rightAnswer=(EditText)findViewById(R.id.rightAnswer);
		wrongAnswerOne=(EditText)findViewById(R.id.wrongAnswerOne);
		wrongAnswerTwo=(EditText)findViewById(R.id.wrongAnswerTwo);
		backToLogin=(Button)findViewById(R.id.backBtn);
		saveQuestion=(Button)findViewById(R.id.saveEdit);
		
		backToLogin.setOnClickListener(this);
		saveQuestion.setOnClickListener(this);
		
		Intent intent=getIntent();
		selectedQuestion=intent.getStringExtra("question");
		db=QuizDB.getInstance(this);
		if(!selectedQuestion.equals("none")){
			questionData=db.GetSpecificQuestion(selectedQuestion);
			FillTexts();
		}
		
		
	}
	
	private void FillTexts(){		
		questionText.setText(questionData.Question);
		rightAnswer.setText(questionData.RightAnswer);
		wrongAnswerOne.setText(questionData.WrongAnswerOne);
		wrongAnswerTwo.setText(questionData.WrongAnswerTwo);
		
	}
	
	private void SaveQuestionData(){
		String questionString=questionText.getText().toString();
		String rightAnswerString=rightAnswer.getText().toString();
		String wrongAnswerOneString=wrongAnswerOne.getText().toString();
		String wrongAnswerTwoString=wrongAnswerTwo.getText().toString();
		
		if(questionString.equals("") || rightAnswerString.equals("")
				|| wrongAnswerOneString.equals("") || wrongAnswerTwoString.equals("")){
			Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
		}else{
			if(selectedQuestion.equals("none")){
				Question newQuestion=new Question(questionString, wrongAnswerOneString, 
						wrongAnswerTwoString, rightAnswerString);
				db.AddQuestion(newQuestion);
			}else{
					Question newQuestionData=new Question(questionString, wrongAnswerOneString, 
							wrongAnswerTwoString, rightAnswerString);
					db.EditQuestion(selectedQuestion, newQuestionData);
			}
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_question, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Intent adminPanelIntent=new Intent(this,AdminPanel.class);
		switch (v.getId()) {
		case R.id.saveEdit:
			SaveQuestionData();
			startActivity(adminPanelIntent);
			break;

		case R.id.backBtn:
			startActivity(adminPanelIntent);
			break;
		}
		
	}

}
