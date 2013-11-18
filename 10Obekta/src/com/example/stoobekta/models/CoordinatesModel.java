package com.example.stoobekta.models;

public class CoordinatesModel {

	//Helper model class for the sql querry
	
	
	public double SmallerLatitude;
	public double BiggerLatitude;
	
	public double SmallerLongitude;
	public double BiggerLongitude;
	
	public CoordinatesModel(double latitude,double longitude){
		this.SmallerLatitude=latitude-0.1;
		this.BiggerLatitude=latitude+0.1;
		
		this.SmallerLongitude=longitude-0.1;
		this.BiggerLongitude=longitude+0.1;
	}

	
}
