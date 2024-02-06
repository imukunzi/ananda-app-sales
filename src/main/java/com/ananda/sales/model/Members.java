package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class Members {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "email")
	private String email;

	@NotBlank
	@Column(name = "firstname")
	private String firstname;

	@NotBlank
	@Column(name = "lastname")
	private String lastname;

	@NotBlank
	@Column(name = "account")
	private String account;

	@Column(name = "phone")
	private String phone;

	@Column(name = "joiningdate")
	private String joiningdate;

	@Column(name = "user_group")
	private String user_group;
	 
	@Column(name = "password_expiration_date")
	private String password_expiration_date;

	@Column(name = "report_duration")
	private String report_duration;
	
	@Column(name = "disabled")
	private String disabled;
	
	@Column(name = "otp_enabled")
	private String otp_enabled;

	public Members() {

	}

	public Members(String email, @NotBlank String firstname, @NotBlank String lastname, @NotBlank String account,
			String phone, String joiningdate,String user_group,String password_expiration_date,String report_duration,String disabled,String otp_enabled) {
		super();
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.account = account;
		this.phone = phone;
		this.joiningdate = joiningdate;
		this.user_group=user_group;
		this.password_expiration_date=password_expiration_date;
		this.report_duration=report_duration;
		this.disabled=disabled;
		this.otp_enabled=otp_enabled;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getJoiningdate() {
		return joiningdate;
	}

	public void setJoiningdate(String joiningdate) {
		this.joiningdate = joiningdate;
	}

	public String getUser_group() {
		return user_group;
	}

	public void setUser_group(String user_group) {
		this.user_group = user_group;
	}

	public String getPassword_expiration_date() {
		return password_expiration_date;
	}

	public void setPassword_expiration_date(String password_expiration_date) {
		this.password_expiration_date = password_expiration_date;
	}

	public String getReport_duration() {
		return report_duration;
	}

	public void setReport_duration(String report_duration) {
		this.report_duration = report_duration;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getOtp_enabled() {
		return otp_enabled;
	}

	public void setOtp_enabled(String otp_enabled) {
		this.otp_enabled = otp_enabled;
	}

	@Override
	public String toString() {
		return "Members [id=" + id + ", email=" + email + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", account=" + account + ", phone=" + phone + ", joiningdate=" + joiningdate + ", user_group="
				+ user_group + ", password_expiration_date=" + password_expiration_date + ", report_duration="
				+ report_duration + ", disabled=" + disabled + ", otp_enabled=" + otp_enabled + "]";
	}

			

}
