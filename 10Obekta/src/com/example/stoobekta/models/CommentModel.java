package com.example.stoobekta.models;

import java.util.Date;

public class CommentModel {
	public String Comment ;
    public String DateAdded ;
    public int SiteID;
    
    @Override
    public String toString() {
        return "Коментар:"+this.Comment;
    
    }
}
