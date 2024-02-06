package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity(name="accesscontrolotp")
@Table(name="accesscontrolotp")
public class AccessControllOtp {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
	//@NotBlank
	@Column(name = "user")
    private String user;
	
	//@NotBlank
	@Column(name = "phone")
    private String phone;
	
	//@NotBlank
	@Column(name = "accesscode")
    private String accessCode;
	
	@NotBlank
	@Column(name = "type")
    private String type;
	
	//@NotBlank
	@Column(name = "other")
    private String other;
	
	//@NotBlank
	@Column(name = "date")
    private String date;
	
	//@NotBlank
	@Column(name = "expiration")//expirationtime
    private String expiration;
	
	//@NotBlank
	@Column(name = "username")
    private String username;

	public AccessControllOtp() {
		 
	}
	
	

	 
	public AccessControllOtp(String user, String phone, String accessCode, @NotBlank String type, String other,
			String date,  String expiration,  String username) {
		super();
		this.user = user;
		this.phone = phone;
		this.accessCode = accessCode;
		this.type = type;
		this.other = other;
		this.date = date;
		this.expiration = expiration;
		this.username = username;
	}




	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "AccessControllOtp [id=" + id + ", user=" + user + ", phone=" + phone + ", accessCode=" + accessCode
				+ ", type=" + type + ", other=" + other + ", date=" + date + ", expiration=" + expiration
				+ ", username=" + username + "]";
	}

	
	
}
