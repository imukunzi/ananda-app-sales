package com.ananda.sales.controller;

import java.io.UnsupportedEncodingException;
 
import java.util.HashSet;
import java.util.List;
 
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
 
import org.springframework.web.bind.annotation.RestController;

import com.ananda.sales.security.service.UserDetailsServiceImpl;
 
import com.ananda.sales.model.ERole;
import com.ananda.sales.model.ReportBodyDetails;
import com.ananda.sales.model.Role;
import com.ananda.sales.model.User;
import com.ananda.sales.payload.request.LoginRequest;
import com.ananda.sales.payload.request.SignupRequest;
import com.ananda.sales.payload.response.JwtResponse;
import com.ananda.sales.payload.response.MessageResponse;
import com.ananda.sales.repository.RoleRepository;
import com.ananda.sales.repository.UserRepository;
import com.ananda.sales.security.jwt.JwtUtils;
import com.ananda.sales.security.service.SignupRequest_ResetPassword;
import com.ananda.sales.security.service.UserDetailsImpl;

import net.bytebuddy.utility.RandomString;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	private JavaMailSenderImpl mailSender;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	private Sort.Direction getSortDirection(String direction) {

		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;

	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		
		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		
		

				// Create new user's account
				String defaulPassword ="JS1gEHkew817UtOycGcQUEPHEKjwzN";
				User user = new User(signUpRequest.getUsername(), signUpRequest.getUsername(),
						encoder.encode(signUpRequest.getPassword()), signUpRequest.getFirstname(), signUpRequest.getLastname(),signUpRequest.getPhone(),signUpRequest.getAccount(),signUpRequest.getUser_group(),signUpRequest.getPassword_expiration_date(),signUpRequest.getReport_duration(),signUpRequest.getDisabled(),signUpRequest.getOtp_enabled());

								
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "manager":
					Role accountantRole = roleRepository.findByName(ERole.ROLE_MANAGER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(accountantRole);

					break;
				case "saler":
					Role salerRole = roleRepository.findByName(ERole.ROLE_SALER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(salerRole);

					break;
				case "shipping":
					Role ITRole = roleRepository.findByName(ERole.ROLE_SHIPPING)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(ITRole);
					break;
				case "cashier":
					Role cashierRole = roleRepository.findByName(ERole.ROLE_CASHIER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(cashierRole);
					break;
				case "stock":
					Role stockRole = roleRepository.findByName(ERole.ROLE_STOCK)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(stockRole);
					break;
					
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);
		
		//activate account
		//processSetPassword(signUpRequest.getUsername());

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	 //forgetPassword
	@PostMapping("/forgot_password/{username}")
		public ResponseEntity<?> processSetPassword(@PathVariable("username") String username) {
			
					
			String token = RandomString.make(30);
					

			try {
				userDetailsServiceImpl.updateResetPasswordToken(token, username);
				// String resetPasswordLink = Utility.getSiteURL(request) +
				// "/reset_password?token=" + token;
				String resetPasswordLink = "http://localhost:3000" + "/activation?token=" + token;
				sendEmail_registration(username, resetPasswordLink);
				// model.addAttribute("message", "We have sent a reset password link to your
				// email. Please check.");

			} catch (UsernameNotFoundException ex) {
				// model.addAttribute("error", ex.getMessage());
				return ResponseEntity.ok(new MessageResponse("Username not found!"));
			} catch (UnsupportedEncodingException | MessagingException e) {
				// model.addAttribute("error", "Error while sending email");
				return ResponseEntity.ok(new MessageResponse("Unable to precess your email!"));
			}

			return ResponseEntity.ok(new MessageResponse("Your request was received. Check your email!"));
		}

		//@GetMapping("/forgot_password")
		public String showForgotPasswordForm() {

			return null;

		}

		public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom("info@vidya.rw", "MUKUNZI Support");
			helper.setTo(recipientEmail);

			String subject = "Here's the link to reset your password";

			String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
					+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + link
					+ "\">Change my password</a></p>" + "<br>" + "<p>Ignore this email if you do remember your password, "
					+ "or you have not made the request.</p>";

			helper.setSubject(subject);

			helper.setText(content, true);

			mailSender.send(message);
		}

		public void sendEmail_registration(String recipientEmail, String link)
				throws MessagingException, UnsupportedEncodingException {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom("contact@vidya.rw", "MUKUNZI Sign-up");
			helper.setTo(recipientEmail);

			String subject = "MUKUNZI sign-up";

			String content = "<p>Hello,</p>"
					+ "<p>You have sign-up to MUKUNZI APP you have to set your password to continue the precess.</p>"
					+ "<p>Click the link below to activate your account and set password:</p>" + "<p><a href=\"" + link
					+ "\"> Activate my account</a></p>" + "<br>" + "<p>Regards.</p>";

			helper.setSubject(subject);

			helper.setText(content, true);

			mailSender.send(message);
		}

		@GetMapping("/reset_password/{token}")
		public void showResetPasswordForm(@PathVariable(required = true) String token) {

		}

		@PostMapping("/reset_password")
		public ResponseEntity<?> processResetPassword(@Valid @RequestBody SignupRequest_ResetPassword signUpRequest) {

			String password = signUpRequest.getPassword();
			String token = signUpRequest.getToken();
			
			
			User customer = userDetailsServiceImpl.getByResetPasswordToken(token);
			

			if (customer == null) {
							
				return ResponseEntity.ok(new MessageResponse("Invalid token!"));
				
			} else {
				
				userDetailsServiceImpl.updatePassword(customer, password);
				
						
				return ResponseEntity.ok(new MessageResponse("You can now signin!"));
			}

			

		}
		
		
		
}
