package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name="cashing")
@Table(name="cashing")
public class Cashing {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
	@NotBlank
	@Column(name = "date")
    private String date;

	@NotBlank
	@Column(name = "cashier")
    private String cashier;

	@NotNull(message = "Please enter Amount")
	@Column(name = "amount"  )
	private double amount;

	@Column(name = "amount2") 
    private String amount2;

	@Column(name = "headcashier")
    private String headCashier;
	
	@Column(name = "headcashieramount")
    private double headCashierAmount;


	@Column(name = "comment")
    private String comment;

	 
	@Column(name = "fivethousands")
    private double fiveThousands;

	 
	@Column(name = "twothousands")
    private double twoThousands;

	 
	@Column(name = "onethousands")
    private double oneThousands;

	 
	@Column(name = "fivehundred" )
    private double fiveHundred;

	 
	@Column(name = "onehundred")
    private double onehundred;

	
	@Column(name = "fifty" )
    private double fifty;

	 
	@Column(name = "twenty")
    private double twenty;

	 
	@Column(name = "ten" )
    private double ten;
	
	 
	@Column(name = "five" )
    private double five;
	
	 
	@Column(name = "two")
    private double two;
	
	 
	@Column(name = "one")
    private double one;

	 
	@Column(name = "euro")
    private double euro;

	 
	@Column(name = "eurorate")
    private double euroRate;

	 
	@Column(name = "euro2" )
    private double euro2;

	 
	@Column(name = "eurorate2")
    private double euroRate2;

	 
	@Column(name = "euro3")
    private double euro3;

	@Column(name = "eurorate3")
    private double euroRate3;

	 
	@Column(name = "euro4")
    private double euro4;

	 
	@Column(name = "eurorate4")
    private double euroRate4;
    

	 
	@Column(name = "dollar")
    private double dollar;

	 
	@Column(name = "dollarrate")
    private double dollarRate;

	 
	@Column(name = "dollar2")
    private double dollar2;

	 
	@Column(name = "dollarrate2")
    private double dollarRate2;

	 
	@Column(name = "dollar3")
    private double dollar3;

	 
	@Column(name = "dollarrate3")
    private double dollarRate3;

	 
	@Column(name = "dollar4")
    private double dollar4;

	 
	@Column(name = "dollarrate4")
    private double dollarRate4;
    
	@Column(name = "caisseinit")
    private double caisseInit;

	@Column(name = "pointername")
    private String pointerName;

	@Column(name = "pointeramount")
    private double pointerAmount;
	
		
	@Column(name = "receivedby")
    private String receivedBy;
	
	 

	public Cashing() {
		super();
	}



	public Cashing(String date, String cashier, double amount, String amount2, String headCashier,
			double headCashierAmount, String comment, double fiveThousands, double twoThousands, double oneThousands,
			double fiveHundred, double onehundred, double fifty, double twenty, double ten, double five, double two,
			double one, double euro, double euroRate, double euro2, double euroRate2, double euro3, double euroRate3,
			double euro4, double euroRate4, double dollar, double dollarRate, double dollar2, double dollarRate2,
			double dollar3, double dollarRate3, double dollar4, double dollarRate4, double caisseInit,
			String pointerName, double pointerAmount, String receivedBy) {
		super();
		this.date = date;
		this.cashier = cashier;
		this.amount = amount;
		this.amount2 = amount2;
		this.headCashier = headCashier;
		this.headCashierAmount = headCashierAmount;
		this.comment = comment;
		this.fiveThousands = fiveThousands;
		this.twoThousands = twoThousands;
		this.oneThousands = oneThousands;
		this.fiveHundred = fiveHundred;
		this.onehundred = onehundred;
		this.fifty = fifty;
		this.twenty = twenty;
		this.ten = ten;
		this.five = five;
		this.two = two;
		this.one = one;
		this.euro = euro;
		this.euroRate = euroRate;
		this.euro2 = euro2;
		this.euroRate2 = euroRate2;
		this.euro3 = euro3;
		this.euroRate3 = euroRate3;
		this.euro4 = euro4;
		this.euroRate4 = euroRate4;
		this.dollar = dollar;
		this.dollarRate = dollarRate;
		this.dollar2 = dollar2;
		this.dollarRate2 = dollarRate2;
		this.dollar3 = dollar3;
		this.dollarRate3 = dollarRate3;
		this.dollar4 = dollar4;
		this.dollarRate4 = dollarRate4;
		this.caisseInit = caisseInit;
		this.pointerName = pointerName;
		this.pointerAmount = pointerAmount;
		this.receivedBy = receivedBy; 
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getCashier() {
		return cashier;
	}



	public void setCashier(String cashier) {
		this.cashier = cashier;
	}



	public double getAmount() {
		return amount;
	}



	public void setAmount(double amount) {
		this.amount = amount;
	}



	public String getAmount2() {
		return amount2;
	}



	public void setAmount2(String amount2) {
		this.amount2 = amount2;
	}



	public String getHeadCashier() {
		return headCashier;
	}



	public void setHeadCashier(String headCashier) {
		this.headCashier = headCashier;
	}



	public double getHeadCashierAmount() {
		return headCashierAmount;
	}



	public void setHeadCashierAmount(double headCashierAmount) {
		this.headCashierAmount = headCashierAmount;
	}



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}



	public double getFiveThousands() {
		return fiveThousands;
	}



	public void setFiveThousands(double fiveThousands) {
		this.fiveThousands = fiveThousands;
	}



	public double getTwoThousands() {
		return twoThousands;
	}



	public void setTwoThousands(double twoThousands) {
		this.twoThousands = twoThousands;
	}



