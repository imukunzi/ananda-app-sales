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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ananda.sales.model.ContainerCost;
import com.ananda.sales.repository.ContainerCostRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class ContainerCostController {

	@Autowired
	ContainerCostRepository containerCostRepo;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private Sort.Direction getSortDirection(String direction) {

		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;

	}

	@GetMapping("/shipping")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getAllProductsPage(@RequestParam(required = false) String code,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) {
		
		System.out.println("=====shipping====");

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

			List<ContainerCost> customers = new ArrayList<ContainerCost>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<ContainerCost> pageTuts;
			if (code == null)
				pageTuts = containerCostRepo.findAll(pagingSort);
			else
				pageTuts = containerCostRepo.findByproductContaining(code, pagingSort);

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

	@PostMapping("/shipping")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ContainerCost> saveCustomer(@RequestBody ContainerCost c) {
		try {

			String product = c.getProduct();
			String time = formatter.format(new Date());
			String code = c.getCode();
			String cartons = c.getCartons();
			double qty = c.getQty();
			double container_cbm = c.getContainer_cbm(); // contaner capacity
			double transportPerCbm = c.getTransportPerCbm();
			double cbm = c.getCbm(); // loaded capacity
			double totalCostCbm = c.getTotalCostCbm();
			double transportOfUnit = c.getTransportOfUnit();
			double cifValue = c.getCifValue();
			double taxes53PerCent = c.getTaxes53PerCent();
			double taxesOfTotalCost = c.getTaxesOfTotalCost();
			double exchange = c.getExchange();
			double totalTaxFrw = c.getTotalTaxFrw();
			double buyingUnitChineseMoney = c.getBuyingUnitChineseMoney();
			double exchangeChinese = c.getExchangeChinese();
			double buyingUnitUsd = c.getBuyingUnitUsd();
			double buyingCostUnity = c.getBuyingCostUnity();
			double exchangeFrw = c.getExchange();
			double profit = c.getProfit();
			double valueInStock = c.getValueInStock();
			double rra21 = c.getRra21();
			double saleUnitValue = c.getSaleUnitValue();
			String username = c.getUsername();

			ContainerCost p = containerCostRepo.save(new ContainerCost(product, time, code, cartons, qty, container_cbm,
					transportPerCbm, cbm, totalCostCbm, transportOfUnit, cifValue, taxes53PerCent, taxesOfTotalCost,
					exchange, totalTaxFrw, buyingUnitChineseMoney, exchangeChinese, buyingUnitUsd, buyingCostUnity,
					exchangeFrw, profit, valueInStock, rra21, saleUnitValue, username));

			return new ResponseEntity<>(p, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/shipping/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ContainerCost> getContainer(@PathVariable("id") long id) {

		Optional<ContainerCost> data = containerCostRepo.findById(id);

		if (data.isPresent()) {
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/shipping/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ContainerCost> updat(@PathVariable("id") long id, @RequestBody ContainerCost c) {

		Optional<ContainerCost> data = containerCostRepo.findById(id);

		if (data.isPresent()) {

			ContainerCost cost = data.get();

			cost.setProduct(c.getProduct());
			cost.setTime(c.getTime());
			cost.setCode(c.getCode());
			cost.setCartons(c.getCartons());
			cost.setContainer_cbm(c.getContainer_cbm()); // contaner capacity
			cost.setTransportPerCbm(c.getTransportPerCbm());  
			cost.setTotalCostCbm(c.getTotalCostCbm());  
			cost.setTransportOfUnit(c.getTransportOfUnit());  
			cost.setCifValue(c.getCifValue());  
			cost.setTaxes53PerCent(c.getTaxes53PerCent());
			cost.setTaxesOfTotalCost(c.getTaxesOfTotalCost());  
			cost.setExchange(c.getExchange()); 
			cost.setTotalTaxFrw(c.getTotalTaxFrw()); 
			cost.setBuyingUnitChineseMoney(c.getBuyingUnitChineseMoney());  
			cost.setExchangeChinese(c.getExchangeChinese());  
			cost.setBuyingUnitUsd(c.getBuyingUnitUsd()) ;
			cost.setBuyingCostUnity(c.getBuyingCostUnity());
			cost.setExchange(c.getExchange());  
			cost.setProfit(c.getProfit());  
			cost.setValueInStock(c.getValueInStock()); 
			cost.setRra21(c.getRra21());
			cost.setSaleUnitValue(c.getSaleUnitValue());
			cost.setUsername(c.getUsername()); 

			return new ResponseEntity<>(containerCostRepo.save(cost), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
