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

import com.ananda.app.model.Previsions;
import com.ananda.sales.repository.PrevisionRepository;
import com.ananda.sales.model.Prevision;
import com.ananda.sales.model.Products;
import com.ananda.sales.model.ReportBodyDetails;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class PrevisionController {
	
	@Autowired
	private PrevisionRepository previsionRepository;
	
	private Sort.Direction getSortDirection(String direction) {

		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;

	}
	
	
	//kwirinda duplicate kuri prevision
	@PostMapping("/prevision")
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	public ResponseEntity<String> savePrevision(@RequestBody Prevision prevision) {
		
		String response="";

		try {
			
			int count  = previsionRepository.checkSavedPrevisionDuplication(prevision.getYear(), prevision.getType());
			
			if(count > 0) {
				System.out.println("you hava saved this provision");
				response="This prevision exist.";
			}else {
				
				String username = prevision.getUsername();
				int year = prevision.getYear();
				String description = prevision.getDescription();
				String type = prevision.getType();
				double january = prevision.getJanuary();
				double february = prevision.getFebruary();
				double march = prevision.getMarch();
				double april = prevision.getApril();
				double may = prevision.getMay();
				double june = prevision.getJune();
				double july = prevision.getJuly();
				double augostin = prevision.getAugostin();
				double september = prevision.getSeptember();
				double octomber = prevision.getOctomber();
				double november = prevision.getNovember();
				double december = prevision.getDecember();

				Prevision p = previsionRepository.save(new Prevision( username,  year,  description,  type,  january,  february,
						 march,  april,  may,  june,  july,  augostin,  september, octomber,  november,  december));
				
				response="Saved successfully.";
				
			}
			

				
				return new ResponseEntity<>(response, HttpStatus.CREATED);

			
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	//retrive data for table
	@GetMapping("/prevision")
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	public ResponseEntity<Map<String, Object>> getAllPrevisionPage(@RequestParam(required = false) String code,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) {
  
//		System.out.println("========123========"); 
		
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

			List<Prevision> prevision = new ArrayList<Prevision>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<Prevision> pageTuts;
			if (code == null)
				pageTuts = previsionRepository.findAll(pagingSort);
			else
				pageTuts = previsionRepository.findByYearContaining(code, pagingSort);

			prevision = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", prevision);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	//ipi for getting table of previsionlist by id
	@GetMapping("/prevision/{id}")
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	public ResponseEntity<Prevision> getPrevision(@PathVariable("id") long id) {

		Optional<Prevision> previsionData = previsionRepository.findById(id);

		if (previsionData.isPresent()) {
			return new ResponseEntity<>(previsionData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	
	//Editing 
	@PutMapping("/prevision/{id}")
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	public ResponseEntity<String> updatePrevision(@PathVariable("id") long id, @RequestBody Prevision p) {

//		int count = productRepository.checkIfProductExist(p.getName(), p.getCode(), p.getSize(), p.getColor());
//		
//		System.out.println("====118==="+count);
//		
//		if(count > 0) {
//			return new ResponseEntity<>("This product exist!", HttpStatus.OK);
//			
//		}

		Optional<Prevision> previsionData = previsionRepository.findById(id);

		if (previsionData.isPresent()) {

			Prevision prevision = previsionData.get();

			prevision.setUsername(p.getUsername());
			prevision.setYear(p.getYear());
			prevision.setDescription(p.getDescription());
			prevision.setType(p.getType());
			prevision.setJanuary(p.getJanuary());
			prevision.setFebruary(p.getFebruary());
			prevision.setMarch(p.getMarch());
			prevision.setApril(p.getApril());
			prevision.setMay(p.getMay());
		    prevision.setJune(p.getJune());
		    prevision.setJuly(p.getJuly());
		    prevision.setAugostin(p.getAugostin());
		    prevision.setSeptember(p.getSeptember());
		    prevision.setOctomber(p.getOctomber());
		    prevision.setNovember(p.getNovember());
		    prevision.setDecember(p.getDecember());
		
			previsionRepository.save(prevision);

			return new ResponseEntity<>("Updated successfully!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	
	//delete
	@DeleteMapping("/prevision/{id}")
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {

		try {
			previsionRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}




