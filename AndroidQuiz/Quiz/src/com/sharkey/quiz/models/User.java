package com.sharkey.quiz.models;


public class User implements CheckAdmin {

	protected String username;
	protected String password;
	
	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	
	public User(String username,String password){
		this.password=password;
		this.username=username;
	}
	
	
	@Override
	public boolean IsAdmin() {
		
		return false;
	}


}
