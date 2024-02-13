package com.ananda.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.ananda.sales.model.Customers;

import com.ananda.sales.repository.CustomersRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class CustomersController {

	@Autowired
	CustomersRepository customerRepository;

	private Sort.Direction getSortDirection(String direction) {

		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;

	}

	@GetMapping("/customers")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getAllProductsPage(@RequestParam(required = false) String code,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size,@RequestParam(required = false) String type ,
			@RequestParam(defaultValue = "id,desc") String[] sort) {
		
		
		
		
		try {
			List<Order> orders = new ArrayList<Order>();

			if (sort[0].contains(",")) {
				// will sort more than 2 fields
				// sortOrder="field, direction"
				for (String sortOrder : sort) {
					String[] _sort = sortOrder.split(",");
					orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
				}
			} else {
				// sort=[field, direction]
				orders.add(new Order(getSortDirection(sort[1]), sort[0]));
			}

			List<Customers> customers = new ArrayList<Customers>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
			
			
			Page<Customers> pageTuts;
			if (code == null)
				pageTuts = customerRepository.findAll(pagingSort);
			else if(type.equals("Phone")) {
				pageTuts = customerRepository.findByPhoneContaining(code, pagingSort);
			}else {
				pageTuts = customerRepository.findByFullnameContaining(code, pagingSort);
			}
						
			
			
			customers = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", customers);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++"+e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	@PostMapping("/customers")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Customers> saveCustomer(@RequestBody Customers b){
		try { 

			Customers p = customerRepository.save(new Customers(b.getFullname().toUpperCase(),b.getContact(),b.getPhone(),b.getEmail(),b.getOther(),b.getRemark(),b.getUsername(),b.getCelebrated_day(),b.getCelebrated_day_description()));

			return new ResponseEntity<>(p, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	@GetMapping("/customers/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Customers> getCustomer(@PathVariable("id") long id) {
		
    Optional<Customers> data = customerRepository.findById(id);
		
		if (data.isPresent()) {
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/customers/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Customers> updatCustomer(@PathVariable("id") long id, @RequestBody Customers p) {
		
		
	    Optional<Customers> data = customerRepository.findById(id);
			
			 

			if (data.isPresent()) {
				
				Customers customer = data.get();
				
				customer.setFullname(p.getFullname());
				customer.setContact(p.getContact());
				customer.setPhone(p.getPhone());
				customer.setEmail(p.getEmail());
				customer.setOther(p.getOther());
				customer.setRemark(p.getRemark());
				customer.setCelebrated_day(p.getCelebrated_day());
				customer.setCelebrated_day_description(p.getCelebrated_day_description());
				 
				
				return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
	}
	
	@DeleteMapping("/customer/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
		
		try {
			customerRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}
