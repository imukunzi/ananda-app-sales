package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "stock_level")
@Table(name = "stock_level")
public class StockLevel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "code")
	private String code;

	@Column(name = "pid")
	private Integer pid;

	@Column(name = "name")
	private String name;

	@Column(name = "color")
	private String color;

	@Column(name = "size")
	private String size;

	@Column(name = "stockname")
	private String stockname;

	@Column(name = "current_stock_qty")
	private Integer current_stock_qty;

	@Column(name = "status")
	private String status;
	
	@Column(name = "updated_from_regacy")
	private String updated_from_regacy;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "description")
	private String description;

	public StockLevel() {
		super();
	}

	public StockLevel(String code, Integer pid, String name, String color, String size, String stockname,
			Integer current_stock_qty, String status,String updated_from_regacy,double price,String description) {
		super();
		this.code = code;
		this.pid = pid;
		this.name = name;
		this.color = color;
		this.size = size;
		this.stockname = stockname;
		this.current_stock_qty = current_stock_qty;
		this.status = status;
		this.updated_from_regacy=updated_from_regacy;
		this.price=price;
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

	public String getUpdated_from_regacy() {
		return updated_from_regacy;
	}

	public void setUpdated_from_regacy(String updated_from_regacy) {
		this.updated_from_regacy = updated_from_regacy;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "StockLevel [id=" + id + ", code=" + code + ", pid=" + pid + ", name=" + name + ", color=" + color
				+ ", size=" + size + ", stockname=" + stockname + ", current_stock_qty=" + current_stock_qty
				+ ", status=" + status + ", updated_from_regacy=" + updated_from_regacy + ", price=" + price
				+ ", description=" + description + "]";
	}

	

	
	
}
