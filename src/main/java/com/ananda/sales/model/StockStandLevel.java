package com.ananda.sales.model;

public class StockStandLevel {
	
	//class for select productds details
    private String pid;
    private String code;
    private String productName;
    private String size;
    private String color;

    public StockStandLevel(String pid, String code, String productName, String size,String color) {
        this.pid = pid;
        this.code = code;
        this.productName = productName;
        this.size = size;
        this.color = color;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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
