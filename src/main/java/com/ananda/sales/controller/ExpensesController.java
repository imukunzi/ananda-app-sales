package com.ananda.sales.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ananda.sales.model.Expenses;
import com.ananda.sales.model.ReportBodyDetails;
import com.ananda.sales.model.SalesSummary;
import com.ananda.sales.repository.ExpensesRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class ExpensesController {
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private ExpensesRepository expensesRepository;
	
	private long currentPage = 1;
	private long totalItems;
	private long totalPages;
	private List<SalesSummary> sales;

	@PostMapping("/newexpense")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Expenses> saveExpense(@RequestBody Expenses p) {

		try {
			
			   
			 

			    String date=formatter.format(new Date());
				String expenseType = p.getExpenseType();
				double amount = p.getAmount();
				String other = p.getOther();
				String username = p.getUsername();
				String location = p.getLocation();
				 
				
				
				Expenses exp = expensesRepository.save(new Expenses(date, expenseType, expenseType, amount, other, username,location));
			    
				 
		 
			return new ResponseEntity<>(exp, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	@PostMapping("/expenselist")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getExpenses(@RequestBody ReportBodyDetails r) {

try {
			
			String date1 = r.getText1();
			String date2 = r.getText2();
			String cashier = r.getText4();
			
						
			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", expensesRepository.findBydate(date1,date2,cashier));
			response.put("currentPage", currentPage);
			response.put("totalItems", totalItems);
			response.put("totalPages", totalPages);
			
			
			
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}



	}
	
	@GetMapping("/expense/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Expenses> getExpense(@PathVariable("id") long id) {

		Optional<Expenses> data = expensesRepository.findById(id);

		if (data.isPresent()) {
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	

}
