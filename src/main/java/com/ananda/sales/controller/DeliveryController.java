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

import com.ananda.sales.model.Delivery;
import com.ananda.sales.model.ReportBodyDetails;
import com.ananda.sales.repository.DeliveryRepository;
import com.ananda.sales.repository.StockLevelRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class DeliveryController {

	@Autowired
	DeliveryRepository deliveryRepo;
	
	@Autowired
	StockLevelRepository stockLevelRepo;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private Sort.Direction getSortDirection(String direction) {

		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;

	}

	@GetMapping("/delivery")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getAllDeliveryPage(@RequestParam(required = false) String code,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "200") int size,
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

			List<Delivery> delivery = new ArrayList<Delivery>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<Delivery> pageTuts;
			if (code == null)
				pageTuts = deliveryRepo.findAll(pagingSort);
			else
				pageTuts = deliveryRepo.findBycustomerContaining(code, pagingSort);

			delivery = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", delivery);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/deliveryreport")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Delivery>> getDeliveryReport(@RequestBody ReportBodyDetails r) {

		try {
			
			String date1 = r.getText1();
			String date2 = r.getText2();
			String stock = r.getText3();
			
			System.out.println("====116==="+stock+ " "+date1+" "+date2);
			
			List<Delivery> delivery = deliveryRepo.findReportByDate(stock,date1, date2);
						

			return new ResponseEntity<>(delivery, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/deliveryreportreste")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Delivery>> getDeliveryReportReste(@RequestBody ReportBodyDetails r) {

		try {
			
			String date1 = r.getText1();
			String date2 = r.getText2();
			String stock = r.getText3();
			
			List<Delivery> delivery = deliveryRepo.findReportByDateReste(stock,date1, date2);
						

			return new ResponseEntity<>(delivery, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/deliveryreportpending")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Delivery>> getDeliveryReportPending(@RequestBody ReportBodyDetails r) {

		try {
			
			String date1 = r.getText1();
			String date2 = r.getText2();
			String stock = r.getText3();
			
			List<Delivery> delivery = deliveryRepo.findReportByDatePending(stock,date1, date2);
			
							

			return new ResponseEntity<>(delivery, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/deliveryreportcompleted")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Delivery>> getDeliveryReportCompleted(@RequestBody ReportBodyDetails r) {

		try {
			
			String date1 = r.getText1();
			String date2 = r.getText2();
			String stock = r.getText3();
			
			List<Delivery> delivery = deliveryRepo.findReportByDateCompleted(stock,date1, date2);
						

			return new ResponseEntity<>(delivery, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/deliverysearch")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Delivery>> getDeliveryReportSearch(@RequestBody ReportBodyDetails r) {

		try {
			
			String type = r.getText1();
			String searchText = r.getText2();
			
			if(type.equals("customer")) {
				
				return new ResponseEntity<>(deliveryRepo.searchByCustomer(searchText), HttpStatus.OK);
			}else {
								

				return new ResponseEntity<>(deliveryRepo.searchByOrderNo(searchText), HttpStatus.OK);
			}
			
			
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	@GetMapping("/delivery/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Delivery> getDelivery(@PathVariable("id") long id) {

		Optional<Delivery> data = deliveryRepo.findById(id);

		if (data.isPresent()) {
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/deliverybyorder/{orderno}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Delivery>> getDeliveryByOrderNo(@PathVariable("orderno") String orderNo) {

		List<Delivery> data = deliveryRepo.findByorderNoContaining(orderNo);

		return new ResponseEntity<>(data, HttpStatus.OK);

	}
	
	@GetMapping("/deliveryrestebyorder/{orderno}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Delivery>> findReportResteByOrderNo(@PathVariable("orderno") String orderNo) {

		List<Delivery> data = deliveryRepo.findReportResteByOrderNo(orderNo);

		return new ResponseEntity<>(data, HttpStatus.OK);

	}

	@PutMapping("/delivery/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Delivery> updateDelivery(@PathVariable("id") long id, @RequestBody Delivery p) {

		Optional<Delivery> data = deliveryRepo.findById(id);

		if (data.isPresent()) {

			Delivery d = data.get();
			
			int totalDelivered = p.getQtyDelivered() + p.getLastQtyDelivered();
			int totalremaining = p.getQtyOrdered() - totalDelivered;
			
			String status1=null;
			if(totalDelivered == p.getQtyOrdered() ) {
				status1="Completed";
			}else if(totalDelivered < p.getQtyOrdered()) {
				status1="Reste";
			}else if(totalDelivered > p.getQtyOrdered()) {
				status1="Error";
				
			}

			d.setQtyToDeliver(p.getQtyToDeliver());
			d.setQtyDelivered(totalDelivered);
			d.setLastQtyDelivered(p.getLastQtyDelivered());
			d.setQtyRemaining(totalremaining);
			d.setDeliveryStatus(status1);
			d.setLocked("Locked");
			d.setDeliveredBy(p.getDeliveredBy());
			d.setDeliveryDate(formatter.format( new Date()));
			
			//updte stock level
			//get current stocklevel
			int existingQty =stockLevelRepo.getCurrentStockLevel(d.getPid(), d.getStock());
			int totalQty2 = existingQty - p.getLastQtyDelivered();
			
						
			String status = "Critical";
			 
			if(totalQty2 >700) {
				status="Full";
			}else if(totalQty2<700 && totalQty2 >500) {
				status="Normal";
			}else if(totalQty2 <500 && totalQty2 > 200) {
				status="Low";
			}else if(totalQty2<200) {
				status="Critical";
			}
			
			//System.out.println("========update stock level"+d.getPid()+" "+d.getStock()+" "+totalQty2);

			//stockLevelRepo.updateStockLevel(totalQty2,status, d.getPid(), d.getStock());
			
					

			return new ResponseEntity<>(deliveryRepo.save(d), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/lockdelivery/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Integer> lockDelivery(@PathVariable("id") long id){
		
		int i = deliveryRepo.lockDelivery(id);
		
		if(i==1) {
		
		return new ResponseEntity<>(i, HttpStatus.OK);
		
	} else {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
		
	}
	
	@GetMapping("/authorizedelivery/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Integer> authorizeDelivery(@PathVariable("id") long id){
		
		int i = deliveryRepo.authorizeDelivery(id);
		
		if(i==1) {
		
		return new ResponseEntity<>(i, HttpStatus.OK);
		
	} else {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
		
	}

}
