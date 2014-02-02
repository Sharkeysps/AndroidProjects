package com.sharkey.quiz;

import java.util.ArrayList;

import com.sharkey.quiz.db.QuizDB;
import com.sharkey.quiz.models.Question;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionPanel extends Activity implements OnClickListener {

	Button nextQuestion;
	Button backToLoginButton;
	TextView questionName;
	
	String playerNameString;
	RadioGroup answers;
	RadioButton answerOne;
	RadioButton answerTwo;
	RadioButton answerThree;
	RadioButton selectedAnswer;
	
	int questionCounter;
	int result=0;
	Question currentQuestion;
	ArrayList<Question> questions;
	QuizDB db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_panel);
		db=QuizDB.getInstance(this);
		
		Intent quizIntent=getIntent();
		playerNameString=quizIntent.getStringExtra("username");

		questionName=(TextView)findViewById(R.id.questionText);
		answers=(RadioGroup)findViewById(R.id.radioQuestion);
		answerOne=(RadioButton)findViewById(R.id.radioButton1);
		answerTwo=(RadioButton)findViewById(R.id.radioButton2);
		answerThree=(RadioButton)findViewById(R.id.radioButton3);
		
		nextQuestion=(Button)findViewById(R.id.nextQuestion);
		nextQuestion.setOnClickListener(this);
		backToLoginButton=(Button)findViewById(R.id.backButton);
		nextQuestion.setOnClickListener(this);
		
		questionCounter=0;
		questions=new ArrayList<Question>();
		questions=db.getQuestions();
		
		FillQuestionFields(0);
		
	}
	
	private void FillQuestionFields(int counter){
		currentQuestion=questions.get(counter);
		
		questionName.setText(currentQuestion.Question);
		answerOne.setText(currentQuestion.WrongAnswerOne);
		answerTwo.setText(currentQuestion.WrongAnswerTwo);
		answerThree.setText(currentQuestion.RightAnswer);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question_panel, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Intent backToPlayerPanelIntent=new Intent(this,PlayerPanel.class);
		backToPlayerPanelIntent.putExtra("username", playerNameString);
		
		switch (v.getId()) {
		case R.id.nextQuestion:
			if(answers.getCheckedRadioButtonId()==-1){
				Toast.makeText(this, "Select something", Toast.LENGTH_LONG).show();
			}else if(questions.size()-1!=questionCounter){
				int selectedId = answers.getCheckedRadioButtonId();
				selectedAnswer=(RadioButton)findViewById(selectedId);
				if(selectedAnswer.getText().toString()
						.equals(currentQuestion.RightAnswer)){
					result++;
				}
				answers.clearCheck();
				questionCounter++;
				FillQuestionFields(questionCounter);
			}else{
				db.UpdateUserPoints(playerNameString, result);
				
				Toast.makeText(this, "You got "+result +"out of"
				+questions.size()+" right", Toast.LENGTH_LONG).show();
				
				startActivity(backToPlayerPanelIntent);
			}
			break;

		case R.id.backButton:
			startActivity(backToPlayerPanelIntent);
			break;
		}
	
	}
	
	
	

}