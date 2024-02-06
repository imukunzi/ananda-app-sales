package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="salesdetail")
@Table(name="salesdetail")
public class SalesDetails {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int id;
	
	@Column(name = "orderid")
	private String orderid;
	
	@Column(name = "ordertime")
	private String orderTime;
	
	@Column(name = "product")
	private String product;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "pid")
	private int pid;
	
	@Column(name = "qty")
	private int qty;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "size")
	private String size;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "saller")
	private String saller;
	
	@Column(name = "customer")
	private String customer;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "salesstatus")
	private String salesStatus;
	
	@Column(name = "stand")
	private String stand;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "total")
	private Double total;
	
	@Column(name = "paymenttime")
	private String paymentTime;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "cashier")
	private String cashier;
	
	@Column(name = "min_price")
	private double min_price;
	
	@Column(name = "authorization_status")
	private String authorization_status;
	
	@Column(name = "authorization_by")
	private String authorization_by;
	
	@Column(name = "product_price")
	private double product_price;
	
	@Column(name = "description")
	private String description;
	
	

	public SalesDetails() {
		super();
	}



	public SalesDetails(String orderid, String orderTime, String product, String code, int pid, int qty, String color,
			String size, double price, String saller, String customer, String phone, String salesStatus, String stand,
			String username,double total,String paymentTime,String location,String cashier,double min_price,String authorization_status,String authorization_by,double product_price,String description) {
		super();
		this.orderid = orderid;
		this.orderTime = orderTime;
		this.product = product;
		this.code = code;
		this.pid = pid;
		this.qty = qty;
		this.color = color;
		this.size = size;
		this.price = price;
		this.saller = saller;
		this.customer = customer;
		this.phone = phone;
		this.salesStatus = salesStatus;
		this.stand = stand;
		this.username = username;
		this.total=total;
		this.paymentTime=paymentTime;
		this.location =location;
		this.cashier = cashier;
		this.min_price=min_price;
		this.authorization_status=authorization_status;
		this.authorization_by=authorization_by;
		this.product_price=product_price;
		this.description=description;
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



	public String getProduct() {
		return product;
	}



	public void setProduct(String product) {
		this.product = product;
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



	public int getQty() {
		return qty;
	}



	public void setQty(int qty) {
		this.qty = qty;
	}



	public String getColor() {
		return color;
	}



	public void setColor(String color) {
		this.color = color;
	}



	public String getSize() {
		return size;
	}



	public void setSize(String size) {
		this.size = size;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public String getSaller() {
		return saller;
	}



	public void setSaller(String saller) {
		this.saller = saller;
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



	public String getSalesStatus() {
		return salesStatus;
	}



	public void setSalesStatus(String salesStatus) {
		this.salesStatus = salesStatus;
	}



	public String getStand() {
		return stand;
	}



	public void setStand(String stand) {
		this.stand = stand;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public Double getTotal() {
		return total;
	}



	public void setTotal(Double total) {
		this.total = total;
	}



	public String getPaymentTime() {
		return paymentTime;
	}



	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
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



	public double getMin_price() {
		return min_price;
	}



	public void setMin_price(double min_price) {
		this.min_price = min_price;
	}



	public String getAuthorization_status() {
		return authorization_status;
	}



	public void setAuthorization_status(String authorization_status) {
		this.authorization_status = authorization_status;
	}



	public String getAuthorization_by() {
		return authorization_by;
	}



	public void setAuthorization_by(String authorization_by) {
		this.authorization_by = authorization_by;
	}



	public double getProduct_price() {
		return product_price;
	}



	public void setProduct_price(double product_price) {
		this.product_price = product_price;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	@Override
	public String toString() {
		return "SalesDetails [id=" + id + ", orderid=" + orderid + ", orderTime=" + orderTime + ", product=" + product
				+ ", code=" + code + ", pid=" + pid + ", qty=" + qty + ", color=" + color + ", size=" + size
				+ ", price=" + price + ", saller=" + saller + ", customer=" + customer + ", phone=" + phone
				+ ", salesStatus=" + salesStatus + ", stand=" + stand + ", username=" + username + ", total=" + total
				+ ", paymentTime=" + paymentTime + ", location=" + location + ", cashier=" + cashier + ", min_price="
				+ min_price + ", authorization_status=" + authorization_status + ", authorization_by="
				+ authorization_by + ", product_price=" + product_price + ", description=" + description + "]";
	}



		
	 

}
