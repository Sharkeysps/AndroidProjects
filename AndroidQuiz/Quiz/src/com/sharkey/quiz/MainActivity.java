package com.sharkey.quiz;

import com.sharkey.quiz.db.QuizDB;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	Button loginButton;
	Button registerNewButton;
	EditText username;
	EditText password;
	QuizDB db;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //this refers to current Context
        db=QuizDB.getInstance(this);
        
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        
        loginButton=(Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        
        registerNewButton=(Button)findViewById(R.id.registerBtn);
        registerNewButton.setOnClickListener(this);
        
    }
 
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.registerBtn:
			Intent intent = new Intent(this, RegisterUserActivity.class);
			intent.putExtra("rights", "player");
			startActivity(intent);
			break;

		case R.id.loginButton:
			
			boolean testAdmin=db.CheckAdmin(username.getText().toString(), 
					password.getText().toString());
			boolean testUser=db.CheckLogin(username.getText().toString(), 
					password.getText().toString());
			if(testAdmin){
				Intent adminPanel=new Intent(this,AdminPanel.class);
				startActivity(adminPanel);
			}else if(testUser){
				Intent playerPanelIntent=new Intent(this,PlayerPanel.class);
				playerPanelIntent.putExtra("username", 
						username.getText().toString());
				startActivity(playerPanelIntent);
			}else{
				Toast.makeText(this, "Login unsuccessful", Toast.LENGTH_LONG).show();
			}
			
			break;
		}

	}
    
}
