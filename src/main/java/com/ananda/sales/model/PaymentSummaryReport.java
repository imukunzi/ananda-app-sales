package com.ananda.sales.model;


public class PaymentSummaryReport {
	
	
	private String cash;

	private String momo;
	 
	private String cheque;
	
	private String transfer;
	 
	private String visa;
	
	private String total;
	
	private String momo_charges;

	public PaymentSummaryReport(String cash, String momo, String cheque, String transfer, String visa, String total,String momo_charges) {
		super();
		this.cash = cash;
		this.momo = momo;
		this.cheque = cheque;
		this.transfer = transfer;
		this.visa = visa;
		this.total = total;
		this.momo_charges=momo_charges;
	}

	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public String getMomo() {
		return momo;
	}

	public void setMomo(String momo) {
		this.momo = momo;
	}

	public String getCheque() {
		return cheque;
	}

	public void setCheque(String cheque) {
		this.cheque = cheque;
	}

	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public String getVisa() {
		return visa;
	}

	public void setVisa(String visa) {
		this.visa = visa;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getMomo_charges() {
		return momo_charges;
	}

	public void setMomo_charges(String momo_charges) {
		this.momo_charges = momo_charges;
	}

	

	
}
