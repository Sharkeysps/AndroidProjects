package com.example.stoobekta.models;

import java.io.Serializable;

public class DetailedSiteInfoModel implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public int getNumber() {
		return Number;
	}
	public void setNumber(int number) {
		Number = number;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getTicketChild() {
		return TicketChild;
	}
	public void setTicketChild(String ticketChild) {
		TicketChild = ticketChild;
	}
	public String getTicketAdult() {
		return TicketAdult;
	}
	public void setTicketAdult(String ticketAdult) {
		TicketAdult = ticketAdult;
	}
	public String getWorkingHours() {
		return WorkingHours;
	}
	public void setWorkingHours(String workingHours) {
		WorkingHours = workingHours;
	}
	public String City;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String Name;
	public int Number;
	public String Description;
	public String TicketChild;
	public String TicketAdult;
	public String WorkingHours;

	
	
	

}
