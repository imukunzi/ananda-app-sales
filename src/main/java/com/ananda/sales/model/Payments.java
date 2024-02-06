package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="payments")
@Table(name="payments")
public class Payments {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
	@Column(name = "orderno")
	private String orderno;
	
	@Column(name = "customer")
	private String customer;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "ordertime")
	private String orderTime;
	
	@Column(name = "saller")
	private String saller;
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "amount_payed")
	private Double amount_payed;
	
	@Column(name = "balance")
	private Double balance;
	
	@Column(name = "cash")
	private Double cash;
	
	@Column(name = "momo")
	private Double momo;
	
	@Column(name = "cheque")
	private Double cheque;
	
	@Column(name = "transfer")
	private Double transfer;
	
	@Column(name = "visa")
	private Double visa;
	
	@Column(name = "total_received")
	private Double total_received;
	
	@Column(name = "payment_time")
	private String payment_time;
	
	@Column(name = "cashier")
	private String cashier;
	
	@Column(name = "delivery")
	private String delivery;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "momo_charges")
	private double momo_charges;

	public Payments() {
		super();
	}

	public Payments(String orderno, String customer, String phone, String orderTime, String saller, Double amount,
			Double amount_payed, Double balance, Double cash, Double momo, Double cheque, Double transfer, Double visa,
			Double total_received, String payment_time, String cashier, String delivery,String location,double momo_charges) {
		super();
		this.orderno = orderno;
		this.customer = customer;
		this.phone = phone;
		this.orderTime = orderTime;
		this.saller = saller;
		this.amount = amount;
		this.amount_payed = amount_payed;
		this.balance = balance;
		this.cash = cash;
		this.momo = momo;
		this.cheque = cheque;
		this.transfer = transfer;
		this.visa = visa;
		this.total_received = total_received;
		this.payment_time = payment_time;
		this.cashier = cashier;
		this.delivery = delivery;
		this.location=location;
		this.momo_charges=momo_charges;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
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

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getSaller() {
		return saller;
	}

	public void setSaller(String saller) {
		this.saller = saller;
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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	public Double getMomo() {
		return momo;
	}

	public void setMomo(Double momo) {
		this.momo = momo;
	}

	public Double getCheque() {
		return cheque;
	}

	public void setCheque(Double cheque) {
		this.cheque = cheque;
	}

	public Double getTransfer() {
		return transfer;
	}

	public void setTransfer(Double transfer) {
		this.transfer = transfer;
	}

	public Double getVisa() {
		return visa;
	}

	public void setVisa(Double visa) {
		this.visa = visa;
	}

	public Double getTotal_received() {
		return total_received;
	}

	public void setTotal_received(Double total_received) {
		this.total_received = total_received;
	}

	public String getPayment_time() {
		return payment_time;
	}

	public void setPayment_time(String payment_time) {
		this.payment_time = payment_time;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	
	

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getMomo_charges() {
		return momo_charges;
	}

	public void setMomo_charges(double momo_charges) {
		this.momo_charges = momo_charges;
	}

	@Override
	public String toString() {
		return "Payments [id=" + id + ", orderno=" + orderno + ", customer=" + customer + ", phone=" + phone
				+ ", orderTime=" + orderTime + ", saller=" + saller + ", amount=" + amount + ", amount_payed="
				+ amount_payed + ", balance=" + balance + ", cash=" + cash + ", momo=" + momo + ", cheque=" + cheque
				+ ", transfer=" + transfer + ", visa=" + visa + ", total_received=" + total_received + ", payment_time="
				+ payment_time + ", cashier=" + cashier + ", delivery=" + delivery + ", location=" + location
				+ ", momo_charges=" + momo_charges + "]";
	}

	
	

}
