package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="prevision")
@Table(name="prevision")
public class Prevision {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "year")
	private int year;
	
	@Column(name = "description")
	private String description;
	
	 
	@Column(name = "type")
	private String type;
	
	@Column(name = "january")
	private double  january=0;
	
	@Column(name = "february")
	private double february=0;
	
	 
	@Column(name = "march")
	private double march=0;
	
	@Column(name = "april")
	private double april=0;
	
	@Column(name = "may")
	private double may=0;
	
	@Column(name = "june")
	private double june=0;
	
	 
	@Column(name = "july")
	private double july=0;
	
	@Column(name = "augostin")
	private double augostin=0;
	
	@Column(name = "september")
	private double september=0;
	
	@Column(name = "octomber")
	private double octomber=0;
	
	@Column(name = "november")
	private double november=0;
	
	 
	@Column(name = "december")
	private double december=0;

//constructors
	public Prevision() {
		super();
	}


	public Prevision(String username, int year, String description, String type, double january, double february,
			double march, double april, double may, double june, double july, double augostin, double september,
			double octomber, double november, double december) {
		super();
		this.username = username;
		this.year = year;
		this.description = description;
		this.type = type;
		this.january = january;
		this.february = february;
		this.march = march;
		this.april = april;
		this.may = may;
		this.june = june;
		this.july = july;
		this.augostin = augostin;
		this.september = september;
		this.octomber = octomber;
		this.november = november;
		this.december = december;
	}

//getter & setter
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public double getJanuary() {
		return january;
	}


	public void setJanuary(double january) {
		this.january = january;
	}


	public double getFebruary() {
		return february;
	}


	public void setFebruary(double february) {
		this.february = february;
	}


	public double getMarch() {
		return march;
	}


	public void setMarch(double march) {
		this.march = march;
	}


	public double getApril() {
		return april;
	}


	public void setApril(double april) {
		this.april = april;
	}


	public double getMay() {
		return may;
	}


	public void setMay(double may) {
		this.may = may;
	}


	public double getJune() {
		return june;
	}


	public void setJune(double june) {
		this.june = june;
	}


	public double getJuly() {
		return july;
	}


	public void setJuly(double july) {
		this.july = july;
	}


	public double getAugostin() {
		return augostin;
	}


	public void setAugostin(double augostin) {
		this.augostin = augostin;
	}


	public double getSeptember() {
		return september;
	}


	public void setSeptember(double september) {
		this.september = september;
	}


	public double getOctomber() {
		return octomber;
	}


	public void setOctomber(double octomber) {
		this.octomber = octomber;
	}


	public double getNovember() {
		return november;
	}


	public void setNovember(double november) {
		this.november = november;
	}


	public double getDecember() {
		return december;
	}


	public void setDecember(double december) {
		this.december = december;
	}

//to String
	@Override
	public String toString() {
		return "Prevision [id=" + id + ", username=" + username + ", year=" + year + ", description=" + description
				+ ", type=" + type + ", january=" + january + ", february=" + february + ", march=" + march + ", april="
				+ april + ", may=" + may + ", june=" + june + ", july=" + july + ", augostin=" + augostin
				+ ", september=" + september + ", octomber=" + octomber + ", november=" + november + ", december="
				+ december + "]";
	}
	
	
	
	
}
