package com.ananda.sales.model;

import javax.persistence.Column;

public class MemberDetails {
	
	private String account;
	private String firstname;
	private String lastname;
	private String other;
	private String user_group;
	private String password_expiration_date;
	private String report_duration;
	private String disabled;
	private String otp_enabled;
	
	
	public MemberDetails(String account, String firstname, String lastname, String other, String user_group,
			String password_expiration_date, String report_duration, String disabled, String otp_enabled) {
		 
		this.account = account;
		this.firstname = firstname;
		this.lastname = lastname;
		this.other = other;
		this.user_group = user_group;
		this.password_expiration_date = password_expiration_date;
		this.report_duration = report_duration;
		this.disabled = disabled;
		this.otp_enabled = otp_enabled;
	}


	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
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


	public String getOther() {
		return other;
	}


	public void setOther(String other) {
		this.other = other;
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
	
	
	

}
