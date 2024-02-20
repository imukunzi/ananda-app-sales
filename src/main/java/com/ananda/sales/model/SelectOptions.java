package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name="select_options")
@Table(name="select_options",uniqueConstraints = { 
		@UniqueConstraint(columnNames = "value") 
	})
public class SelectOptions {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "value")
	private String value;
	
	@Column(name = "select_order")
	private String select_order;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "other")
	private String other;
	
	@Column(name = "saller_access_code")
	private String saller_access_code;
	
	@Column(name = "saller_percentage")
	private double saller_percentage=0;


	public SelectOptions() {
		 
	}

	public SelectOptions(String category, String value, String select_order, String username,String other,String saller_access_code) {
		super();
		this.category = category;
		this.value = value;
		this.select_order = select_order;
		this.username = username;
		this.other=other;
		this.saller_access_code=saller_access_code;
	}
	
	public SelectOptions(String category, String value, String select_order, String username,String other,double saller_percentage) {
		super();
		this.category = category;
		this.value = value;
		this.select_order = select_order;
		this.username = username;
		this.other=other;
		this.saller_percentage = saller_percentage;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSelect_order() {
		return select_order;
	}

	public void setSelect_order(String select_order) {
		this.select_order = select_order;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getSaller_access_code() {
		return saller_access_code;
	}

	public void setSaller_access_code(String saller_access_code) {
		this.saller_access_code = saller_access_code;
	}

	public double getSaller_percentage() {
		return saller_percentage;
	}

	public void setSaller_percentage(double saller_percentage) {
		this.saller_percentage = saller_percentage;
	}

	@Override
	public String toString() {
		return "SelectOptions [id=" + id + ", category=" + category + ", value=" + value + ", select_order="
				+ select_order + ", username=" + username + ", other=" + other + ", saller_access_code="
				+ saller_access_code + ", saller_percentage=" + saller_percentage + "]";
	}

	
	
	

}
