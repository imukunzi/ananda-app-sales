package com.ananda.sales.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

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
import com.ananda.sales.repository.AccessContollRepository;
import com.ananda.sales.repository.AccessControllOtpRepository;
import com.ananda.sales.sms.SendSms;

import net.bytebuddy.utility.RandomString;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class AccessControlOtpController {
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private AccessControllOtpRepository accessControlOtpRepository;
	
	 String source = "Golden.F";
     String destination;
     private SendSms sms;
	
	//get access controll list
	private Sort.Direction getSortDirection(String direction) {

		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;

	}

	@GetMapping("/accessotplist")
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

			List<AccessControllOtp> accessControl = new ArrayList<AccessControllOtp>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<AccessControllOtp> pageTuts;
			if (code == null)
				pageTuts = accessControlOtpRepository.findAll(pagingSort);
			else
				pageTuts = accessControlOtpRepository.findByphoneContaining(code, pagingSort);

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
	
	@PostMapping("/newaccessotp")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Integer> saveAccess(@RequestBody AccessControllOtp a) {

		try {
			
			String token = RandomString.make(7);
			
			   			    
				String user = a.getUser();
				String phone = a.getPhone();
				String accessCode = token;
				String type = a.getType();
				String other = a.getOther();
				String username = a.getUsername();
				String date=formatter.format(new Date());
				String expiration ="";
				
				 
				AccessControllOtp i =accessControlOtpRepository.save(new AccessControllOtp(  user,phone,accessCode,type, other,
						  date,expiration, username));
				
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
	
	@GetMapping("/accessotplist/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<AccessControllOtp> getAccess(@PathVariable("id") long id) {

		Optional<AccessControllOtp> data = accessControlOtpRepository.findById(id);

		if (data.isPresent()) {
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/accessotplist/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> update(@PathVariable("id") long id, @RequestBody AccessControllOtp p) {

		
		Optional<AccessControllOtp> data = accessControlOtpRepository.findById(id);

		if (data.isPresent()) {

			AccessControllOtp access = data.get();

			access.setUser(p.getUser());
			access.setPhone(p.getPhone());
			//access.setAccessCode(p.getAccessCode());
			access.setType(p.getType());
			access.setOther(p.getOther());
			
			
			//accessControlOtpRepository.save(access);

			return new ResponseEntity<>("Updated successfully!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/resetotp/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> reset(@PathVariable("id") long id) {

		
		String token = RandomString.make(7);
  
					
	   int i=  accessControlOtpRepository.resetAccessCode(token, id);
	   
	   
	   String response="";
	   String message="Authorization code: "+token;
	   sms = new SendSms();			
	  
     
	   
	   Optional<AccessControllOtp> data = accessControlOtpRepository.findById(id);

		if (data.isPresent()) {

			AccessControllOtp access = data.get();
			
			destination = "25"+access.getPhone();
			
		}
	   
	   if(i==1) {
		   
		  
		   
		   response="Done successfully.";
		 //send sms
			 sms.submitMessage("clr-golden", "Gold@21e", message,"0", "1", destination, source, "rslr.connectbind.com",8080);
		   
	   }else {
		   response="Not done!";
	   }
	   
	  // System.out.println("====access otp 203===="+id+" "+i+" "+destination);

	return new ResponseEntity<>(response, HttpStatus.OK);
		 

	}

	@DeleteMapping("/accessotplist/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {

		try {
			accessControlOtpRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/accessotpauthorization/{code}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getAccessAuthorization(@PathVariable("code") String code) {
		
		

		int y = accessControlOtpRepository.findIfAuthorizationCodeExist(code);
		String authorizedBy = accessControlOtpRepository.findIfAuthorizationCodeExistUser(code);
		String authorizedBranch = accessControlOtpRepository.findIfAuthorizationCodeExistUserBranch(code);
		
		//System.out.println("access otp authorization 213======"+y+" "+code+" user:"+authorizedBy);

		
		Map<String, Object> response = new HashMap<>();
		response.put("isAuthorized", y);
		response.put("authorizedBy", authorizedBy);
		response.put("authorizedBranch", authorizedBranch);
		

		return new ResponseEntity<>(response, HttpStatus.OK);
		

	}
	
	@GetMapping("/accessotpdelete/{code}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getAccessDelete(@PathVariable("code") String code) {
		
		

		int y = accessControlOtpRepository.findIfDeleteCodeExist(code);
		String authorizedBy = accessControlOtpRepository.findIfDeleteCodeExistUser(code);
		String authorizedBranch = accessControlOtpRepository.findIfDeleteCodeExistUserBranch(code);
		
		//System.out.println("access otp authorization 213======"+y+" "+code+" user:"+authorizedBy);

		
		Map<String, Object> response = new HashMap<>();
		response.put("isAuthorized", y);
		response.put("authorizedBy", authorizedBy);
		response.put("authorizedBranch", authorizedBranch);
		

		return new ResponseEntity<>(response, HttpStatus.OK);
		

	}
	
	

}
