package com.ananda.sales.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


	
	@Entity(name="products")
	@Table(name="products")
	public class Products {
		
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "pid")
		private long id;
		
		@Column(name = "code")
		private String code;
				 
		@Column(name = "name")
		private String name;
		
		@Column(name = "description")
		private String description;
		
		 
		@Column(name = "min_price")
		private int min_price;
		
		 
		@Column(name = "max_price")
		private int max_price;
		
		@Column(name = "suply_price")
		private int suply_price;
		
		@Column(name = "color")
		private String color;
		
		@Column(name = "size")
		private String size;
		
		@Column(name = "remark")
		private String remark;
		
				
		@Column(name = "username")
		private String username;
		
		@Column(name = "goldenstand")
		private String goldenStand;
		
		@Column(name = "classicstand")
		private String classicStand;
		
		@Column(name = "businesstand")
		private String businesStand;
		
		@Column(name = "economicstand")
		private String economicStand;
		
		@Column(name = "kidstand")
		private String kidStand;
		
		@Column(name = "shoesstand")
		private String shoesStand;
		
		@Column(name = "watchstand")
		private String watchStand;
		
		@Column(name = "womenstand")
		private String womenStand;
		
		@Column(name = "confirmation")
		private String confirmation;
		
		@Lob
		private byte[] image;
		
		

		public Products() {
			 
		}



		public Products(String code, String name, String description, int min_price, int max_price,int suply_price, String color,
				String size, String remark, String username, String goldenStand,
				String classicStand, String businesStand, String economicStand, String kidStand, String shoesStand,
				String watchStand, String womenStand,String confirmation) {
			super();
			this.code = code;
			this.name = name;
			this.description = description;
			this.min_price = min_price;
			this.max_price = max_price;
			this.suply_price=suply_price;
			this.color = color;
			this.size = size;
			this.remark = remark;
			
			this.username = username;
			this.goldenStand = goldenStand;
			this.classicStand = classicStand;
			this.businesStand = businesStand;
			this.economicStand = economicStand;
			this.kidStand = kidStand;
			this.shoesStand = shoesStand;
			this.watchStand = watchStand;
			this.womenStand = womenStand;
			this.confirmation= confirmation;
			
		}



		public long getId() {
			return id;
		}



		public void setId(long id) {
			this.id = id;
		}



		public String getCode() {
			return code;
		}



		public void setCode(String code) {
			this.code = code;
		}



		public String getName() {
			return name;
		}



		public void setName(String name) {
			this.name = name;
		}



		public String getDescription() {
			return description;
		}



		public void setDescription(String description) {
			this.description = description;
		}



		public int getMin_price() {
			return min_price;
		}



		public void setMin_price(int min_price) {
			this.min_price = min_price;
		}



		public int getMax_price() {
			return max_price;
		}



		public void setMax_price(int max_price) {
			this.max_price = max_price;
		}



		public String getColor() {
			return color;
		}



		public void setColor(String color) {
			this.color = color;
		}



		public String getSize() {
			return size;
		}



		public void setSize(String size) {
			this.size = size;
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



		public String getGoldenStand() {
			return goldenStand;
		}



		public void setGoldenStand(String goldenStand) {
			this.goldenStand = goldenStand;
		}



		public String getClassicStand() {
			return classicStand;
		}



		public void setClassicStand(String classicStand) {
			this.classicStand = classicStand;
		}



		public String getBusinesStand() {
			return businesStand;
		}



		public void setBusinesStand(String businesStand) {
			this.businesStand = businesStand;
		}



		public String getEconomicStand() {
			return economicStand;
		}



		public void setEconomicStand(String economicStand) {
			this.economicStand = economicStand;
		}



		public String getKidStand() {
			return kidStand;
		}



		public void setKidStand(String kidStand) {
			this.kidStand = kidStand;
		}



		public String getShoesStand() {
			return shoesStand;
		}



		public void setShoesStand(String shoesStand) {
			this.shoesStand = shoesStand;
		}



		public String getWatchStand() {
			return watchStand;
		}



		public void setWatchStand(String watchStand) {
			this.watchStand = watchStand;
		}



		public String getWomenStand() {
			return womenStand;
		}



		public void setWomenStand(String womenStand) {
			this.womenStand = womenStand;
		}



		public String getConfirmation() {
			return confirmation;
		}



		public void setConfirmation(String confirmation) {
			this.confirmation = confirmation;
		}
		
		



		public byte[] getImage() {
			return image;
		}



		public void setImage(byte[] image) {
			this.image = image;
		}



		public int getSuply_price() {
			return suply_price;
		}



		public void setSuply_price(int suply_price) {
			this.suply_price = suply_price;
		}



		@Override
		public String toString() {
			return "Products [id=" + id + ", code=" + code + ", name=" + name + ", description=" + description
					+ ", min_price=" + min_price + ", max_price=" + max_price + ", suply_price=" + suply_price
					+ ", color=" + color + ", size=" + size + ", remark=" + remark + ", username=" + username
					+ ", goldenStand=" + goldenStand + ", classicStand=" + classicStand + ", businesStand="
					+ businesStand + ", economicStand=" + economicStand + ", kidStand=" + kidStand + ", shoesStand="
					+ shoesStand + ", watchStand=" + watchStand + ", womenStand=" + womenStand + ", confirmation="
					+ confirmation + ", image=" + Arrays.toString(image) + "]";
		}




		

}
