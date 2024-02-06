package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="stock_legacy_data")
@Table(name="stock_legacy_data")
public class StockLegacy {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
	@Column(name = "stand")
	private String stand;
	
	@Column(name = "pid")
    private String pid;

	@Column(name = "code")
    private String code;

	@Column(name = "product")
    private String product;

	@Column(name = "size")
    private String size;

	@Column(name = "qty_in")
    private int entry;

	@Column(name = "qty_out")
    private int exit;

	@Column(name = "transfer_in")
    private int transfer_in;

	@Column(name = "transfer_out")
    private int transfer_out;

	@Column(name = "qty_damage")
    private int damange;

	@Column(name = "actualstock")
    private int actualStock;
	
	@Column(name = "color")
    private String color;


	public StockLegacy() {
		super();
	}


	public StockLegacy(String stand, String pid, String code, String product, String size, int entry, int exit,
			int transfer_in, int transfer_out, int damange,int actualStock, String color) {
		super();
		this.stand = stand;
		this.pid = pid;
		this.code = code;
		this.product = product;
		this.size = size;
		this.entry = entry;
		this.exit = exit;
		this.transfer_in = transfer_in;
		this.transfer_out = transfer_out;
		this.damange = damange;
		this.actualStock=actualStock;
		this.color = color;
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


	public String getPid() {
		return pid;
	}


	public void setPid(String pid) {
		this.pid = pid;
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


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public int getEntry() {
		return entry;
	}


	public void setEntry(int entry) {
		this.entry = entry;
	}


	public int getExit() {
		return exit;
	}


	public void setExit(int exit) {
		this.exit = exit;
	}


	public int getTransfer_in() {
		return transfer_in;
	}


	public void setTransfer_in(int transfer_in) {
		this.transfer_in = transfer_in;
	}


	public int getTransfer_out() {
		return transfer_out;
	}


	public void setTransfer_out(int transfer_out) {
		this.transfer_out = transfer_out;
	}


	public int getDamange() {
		return damange;
	}


	public void setDamange(int damange) {
		this.damange = damange;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public int getActualStock() {
		return actualStock;
	}


	public void setActualStock(int actualStock) {
		this.actualStock = actualStock;
	}


	@Override
	public String toString() {
		return "StockLegacy [id=" + id + ", stand=" + stand + ", pid=" + pid + ", code=" + code + ", product=" + product
				+ ", size=" + size + ", entry=" + entry + ", exit=" + exit + ", transfer_in=" + transfer_in
				+ ", transfer_out=" + transfer_out + ", damange=" + damange + ", actualStock=" + actualStock
				+ ", color=" + color + "]";
	}


	

	


	

}
