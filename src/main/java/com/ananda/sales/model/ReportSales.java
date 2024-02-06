package com.ananda.sales.model;

public class ReportSales {
	
	private String reportSales_totalSales;
	private String reportSales_newSales;
	private String reportSales_salesPayment;
	private String reportSales_salesDeposit;
	private String reportSales_salesCredit;
	
	public ReportSales(String reportSales_totalSales, String reportSales_newSales, String reportSales_salesPayment,
			String reportSales_salesDeposit, String reportSales_salesCredit) {
		super();
		this.reportSales_totalSales = reportSales_totalSales;
		this.reportSales_newSales = reportSales_newSales;
		this.reportSales_salesPayment = reportSales_salesPayment;
		this.reportSales_salesDeposit = reportSales_salesDeposit;
		this.reportSales_salesCredit = reportSales_salesCredit;
	}

	public String getReportSales_totalSales() {
		return reportSales_totalSales;
	}

	public void setReportSales_totalSales(String reportSales_totalSales) {
		this.reportSales_totalSales = reportSales_totalSales;
	}

	public String getReportSales_newSales() {
		return reportSales_newSales;
	}

	public void setReportSales_newSales(String reportSales_newSales) {
		this.reportSales_newSales = reportSales_newSales;
	}

	public String getReportSales_salesPayment() {
		return reportSales_salesPayment;
	}

	public void setReportSales_salesPayment(String reportSales_salesPayment) {
		this.reportSales_salesPayment = reportSales_salesPayment;
	}

	public String getReportSales_salesDeposit() {
		return reportSales_salesDeposit;
	}

	public void setReportSales_salesDeposit(String reportSales_salesDeposit) {
		this.reportSales_salesDeposit = reportSales_salesDeposit;
	}

	public String getReportSales_salesCredit() {
		return reportSales_salesCredit;
	}

	public void setReportSales_salesCredit(String reportSales_salesCredit) {
		this.reportSales_salesCredit = reportSales_salesCredit;
	}

	@Override
	public String toString() {
		return "ReportSales [reportSales_totalSales=" + reportSales_totalSales + ", reportSales_newSales="
				+ reportSales_newSales + ", reportSales_salesPayment=" + reportSales_salesPayment
				+ ", reportSales_salesDeposit=" + reportSales_salesDeposit + ", reportSales_salesCredit="
				+ reportSales_salesCredit + "]";
	}

	
	
	
}
