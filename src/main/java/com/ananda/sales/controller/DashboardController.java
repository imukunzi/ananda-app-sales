package com.ananda.sales.controller;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ananda.app.model.ReportBodyDetails;
import com.ananda.sales.repository.PaymentsRepository;
import com.ananda.sales.repository.SalesSummaryRepository;
import com.ananda.sales.repository.CustomersRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/") //creating address api
public class DashboardController {
	
	@Autowired
	CustomersRepository customerRepository;
	
	@Autowired
	private SalesSummaryRepository salesSummaryRepo;
	
	LocalDate currentdate = LocalDate.now();
	int currentDay = currentdate.getDayOfMonth();
	Month currentMonth = currentdate.getMonth();
	int currentMonthNumber = currentdate.getMonthValue();
	int currentYear = currentdate.getYear();
	
	private DecimalFormat decimalFormatter = new DecimalFormat("#,###.00");

	private double SALES_JAN=0;
	private double SALES_FEB=0;
	private double SALES_MARC=0;
	private double SALES_APR=0;
	private double SALES_MAY=0;
	private double SALES_JUN=0;
	private double SALES_JULY=0;
	private double SALES_AUG=0;
	private double SALES_SEPT=0;
	private double SALES_OCT=0;
	private double SALES_NOV=0;
	private double SALES_DEC=0;
	
	private double TARGET_SALES_JAN=20000;
	private double TARGET_SALES_FEB=0;
	private double TARGET_SALES_MARC=0;
	private double TARGET_SALES_APR=0;
	private double TARGET_SALES_MAY=0;
	private double TARGET_SALES_JUN=0;
	private double TARGET_SALES_JULY=0;
	private double TARGET_SALES_AUG=0;
	private double TARGET_SALES_SEPT=0;
	private double TARGET_SALES_OCT=0;
	private double TARGET_SALES_NOV=0;
	private double TARGET_SALES_DEC=0;
	
