package com.ananda.sales.model;

public class OrderDetailsTotal {

	private String customer;
	private String phone;
	private String orderNo;
	private String date;
	private String total;
	private String payed;
	private String balance;
	private String other;
	private String saller;
	private String cashier;

	public OrderDetailsTotal(String customer, String phone, String orderNo, String date, String total, String payed,
			String balance,String other,String saller,String cashier) {
		super();
		this.customer = customer;
		this.phone = phone;
		this.orderNo = orderNo;
		this.date = date;
		this.total = total;
		this.payed = payed;
		this.balance = balance;
		this.other=other;
		this.saller=saller;
		this.cashier=cashier;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPayed() {
		return payed;
	}

	public void setPayed(String payed) {
		this.payed = payed;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getSaller() {
		return saller;
	}

	public void setSaller(String saller) {
		this.saller = saller;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
	
	
	

}
