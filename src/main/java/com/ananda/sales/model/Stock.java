package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="stock")
@Table(name="stock")
public class Stock {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
	@Column(name = "stand")
	private String stand;
	
	@Column(name = "date1")
	private String date1;
	
	@Column(name = "pid")
	private int pid;
	
	@Column(name = "product")
	private String product;
	
	@Column(name = "qty_in")
	private Integer qty_in;
	
	@Column(name = "stockkeeper")
	private String stockkeeper;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "size")
	private String size;
	
	@Column(name = "qty_out")
	private Integer qty_out;
	
	@Column(name = "qty_transfer_in")
	private Integer qty_transfer_in;
	
	@Column(name = "qty_transfer_out")
	private Integer qty_transfer_out;
	
	@Column(name = "qty_damage")
	private Integer qty_damage;
	
	@Column(name = "stand_destination")
	private String stand_destination;
	
	@Column(name = "stand_source")
	private String stand_source;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "comment")
	private String comment;
	
	@Column(name = "transfer_type")
	private String transfer_type;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "stock_type")
	private String stock_type;
	
	@Column(name = "stockentryid")
	private String stockEntryId;
	
	@Column(name = "returned")
	private int returned;
	
	@Column(name = "confirmation")
	private String confirmation;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "qty_requested")
	private int qty_requested;
	
	@Column(name = "stand_supply_request_date")
	private String stand_supply_request_date;
	
	@Column(name = "stand_supply_request_officer")
	private String stand_supply_request_officer;
	
	@Column(name = "business_operation_manager_name")
	private String business_operation_manager_name;
	
	@Column(name = "business_operation_manager_approval")
	private String business_operation_manager_approval;
	
	@Column(name = "business_operation_manager_approval_date")
	private String business_operation_manager_approval_date;
	
	@Column(name = "branch_manager_name")
	private String branch_manager_name;
	
	@Column(name = "branch_manager_approval")
	private String branch_manager_approval;
	
	@Column(name = "branch_manager_approval_date")
	private String branch_manager_approval_date;
	
	@Column(name = "stockkeeper_name")
	private String stockkeeper_name;
	
	@Column(name = "stockkeeper_approval")
	private String stockkeeper_approval;
	
	@Column(name = "stockkeeper_approval_date")
	private String stockkeeper_approval_date;
	
	@Column(name = "stockkeeper_approval_qty")
	private int stockkeeper_approval_qty;
	
	@Column(name = "stand_receiption_confirmation")
	private String stand_receiption_confirmation;
	
	@Column(name = "stand_receiption_confirmation_date")
	private String stand_receiption_confirmation_date;
	
	@Column(name = "request_Status")
	private String request_Status;
	
	@Column(name = "price_value")
	private double price_value;

	public Stock() {
		 
	}

	public Stock(String stand, String date1, int pid, String product, Integer qty_in, String stockkeeper,
			String code, String size, Integer qty_out, Integer qty_transfer_in, Integer qty_transfer_out,
			Integer qty_damage, String stand_destination, String stand_source, String description, String comment,
			String transfer_type, String color, String stock_type,String stockEntryId,int returned,String confirmation,String location,double price_value) {
		super();
		 
		this.stand = stand;
		this.date1 = date1;
		this.pid = pid;
		this.product = product;
		this.qty_in = qty_in;
		this.stockkeeper = stockkeeper;
		this.code = code;
		this.size = size;
		this.qty_out = qty_out;
		this.qty_transfer_in = qty_transfer_in;
		this.qty_transfer_out = qty_transfer_out;
		this.qty_damage = qty_damage;
		this.stand_destination = stand_destination;
		this.stand_source = stand_source;
		this.description = description;
		this.comment = comment;
		this.transfer_type = transfer_type;
		this.color = color;
		this.stock_type = stock_type;
		this.stockEntryId=stockEntryId;
		this.returned=returned;
		this.confirmation=confirmation;
		this.location=location;
		this.price_value=price_value;
	}
	
	
	//updated stock object

	public Stock(String stand, String date1, int pid, String product, Integer qty_in, String stockkeeper, String code,
			String size, Integer qty_out, Integer qty_transfer_in, Integer qty_transfer_out, Integer qty_damage,
			String stand_destination, String stand_source, String description, String comment, String transfer_type,
			String color, String stock_type, String stockEntryId, int returned, String confirmation, String location,
			int qty_requested, String stand_supply_request_date, String stand_supply_request_officer,
			String business_operation_manager_name, String business_operation_manager_approval,
			String business_operation_manager_approval_date, String branch_manager_name, String branch_manager_approval,
			String branch_manager_approval_date, String stockkeeper_name, String stockkeeper_approval,
			String stockkeeper_approval_date, int stockkeeper_approval_qty, String stand_receiption_confirmation,
			String stand_receiption_confirmation_date, String request_Status,double price_value) {
		 
		this.stand = stand;
		this.date1 = date1;
		this.pid = pid;
		this.product = product;
		this.qty_in = qty_in;
		this.stockkeeper = stockkeeper;
		this.code = code;
		this.size = size;
		this.qty_out = qty_out;
		this.qty_transfer_in = qty_transfer_in;
		this.qty_transfer_out = qty_transfer_out;
		this.qty_damage = qty_damage;
		this.stand_destination = stand_destination;
		this.stand_source = stand_source;
		this.description = description;
		this.comment = comment;
		this.transfer_type = transfer_type;
		this.color = color;
		this.stock_type = stock_type;
		this.stockEntryId = stockEntryId;
		this.returned = returned;
		this.confirmation = confirmation;
		this.location = location;
		this.qty_requested = qty_requested;
		this.stand_supply_request_date = stand_supply_request_date;
		this.stand_supply_request_officer = stand_supply_request_officer;
		this.business_operation_manager_name = business_operation_manager_name;
		this.business_operation_manager_approval = business_operation_manager_approval;
		this.business_operation_manager_approval_date = business_operation_manager_approval_date;
		this.branch_manager_name = branch_manager_name;
		this.branch_manager_approval = branch_manager_approval;
		this.branch_manager_approval_date = branch_manager_approval_date;
		this.stockkeeper_name = stockkeeper_name;
		this.stockkeeper_approval = stockkeeper_approval;
		this.stockkeeper_approval_date = stockkeeper_approval_date;
		this.stockkeeper_approval_qty = stockkeeper_approval_qty;
		this.stand_receiption_confirmation = stand_receiption_confirmation;
		this.stand_receiption_confirmation_date = stand_receiption_confirmation_date;
		this.request_Status = request_Status;
		this.price_value=price_value;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStand() {
		return stand;
	}

	public void setStand(String stand) {
		this.stand = stand;
	}

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Integer getQty_in() {
		return qty_in;
	}

	public void setQty_in(Integer qty_in) {
		this.qty_in = qty_in;
	}

	public String getStockkeeper() {
		return stockkeeper;
	}

	public void setStockkeeper(String stockkeeper) {
		this.stockkeeper = stockkeeper;
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

	public Integer getQty_out() {
		return qty_out;
	}

	public void setQty_out(Integer qty_out) {
		this.qty_out = qty_out;
	}

	public Integer getQty_transfer_in() {
		return qty_transfer_in;
	}

	public void setQty_transfer_in(Integer qty_transfer_in) {
		this.qty_transfer_in = qty_transfer_in;
	}

	public Integer getQty_transfer_out() {
		return qty_transfer_out;
	}

	public void setQty_transfer_out(Integer qty_transfer_out) {
		this.qty_transfer_out = qty_transfer_out;
	}

	public Integer getQty_damage() {
		return qty_damage;
	}

	public void setQty_damage(Integer qty_damage) {
		this.qty_damage = qty_damage;
	}

	public String getStand_destination() {
		return stand_destination;
	}

	public void setStand_destination(String stand_destination) {
		this.stand_destination = stand_destination;
	}

	public String getStand_source() {
		return stand_source;
	}

	public void setStand_source(String stand_source) {
		this.stand_source = stand_source;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTransfer_type() {
		return transfer_type;
	}

	public void setTransfer_type(String transfer_type) {
		this.transfer_type = transfer_type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getStock_type() {
		return stock_type;
	}

	public void setStock_type(String stock_type) {
		this.stock_type = stock_type;
	}
	
	

	public String getStockEntryId() {
		return stockEntryId;
	}

	public void setStockEntryId(String stockEntryId) {
		this.stockEntryId = stockEntryId;
	}
	
	

	public int getReturned() {
		return returned;
	}

	public void setReturned(int returned) {
		this.returned = returned;
	}
	
	

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}
	
	

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getQty_requested() {
		return qty_requested;
	}

	public void setQty_requested(int qty_requested) {
		this.qty_requested = qty_requested;
	}

	public String getStand_supply_request_date() {
		return stand_supply_request_date;
	}

	public void setStand_supply_request_date(String stand_supply_request_date) {
		this.stand_supply_request_date = stand_supply_request_date;
	}

	public String getStand_supply_request_officer() {
		return stand_supply_request_officer;
	}

	public void setStand_supply_request_officer(String stand_supply_request_officer) {
		this.stand_supply_request_officer = stand_supply_request_officer;
	}

	public String getBusiness_operation_manager_name() {
		return business_operation_manager_name;
	}

	public void setBusiness_operation_manager_name(String business_operation_manager_name) {
		this.business_operation_manager_name = business_operation_manager_name;
	}

	public String getBusiness_operation_manager_approval() {
		return business_operation_manager_approval;
	}

	public void setBusiness_operation_manager_approval(String business_operation_manager_approval) {
		this.business_operation_manager_approval = business_operation_manager_approval;
	}

	public String getBusiness_operation_manager_approval_date() {
		return business_operation_manager_approval_date;
	}

	public void setBusiness_operation_manager_approval_date(String business_operation_manager_approval_date) {
		this.business_operation_manager_approval_date = business_operation_manager_approval_date;
	}

	public String getBranch_manager_name() {
		return branch_manager_name;
	}

	public void setBranch_manager_name(String branch_manager_name) {
		this.branch_manager_name = branch_manager_name;
	}

	public String getBranch_manager_approval() {
		return branch_manager_approval;
	}

	public void setBranch_manager_approval(String branch_manager_approval) {
		this.branch_manager_approval = branch_manager_approval;
	}

	public String getBranch_manager_approval_date() {
		return branch_manager_approval_date;
	}

	public void setBranch_manager_approval_date(String branch_manager_approval_date) {
		this.branch_manager_approval_date = branch_manager_approval_date;
	}

	public String getStockkeeper_name() {
		return stockkeeper_name;
	}

	public void setStockkeeper_name(String stockkeeper_name) {
		this.stockkeeper_name = stockkeeper_name;
	}

	public String getStockkeeper_approval() {
		return stockkeeper_approval;
	}

	public void setStockkeeper_approval(String stockkeeper_approval) {
		this.stockkeeper_approval = stockkeeper_approval;
	}

	public String getStockkeeper_approval_date() {
		return stockkeeper_approval_date;
	}

	public void setStockkeeper_approval_date(String stockkeeper_approval_date) {
		this.stockkeeper_approval_date = stockkeeper_approval_date;
	}

	public int getStockkeeper_approval_qty() {
		return stockkeeper_approval_qty;
	}

	public void setStockkeeper_approval_qty(int stockkeeper_approval_qty) {
		this.stockkeeper_approval_qty = stockkeeper_approval_qty;
	}

	public String getStand_receiption_confirmation() {
		return stand_receiption_confirmation;
	}

	public void setStand_receiption_confirmation(String stand_receiption_confirmation) {
		this.stand_receiption_confirmation = stand_receiption_confirmation;
	}

	public String getStand_receiption_confirmation_date() {
		return stand_receiption_confirmation_date;
	}

	public void setStand_receiption_confirmation_date(String stand_receiption_confirmation_date) {
		this.stand_receiption_confirmation_date = stand_receiption_confirmation_date;
	}

	public String getRequest_Status() {
		return request_Status;
	}

	public void setRequest_Status(String request_Status) {
		this.request_Status = request_Status;
	}

	public double getPrice_value() {
		return price_value;
	}

	public void setPrice_value(double price_value) {
		this.price_value = price_value;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", stand=" + stand + ", date1=" + date1 + ", pid=" + pid + ", product=" + product
				+ ", qty_in=" + qty_in + ", stockkeeper=" + stockkeeper + ", code=" + code + ", size=" + size
				+ ", qty_out=" + qty_out + ", qty_transfer_in=" + qty_transfer_in + ", qty_transfer_out="
				+ qty_transfer_out + ", qty_damage=" + qty_damage + ", stand_destination=" + stand_destination
				+ ", stand_source=" + stand_source + ", description=" + description + ", comment=" + comment
				+ ", transfer_type=" + transfer_type + ", color=" + color + ", stock_type=" + stock_type
				+ ", stockEntryId=" + stockEntryId + ", returned=" + returned + ", confirmation=" + confirmation
				+ ", location=" + location + ", qty_requested=" + qty_requested + ", stand_supply_request_date="
				+ stand_supply_request_date + ", stand_supply_request_officer=" + stand_supply_request_officer
				+ ", business_operation_manager_name=" + business_operation_manager_name
				+ ", business_operation_manager_approval=" + business_operation_manager_approval
				+ ", business_operation_manager_approval_date=" + business_operation_manager_approval_date
				+ ", branch_manager_name=" + branch_manager_name + ", branch_manager_approval="
				+ branch_manager_approval + ", branch_manager_approval_date=" + branch_manager_approval_date
				+ ", stockkeeper_name=" + stockkeeper_name + ", stockkeeper_approval=" + stockkeeper_approval
				+ ", stockkeeper_approval_date=" + stockkeeper_approval_date + ", stockkeeper_approval_qty="
				+ stockkeeper_approval_qty + ", stand_receiption_confirmation=" + stand_receiption_confirmation
				+ ", stand_receiption_confirmation_date=" + stand_receiption_confirmation_date + ", request_Status="
				+ request_Status + ", price_value=" + price_value + "]";
	}

	
		

}