	public double getOneThousands() {
		return oneThousands;
	}



	public void setOneThousands(double oneThousands) {
		this.oneThousands = oneThousands;
	}



	public double getFiveHundred() {
		return fiveHundred;
	}



	public void setFiveHundred(double fiveHundred) {
		this.fiveHundred = fiveHundred;
	}



	public double getOnehundred() {
		return onehundred;
	}



	public void setOnehundred(double onehundred) {
		this.onehundred = onehundred;
	}



	public double getFifty() {
		return fifty;
	}



	public void setFifty(double fifty) {
		this.fifty = fifty;
	}



	public double getTwenty() {
		return twenty;
	}



	public void setTwenty(double twenty) {
		this.twenty = twenty;
	}



	public double getTen() {
		return ten;
	}



	public void setTen(double ten) {
		this.ten = ten;
	}



	public double getFive() {
		return five;
	}



	public void setFive(double five) {
		this.five = five;
	}



	public double getTwo() {
		return two;
	}



	public void setTwo(double two) {
		this.two = two;
	}



	public double getOne() {
		return one;
	}



	public void setOne(double one) {
		this.one = one;
	}



	public double getEuro() {
		return euro;
	}



	public void setEuro(double euro) {
		this.euro = euro;
	}



	public double getEuroRate() {
		return euroRate;
	}



	public void setEuroRate(double euroRate) {
		this.euroRate = euroRate;
	}



	public double getEuro2() {
		return euro2;
	}



	public void setEuro2(double euro2) {
		this.euro2 = euro2;
	}



	public double getEuroRate2() {
		return euroRate2;
	}



	public void setEuroRate2(double euroRate2) {
		this.euroRate2 = euroRate2;
	}



	public double getEuro3() {
		return euro3;
	}



	public void setEuro3(double euro3) {
		this.euro3 = euro3;
	}



	public double getEuroRate3() {
		return euroRate3;
	}



	public void setEuroRate3(double euroRate3) {
		this.euroRate3 = euroRate3;
	}



	public double getEuro4() {
		return euro4;
	}



	public void setEuro4(double euro4) {
		this.euro4 = euro4;
	}



	public double getEuroRate4() {
		return euroRate4;
	}



	public void setEuroRate4(double euroRate4) {
		this.euroRate4 = euroRate4;
	}



	public double getDollar() {
		return dollar;
	}



	public void setDollar(double dollar) {
		this.dollar = dollar;
	}



	public double getDollarRate() {
		return dollarRate;
	}



	public void setDollarRate(double dollarRate) {
		this.dollarRate = dollarRate;
	}



	public double getDollar2() {
		return dollar2;
	}



	public void setDollar2(double dollar2) {
		this.dollar2 = dollar2;
	}



	public double getDollarRate2() {
		return dollarRate2;
	}



	public void setDollarRate2(double dollarRate2) {
		this.dollarRate2 = dollarRate2;
	}



	public double getDollar3() {
		return dollar3;
	}



	public void setDollar3(double dollar3) {
		this.dollar3 = dollar3;
	}



	public double getDollarRate3() {
		return dollarRate3;
	}



	public void setDollarRate3(double dollarRate3) {
		this.dollarRate3 = dollarRate3;
	}



	public double getDollar4() {
		return dollar4;
	}



	public void setDollar4(double dollar4) {
		this.dollar4 = dollar4;
	}



	public double getDollarRate4() {
		return dollarRate4;
	}



	public void setDollarRate4(double dollarRate4) {
		this.dollarRate4 = dollarRate4;
	}



	public double getCaisseInit() {
		return caisseInit;
	}



	public void setCaisseInit(double caisseInit) {
		this.caisseInit = caisseInit;
	}



	public String getPointerName() {
		return pointerName;
	}



	public void setPointerName(String pointerName) {
		this.pointerName = pointerName;
	}



	public double getPointerAmount() {
		return pointerAmount;
	}



	public void setPointerAmount(double pointerAmount) {
		this.pointerAmount = pointerAmount;
	}



	public String getReceivedBy() {
		return receivedBy;
	}



	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}



	@Override
	public String toString() {
		return "Cashing [id=" + id + ", date=" + date + ", cashier=" + cashier + ", amount=" + amount + ", amount2="
				+ amount2 + ", headCashier=" + headCashier + ", headCashierAmount=" + headCashierAmount + ", comment="
				+ comment + ", fiveThousands=" + fiveThousands + ", twoThousands=" + twoThousands + ", oneThousands="
				+ oneThousands + ", fiveHundred=" + fiveHundred + ", onehundred=" + onehundred + ", fifty=" + fifty
				+ ", twenty=" + twenty + ", ten=" + ten + ", five=" + five + ", two=" + two + ", one=" + one + ", euro="
				+ euro + ", euroRate=" + euroRate + ", euro2=" + euro2 + ", euroRate2=" + euroRate2 + ", euro3=" + euro3
				+ ", euroRate3=" + euroRate3 + ", euro4=" + euro4 + ", euroRate4=" + euroRate4 + ", dollar=" + dollar
				+ ", dollarRate=" + dollarRate + ", dollar2=" + dollar2 + ", dollarRate2=" + dollarRate2 + ", dollar3="
				+ dollar3 + ", dollarRate3=" + dollarRate3 + ", dollar4=" + dollar4 + ", dollarRate4=" + dollarRate4
				+ ", caisseInit=" + caisseInit + ", pointerName=" + pointerName + ", pointerAmount=" + pointerAmount
				+ ", receivedBy=" + receivedBy + "]";
	}


	

}
