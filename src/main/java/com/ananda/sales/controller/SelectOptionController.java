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
import org.springframework.security.access.method.P;
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

import com.ananda.sales.model.Members;
import com.ananda.sales.model.SelectOptions;
import com.ananda.sales.model.SelectionCategory;
import com.ananda.sales.model.User;
import com.ananda.sales.repository.MembersRepository;
import com.ananda.sales.repository.SelectCategoryRepo;
import com.ananda.sales.repository.SelectOptionRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class SelectOptionController {
	
	@Autowired
	private SelectOptionRepository selectRepo;
	
	@Autowired
	private SelectCategoryRepo selectCategoryRepo;
	
	@Autowired
	private MembersRepository memberRepo;
	
	private Sort.Direction getSortDirection(String direction){
		
		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;
		
	}
	
	
	@GetMapping("/selectoptions")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getAllProductsPage(@RequestParam(required = false) String code,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort){
		
		
		 

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

			List<SelectOptions> products = new ArrayList<SelectOptions>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<SelectOptions> pageTuts;
			if (code == null)
				pageTuts = selectRepo.findAll(pagingSort);
			else
				pageTuts = selectRepo.findByCategoryContaining(code, pagingSort);
			
			
			products = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", products);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++"+e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/selectoptions")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<SelectOptions> save(@RequestBody SelectOptions s){

		try {

			SelectOptions p = selectRepo.save(new SelectOptions(s.getCategory(),s.getValue(),s.getSelect_order(),s.getUsername(),s.getOther(),"NA"));

			return new ResponseEntity<>(p, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
	}
	
	@GetMapping("/selectoptions/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<SelectOptions> getProduct(@PathVariable("id") long id) {
		
		Optional<SelectOptions> data = selectRepo.findById(id);
		
		if (data.isPresent()) {
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/selectoptionsaller/{saller}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<SelectOptions> getSallerDetails(@PathVariable("saller") String saller) {
		
		SelectOptions data = selectRepo.findSallerDetails(saller);
		
		System.out.println("saller "+saller+" "+data);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
		
//		if (data.getValue() !=null) {
//			return new ResponseEntity<>(data, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
		
	}
	
	@PutMapping("/selectoptions/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<SelectOptions> update(@PathVariable("id") long id, @RequestBody SelectOptions p) {
		
			
    Optional<SelectOptions> data = selectRepo.findById(id);
		
		
		if (data.isPresent()) {
			
			SelectOptions s = data.get();
			
			s.setCategory(p.getCategory());
			s.setValue(p.getValue());
			s.setSelect_order(p.getSelect_order());
			s.setOther(p.getOther());
			s.setUsername(p.getUsername());
			s.setSaller_access_code(p.getSaller_access_code());
			 
			
			return new ResponseEntity<>(selectRepo.save(s), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/selectoptions/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
		
		try {
			selectRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/selectcategory")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<SelectionCategory>> getSelectCategory() {

		try {
			List<SelectionCategory> c = new ArrayList<SelectionCategory>();

			selectCategoryRepo.findAll().forEach(c::add);

			if (c.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(c, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/selectedoptions/{category}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<SelectOptions>> getSelectedOption(@PathVariable("category") String category) {

		try {
			List<SelectOptions> c = new ArrayList<SelectOptions>();

			selectRepo.findByCategoryContaining(category).forEach(c::add);

			if (c.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(c, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/selecttransfertype/{category}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<SelectOptions>> getSelectTransfer(@PathVariable("category") String category) {

		
		 
		try {
			List<SelectOptions> c = new ArrayList<SelectOptions>();

			 
			selectRepo.findByCategoryContaining(category).forEach(c::add);
			 
			if (c.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			 

			return new ResponseEntity<>(c, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/selectedoptionsallers/{branch}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<SelectOptions>> getSallersByBranch(@PathVariable("branch") String branch){

		try {
			
					

			return new ResponseEntity<>(selectRepo.findSallersByBranch(branch), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
	}
	
	@GetMapping("/selectedoptionscashier/{category}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<SelectOptions>> getCashierByBranch(@PathVariable("branch") String branch){

		try {

			

			return new ResponseEntity<>(selectRepo.findCashierByBranch(branch), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
	}
	
	@GetMapping("/selectedoptionstand/{category}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<SelectOptions>> getStandByBranch(@PathVariable("branch") String branch){

		try {

			

			return new ResponseEntity<>(selectRepo.findStandByBranch(branch), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
	}
	
	@GetMapping("/selectallusernames")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Members>> getSelectedOptionUsername() {

		try {
			List<Members> c = new ArrayList<Members>();

			memberRepo.findAll().forEach(c::add);
			
			System.out.println("===329===option select=="+c);

			if (c.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(c, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	

}
