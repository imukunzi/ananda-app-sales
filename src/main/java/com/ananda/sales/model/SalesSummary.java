package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="salessummary")
@Table(name="salessummary")
public class SalesSummary {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int id;
	
	@Column(name = "orderid")
	private String orderid;
	
	@Column(name = "ordertime")
	private String orderTime;
	
	@Column(name = "customer")
	private String customer;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "amount_payed")
	private Double amount_payed;
	
	@Column(name = "balance")
	private Double balance;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "saller")
	private String saller;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "paymenttime")
	private String paymentTime;
	
	@Column(name = "comment")
	private String comment;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "cashier")
	private String cashier;
	
	@Column(name = "other")
	private String other;

	public SalesSummary() {
		super();
	}

	public SalesSummary(String orderid, String orderTime, String customer, String phone, Double amount,
			Double amount_payed,double balance, String status, String saller, String username,String paymentTime,String comment,String location,String cashier,String other) {
		super();
		this.orderid = orderid;
		this.orderTime = orderTime;
		this.customer = customer;
		this.phone = phone;
		this.amount = amount;
		this.amount_payed = amount_payed;
		this.balance=balance;
		this.status = status;
		this.saller = saller;
		this.username = username;
		this.paymentTime = paymentTime;
		this.comment =comment;
		this.location= location;
		this.cashier=cashier;
		this.other=other;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
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
	
	

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getAmount_payed() {
		return amount_payed;
	}

	public void setAmount_payed(Double amount_payed) {
		this.amount_payed = amount_payed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSaller() {
		return saller;
	}

	public void setSaller(String saller) {
		this.saller = saller;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		
		
	}
	
	

	public String getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	
	

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	@Override
	public String toString() {
		return "SalesSummary [id=" + id + ", orderid=" + orderid + ", orderTime=" + orderTime + ", customer=" + customer
				+ ", phone=" + phone + ", amount=" + amount + ", amount_payed=" + amount_payed + ", balance=" + balance
				+ ", status=" + status + ", saller=" + saller + ", username=" + username + ", paymentTime="
				+ paymentTime + ", comment=" + comment + ", location=" + location + ", cashier=" + cashier + ", other="
				+ other + "]";
	}
	

}
