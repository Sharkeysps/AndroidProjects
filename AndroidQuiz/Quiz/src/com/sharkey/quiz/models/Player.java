package com.sharkey.quiz.models;

public class Player extends User {

	public Player(String username, String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean IsAdmin() {
		
		return false;
	}

	
}
