package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="delivery")
@Table(name="delivery")
public class Delivery {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
	@Column(name = "oderdate")
	private String orderDate;
	
	@Column(name = "customer")
	private String customer;
	
	@Column(name = "orderno")
	private String orderNo;
	
	@Column(name = "stock")
	private String stock;
	
	@Column(name = "item")
	private String item;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "size")
	private String size;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "pid")
	private int pid;
	
	@Column(name = "qtyorderd")
	private int qtyOrdered;
	
	@Column(name = "qtytodeliver")
	private int qtyToDeliver;
	
	@Column(name = "qtydelivered")
	private int qtyDelivered;
	
	@Column(name = "lastqtydelivered")
	private int lastQtyDelivered;
	
	@Column(name = "qtyremaining")
	private int qtyRemaining;
	
	@Column(name = "paymentstatus")
	private String paymentStatus;
	
	@Column(name = "deliverystatus")
	private String deliveryStatus;
	
	@Column(name = "locked")
	private String locked;
	
	@Column(name = "deliveredate")
	private String deliveryDate;
	
	@Column(name = "createdby")
	private String createdBy;
	
	@Column(name = "deliveredby")
	private String deliveredBy;
	
	@Column(name = "location")
	private String location;
	
	public Delivery() {
		
	}

	public Delivery(String orderDate, String customer, String orderNo, String stock, String item,String code,String size,String color,int pid, int qtyOrdered,
			int qtyToDeliver, int qtyDelivered, int lastQtyDelivered, int qtyRemaining, String paymentStatus,
			String deliveryStatus, String locked, String deliveryDate, String createdBy, String deliveredBy,String location) {
		super();
		this.orderDate = orderDate;
		this.customer = customer;
		this.orderNo = orderNo;
		this.stock = stock;
		this.item = item;
		this.code=code;
		this.size=size;
		this.color=color;
		this.pid=pid;
		this.qtyOrdered = qtyOrdered;
		this.qtyToDeliver = qtyToDeliver;
		this.qtyDelivered = qtyDelivered;
		this.lastQtyDelivered = lastQtyDelivered;
		this.qtyRemaining = qtyRemaining;
		this.paymentStatus = paymentStatus;
		this.deliveryStatus = deliveryStatus;
		this.locked = locked;
		this.deliveryDate = deliveryDate;
		this.createdBy = createdBy;
		this.deliveredBy = deliveredBy;
		this.location=location;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyOrdered(int qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public int getQtyToDeliver() {
		return qtyToDeliver;
	}

	public void setQtyToDeliver(int qtyToDeliver) {
		this.qtyToDeliver = qtyToDeliver;
	}

	public int getQtyDelivered() {
		return qtyDelivered;
	}

	public void setQtyDelivered(int qtyDelivered) {
		this.qtyDelivered = qtyDelivered;
	}

	public int getLastQtyDelivered() {
		return lastQtyDelivered;
	}

	public void setLastQtyDelivered(int lastQtyDelivered) {
		this.lastQtyDelivered = lastQtyDelivered;
	}

	public int getQtyRemaining() {
		return qtyRemaining;
	}

	public void setQtyRemaining(int qtyRemaining) {
		this.qtyRemaining = qtyRemaining;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDeliveredBy() {
		return deliveredBy;
	}

	public void setDeliveredBy(String deliveredBy) {
		this.deliveredBy = deliveredBy;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	

	
}
