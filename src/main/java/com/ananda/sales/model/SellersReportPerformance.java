package com.ananda.sales.model;

public class SellersReportPerformance {
	
	
	private String item;
	private String code;
	private int pid;
	private double price;
	private int qty;
	private double supply_price;
	private double percentage;
	private String totalCommission;
	private String description;
	
	
	
	public SellersReportPerformance(String item, String code,int pid, double price, int qty, double supply_price,
			double percentage,String totalCommission,String description) {
		super();
		this.item = item;
		this.code = code;
		this.pid=pid;
		this.price = price;
		this.qty = qty;
		this.supply_price = supply_price;
		this.percentage = percentage;
		this.totalCommission=totalCommission;
		this.description=description;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public double getSupply_price() {
		return supply_price;
	}
	public void setSupply_price(double supply_price) {
		this.supply_price = supply_price;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public String getTotalCommission() {
		return totalCommission;
	}
	public void setTotalCommission(String totalCommission) {
		this.totalCommission = totalCommission;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "SellersReportPerformance [item=" + item + ", code=" + code + ", pid=" + pid + ", price=" + price
				+ ", qty=" + qty + ", supply_price=" + supply_price + ", percentage=" + percentage
				+ ", totalCommission=" + totalCommission + ", description=" + description + "]";
	}
	
	
	

}
