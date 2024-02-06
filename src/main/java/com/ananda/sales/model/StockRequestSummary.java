package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="stock_request_summary")
@Table(name="stock_request_summary")
public class StockRequestSummary {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
	@Column(name = "stand")
	private String stand;
	
	@Column(name = "date1")
	private String date1;
	
	
	@Column(name = "stockkeeper")
	private String stockkeeper;
	
 	
	@Column(name = "comment")
	private String comment;
	
	@Column(name = "transfer_type")
	private String transfer_type;
	
	
	 
	
	@Column(name = "stock_type")
	private String stock_type;
	
	@Column(name = "stockentryid")
	private String stockEntryId;
	
 
	
	@Column(name = "location")
	private String location;
	
	 	
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
	
	@Column(name = "printing_version")
	private String printing_version;
	
	

	public StockRequestSummary() {
		 
	}

	public StockRequestSummary(String stand, String date1, String stockkeeper, String comment,
			String transfer_type, String stock_type, String stockEntryId, String location,
			String stand_supply_request_date, String stand_supply_request_officer,
			String business_operation_manager_name, String business_operation_manager_approval,
			String business_operation_manager_approval_date, String branch_manager_name, String branch_manager_approval,
			String branch_manager_approval_date, String stockkeeper_name, String stockkeeper_approval,
			String stockkeeper_approval_date, int stockkeeper_approval_qty, String stand_receiption_confirmation,
			String stand_receiption_confirmation_date, String request_Status,String printing_version) {
		 
		this.stand = stand;
		this.date1 = date1;
		this.stockkeeper = stockkeeper;
		this.comment = comment;
		this.transfer_type = transfer_type;
		this.stock_type = stock_type;
		this.stockEntryId = stockEntryId;
		this.location = location;
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
		this.printing_version=printing_version;
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

	public String getStockkeeper() {
		return stockkeeper;
	}

	public void setStockkeeper(String stockkeeper) {
		this.stockkeeper = stockkeeper;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getPrinting_version() {
		return printing_version;
	}

	public void setPrinting_version(String printing_version) {
		this.printing_version = printing_version;
	}

	@Override
	public String toString() {
		return "StockRequestSummary [id=" + id + ", stand=" + stand + ", date1=" + date1 + ", stockkeeper="
				+ stockkeeper + ", comment=" + comment + ", transfer_type=" + transfer_type + ", stock_type="
				+ stock_type + ", stockEntryId=" + stockEntryId + ", location=" + location
				+ ", stand_supply_request_date=" + stand_supply_request_date + ", stand_supply_request_officer="
				+ stand_supply_request_officer + ", business_operation_manager_name=" + business_operation_manager_name
				+ ", business_operation_manager_approval=" + business_operation_manager_approval
				+ ", business_operation_manager_approval_date=" + business_operation_manager_approval_date
				+ ", branch_manager_name=" + branch_manager_name + ", branch_manager_approval="
				+ branch_manager_approval + ", branch_manager_approval_date=" + branch_manager_approval_date
				+ ", stockkeeper_name=" + stockkeeper_name + ", stockkeeper_approval=" + stockkeeper_approval
				+ ", stockkeeper_approval_date=" + stockkeeper_approval_date + ", stockkeeper_approval_qty="
				+ stockkeeper_approval_qty + ", stand_receiption_confirmation=" + stand_receiption_confirmation
				+ ", stand_receiption_confirmation_date=" + stand_receiption_confirmation_date + ", request_Status="
				+ request_Status + ", printing_version=" + printing_version + "]";
	}

	
	
	

}
