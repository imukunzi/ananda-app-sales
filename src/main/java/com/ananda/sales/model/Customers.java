package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="customers")
@Table(name="customers")
public class Customers {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
	@Column(name = "fullname")
	private String fullname;
	
	@Column(name = "contact")
	private String contact;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "other")
	private String other;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "username")
	private String username;
	

	public Customers() {
		 
	}


	public Customers(String fullname, String contact, String phone, String email, String other, String remark,String username) {
		 
		this.fullname = fullname;
		this.contact = contact;
		this.phone = phone;
		this.email = email;
		this.other = other;
		this.remark = remark;
		this.username = username;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getFullname() {
		return fullname;
	}


	public void setFullname(String fullname) {
		this.fullname = fullname;
	}


	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getOther() {
		return other;
	}


	public void setOther(String other) {
		this.other = other;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	@Override
	public String toString() {
		return "Customers [id=" + id + ", fullname=" + fullname + ", contact=" + contact + ", phone=" + phone
				+ ", email=" + email + ", other=" + other + ", remark=" + remark + ", username=" + username + "]";
	}


	

}
