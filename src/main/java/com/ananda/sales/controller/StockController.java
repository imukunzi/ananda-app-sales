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

import com.ananda.sales.model.Products;
import com.ananda.sales.model.ReportBodyDetails;
import com.ananda.sales.model.Stock;
import com.ananda.sales.model.StockLevel;
import com.ananda.sales.repository.GetStockData;
import com.ananda.sales.repository.ProductsRepository;
import com.ananda.sales.repository.StockLegacyRepository;
import com.ananda.sales.repository.StockLevelRepository;
import com.ananda.sales.repository.StockRepository;

import net.bytebuddy.utility.RandomString;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class StockController {

	private GetStockData getStockData = new GetStockData();

	@Autowired
	StockRepository stockRepo;

	@Autowired
	ProductsRepository productRepo;

	@Autowired
	StockLevelRepository stockLevelRepo;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private StockLegacyRepository stockLegacyRepo;

	private Integer total_qty;
	private Integer total_qty_tranfer;

	private List<Products> productList;

	private int sum = 0;
	private List<Stock> stockIn = new ArrayList<Stock>();

	private String confirmationMessage;

	private Sort.Direction getSortDirection(String direction) {

		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;

	}

	@GetMapping("/stock")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getAllStockPage(@RequestParam(required = false) String code,
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

			List<Stock> stock = new ArrayList<Stock>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<Stock> pageTuts;
			if (code == null)
				pageTuts = stockRepo.findAll(pagingSort);
			else
				pageTuts = stockRepo.findByCodeContaining(code, pagingSort);

			stock = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", stock);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/stock")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Stock> saveProducts(@RequestBody Stock stock) {

		try {

			Stock p = stockRepo.save(new Stock(stock.getStand(), stock.getDate1(), stock.getPid(), stock.getProduct(),
					stock.getQty_in(), stock.getStockkeeper(), stock.getCode(), stock.getSize(), stock.getQty_out(),
					stock.getQty_transfer_in(), stock.getQty_transfer_out(), stock.getQty_damage(),
					stock.getStand_destination(), stock.getStand_source(), stock.getDescription(), stock.getComment(),
					stock.getTransfer_type(), stock.getColor(), stock.getStock_type(), stock.getStockEntryId(),
					stock.getReturned(), "Pending", stock.getLocation(),stock.getPrice_value()));

			// productRepo.updateavailableQty(stock.getQty_in(),
			// Long.valueOf(stock.getPid()));

			// check if product exist in stock level and create default stock level 0

			Integer count = stockLevelRepo.checkIfProductExist(stock.getPid(), stock.getStand());
			Integer current_qty = stockLevelRepo.getCurrentStockLevel(stock.getPid(), stock.getStand());
			Products product = productRepo.getById(Long.valueOf(stock.getPid()));

			System.out.println("======stock====149====="+stock.getPrice_value());
			
			if (count == 0) {
				total_qty = 0 + stock.getQty_in();
			} else {
				total_qty = current_qty + stock.getQty_in();
			}

			String status = "";

			if (count == 0 || count == null) {
				stockLevelRepo.save(new StockLevel(stock.getCode(), stock.getPid(), stock.getProduct(),
						stock.getColor(), stock.getSize(), stock.getStand(), 0, status, "Updated",stock.getPrice_value(),stock.getDescription()));
			} else {

				if (total_qty > 700) {
					status = "Full";
				} else if (total_qty < 700 && total_qty > 500) {
					status = "Normal";
				} else if (total_qty < 500 && total_qty > 200) {
					status = "Low";
				} else if (total_qty < 200) {
					status = "Critical";
				}

				// stockLevelRepo.updateStockLevel(total_qty, status, stock.getPid(),
				// stock.getStand());
				;
				
				

			}

			return new ResponseEntity<>(p, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	
	@PostMapping("/getbystockanddate")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Stock>> getByStockandDate(@RequestBody ReportBodyDetails r) {
		
		String date1 = r.getText1();
		String date2 =r.getText2();
		String stock = r.getText3();
		String type =r.getText4();
		
		
		 try {
			 
			 List<Stock> l =stockRepo.findByStockandDate(date1, date2, stock,type);
			 
						 

			return new ResponseEntity<>(l, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/stockreturned")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Stock> saveStockReturn(@RequestBody Stock stock) {

		try {

			Stock p = stockRepo.save(new Stock(stock.getStand(), stock.getDate1(), stock.getPid(), stock.getProduct(),
					stock.getQty_in(), stock.getStockkeeper(), stock.getCode(), stock.getSize(), stock.getQty_out(),
					stock.getQty_transfer_in(), stock.getQty_transfer_out(), stock.getQty_damage(),
					stock.getStand_destination(), stock.getStand_source(), stock.getDescription(), stock.getComment(),
					stock.getTransfer_type(), stock.getColor(), stock.getStock_type(), stock.getStockEntryId(),
					stock.getReturned(), "Pending", stock.getLocation(),stock.getPrice_value()));

			// productRepo.updateavailableQty(stock.getQty_in(),
			// Long.valueOf(stock.getPid()));

			// check if product exist in stock level and create default stock level 0

			Integer count = stockLevelRepo.checkIfProductExist(stock.getPid(), stock.getStand());
			Integer current_qty = stockLevelRepo.getCurrentStockLevel(stock.getPid(), stock.getStand());
			Products product = productRepo.getById(Long.valueOf(stock.getPid()));

			if (count == 0) {
				total_qty = 0 + stock.getReturned();
			} else {
				total_qty = current_qty + stock.getReturned();
			}

			String status = "";

			if (count == 0 || count == null) {
				stockLevelRepo.save(new StockLevel(stock.getCode(), stock.getPid(), stock.getProduct(),
						stock.getColor(), stock.getSize(), stock.getStand(), stock.getQty_in(), status, "Updated",product.getMin_price(),stock.getDescription()));
			} else {

				if (total_qty > 700) {
					status = "Full";
				} else if (total_qty < 700 && total_qty > 500) {
					status = "Normal";
				} else if (total_qty < 500 && total_qty > 200) {
					status = "Low";
				} else if (total_qty < 200) {
					status = "Critical";
				}

				stockLevelRepo.updateStockLevel(total_qty, status, stock.getPid(), stock.getStand());
				;

			}

			return new ResponseEntity<>(p, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/stockout")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Stock> saveStockOut(@RequestBody Stock stock) {
		
		
		try {

			Stock p = stockRepo.save(new Stock(stock.getStand(), stock.getDate1(), stock.getPid(), stock.getProduct(),
					stock.getQty_in(), stock.getStockkeeper(), stock.getCode(), stock.getSize(), stock.getQty_out(),
					stock.getQty_transfer_in(), stock.getQty_transfer_out(), stock.getQty_damage(),
					stock.getStand_destination(), stock.getStand_source(), stock.getDescription(), stock.getComment(),
					stock.getTransfer_type(), stock.getColor(), stock.getStock_type(), stock.getStockEntryId(),
					stock.getReturned(), "Pending", stock.getLocation(),stock.getPrice_value()));
			
			Integer count1 = stockLevelRepo.checkIfProductExist(stock.getPid(), stock.getStand());
			Integer current_qty1 = stockLevelRepo.getCurrentStockLevel(stock.getPid(), stock.getStand_destination());
			Products product = productRepo.getById(Long.valueOf(stock.getPid()));

			
			String status1 = "";

			if (count1 == 0 || count1 == null) {
				stockLevelRepo.save(new StockLevel(stock.getCode(), stock.getPid(), stock.getProduct(),
						stock.getColor(), stock.getSize(), stock.getStand(), 0, status1, "Updated",product.getMax_price(),stock.getDescription()));
			}
			
			
			
			if(stock.getStand().equals("GAKINJIRO")) {
				
				Stock i = stockRepo.save(new Stock(stock.getStand_destination(), stock.getDate1(), stock.getPid(), stock.getProduct(),
						stock.getQty_out(), stock.getStockkeeper(), stock.getCode(), stock.getSize(), 0,
						stock.getQty_transfer_in(), stock.getQty_transfer_out(), stock.getQty_damage(),
						stock.getStand_destination(), stock.getStand_source(), stock.getDescription(), stock.getComment(),
						stock.getTransfer_type(), stock.getColor(), "IN", stock.getStockEntryId(),
						stock.getReturned(), "Pending", stock.getLocation(),stock.getPrice_value()));
				
				// check if product exist in stock level and create default stock level 0

				Integer count = stockLevelRepo.checkIfProductExist(stock.getPid(), stock.getStand_destination());
				Integer current_qty = stockLevelRepo.getCurrentStockLevel(stock.getPid(), stock.getStand_destination());
				

				
				String status = "";

				if (count == 0 || count == null) {
					stockLevelRepo.save(new StockLevel(stock.getCode(), stock.getPid(), stock.getProduct(),
							stock.getColor(), stock.getSize(), stock.getStand_destination(), 0, status, "Updated",product.getMax_price(),stock.getDescription()));
				}
				
			}else {
				
				
			}

			// productRepo.updateavailableQty(stock.getQty_in(),
			// Long.valueOf(stock.getPid()));

			// check if product exist in stock level and create default stock level 0

			Integer count = stockLevelRepo.checkIfProductExist(stock.getPid(), stock.getStand());
			Integer current_qty = stockLevelRepo.getCurrentStockLevel(stock.getPid(), stock.getStand());

			if (count == 0) {
				total_qty = 0 - stock.getQty_out();
			} else {
				total_qty = current_qty - stock.getQty_out();
			}

			String status = "";

			if (count == 0 || count == null) {
				// stockLevelRepo.save(new StockLevel(stock.getCode(), stock.getPid(),
				// stock.getProduct(),
				// stock.getColor(), stock.getSize(), stock.getStand(), stock.getQty_out(),
				// status, "Updated"));
			} else {

				if (total_qty > 700) {
					status = "Full";
				} else if (total_qty < 700 && total_qty > 500) {
					status = "Normal";
				} else if (total_qty < 500 && total_qty > 200) {
					status = "Low";
				} else if (total_qty < 200) {
					status = "Critical";
				}

				// stockLevelRepo.updateStockLevel(total_qty, status, stock.getPid(),
				// stock.getStand());
				;
				
							

			}

			return new ResponseEntity<>(p, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/stock/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Stock> getStock(@PathVariable("id") long id) {

		Optional<Stock> data = stockRepo.findById(id);

		if (data.isPresent()) {
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/stock/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Stock> updateStock(@PathVariable("id") long id, @RequestBody Stock p) {

		Optional<Stock> data = stockRepo.findById(id);

		if (data.isPresent()) {

			Stock s = data.get();

//updating stock level

			// existing pid stock and stock level
			// Integer existingQty = stockLevelRepo.getCurrentStockLevel(s.getPid(),
			// s.getStand());
			// Integer oldQty = s.getQty_in();

			// old item

			// Integer totalQty = existingQty - oldQty;

			/*
			 * String status = "Critical"; if (totalQty > 700) { status = "Full"; } else if
			 * (totalQty < 700 && totalQty > 500) { status = "Normal"; } else if (totalQty <
			 * 500 && totalQty > 200) { status = "Low"; } else if (totalQty < 200) { status
			 * = "Critical"; }
			 */
			// stockLevelRepo.updateStockLevel(totalQty, status, s.getPid(), s.getStand());

			// actual stock on update for new item
			/*
			 * Integer existingQty2 = stockLevelRepo.getCurrentStockLevel(p.getPid(),
			 * p.getStand()); Integer newQty = p.getQty_in();
			 * 
			 * Integer totalQty2 = existingQty2 + newQty;
			 * 
			 * if (totalQty2 > 700) { status = "Full"; } else if (totalQty2 < 700 &&
			 * total_qty > 500) { status = "Normal"; } else if (totalQty2 < 500 && total_qty
			 * > 200) { status = "Low"; } else if (totalQty2 < 200) { status = "Critical"; }
			 */

			// stockLevelRepo.updateStockLevel(totalQty2, status, p.getPid(), p.getStand());

			s.setStand(p.getStand());
			s.setDate1(p.getDate1());
			s.setPid(p.getPid());
			s.setProduct(p.getProduct());
			s.setQty_in(p.getQty_in());
			s.setStockkeeper(p.getStockkeeper());
			s.setCode(p.getCode());
			s.setSize(p.getSize());
			s.setQty_out(p.getQty_out());
			s.setPrice_value(p.getPrice_value());
			s.setQty_transfer_in(p.getQty_transfer_in());
			s.setQty_transfer_out(p.getQty_transfer_out());
			s.setQty_damage(p.getQty_damage());
			s.setStand_destination(p.getStand_destination());
			s.setStand_source(p.getStand_source());
			s.setDescription(p.getDescription());
			s.setComment(p.getComment());
			s.setTransfer_type(p.getTransfer_type());
			s.setColor(p.getColor());
			s.setDescription(p.getDescription());
			
			//check if default stock level exist
			Integer count1 = stockLevelRepo.checkIfProductExist(p.getPid(), p.getStand_destination());
			Integer current_qty1 = stockLevelRepo.getCurrentStockLevel(p.getPid(), p.getStand_destination());
			Products product = productRepo.getById(Long.valueOf(s.getPid()));

			
			String status1 = "";

			if (count1 == 0 || count1 == null) {
				stockLevelRepo.save(new StockLevel(p.getCode(), p.getPid(), p.getProduct(),
						p.getColor(), p.getSize(), p.getStand(), 0, status1, "Updated",product.getMax_price(),p.getDescription()));
			}
			

			return new ResponseEntity<>(stockRepo.save(s), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/stockreturned/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Stock> updateStockReturned(@PathVariable("id") long id, @RequestBody Stock p) {

		Optional<Stock> data = stockRepo.findById(id);

		if (data.isPresent()) {

			Stock s = data.get();

//updating stock level

			// existing pid stock and stock level
			Integer existingQty = stockLevelRepo.getCurrentStockLevel(s.getPid(), s.getStand());
			Integer oldQty = s.getReturned();
			// old item

			Integer totalQty = existingQty - oldQty;

			String status = "Critical";
			if (totalQty > 700) {
				status = "Full";
			} else if (totalQty < 700 && totalQty > 500) {
				status = "Normal";
			} else if (totalQty < 500 && totalQty > 200) {
				status = "Low";
			} else if (totalQty < 200) {
				status = "Critical";
			}

			stockLevelRepo.updateStockLevel(totalQty, status, s.getPid(), s.getStand());

			// actual stock on update for new item
			Integer existingQty2 = stockLevelRepo.getCurrentStockLevel(p.getPid(), p.getStand());
			Integer newQty = p.getReturned();

			Integer totalQty2 = existingQty2 + newQty;

			if (totalQty2 > 700) {
				status = "Full";
			} else if (totalQty2 < 700 && total_qty > 500) {
				status = "Normal";
			} else if (totalQty2 < 500 && total_qty > 200) {
				status = "Low";
			} else if (totalQty2 < 200) {
				status = "Critical";
			}

			stockLevelRepo.updateStockLevel(totalQty2, status, p.getPid(), p.getStand());

			s.setStand(p.getStand());
			s.setDate1(p.getDate1());
			s.setPid(p.getPid());
			s.setProduct(p.getProduct());
			s.setQty_in(p.getQty_in());
			s.setStockkeeper(p.getStockkeeper());
			s.setCode(p.getCode());
			s.setSize(p.getSize());
			s.setReturned(p.getReturned());

			s.setStand_source(p.getStand_source());
			s.setDescription(p.getDescription());
			s.setComment(p.getComment());
			s.setTransfer_type(p.getTransfer_type());
			s.setColor(p.getColor());
			s.setConfirmation(p.getConfirmation());

			return new ResponseEntity<>(stockRepo.save(s), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/stockout/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Stock> updateStockOut(@PathVariable("id") long id, @RequestBody Stock p) {

		Optional<Stock> data = stockRepo.findById(id);

		if (data.isPresent()) {

			Stock s = data.get();

			// updating stock level

			// existing pid stock and stock level
			/*
			 * Integer existingQty = stockLevelRepo.getCurrentStockLevel(s.getPid(),
			 * s.getStand()); Integer oldQty = s.getQty_out();
			 * 
			 * // old item
			 * 
			 * Integer totalQty = existingQty + oldQty;
			 * 
			 * String status = "Critical"; if (totalQty > 700) { status = "Full"; } else if
			 * (totalQty < 700 && totalQty > 500) { status = "Normal"; } else if (totalQty <
			 * 500 && totalQty > 200) { status = "Low"; } else if (totalQty < 200) { status
			 * = "Critical"; }
			 * 
			 * stockLevelRepo.updateStockLevel(totalQty, status, s.getPid(), s.getStand());
			 * 
			 * // actual stock on update for new item Integer existingQty2 =
			 * stockLevelRepo.getCurrentStockLevel(p.getPid(), p.getStand()); Integer newQty
			 * = p.getQty_out();
			 * 
			 * Integer totalQty2 = existingQty2 - newQty;
			 * 
			 * if (totalQty2 > 700) { status = "Full"; } else if (totalQty2 < 700 &&
			 * totalQty2 > 500) { status = "Normal"; } else if (totalQty2 < 500 && totalQty2
			 * > 200) { status = "Low"; } else if (totalQty2 < 200) { status = "Critical"; }
			 */

			// stockLevelRepo.updateStockLevel(totalQty2, status, p.getPid(), p.getStand());

			s.setStand(p.getStand());
			s.setDate1(p.getDate1());
			s.setPid(p.getPid());
			s.setProduct(p.getProduct());
			s.setQty_in(p.getQty_in());
			s.setStockkeeper(p.getStockkeeper());
			s.setCode(p.getCode());
			s.setSize(p.getSize());
			s.setQty_out(p.getQty_out());
			s.setPrice_value(p.getPrice_value());
			s.setQty_transfer_in(p.getQty_transfer_in());
			s.setQty_transfer_out(p.getQty_transfer_out());
			s.setQty_damage(p.getQty_damage());
			s.setStand_destination(p.getStand_destination());
			s.setStand_source(p.getStand_source());
			s.setDescription(p.getDescription());
			s.setComment(p.getComment());
			s.setTransfer_type(p.getTransfer_type());
			s.setColor(p.getColor());
			s.setConfirmation(p.getConfirmation());
			s.setDescription(p.getDescription());
			
			Products product = productRepo.getById(Long.valueOf(s.getPid()));
			
			int count2=0;
			String status1 = "";
			
			Integer count1 = stockLevelRepo.checkIfProductExist(p.getPid(), p.getStand_destination());
			//Integer current_qty1 = stockLevelRepo.getCurrentStockLevel(p.getPid(), p.getStand_destination());

			
			
			if(stockLevelRepo.checkIfProductExist(p.getPid(), p.getStand_destination()) != null) {
				count2 = stockLevelRepo.checkIfProductExist(p.getPid(), p.getStand());
			}
			
			if (count2 < 1 || count1 <1) {
				stockLevelRepo.save(new StockLevel(p.getCode(), p.getPid(), p.getProduct(),
						p.getColor(), p.getSize(), p.getStand(), 0, status1, "Updated",product.getMax_price(),p.getDescription()));
			}
			
			

			/*
			 * if (count1 == 0 || count1 == null) { stockLevelRepo.save(new
			 * StockLevel(p.getCode(), p.getPid(), p.getProduct(), p.getColor(),
			 * p.getSize(), p.getStand(), 0, status1, "Updated")); }
			 */

			return new ResponseEntity<>(stockRepo.save(s), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/stock/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {

		try {
			// stockRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/////////////////////////// STOCK
	/////////////////////////// IN/////////////////////////////////////////////

	@GetMapping("/stockinlist")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getStockInListPage(@RequestParam(required = false) String code,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size,
			@RequestParam(required = false) String date, @RequestParam(defaultValue = "id,desc") String[] sort,@RequestParam(required = false) String date1,@RequestParam(required = false) String date2,@RequestParam(required = false) String stock_type) {

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

			List<Stock> stock = new ArrayList<Stock>();
			//Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			//Page<Stock> pageTuts;
			if (code != null)
				stock = stockRepo.findStockInByDateSearch(code, stock_type);
			else
				//pageTuts = stockRepo.findByDate1Containing(date, pagingSort);

			stock = stockRepo.findStockInByDate(date, date, stock_type);

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", stock);
			response.put("currentPage", 0);
			response.put("totalItems", 0);
			response.put("totalPages", 0);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/stockin")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Stock>> getStockInList(@RequestBody ReportBodyDetails request) {

		String date1 = request.getText1();
		String date2 = request.getText2();
		String stock_type = request.getText3();
		
		System.out.println("STOCK 683==="+stock_type+" "+stock_type);

		try {
			List<Stock> stockIn = stockRepo.findStockInByDate(date1, date2, stock_type);

			return new ResponseEntity<>(stockIn, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/stockininspection")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getStockInListInspection(@RequestBody ReportBodyDetails request) {

		String date1 = request.getText1();
		String date2 = request.getText2();
		String stock_type = request.getText3();
		String location = request.getText4();

		try {

			if (stock_type.equals("IN")) {
				sum = 0;
				stockIn = stockRepo.findStockInByDateInspection(date1, date2, stock_type, location);
				sum = stockRepo.findStockInByDateInspectionSum(date1, date2, stock_type, location);

			} else {
				// stockIn = stockRepo.findStockInByDateInspection(date1, date2, stock_type);
				// sum = stockRepo.findStockOutByDateInspectionSum(date1, date2, stock_type);
				// System.out.println("===577==inspection"+sum);
			}

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", stockIn);
			response.put("totalItems", sum);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/stockinsearch")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Stock>> getStockInListSearch(@RequestBody ReportBodyDetails request) {

		String date1 = request.getText1();
		String date2 = request.getText2();
		String stock_type = request.getText3();
		String searchText = request.getText4();

		try {
			List<Stock> stockIn = stockRepo.findStockInByDateSearch(searchText, stock_type);

			return new ResponseEntity<>(stockIn, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/stockinsearchwithname")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Stock>> getStockInListSearchwithname(@RequestBody ReportBodyDetails request) {

		String date1 = request.getText1();
		String date2 = request.getText2();
		String stock_type = request.getText3();
		String searchText = request.getText4();
		
		//System.out.println("STOCK 760==="+stock_type+" "+searchText);

		try {
			List<Stock> stockIn = stockRepo.findStockInByDateSearchwithname(searchText, stock_type);

			return new ResponseEntity<>(stockIn, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	////////////////////////////// stock
	////////////////////////////// level//////////////////////////////////////////////

	@GetMapping("/stocklevel")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getAllStockLevel(@RequestParam(required = false) String code,
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

			List<StockLevel> stock = new ArrayList<StockLevel>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<StockLevel> pageTuts;
			if (code == null)
				pageTuts = stockLevelRepo.findAll(pagingSort);
			else
				pageTuts = stockLevelRepo.findBynameContaining(code, pagingSort);

			stock = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", stock);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@DeleteMapping("/stocklevel/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteStockLevel(@PathVariable("id") long id) {

		try {
			 stockLevelRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/stocklevelbystock")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getAllStockLevelByStock(@RequestParam(required = false) String code,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort,
			@RequestParam(required = false) String optionCategorySelectedValue) {

		// getLegacyStockLevel(optionCategorySelectedValue,code);
		// Bus

		//System.out.println("==stock==656==" + code + " stock:" + optionCategorySelectedValue);

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

			List<StockLevel> stock = new ArrayList<StockLevel>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<StockLevel> pageTuts;
			if (optionCategorySelectedValue == null)
				pageTuts = stockLevelRepo.findAll(pagingSort);
			else
				pageTuts = stockLevelRepo.findBystocknameContaining(optionCategorySelectedValue, pagingSort);

			stock = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", stock);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/stocklevelbystockbydescription")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<StockLevel>> getAllStockLevelByStockByDescription(@RequestBody ReportBodyDetails r) {

		 

		try {
			
			String stock=r.getText1();
			String description=r.getText2();

			List<StockLevel> list = stockLevelRepo.getStockLevelByStockByDescription(stock,description);
			
			System.out.println("+++++++++++++++stock 903 ++++++++++++++++++++++" + list);

			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	@GetMapping("/stocklevelbystock2")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getAllStockLevelByStock2(@RequestBody ReportBodyDetails body,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) {

		String code = body.getText1();
		String stockName = body.getText2();

		// getLegacyStockLevel(stockName,code);

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

			List<StockLevel> stock = new ArrayList<StockLevel>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<StockLevel> pageTuts;
			if (code == null)
				pageTuts = stockLevelRepo.findAll(pagingSort);
			else
				pageTuts = stockLevelRepo.findBystocknameContaining(code, pagingSort);

			stock = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", stock);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/stockentrycode")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> generateStockEntryCode() {

		String stockEntryCode = RandomString.make(3);

		return new ResponseEntity<>(stockEntryCode, HttpStatus.OK);
	}

	// get stocklevel by id
	@GetMapping("/stocklevel/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<StockLevel> getStockLevelById(@PathVariable("id") long id) {

		Optional<StockLevel> data = stockLevelRepo.findById(id);

		if (data.isPresent()) {

			StockLevel s = data.get();

			return new ResponseEntity<>(s, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	// use this to update stock level if needed
	@PutMapping("/stocklevel/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public void updateStockLevel(@PathVariable("id") long id, @RequestBody StockLevel p) {

		String status = "Critical";
		if (p.getCurrent_stock_qty() > 700) {
			status = "Full";
		} else if (p.getCurrent_stock_qty() < 700 && p.getCurrent_stock_qty() > 500) {
			status = "Normal";
		} else if (p.getCurrent_stock_qty() < 500 && p.getCurrent_stock_qty() > 200) {
			status = "Low";
		} else if (p.getCurrent_stock_qty() < 200) {
			status = "Critical";
		}

		stockLevelRepo.updateStockLevel(p.getCurrent_stock_qty(), status, p.getPid(), p.getStockname());

		Optional<StockLevel> data = stockLevelRepo.findById(id);

	}

	@GetMapping("/initialemptystock")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public void createInitialStockLevel() {

		productList = new ArrayList<>();
		productList = productRepo.findAll();

		System.out.println("====method call====");

		String stock = "BUSINESS";

		for (Products s : productList) {

			String code = s.getCode();
			int pid = (int) s.getId();
			String name = s.getName();
			String color = s.getColor();
			String size = s.getColor();
			String stockname = stock;
			int current_stock_qty = 0;
			String status = "Low";

			// stockLevelRepo.save(new StockLevel(code, pid, name, color, size, stockname,
			// current_stock_qty, status));

			// System.out.println("======559====adding="+name+" stock:"+stockname);

		}

	}

	@GetMapping("/legacy")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public void getLegacyStockLevel(String stand, String code) {

		System.out.println("======833====legacy====");

		//getStockData.getStockLevel("Bussiness Class Stand", "2A686785-107");
	}

	@PostMapping("/getstocklevel")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Integer> getCurrentStockLevel(@RequestBody ReportBodyDetails r) {

		try {
			int pid = Integer.valueOf(r.getText1());
			String stand = r.getText2();

			Integer existingQty = stockLevelRepo.getCurrentStockLevel(pid, stand);

			return new ResponseEntity<>(existingQty, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/getstocklevelbyproductname")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Integer> getCurrentStockLevelProduct(@RequestBody ReportBodyDetails r) {

		try {
			String stand =r.getText1();
			String product = r.getText2();

			Integer qty = stockLevelRepo.getCurrentStockLevelProduct(product,stand);

			return new ResponseEntity<>(qty, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/confirmation")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> updateCurrentStockLevelOnConfirmation(@RequestBody ReportBodyDetails r) {

		try {
			int pid = Integer.valueOf(r.getText1());
			String stand = r.getText2();
			int qty_in = Integer.valueOf(r.getText3());
			String product = r.getText4();
			String size = r.getText5();
			String color = r.getText6();
			String code = r.getText7();
			long id = Integer.valueOf(r.getText8());

			// check if this product is not confirmed
			
			//System.out.println("======953===="+stockRepo.checkIfConfirmed(id).equals("Pending"));

			if (stockRepo.checkIfConfirmed(id).equals("Pending")) {

				Integer current_qty = stockLevelRepo.getCurrentStockLevel(pid, stand);

				int qty = current_qty + qty_in;

				// check if product exist in stock level and create default stock level 0

				Integer count = stockLevelRepo.checkIfProductExist(pid, stand);

				if (count == 0) {
					total_qty = 0 + qty_in;
				} else {
					total_qty = current_qty + qty_in;
				}

				String status = "";
				
				Products p = productRepo.getById(Long.valueOf(pid));

				if (count == 0 || count == null) {
					stockLevelRepo.save(new StockLevel(code, pid, product, color, size, stand, Integer.valueOf(qty_in),
							status, "Updated",p.getMax_price(),p.getDescription()));
				} else {

					if (total_qty > 700) {
						status = "Full";
					} else if (total_qty < 700 && total_qty > 500) {
						status = "Normal";
					} else if (total_qty < 500 && total_qty > 200) {
						status = "Low";
					} else if (total_qty < 200) {
						status = "Critical";
					}

					stockLevelRepo.updateStockLevel(total_qty, status, pid, stand);
					stockRepo.updatedStockConfirmed(id);
					
					confirmationMessage="Confirmed successfully";

				}
				
				return new ResponseEntity<>(confirmationMessage, HttpStatus.OK);

			} else {
				confirmationMessage="This entry was confimed before.";
				
				System.out.println();
				
				return new ResponseEntity<>(confirmationMessage, HttpStatus.OK);
				

			}

			 
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			 return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/confirmationout")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> updateCurrentStockOutLevelOnConfirmation(@RequestBody ReportBodyDetails r) {

		try {
			int pid = Integer.valueOf(r.getText1());
			String stand = r.getText2();
			int qty_out = Integer.valueOf(r.getText3());
			String product = r.getText4();
			String size = r.getText5();
			String color = r.getText6();
			String code = r.getText7();
			long id = Integer.valueOf(r.getText8());

			Integer current_qty = stockLevelRepo.getCurrentStockLevel(pid, stand);
			
			Products p = productRepo.getById(Long.valueOf(pid));

			int qty = current_qty - qty_out;

			// check if product exist in stock level and create default stock level 0
			
			if (stockRepo.checkIfConfirmed(id).equals("Pending")) {
				
				Integer count = stockLevelRepo.checkIfProductExist(pid, stand);

				if (count == 0) {
					total_qty = 0 - qty_out;
				} else {
					total_qty = current_qty - qty_out;
				}

				String status = "";

				if (count == 0 || count == null) {
					stockLevelRepo.save(new StockLevel(code, pid, product, color, size, stand, Integer.valueOf(total_qty),
							status, "Updated",p.getMax_price(),p.getDescription()));
				} else {

					if (total_qty > 700) {
						status = "Full";
					} else if (total_qty < 700 && total_qty > 500) {
						status = "Normal";
					} else if (total_qty < 500 && total_qty > 200) {
						status = "Low";
					} else if (total_qty < 200) {
						status = "Critical";
					}

					stockLevelRepo.updateStockLevel(total_qty, status, pid, stand);
					stockRepo.updatedStockConfirmed(id);

				}
				confirmationMessage="Confirmed successully.";
				return new ResponseEntity<>(confirmationMessage, HttpStatus.OK);
				
			}else {
				confirmationMessage="This was confirmed before.";
				return new ResponseEntity<>(confirmationMessage, HttpStatus.OK);
				
			}

			

			 
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			 return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	@PostMapping("/stocktransfer")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> stockTransfer(@RequestBody ReportBodyDetails body) {

		String current_stock = body.getText1();
		int current_qty = Integer.valueOf(body.getText2()) ;
		String  transfer_to = body.getText3();
		int qty_to_transfer = Integer.valueOf(body.getText4()) ;
		
		int pid = Integer.valueOf(body.getText5()) ;
		
		String currentDate = formatter2.format(new Date());
		double price_value =Double.valueOf(body.getText6()) ;
		String location = body.getText7();
		String username = body.getText8();
		String product = body.getText9();
		String code = body.getText10();

		 
		int qty_to_transfer_level = stockLevelRepo.getCurrentStockLevel(pid, transfer_to);
		
		try {
			
			String status = "";
			
			
			 
			 total_qty = current_qty - qty_to_transfer;
			 total_qty_tranfer = qty_to_transfer_level+qty_to_transfer;
			 
			// System.out.println("=========1208===== stock "+current_stock+" qty:"+current_qty+" transfer to:"+transfer_to+" "+qty_to_transfer+" pid:"+pid +" total qty:"+total_qty+" total_qty_tranfer:"+total_qty_tranfer);
			 
			 
			 
			 if (total_qty > 700) {
					status = "Full";
				} else if (total_qty < 700 && total_qty > 500) {
					status = "Normal";
				} else if (total_qty < 500 && total_qty > 200) {
					status = "Low";
				} else if (total_qty < 200) {
					status = "Critical";
				}

				stockLevelRepo.updateStockLevel(total_qty, status, pid, current_stock);
				
				//update destination level 
				stockLevelRepo.updateStockLevel(total_qty_tranfer, status, pid, transfer_to);
				
				//save stock out
				stockRepo.save(new Stock(current_stock, currentDate, pid,product, 0, username,code, "NA", qty_to_transfer, 0, 0,0, current_stock, current_stock, "TRANSFER", "TRANFER","", "NA", "OUT","", 0,"Confirmed",location,price_value));
				
				//save stock in
				stockRepo.save(new Stock(transfer_to, currentDate, pid,product, qty_to_transfer, username,code, "NA", 0, 0, 0,0, current_stock, current_stock, "TRANSFER", "TRANFER","", "NA", "IN","", 0,"Confirmed",location,price_value));


			return new ResponseEntity<>("OK", HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
