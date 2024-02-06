package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="expenses")
@Table(name="expenses")
public class Expenses {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "expensetype")
	private String expenseType;
	
	@Column(name = "purpose")
	private String purpose;
	
	@Column(name = "amount")
	private double amount;
	
	@Column(name = "other")
	private String other;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "location")
	private String location;

	public Expenses() {
		super();
	}

	public Expenses(String date, String expenseType, String purpose, double amount, String other, String username,String location) {
		super();
		this.date = date;
		this.expenseType = expenseType;
		this.purpose = purpose;
		this.amount = amount;
		this.other = other;
		this.username = username;
		this.location = location;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Expenses [id=" + id + ", date=" + date + ", expenseType=" + expenseType + ", purpose=" + purpose
				+ ", amount=" + amount + ", other=" + other + ", username=" + username + ", location=" + location + "]";
	}

	

}
