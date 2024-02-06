package com.ananda.sales.model;


public class PaymentSummary {
	
	
	private Double cash;

	private Double momo;
	 
	private Double cheque;
	
	private Double transfer;
	 
	private Double visa;
	
	private Double total;

	public PaymentSummary(Double cash, Double momo, Double cheque, Double transfer, Double visa, Double total) {
		super();
		this.cash = cash;
		this.momo = momo;
		this.cheque = cheque;
		this.transfer = transfer;
		this.visa = visa;
		this.total = total;
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

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "PaymentSummary [cash=" + cash + ", momo=" + momo + ", cheque=" + cheque + ", transfer=" + transfer
				+ ", visa=" + visa + ", total=" + total + "]";
	}

	
	

}
