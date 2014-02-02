package com.sharkey.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AdminPanel extends Activity implements OnClickListener{

	Button addAdminButton;
	Button addQuestionButton;
	Button listQuestionsButton;
	Button backToLoginButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_panel);
		
		addAdminButton=(Button)findViewById(R.id.addAdmin);
		addQuestionButton=(Button)findViewById(R.id.addQuestion);
		listQuestionsButton=(Button)findViewById(R.id.listQuestions);
		backToLoginButton=(Button)findViewById(R.id.backToLoginBtn);
		
		 //'this' refers to current Context
		addAdminButton.setOnClickListener(this);
		addQuestionButton.setOnClickListener(this);
		listQuestionsButton.setOnClickListener(this);
		backToLoginButton.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_panel, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addAdmin: 
			Intent addAdminIntent=new Intent(this,RegisterUserActivity.class);
			addAdminIntent.putExtra("rights", "admin");
			startActivity(addAdminIntent);
			break;

		case R.id.addQuestion:
			Intent addQuestionIntent=new Intent(this,EditQuestion.class);
			addQuestionIntent.putExtra("question", "none");
			startActivity(addQuestionIntent);
			break;
		
		case R.id.listQuestions:
			Intent listQuestions=new Intent(this,AllQuestionsBrowser.class);
			startActivity(listQuestions);
			break;
			
		case R.id.backToLoginBtn:
			Intent backToLoginIntent=new Intent(this,MainActivity.class);
			startActivity(backToLoginIntent);
			break;
		}
		
	}

}
