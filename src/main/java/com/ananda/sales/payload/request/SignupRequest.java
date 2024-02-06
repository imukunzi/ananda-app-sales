package com.ananda.sales.payload.request;

import java.util.Set;

import javax.validation.constraints.*;
 
public class SignupRequest {
	@NotBlank
	@Email
    @Size(min = 1, max = 1000)
    private String username;
 
    //@NotBlank
    @Size(max = 1000)
    @Email
    private String email;
    
    private Set<String> role;
    
    //@NotBlank
    //@Size(min = 6, max = 400)
    private String password;
    
    @NotBlank
    @Size(min = 1, max = 1000)
    private String firstname;
    
    @NotBlank
    @Size(min = 1, max = 1000)
    private String lastname;
    
    @NotBlank
    @Size(min = 1, max = 10)
    private String phone;
    
    @NotBlank
    @Size(min = 1, max = 20)
    private String account;
    
    @NotBlank
    @Size(min = 1, max = 40)
    private String user_group;
    
    @NotBlank
    @Size(min = 1, max = 20)
    private String password_expiration_date;
    
    @NotBlank
    //@Size(min = 1, max = 5)
    private String report_duration;
    
    @NotBlank
    //@Size(min = 1, max = 5)
    private String disabled;
    
    @NotBlank
    //@Size(min = 1, max = 5)
    private String otp_enabled;
    
    
  
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
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
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

