package com.ananda.sales.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity(name="users")
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 100)
	private String username;

	@NotBlank
	@Size(max = 100)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;
	
	@NotBlank
	//@Size(max = 120)
	private String firstname;
	
	@NotBlank
	//@Size(max = 120)
	private String lastname;
	
	@NotBlank
	//@Size(max = 10)
	private String phone;
	
	@NotBlank
	//@Size(max = 20)
	private String account;
	
	//@NotBlank
	//@Size(max = 20)
	private String user_group;
	
	//@NotBlank
	//@Size(max = 20)
	private String password_expiration_date;
	
	 
	private String report_duration;
	
	private String disabled;
	
	private String otp_enabled;
		

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	@Column(name = "reset_password_token")
    private String resetPasswordToken;

	public User() {
	}

	public User(String username, String email, String password,String firstname,String lastname,String phone,String account,String user_group,String password_expiration_date,String report_duration,String disabled,String otp_enabled) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
		this.account =account;
		this.user_group=user_group;
		this.report_duration=report_duration;
		this.password_expiration_date=password_expiration_date;
		this.report_duration=report_duration;
		this.disabled=disabled;
		this.otp_enabled=otp_enabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	
	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
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
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", firstname=" + firstname + ", lastname=" + lastname + ", phone=" + phone + ", account=" + account
				+ ", user_group=" + user_group + ", password_expiration_date=" + password_expiration_date
				+ ", report_duration=" + report_duration + ", disabled=" + disabled + ", otp_enabled=" + otp_enabled
				+ ", roles=" + roles + ", resetPasswordToken=" + resetPasswordToken + "]";
	}

	
	
	
		 
}
