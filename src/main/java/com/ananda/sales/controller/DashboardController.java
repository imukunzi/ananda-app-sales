package com.ananda.sales.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ananda.app.model.ReportBodyDetails;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/") //creating address api
public class DashboardController {

	private double SALES_JAN=50000;
	private double SALES_FEB=0;
	
	private double TARGET_SALES_JAN=20000;
	
	
	@PostMapping("/dashboard") //Pulling dashboard service
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	public ResponseEntity<Map<String, Object>> getReport(@RequestBody ReportBodyDetails r) {
		
		try{
//			response from back-end and font-end where retrive-data
		Map<String, Object> response = new HashMap<>(); 
		response.put("SALES_JAN",SALES_JAN);
		response.put("SALES_FEB",SALES_FEB);
		
		response.put("TARGET_SALES_JAN",TARGET_SALES_JAN);
		
		
		
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

