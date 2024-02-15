package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "stock_initial")
@Table(name = "stock_initial")
public class StockInitial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "code")
	private String code;

	@Column(name = "pid")
	private int pid;

	@Column(name = "name")
	private String name;

	@Column(name = "color")
	private String color;

	@Column(name = "size")
	private String size;

	@Column(name = "stockname")
	private String stockname;
	
	@Column(name = "date")
	private String date;

	@Column(name = "current_stock_qty")
	private int current_stock_qty;

	@Column(name = "status")
	private String status;
	
		
	@Column(name = "price")
	private double price;
	
	@Column(name = "qty_out")
	private int qty_out;
	
	@Column(name = "price_out")
	private double price_out;
	
	@Column(name = "time_recorded")
	private String time_recorded;
	
	@Column(name = "description")
	private String description;

	public StockInitial() {
		 
	}

	public StockInitial(String code, int pid, String name, String color, String size, String stockname, String date,
			int current_stock_qty, String status, double price, int qty_out, int price_out,String time_recorded,String description) {
		super();
		this.code = code;
		this.pid = pid;  
		this.name = name;
		this.color = color;
		this.size = size;
		this.stockname = stockname;
		this.date = date;
		this.current_stock_qty = current_stock_qty;
		this.status = status;
		this.price = price;
		this.qty_out = qty_out;
		this.price_out = price_out;
		this.time_recorded=time_recorded;
		this.description=description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getStockname() {
		return stockname;
	}

	public void setStockname(String stockname) {
		this.stockname = stockname;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getCurrent_stock_qty() {
		return current_stock_qty;
	}

	public void setCurrent_stock_qty(Integer current_stock_qty) {
		this.current_stock_qty = current_stock_qty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getQty_out() {
		return qty_out;
	}

	public void setQty_out(Integer qty_out) {
		this.qty_out = qty_out;
	}

	public double getPrice_out() {
		return price_out;
	}

	public void setPrice_out(double price_out) {
		this.price_out = price_out;
	}

	
	public String getTime_recorded() {
		return time_recorded;
	}

	public void setTime_recorded(String time_recorded) {
		this.time_recorded = time_recorded;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public void setCurrent_stock_qty(int current_stock_qty) {
		this.current_stock_qty = current_stock_qty;
	}

	public void setQty_out(int qty_out) {
		this.qty_out = qty_out;
	}

		
	
	
}
