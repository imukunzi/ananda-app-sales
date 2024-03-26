package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="orderdetail")
@Table(name="orderdetail")
public class OrderDetails {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
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
	
	@Column(name = "comment")
	private String comment;
	
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
	
	@Column(name = "dealer")
	private String dealer;
	
	@Column(name = "deal_type")
	private String deal_type;
	
	@Column(name = "saller_percentage")
	private double saller_percentage=0;
	
	@Column(name = "mbe_tracking")
	private String mbe_tracking;
	
	@Column(name = "purchase_price")
	private double purchase_price=0;
	
	

	public OrderDetails() {
		super();
	}



	public OrderDetails(String orderid, String orderTime, String product, String code, int pid, int qty, String color,
			String size, double price, String saller, String customer, String phone, String salesStatus, String stand,
			String username,double total,String comment,String location,String cashier,double min_price,String authorization_status,
			String authorization_by,double product_price,String description,String dealer,String deal_type,
			double saller_percentage,String mbe_tracking,double purchase_price) {
		 
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
		this.comment=comment;
		this.location=location;
		this.cashier=cashier;
		this.min_price=min_price;
		this.authorization_status=authorization_status;
		this.authorization_by=authorization_by;
		this.product_price=product_price;
		this.description=description;
		this.dealer = dealer;
		this.deal_type = deal_type;
		this.saller_percentage = saller_percentage;
		this.mbe_tracking = mbe_tracking;
		this.purchase_price = purchase_price;
	
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
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



	public String getDealer() {
		return dealer;
	}



	public void setDealer(String dealer) {
		this.dealer = dealer;
	}



	public String getDeal_type() {
		return deal_type;
	}



	public void setDeal_type(String deal_type) {
		this.deal_type = deal_type;
	}



	public double getSaller_percentage() {
		return saller_percentage;
	}



	public void setSaller_percentage(double saller_percentage) {
		this.saller_percentage = saller_percentage;
	}



	public String getMbe_tracking() {
		return mbe_tracking;
	}



	public void setMbe_tracking(String mbe_tracking) {
		this.mbe_tracking = mbe_tracking;
	}



	public double getPurchase_price() {
		return purchase_price;
	}



	public void setPurchase_price(double purchase_price) {
		this.purchase_price = purchase_price;
	}


	 

}
