package com.ananda.sales.model;

public class User_roles_list {
	
	private int id;
	private int user_id;
	private int role_id;
	private String name;
	private String firstname;
	private String lastname;
	
	
	public User_roles_list() {
		 
	}


	public User_roles_list(int id,int user_id, int role_id, String name, String firstname, String lastname) {
		 
		this.id=id;
		this.user_id = user_id;
		this.role_id = role_id;
		this.name = name;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public int getRole_id() {
		return role_id;
	}


	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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


	@Override
	public String toString() {
		return "User_roles_list [id=" + id + ", user_id=" + user_id + ", role_id=" + role_id + ", name=" + name
				+ ", firstname=" + firstname + ", lastname=" + lastname + "]";
	}


			
	

}