	private double PROFIT_JAN=60000;
	private double PROFIT_FEB=0;
	private double PROFIT_MARC=0;
	private double PROFIT_APR=0;
	private double PROFIT_MAY=0;
	private double PROFIT_JUN=0;
	private double PROFIT_JULY=0;
	private double PROFIT_AUG=0;
	private double PROFIT_SEPT=0;
	private double PROFIT_OCT=0;
	private double PROFIT_NOV=0;
	private double PROFIT_DEC=0;
	
	
	
	
	
	
	@PostMapping("/dashboard") //Pulling dashboard service
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	public ResponseEntity<Map<String, Object>> getReport(@RequestBody ReportBodyDetails r) {
		
		try{
			
			String location = "T2000";
			
			if(salesSummaryRepo.salesMonthlyReport(currentYear+"-01-01 00:00:00", currentYear+"-01-31 23:59:59", location)!=null) {
				SALES_JAN = salesSummaryRepo.salesMonthlyReport(currentYear+"-01-01 00:00:00", currentYear+"-01-31 23:59:59", location);
			}
			
			if(salesSummaryRepo.salesMonthlyReport(currentYear+"-02-01 00:00:00", currentYear+"-02-31 23:59:59", location)!=null) {
				SALES_FEB = salesSummaryRepo.salesMonthlyReport(currentYear+"-02-01 00:00:00", currentYear+"-02-31 23:59:59", location);
			}
			
			if(salesSummaryRepo.salesMonthlyReport(currentYear+"-03-01 00:00:00", currentYear+"-03-31 23:59:59", location)!=null) {
				SALES_MARC = salesSummaryRepo.salesMonthlyReport(currentYear+"-03-01 00:00:00", currentYear+"-03-31 23:59:59", location);
			}
			
			if(salesSummaryRepo.salesMonthlyReport(currentYear+"-04-01 00:00:00", currentYear+"-04-31 23:59:59", location)!=null) {
				SALES_APR = salesSummaryRepo.salesMonthlyReport(currentYear+"-04-01 00:00:00", currentYear+"-04-31 23:59:59", location);
			}
			
			if(salesSummaryRepo.salesMonthlyReport(currentYear+"-05-01 00:00:00", currentYear+"-05-31 23:59:59", location)!=null) {
				SALES_MAY = salesSummaryRepo.salesMonthlyReport(currentYear+"-05-01 00:00:00", currentYear+"-05-31 23:59:59", location);
			}
			
			if(salesSummaryRepo.salesMonthlyReport(currentYear+"-06-01 00:00:00", currentYear+"-06-31 23:59:59", location)!=null) {
				SALES_JUN = salesSummaryRepo.salesMonthlyReport(currentYear+"-06-01 00:00:00", currentYear+"-06-31 23:59:59", location);
			}
			
			if(salesSummaryRepo.salesMonthlyReport(currentYear+"-07-01 00:00:00", currentYear+"-07-31 23:59:59", location)!=null) {
				SALES_JULY = salesSummaryRepo.salesMonthlyReport(currentYear+"-07-01 00:00:00", currentYear+"-07-31 23:59:59", location);
			}
			
			if(salesSummaryRepo.salesMonthlyReport(currentYear+"-08-01 00:00:00", currentYear+"-08-31 23:59:59", location)!=null) {
				SALES_AUG = salesSummaryRepo.salesMonthlyReport(currentYear+"-08-01 00:00:00", currentYear+"-08-31 23:59:59", location);
			}
			
			if(salesSummaryRepo.salesMonthlyReport(currentYear+"-09-01 00:00:00", currentYear+"-09-31 23:59:59", location)!=null) {
				SALES_SEPT = salesSummaryRepo.salesMonthlyReport(currentYear+"-09-01 00:00:00", currentYear+"-09-31 23:59:59", location);
			}
			
			if(salesSummaryRepo.salesMonthlyReport(currentYear+"-10-01 00:00:00", currentYear+"-10-31 23:59:59", location)!=null) {
				SALES_OCT = salesSummaryRepo.salesMonthlyReport(currentYear+"-10-01 00:00:00", currentYear+"-10-31 23:59:59", location);
			}
			
			if(salesSummaryRepo.salesMonthlyReport(currentYear+"-11-01 00:00:00", currentYear+"-11-31 23:59:59", location)!=null) {
				SALES_NOV = salesSummaryRepo.salesMonthlyReport(currentYear+"-11-01 00:00:00", currentYear+"-11-31 23:59:59", location);
			}
			
			if(salesSummaryRepo.salesMonthlyReport(currentYear+"-12-01 00:00:00", currentYear+"-12-31 23:59:59", location)!=null) {
				SALES_DEC = salesSummaryRepo.salesMonthlyReport(currentYear+"-12-01 00:00:00", currentYear+"-12-31 23:59:59", location);
			}
			
			
			
			
			
			System.out.println("======= 96 dashboard====="+SALES_MARC);
			
//			response from back-end and font-end where retrive-data
		Map<String, Object> response = new HashMap<>(); 
		response.put("SALES_JAN",SALES_JAN);
		response.put("SALES_FEB",SALES_FEB);
		response.put("SALES_MARC",SALES_MARC);
		response.put("SALES_APR",SALES_APR);
		response.put("SALES_MAY",SALES_MAY);
		response.put("SALES_JUN",SALES_JUN);
		response.put("SALES_JULY",SALES_JULY);
		response.put("SALES_AUG",SALES_AUG);
		response.put("SALES_SEPT",SALES_SEPT);
		response.put("SALES_OCT",SALES_OCT);
		response.put("SALES_NOV",SALES_NOV);
		response.put("SALES_DEC",SALES_DEC);
		
		response.put("TARGET_SALES_JAN",TARGET_SALES_JAN);
		response.put("TARGET_SALES_FEB",TARGET_SALES_FEB);
		response.put("TARGET_SALES_MARC",TARGET_SALES_MARC);
		response.put("TARGET_SALES_APR",TARGET_SALES_APR);
		response.put("TARGET_SALES_MAY",TARGET_SALES_MAY);
		response.put("TARGET_SALES_JUN",TARGET_SALES_JUN);
		response.put("TARGET_SALES_JULY",TARGET_SALES_JULY);
		response.put("TARGET_SALES_AUG",TARGET_SALES_AUG);
		response.put("TARGET_SALES_SEPT",TARGET_SALES_SEPT);
		response.put("TARGET_SALES_OCT",TARGET_SALES_OCT);
		response.put("TARGET_SALES_NOV",TARGET_SALES_NOV);
		response.put("TARGET_SALES_DEC",TARGET_SALES_DEC);
		
		response.put("PROFIT_JAN",PROFIT_JAN);
		response.put("PROFIT_FEB",PROFIT_FEB);
		response.put("PROFIT_MARC",PROFIT_MARC);
		response.put("PROFIT_APR",PROFIT_APR);
		response.put("PROFIT_MAY",PROFIT_MAY);
		response.put("PROFIT_JUN",PROFIT_JUN);
		response.put("PROFIT_JULY",PROFIT_JULY);
		response.put("PROFIT_AUG",PROFIT_AUG);
		response.put("PROFIT_SEPT",PROFIT_SEPT);
		response.put("PROFIT_OCT",PROFIT_OCT);
		response.put("PROFIT_NOV",PROFIT_NOV);
		response.put("PROFIT_DEC",PROFIT_DEC);
		
		
		
		
		
		
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

