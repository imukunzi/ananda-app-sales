package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity(name="accesscontrol")
@Table(name="accesscontrol")
public class AccessControl {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
	@NotBlank
	@Column(name = "macaddress")
    private String macAddress;

	@NotBlank
	@Column(name = "user")
    private String user;
	
	
	@Column(name = "longitude")
    private String longitude;
	
	
	@Column(name = "latitude")
    private String latitude;
	
	@NotBlank
	@Column(name = "datecreated")
    private String dateCreated;


	public AccessControl() {
		 
	}


	public AccessControl(@NotBlank String macAddress, @NotBlank String user, String longitude, String latitude,
			@NotBlank String dateCreated) {
		 
		this.macAddress = macAddress;
		this.user = user;
		this.longitude = longitude;
		this.latitude = latitude;
		this.dateCreated = dateCreated;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

	

	
	public String getMacAddress() {
		return macAddress;
	}


	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getDateCreated() {
		return dateCreated;
	}


	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}


	@Override
	public String toString() {
		return "AccessControl [id=" + id + ", macaddress=" + macAddress + ", user=" + user + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", dateCreated=" + dateCreated + "]";
	}

		

}
