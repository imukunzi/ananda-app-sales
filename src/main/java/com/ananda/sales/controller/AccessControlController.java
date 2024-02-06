package com.ananda.sales.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.ananda.sales.model.AccessControl;
import com.ananda.sales.model.AccessControllOtp;
import com.ananda.sales.model.Products;
import com.ananda.sales.repository.AccessContollRepository;
import com.ananda.sales.repository.AccessControllOtpRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class AccessControlController {
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private AccessContollRepository accessControlRepository;
	
	@Autowired
	private AccessControllOtpRepository accessControllOtpRepository;
	
	//get access controll list
	private Sort.Direction getSortDirection(String direction) {

		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;

	}

	@GetMapping("/accesslist")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getAllAccessPage(@RequestParam(required = false) String code,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size,
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

			List<AccessControl> accessControl = new ArrayList<AccessControl>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<AccessControl> pageTuts;
			if (code == null)
				pageTuts = accessControlRepository.findAll(pagingSort);
			else
				pageTuts = accessControlRepository.findByMacAddressContaining(code, pagingSort);

			accessControl = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", accessControl);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	//create list of allowed mac address to access
	
	@PostMapping("/newaccess")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Integer> saveAccess(@RequestBody AccessControl a) {

		try {
			
			   			    
				String macAddres = a.getMacAddress();
				String user = a.getUser();
				String longitude = a.getLongitude();
				String latitude = a.getLatitude();
				String date=formatter.format(new Date());
				 
				AccessControl i =accessControlRepository.save(new AccessControl(macAddres, user, longitude, latitude, date));
				
				if(i !=null) {
					return new ResponseEntity<>(1, HttpStatus.OK);
					
				}else {
					return new ResponseEntity<>(0, HttpStatus.OK);
				}
				 
		 
			

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	@GetMapping("/accesslist/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<AccessControl> getAccess(@PathVariable("id") long id) {

		Optional<AccessControl> data = accessControlRepository.findById(id);

		if (data.isPresent()) {
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/accesslist/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> updateProducts(@PathVariable("id") long id, @RequestBody AccessControl p) {

		
		Optional<AccessControl> data = accessControlRepository.findById(id);

		if (data.isPresent()) {

			AccessControl access = data.get();

			access.setMacAddress(p.getMacAddress());
			access.setUser(p.getUser());
			access.setLongitude(p.getLongitude());
			access.setLatitude(p.getLatitude());
			
			
			accessControlRepository.save(access);

			return new ResponseEntity<>("Updated successfully!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/accesslist/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {

		try {
			accessControlRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/accesslistuser/{user}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<AccessControl> getAccessUser(@PathVariable("user") String user) {

		AccessControl access = accessControlRepository.findByUserContaining(user);

		//System.out.println("===198=====access======"+access);
		
				
		
		
		
		return new ResponseEntity<>(access, HttpStatus.OK);

	}
	
	
	
	

}
