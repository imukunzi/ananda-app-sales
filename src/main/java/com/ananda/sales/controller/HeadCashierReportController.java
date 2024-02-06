package com.ananda.sales.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.ananda.sales.model.HeadCashierReport;
import com.ananda.sales.model.ReportBodyDetails;
import com.ananda.sales.repository.HeadCashierRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class HeadCashierReportController {
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private HeadCashierRepository headCashierRepository;
	
	
	@PostMapping("/headcashierreport")
	@PreAuthorize("hasRole('ROLE_HEADCASHIER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> save(@RequestBody HeadCashierReport h) {

		try {
			
			   String response="";
			 

			   String date = formatter.format(new Date());
			   String date1 = formatter2.format(new Date())+" 00:00:00";
			   String date2 = formatter2.format(new Date())+" 59:59:59";
			   
			   String headCashier = h.getHeadCashier();
			   String cashier = h.getCashier();
			   double cash = h.getCash();
			   double momo = h.getMomo();
			   double cheque = h.getCheque();
			   double transfer = h.getTransfer();
			   double visa = h.getVisa();
			   double expenses = h.getExpenses();
			   String comment = h.getComment();
				
				//check if no duplicate
			   
			   //System.out.println("=========saving======report");
				
				if(headCashierRepository.checkIfNoDuplicateExit(date1,date2,headCashier,cashier) ==0) {
					
										
					HeadCashierReport r = headCashierRepository.save(new HeadCashierReport(date, headCashier, cashier, cash, momo, cheque, transfer, visa, expenses, comment));
					response="OK";
				}else {
					response="This report exist.";
					System.out.println("=========saving======report");
					
				}
				 
				
				
				
			    
				 
		 
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	
	@PostMapping("/getheadcashierreport")
	@PreAuthorize("hasRole('ROLE_HEADCASHIER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<HeadCashierReport> getReport(@RequestBody ReportBodyDetails r) {

		try {
	
		   
			   String date1 = r.getText1()+" 00:00:00";
			   String date2 = r.getText1()+" 59:59:59";
			   String cashier =r.getText2();
			
			   HeadCashierReport hd = headCashierRepository.findReport(date1,date2,cashier);
				
			 
		 
			return new ResponseEntity<>(hd, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	
	@PostMapping("/getheadcashierreporttotal")
	@PreAuthorize("hasRole('ROLE_HEADCASHIER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<HeadCashierReport> getReportTotal(@RequestBody ReportBodyDetails r) {

		try {
	
		   
			   String date1 = r.getText1();
			   String date2 = r.getText2();
			   String cashier =r.getText4();
			   
			   
			
			   double cash = headCashierRepository.findReportTotalCash(date1,date2,cashier);
			   double momo = headCashierRepository.findReportTotalMomo(date1,date2,cashier);
			   double cheque = headCashierRepository.findReportTotalCheque(date1,date2,cashier);
			   double transfer = headCashierRepository.findReportTotalTransfer(date1,date2,cashier);
			   double visa = headCashierRepository.findReportTotalVisa(date1,date2,cashier);
			   double expenses = headCashierRepository.findReportTotalExpenses(date1,date2,cashier);
			   
			   //System.out.println("head cashier report 125:"+date1+" "+date2+" "+cashier+" cash:"+cash+" momo:"+momo+" cheque:"+cheque+" tranfer:"+transfer+" visa:"+visa);
				
			 
		 
			return new ResponseEntity<>(new HeadCashierReport(date1, cashier,  cashier,  cash,  momo,  cheque,
					transfer, visa, expenses, "comment"), HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}



}
