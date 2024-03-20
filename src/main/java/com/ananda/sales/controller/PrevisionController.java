package com.ananda.sales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ananda.app.model.Previsions;
import com.ananda.sales.repository.PrevisionRepository;
import com.ananda.sales.model.Prevision;
import com.ananda.sales.model.Products;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class PrevisionController {
	
	@Autowired
	private PrevisionRepository previsionRepository;
	
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

}


