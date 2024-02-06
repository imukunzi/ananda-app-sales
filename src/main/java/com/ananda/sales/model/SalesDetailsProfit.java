package com.ananda.sales.model;

public class SalesDetailsProfit {
	
	private int id;
	private String code;
	private String product;
	private double purchase;
	private double sales;
	private int qty;
	private double total_purchase;
	private double total_sales;
	private double total_income;
	
	
	
	
	public SalesDetailsProfit(int id, String code, String product, double purchase, double sales, int qty,
			double total_purchase, double total_sales, double total_income) {
		super();
		this.id = id;
		this.code = code;
		this.product = product;
		this.purchase = purchase;
		this.sales = sales;
		this.qty = qty;
		this.total_purchase = total_purchase;
		this.total_sales = total_sales;
		this.total_income = total_income;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public double getPurchase() {
		return purchase;
	}
	public void setPurchase(double purchase) {
		this.purchase = purchase;
	}
	public double getSales() {
		return sales;
	}
	public void setSales(double sales) {
		this.sales = sales;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public double getTotal_purchase() {
		return total_purchase;
	}
	public void setTotal_purchase(double total_purchase) {
		this.total_purchase = total_purchase;
	}
	public double getTotal_sales() {
		return total_sales;
	}
	public void setTotal_sales(double total_sales) {
		this.total_sales = total_sales;
	}
	public double getTotal_income() {
		return total_income;
	}
	public void setTotal_income(double total_income) {
		this.total_income = total_income;
	}

	@Override
	public String toString() {
		return "SalesDetailsProfit [id=" + id + ", code=" + code + ", product=" + product + ", purchase=" + purchase
				+ ", sales=" + sales + ", qty=" + qty + ", total_purchase=" + total_purchase + ", total_sales="
				+ total_sales + ", total_income=" + total_income + "]";
	}
	
	

}
