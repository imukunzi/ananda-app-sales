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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ananda.sales.customdb.CustomQueries;
import com.ananda.sales.model.ERole;
import com.ananda.sales.model.MemberDetails;
import com.ananda.sales.model.Members;
import com.ananda.sales.model.ReportBodyDetails;
import com.ananda.sales.model.Role;
import com.ananda.sales.model.User;
import com.ananda.sales.model.User_roles_list;
import com.ananda.sales.payload.response.MessageResponse;
import com.ananda.sales.repository.MembersRepository;
import com.ananda.sales.repository.RoleRepository;
import com.ananda.sales.repository.UserRepository;
import com.ananda.sales.security.service.UserDetailsServiceImpl;

import net.bytebuddy.utility.RandomString;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class MembersController {

	@Autowired
	MembersRepository membersRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	private CustomQueries customQuery;

	private Sort.Direction getSortDirection(String direction) {

		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;

	}

	@GetMapping("/members")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getAllPage(@RequestParam(required = false) String code,
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

			List<Members> customers = new ArrayList<Members>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<Members> pageTuts;
			if (code == null)
				pageTuts = membersRepository.findAll(pagingSort);
			else
				pageTuts = membersRepository.findByfirstnameContaining(code, pagingSort);

			customers = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", customers);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/members/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Members> getMember(@PathVariable("id") long id) {

		Optional<Members> data = membersRepository.findById(id);

		if (data.isPresent()) {
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/members/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Members> updateMember(@PathVariable("id") long id, @RequestBody Members p) {

		Optional<Members> data = membersRepository.findById(id);

		if (data.isPresent()) {

			Members member = data.get();

			member.setAccount(p.getEmail());
			member.setFirstname(p.getFirstname());
			member.setLastname(p.getLastname());
			member.setAccount(p.getAccount());
			member.setPhone(p.getPhone());
			member.setJoiningdate(p.getJoiningdate());
			member.setUser_group(p.getUser_group());
			member.setPassword_expiration_date(p.getPassword_expiration_date());
			member.setReport_duration(p.getReport_duration());
			member.setDisabled(p.getDisabled());
			member.setOtp_enabled(p.getOtp_enabled());

			return new ResponseEntity<>(membersRepository.save(member), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/accountdetails/{account}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<User> getAccountDetails(@PathVariable("account") String account) {

		try {
			User user = userRepository.findByAccount(account);

			// member= membersRepository.findByAccount(account);

			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("======================" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

//	@GetMapping("/membersbyrole/{account}")
//	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//	public ResponseEntity<List<String>> getAccountRole(@PathVariable("account") String account) {
//
//		 
//		
//		try {
//			List<String> list = userRepository.findByAccountRole(account);
//			
//					 
//			return new ResponseEntity<>(list, HttpStatus.OK);
//		} catch (Exception e) {
//			System.out.println("======================"+e);
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//	}

	@GetMapping("/memberdetails/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<MemberDetails> getMemberAccount(@PathVariable("username") String username) {

		try {

			User j = userRepository.getMemberAccount(username);

			MemberDetails d = new MemberDetails(j.getAccount(), j.getFirstname(), j.getLastname(),
					String.valueOf(j.getId()),j.getUser_group(),j.getPassword_expiration_date(),j.getReport_duration(),j.getDisabled(),j.getOtp_enabled());

			return new ResponseEntity<>(d, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/memberoles/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<User> getMemberRoles(@PathVariable("username") String username) {

		try {

			User user = userRepository.getMemberAccount(username);

			return new ResponseEntity<>(user, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/userroles")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<String>> getRoles() {

		try {
			List<String> role = roleRepository.findRoleList();

			return new ResponseEntity<>(role, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/userroles")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> AddRoles(@RequestBody ReportBodyDetails r) {

		try {

			int user_id = Integer.valueOf(r.getText1());
			String role = r.getText2();

			CustomQueries cq = new CustomQueries();
			
			
			int i = cq.addNewUserRole(user_id, role);

			String response;

			if (i == 1) {
				response = "successfully added.";
			} else {
				response = "This role exist.";
			}

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/userrolesremove")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> RemoveRoles(@RequestBody ReportBodyDetails r) {

		try {

			int user_id = Integer.valueOf(r.getText1());
			int role_id = Integer.valueOf(r.getText2());

			CustomQueries cq = new CustomQueries();

			int i = cq.removeRole(user_id, role_id);

			String response;

			if (i == 1) {
				response = "successfully removed.";
			} else {
				response = "Unable to remove role.";
			}

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@PostMapping("/changepassword")
	public ResponseEntity<String> resetPassword(@RequestBody ReportBodyDetails r) {

		String currentPassword = r.getText1();
		String newPassword = r.getText2();
		String username = r.getText4();

		User customer = userRepository.getMemberAccount(username);

		if (customer == null) {

			System.out.println("not done");

			return new ResponseEntity<>("Error occured.", HttpStatus.CREATED);

		} else {

			userDetailsServiceImpl.updatePassword(customer, newPassword);

			System.out.println("done");

			return new ResponseEntity<>("Done", HttpStatus.CREATED);
		}

	}

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@PostMapping("/changepasswordadmin")
	public ResponseEntity<String> adminresetPassword(@RequestBody ReportBodyDetails r) {

		String currentPassword = r.getText1();
		String newPassword = RandomString.make(7);
		String username = r.getText4();

		User customer = userRepository.getMemberAccount(username);

		if (customer == null) {

			return new ResponseEntity<>("Error occured.", HttpStatus.CREATED);

		} else {

			userDetailsServiceImpl.updatePassword(customer, newPassword);

			return new ResponseEntity<>(newPassword, HttpStatus.CREATED);
		}

	}

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping("/usersroles")
	public ResponseEntity<List<User_roles_list>> usersRoles() {

		try {

			CustomQueries cm = new CustomQueries();

			return new ResponseEntity<>(cm.getUsersAndRoles(), HttpStatus.CREATED);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping("/usersrolessearch/{text}")
	public ResponseEntity<List<User_roles_list>> usersRolesSearch(@PathVariable("text") String searchText) {

		try {

			CustomQueries cm = new CustomQueries();
			
			System.out.println("======member=====388==="+cm.getUsersAndRolesSearch(searchText));

			return new ResponseEntity<>(cm.getUsersAndRolesSearch(searchText), HttpStatus.CREATED);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
