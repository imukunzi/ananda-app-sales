package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="head_cashier_report")
@Table(name="head_cashier_report")
public class HeadCashierReport {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pid")
	private long id;
	
	@Column(name = "date")
	private String date;
			 
	@Column(name = "headcashier")
	private String headCashier;
	
	@Column(name = "cashier")
	private String cashier;
	
	@Column(name = "cash")
	private double cash;
	
	@Column(name = "momo")
	private double momo;
	
	@Column(name = "cheque")
	private double cheque;
	
	@Column(name = "transfer")
	private double transfer;
	
	@Column(name = "visa")
	private double visa;
	
	@Column(name = "expenses")
	private double expenses;
	
	@Column(name = "comment")
	private String comment;

	public HeadCashierReport() {
		 
	}

	public HeadCashierReport(String date, String headCashier, String cashier, double cash, double momo, double cheque,
			double transfer, double visa, double expenses, String comment) {
		super();
		this.date = date;
		this.headCashier = headCashier;
		this.cashier = cashier;
		this.cash = cash;
		this.momo = momo;
		this.cheque = cheque;
		this.transfer = transfer;
		this.visa = visa;
		this.expenses = expenses;
		this.comment = comment;
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

	public String getHeadCashier() {
		return headCashier;
	}

	public void setHeadCashier(String headCashier) {
		this.headCashier = headCashier;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public double getMomo() {
		return momo;
	}

	public void setMomo(double momo) {
		this.momo = momo;
	}

	public double getCheque() {
		return cheque;
	}

	public void setCheque(double cheque) {
		this.cheque = cheque;
	}

	public double getTransfer() {
		return transfer;
	}

	public void setTransfer(double transfer) {
		this.transfer = transfer;
	}

	public double getVisa() {
		return visa;
	}

	public void setVisa(double visa) {
		this.visa = visa;
	}

	public double getExpenses() {
		return expenses;
	}

	public void setExpenses(double expenses) {
		this.expenses = expenses;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	

}
