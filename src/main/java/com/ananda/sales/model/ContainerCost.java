package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="containerCost")
@Table(name="containercost")
public class ContainerCost {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
	@Column(name = "product")
	private String product;
	
	@Column(name = "time")
	private String time;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "cartons") //or sac
	private String cartons;
	
	@Column(name = "qty")
	private double qty;
	
	@Column(name = "container_cbm")
	private double container_cbm;  //contaner capacity
	
	@Column(name = "transport_per_cbm")
	private double transportPerCbm;
	
	@Column(name = "cbm")
	private double cbm; //loaded capacity
	
	@Column(name = "total_cost_cbm")
	private double totalCostCbm;
	
	@Column(name = "transport_of_unit")
	private double transportOfUnit;
	
	@Column(name = "cif_value")
	private double cifValue;
	
	@Column(name = "taxes_53_percent")
	private double taxes53PerCent;
	
	@Column(name = "taxes_of_total_cost")
	private double taxesOfTotalCost;
	
	@Column(name = "exchange")
	private double exchange;
	
	@Column(name = "total_tax_frw")
	private double totalTaxFrw;
	
	@Column(name = "buying_unit_chinese_money")
	private double buyingUnitChineseMoney;
	
	@Column(name = "exchange_chinese")
	private double exchangeChinese;
	
	@Column(name = "buying_unity_usd")
	private double buyingUnitUsd;
	
	@Column(name = "buying_cost_unity")
	private double buyingCostUnity;
	
	@Column(name = "exchange_frw") 
	private double exchangeFrw;
	
	@Column(name = "profit")
	private double profit;
	
	@Column(name = "value_in_stock")
	private double valueInStock;
	
	@Column(name = "rra21")
	private double rra21;
	
	@Column(name = "sales_unit_value")
	private double saleUnitValue;
	
	@Column(name = "username")
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

	public ContainerCost() {
		super();
	}

	public ContainerCost(String product, String time, String code, String cartons, double qty, double container_cbm,
			double transportPerCbm, double cbm, double totalCostCbm, double transportOfUnit, double cifValue,
			double taxes53PerCent, double taxesOfTotalCost, double exchange, double totalTaxFrw,
			double buyingUnitChineseMoney, double exchangeChinese, double buyingUnitUsd, double buyingCostUnity,
			double exchangeFrw, double profit, double valueInStock, double rra21, double saleUnitValue,String username) {
		super();
		this.product = product;
		this.time = time;
		this.code = code;
		this.cartons = cartons;
		this.qty = qty;
		this.container_cbm = container_cbm;
		this.transportPerCbm = transportPerCbm;
		this.cbm = cbm;
		this.totalCostCbm = totalCostCbm;
		this.transportOfUnit = transportOfUnit;
		this.cifValue = cifValue;
		this.taxes53PerCent = taxes53PerCent;
		this.taxesOfTotalCost = taxesOfTotalCost;
		this.exchange = exchange;
		this.totalTaxFrw = totalTaxFrw;
		this.buyingUnitChineseMoney = buyingUnitChineseMoney;
		this.exchangeChinese = exchangeChinese;
		this.buyingUnitUsd = buyingUnitUsd;
		this.buyingCostUnity = buyingCostUnity;
		this.exchangeFrw = exchangeFrw;
		this.profit = profit;
		this.valueInStock = valueInStock;
		this.rra21 = rra21;
		this.saleUnitValue = saleUnitValue;
		this.username=username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCartons() {
		return cartons;
	}

	public void setCartons(String cartons) {
		this.cartons = cartons;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public double getContainer_cbm() {
		return container_cbm;
	}

	public void setContainer_cbm(double container_cbm) {
		this.container_cbm = container_cbm;
	}

	public double getTransportPerCbm() {
		return transportPerCbm;
	}

	public void setTransportPerCbm(double transportPerCbm) {
		this.transportPerCbm = transportPerCbm;
	}

	public double getCbm() {
		return cbm;
	}

	public void setCbm(double cbm) {
		this.cbm = cbm;
	}

	public double getTotalCostCbm() {
		return totalCostCbm;
	}

	public void setTotalCostCbm(double totalCostCbm) {
		this.totalCostCbm = totalCostCbm;
	}

	public double getTransportOfUnit() {
		return transportOfUnit;
	}

	public void setTransportOfUnit(double transportOfUnit) {
		this.transportOfUnit = transportOfUnit;
	}

	public double getCifValue() {
		return cifValue;
	}

	public void setCifValue(double cifValue) {
		this.cifValue = cifValue;
	}

	public double getTaxes53PerCent() {
		return taxes53PerCent;
	}

	public void setTaxes53PerCent(double taxes53PerCent) {
		this.taxes53PerCent = taxes53PerCent;
	}

	public double getTaxesOfTotalCost() {
		return taxesOfTotalCost;
	}

	public void setTaxesOfTotalCost(double taxesOfTotalCost) {
		this.taxesOfTotalCost = taxesOfTotalCost;
	}

	public double getExchange() {
		return exchange;
	}

	public void setExchange(double exchange) {
		this.exchange = exchange;
	}

	public double getTotalTaxFrw() {
		return totalTaxFrw;
	}

	public void setTotalTaxFrw(double totalTaxFrw) {
		this.totalTaxFrw = totalTaxFrw;
	}

	public double getBuyingUnitChineseMoney() {
		return buyingUnitChineseMoney;
	}

	public void setBuyingUnitChineseMoney(double buyingUnitChineseMoney) {
		this.buyingUnitChineseMoney = buyingUnitChineseMoney;
	}

	public double getExchangeChinese() {
		return exchangeChinese;
	}

	public void setExchangeChinese(double exchangeChinese) {
		this.exchangeChinese = exchangeChinese;
	}

	public double getBuyingUnitUsd() {
		return buyingUnitUsd;
	}

	public void setBuyingUnitUsd(double buyingUnitUsd) {
		this.buyingUnitUsd = buyingUnitUsd;
	}

	public double getBuyingCostUnity() {
		return buyingCostUnity;
	}

	public void setBuyingCostUnity(double buyingCostUnity) {
		this.buyingCostUnity = buyingCostUnity;
	}

	public double getExchangeFrw() {
		return exchangeFrw;
	}

	public void setExchangeFrw(double exchangeFrw) {
		this.exchangeFrw = exchangeFrw;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double getValueInStock() {
		return valueInStock;
	}

	public void setValueInStock(double valueInStock) {
		this.valueInStock = valueInStock;
	}

	public double getRra21() {
		return rra21;
	}

	public void setRra21(double rra21) {
		this.rra21 = rra21;
	}

	public double getSaleUnitValue() {
		return saleUnitValue;
	}

	public void setSaleUnitValue(double saleUnitValue) {
		this.saleUnitValue = saleUnitValue;
	}

	@Override
	public String toString() {
		return "ContainerCost [id=" + id + ", product=" + product + ", time=" + time + ", code=" + code + ", cartons="
				+ cartons + ", qty=" + qty + ", container_cbm=" + container_cbm + ", transportPerCbm=" + transportPerCbm
				+ ", cbm=" + cbm + ", totalCostCbm=" + totalCostCbm + ", transportOfUnit=" + transportOfUnit
				+ ", cifValue=" + cifValue + ", taxes53PerCent=" + taxes53PerCent + ", taxesOfTotalCost="
				+ taxesOfTotalCost + ", exchange=" + exchange + ", totalTaxFrw=" + totalTaxFrw
				+ ", buyingUnitChineseMoney=" + buyingUnitChineseMoney + ", exchangeChinese=" + exchangeChinese
				+ ", buyingUnitUsd=" + buyingUnitUsd + ", buyingCostUnity=" + buyingCostUnity + ", exchangeFrw="
				+ exchangeFrw + ", profit=" + profit + ", valueInStock=" + valueInStock + ", rra21=" + rra21
				+ ", saleUnitValue=" + saleUnitValue + ", username=" + username + "]";
	}

		

}
