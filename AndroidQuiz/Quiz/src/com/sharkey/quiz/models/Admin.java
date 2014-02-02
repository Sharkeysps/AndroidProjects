package com.sharkey.quiz.models;

public class Admin extends User{

	public Admin(String username, String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean IsAdmin() {
		
		return true;
	}

}
