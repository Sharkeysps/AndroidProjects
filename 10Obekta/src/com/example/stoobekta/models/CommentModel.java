package com.example.stoobekta.models;

public class CommentModel {
	public String Comment ;
    public int SiteID;
    public String UserName;
    
    @Override
    public String toString() {
        return this.UserName+":"+this.Comment;
    
    }
}
