package com.ananda.sales.model;

public class SallersReport {
	
	private String name;
	private String amount;
	private String date;
	
	public SallersReport(String name, String amount, String date) {
		
		this.name = name;
		this.amount = amount;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	

}
