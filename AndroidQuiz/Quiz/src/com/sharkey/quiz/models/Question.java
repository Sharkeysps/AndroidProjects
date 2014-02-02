package com.sharkey.quiz.models;

public class Question {
	public String Question;
	public String WrongAnswerOne;
	public String WrongAnswerTwo;
	public String RightAnswer;
	
	public Question(String question, String wrongAnswerOne,
			String wrongAnswerTwo, String rightAnswer) {
		super();
		this.Question = question;
		this.WrongAnswerOne = wrongAnswerOne;
		this.WrongAnswerTwo = wrongAnswerTwo;
		this.RightAnswer = rightAnswer;
	}
}
