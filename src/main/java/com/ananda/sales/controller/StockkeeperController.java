package com.ananda.sales.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ananda.sales.model.Products;
import com.ananda.sales.model.ReportBodyDetails;
import com.ananda.sales.model.Stock;
import com.ananda.sales.model.StockLevel;
import com.ananda.sales.model.StockRequestSummary;
import com.ananda.sales.repository.StockkeeperRequestSummaryRepository;
import com.ananda.sales.repository.ProductsRepository;
import com.ananda.sales.repository.StockLevelRepository;
import com.ananda.sales.repository.StockRepository;
import com.ananda.sales.repository.StockkeeperRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class StockkeeperController {

	@Autowired
	private StockkeeperRepository stockkeeperRepo;

	@Autowired
	private StockkeeperRequestSummaryRepository stockRequestSummaryRepo;

	@Autowired
	private StockLevelRepository stockLevelRepo;

	@Autowired
	private ProductsRepository productRepo;
	
	@Autowired
	private StockRepository stockRepo;

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

	private long currentPage = 1;
	private long totalItems;
	private long totalPages;

	private Map<String, Object> response;

	private String responseMessage = "";

	private Integer total_qty;
	private Integer total_qty_main_stock;

	@PostMapping("/stockkeeper")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> save(@RequestBody Stock stock) {

		try {

			// check if this item was added before
			String time1 = formatter2.format(new Date()) + " 00:00:00";
			String time2 = formatter2.format(new Date()) + " 59:59:59";

			String stand = stock.getStand();
			String date1 = "";
			int pid = stock.getPid();
			String product = stock.getProduct();
			int qty_in = 0;
			String stockkeeper = stock.getStockkeeper();
			String code = stock.getCode();
			String size = stock.getSize();
			int qty_out = 0;
			int qty_transfer_in = 0;
			int qty_transfer_out = 0;
			int qty_damage = 0;
			String stand_destination = stock.getStand_destination();
			String stand_source = stock.getStand_source();
			String description = stock.getDescription();
			String comment = "REQUEST-FROM-BRANCH";
			String transfer_type = stock.getTransfer_type();
			String color = stock.getColor();
			String stock_type = stock.getStock_type();
			String stockEntryId = stock.getStockEntryId();
			int returned = 0;
			String confirmation = stock.getConfirmation();
			String location = stock.getLocation();
			int qty_requested = stock.getQty_requested();
			String stand_supply_request_date = formatter.format(new Date());
			String stand_supply_request_officer = stock.getStand_supply_request_officer();
//			String business_operation_manager_name = stock.getBusiness_operation_manager_name();
//			String business_operation_manager_approval = stock.getBusiness_operation_manager_approval();
//			String business_operation_manager_approval_date = stock.getBusiness_operation_manager_approval_date();
//			String branch_manager_name = stock.getBranch_manager_name();
//			String branch_manager_approval = stock.getBranch_manager_approval();
//			String branch_manager_approval_date = stock.getBranch_manager_approval_date();

			String business_operation_manager_name = "NA";
			String business_operation_manager_approval = "APPROVED";
			String business_operation_manager_approval_date = time1;
			String branch_manager_name = "NA";
			String branch_manager_approval = "APPROVED";
			String branch_manager_approval_date = time1;

			String stockkeeper_name = stock.getStockkeeper_name();
			String stockkeeper_approval = stock.getStockkeeper_approval();
			String stockkeeper_approval_date = stock.getStockkeeper_approval_date();
			int stockkeeper_approval_qty = stock.getStockkeeper_approval_qty();
			//String stand_receiption_confirmation = stock.getStand_receiption_confirmation();
			String stand_receiption_confirmation = "PENDING";
			String stand_receiption_confirmation_date = stock.getStand_receiption_confirmation_date();
			String request_Status = stock.getRequest_Status();
			String printing_version = "ORGINAL";

			int count = stockkeeperRepo.checkIfProductExistOnThisCurrentRequest(pid, stockEntryId, time1, time2, stand);
			double price_value = productRepo.getProductPrice(pid);
			Products products = productRepo.getById(Long.valueOf(stock.getPid()));
			
			//check if stocklevel exist
			
			Integer count2 = stockLevelRepo.checkIfProductExist(stock.getPid(), "MAIN STOCK");
			
			
			if (count2 == 0 || count2 == null) {
				stockLevelRepo.save(new StockLevel(stock.getCode(), stock.getPid(), stock.getProduct(),
						stock.getColor(), stock.getSize(), "MAIN STOCK", 0, "CRITICAL", "Updated",price_value,products.getDescription()));
			}
			
			

			if (count < 1) {

				System.out.println("============saving new stock=========" + count);

				Stock p = stockkeeperRepo.save(new Stock(stand, date1, pid, product, qty_in, stockkeeper, code, size,
						qty_out, qty_transfer_in, qty_transfer_out, qty_damage, stand_destination, stand_source,
						description, comment, transfer_type, color, stock_type, stockEntryId, returned, confirmation,
						location, qty_requested, stand_supply_request_date, stand_supply_request_officer,
						business_operation_manager_name, business_operation_manager_approval,
						business_operation_manager_approval_date, branch_manager_name, branch_manager_approval,
						branch_manager_approval_date, stockkeeper_name, stockkeeper_approval, stockkeeper_approval_date,
						stockkeeper_approval_qty, stand_receiption_confirmation, stand_receiption_confirmation_date,
						request_Status, price_value));

//				System.out.println("============saving new stock========="+p);

			} else {

				int existingQty = stockkeeperRepo.checkIfProductExistOnThisCurrentRequestQty(pid, stockEntryId, time1,
						time2, stand);
				int newQty = existingQty + qty_requested;
				stockkeeperRepo.checkIfProductExistOnThisCurrentRequestQtyAndAddNewRequestedQty(newQty, pid,
						stockEntryId, time1, time2, stand);

			}

			// stock request summary
			int countstockSummary = stockRequestSummaryRepo.checkIfCurrentStockRequestExist(stockEntryId, time1, time2,
					stand);

			if (countstockSummary == 0) {

				stockRequestSummaryRepo.save(new StockRequestSummary(stand, date1, stockkeeper, comment, transfer_type,
						stock_type, stockEntryId, location, stand_supply_request_date, stand_supply_request_officer,
						business_operation_manager_name, business_operation_manager_approval,
						business_operation_manager_approval_date, branch_manager_name, branch_manager_approval,
						branch_manager_approval_date, stockkeeper_name, stockkeeper_approval, stockkeeper_approval_date,
						stockkeeper_approval_qty, stand_receiption_confirmation, stand_receiption_confirmation_date,
						request_Status,printing_version));

			} else {
				// System.out.println("=====stock keeper 123====="+countstockSummary+" exist");
			}

			return new ResponseEntity<>("", HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/currentrequesttemp/{order}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getRequestTemp(@PathVariable String order) {

		try {

			// System.out.println("===order===202==="+count);

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", stockkeeperRepo.findBystockEntryIdDetails(order));
			response.put("currentPage", currentPage);
			response.put("totalItems", totalItems);
			response.put("totalPages", totalPages);

			// orderDetailsList = new ArrayList<>();
			// orderDetailsList = orderDetailsRepository.findByOrderidDetails(order);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/currentrequestbyid/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getRequestById(@PathVariable long id) {

		response = new HashMap<>();

		try {

			Stock s = stockkeeperRepo.findByid(id);
			// System.out.println("===order===202==="+s);

			response.put("currentOrderDataSummaryByProductId", s);
			response.put("currentPage", currentPage);
			response.put("totalItems", totalItems);
			response.put("totalPages", totalPages);

			// orderDetailsList = new ArrayList<>();
			// orderDetailsList = orderDetailsRepository.findByOrderidDetails(order);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/currentrequest/{order}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getCurrentRequest(@PathVariable String order) {

		try {

			// System.out.println("===reqeust===177==="+order+" summary
			// "+stockRepo.findBystockEntryIdDetailsData(order) );

			Map<String, Object> response = new HashMap<>();
			response.put("currentOrderData", stockkeeperRepo.findBystockEntryIdDetailsData(order));
			response.put("currentOrderDataSummary", stockRequestSummaryRepo.findBystockEntryIdSummary(order));
			response.put("currentPage", currentPage);
			response.put("totalItems", totalItems);
			response.put("totalPages", totalPages);

			// orderDetailsList = new ArrayList<>();
			// orderDetailsList = orderDetailsRepository.findByOrderidDetails(order);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/newrequest/{request}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Integer> saveNewRequest(@PathVariable String request) {

		try {

			String requestTime = formatter.format(new Date());
			String status = "Pending";

			int y = stockkeeperRepo.updateStockRequest(requestTime, status, request);
			int z = stockRequestSummaryRepo.updateStockRequest(requestTime, status, request);

			// System.out.println("request 159 "+y);

			return new ResponseEntity<>(y, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/pendingrequest")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getPengingRequest(@RequestBody ReportBodyDetails r) {

		try {

			String date1 = r.getText1();
			String date2 = r.getText2();
			String location = r.getText3();

			// System.out.println("===request===275==="+stockRequestSummaryRepo.findByrequest_StatusPending(date1,
			// date2, location));

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", stockRequestSummaryRepo.findByrequest_StatusPending(date1, date2, location));
			response.put("currentPage", currentPage);
			response.put("totalItems", totalItems);
			response.put("totalPages", totalPages);

			// orderDetailsList = new ArrayList<>();
			// orderDetailsList = orderDetailsRepository.findByOrderidDetails(order);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PostMapping("/requestreport")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getRequestReport(@RequestBody ReportBodyDetails r) {

		try {

			String date1 = r.getText1();
			String date2 = r.getText2();
			String location = r.getText3();
			String status = r.getText4();

			// System.out.println("===request===306==="+date1+" "+date2+" "+location+"
			// "+status+" "+stockRequestSummaryRepo.findByrequest_Status(status,date1,
			// date2, location));

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", stockRequestSummaryRepo.findByrequest_Status(status, date1, date2, location));
			response.put("currentPage", currentPage);
			response.put("totalItems", totalItems);
			response.put("totalPages", totalPages);

			// orderDetailsList = new ArrayList<>();
			// orderDetailsList = orderDetailsRepository.findByOrderidDetails(order);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	// approving request

	@PostMapping("/approverequest")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> approvingRequest(@RequestBody ReportBodyDetails r) {
		
		String currentDate= formatter2.format(new Date());

		try {

			String time = formatter.format(new Date());
			String approverLevel = r.getText1();
			String approverName = r.getText2();
			String decision = r.getText3();
			String stockEntry = r.getText4();

			// System.out.println("===order===183===level:"+approverLevel+"
			// name:"+approverName+" id:"+stockEntry+" decision"+decision);

			if (approverLevel.equals("BUSINESS-OPERATION")) {

				StockRequestSummary st = stockRequestSummaryRepo.findBystockEntryIdSummary(stockEntry);

				// if (st.getBusiness_operation_manager_approval().equals("")) {

				stockkeeperRepo.updateStockRequestBusinessOperationManager(time, decision, approverName, stockEntry);
				stockRequestSummaryRepo.updateStockRequestBusinessOperationManager(time, decision, approverName,
						stockEntry);

//				} else {
//					responseMessage="You have done this operation before.";
//					//System.out.println("You have done this operation before.");
//				}

			} else if (approverLevel.equals("BRANCH-MANAGER")) {

				responseMessage = "";

				StockRequestSummary st = stockRequestSummaryRepo.findBystockEntryIdSummary(stockEntry);

				if (st.getBranch_manager_approval().equals("")) {

					stockkeeperRepo.updateStockRequestBranchManager(time, decision, approverName, stockEntry);
					stockRequestSummaryRepo.updateStockRequestBranchManager(time, decision, approverName, stockEntry);

				} else {

					System.out.println("You have done this operation before.");
					responseMessage = "You have done this operation before.";

				}

			} else if (approverLevel.equals("STOCKKEEPER")) {

				responseMessage = "";

				StockRequestSummary st = stockRequestSummaryRepo.findBystockEntryIdSummary(stockEntry);

				int stockKeeper_sum_qty = stockkeeperRepo.getStockkeeperapproval_sum_qty(stockEntry);

				// System.out.println("=======351=======stockkeeper====="+stockKeeper_sum_qty);

				if (stockKeeper_sum_qty > 0) {

					stockkeeperRepo.updateStockRequestStockKeeper(time, decision, approverName, stockEntry);
					stockRequestSummaryRepo.updateStockRequestStockKeeper(time, decision, approverName, stockEntry);

					// System.out.println("qty is greater than 0.");

				} else {

					// System.out.println("qty is less than 0.");
					responseMessage = "Please provide approved qty.";

				}

			} else if (approverLevel.equals("STANDKEEPER")) {

				responseMessage = "";

				StockRequestSummary st = stockRequestSummaryRepo.findBystockEntryIdSummary(stockEntry);

				

				if (st.getStand_receiption_confirmation().equals("PENDING")) {

					stockkeeperRepo.updateStockRequestStandKeeper(time, "RECIEVED", approverName, stockEntry);
					stockRequestSummaryRepo.updateStockRequestStandKeeper(time, "RECIEVED", approverName, stockEntry);
					
					

					for (Stock s : stockkeeperRepo.findBystockEntryIdDetailsData(stockEntry)) {
						
						

						if (s.getStockkeeper_approval_qty() > 0) {

							String date = formatter2.format(new Date());
							
							System.out.println("standkeeper "+" confirmation:"+" Updating stock...."+st.getStand_receiption_confirmation()+" updating:"+s);

							stockkeeperRepo.updateStockRequestStockKeeperReceived(date, "RECIEVED", approverName,
									Integer.valueOf(s.getStockkeeper_approval_qty()), s.getId());
//
//							// update stock level

							if (st.getStand_receiption_confirmation().equals("PENDING")) {
								
								

								System.out.println("Updating current stock");

								// check if product exist in stock level and create default stock level 0

								Integer count = stockLevelRepo.checkIfProductExist(s.getPid(), s.getStand());

								if (count == 0) {
									total_qty = 0 + s.getStockkeeper_approval_qty();
								} else {
									Integer current_qty = stockLevelRepo.getCurrentStockLevel(s.getPid(), s.getStand());
									
									Integer current_qty_main_stock = stockLevelRepo.getCurrentStockLevel(s.getPid(), "MAIN STOCK");

									int qty = current_qty + s.getStockkeeper_approval_qty();
									total_qty = current_qty + s.getStockkeeper_approval_qty();
									total_qty_main_stock = current_qty_main_stock - s.getStockkeeper_approval_qty();
								}

								String status = "";

								Products product = productRepo.getById(Long.valueOf(s.getPid()));

								if (count == 0 || count == null) {
									stockLevelRepo.save(new StockLevel(s.getCode(), s.getPid(), s.getProduct(),
											s.getColor(), s.getSize(), s.getStand(),
											Integer.valueOf(s.getStockkeeper_approval_qty()), status, "Updated",
											product.getMax_price(), product.getDescription()));
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

									stockLevelRepo.updateStockLevel(total_qty, status, s.getPid(), s.getStand());
									
									//update mainstock level 
									stockLevelRepo.updateStockLevel(total_qty_main_stock, status, s.getPid(), "MAIN STOCK");
									
									//add stock out
									
								 
//	                       String stand, String date1, int pid, String product, Integer qty_in, String stockkeeper,String code, String size, Integer qty_out, Integer qty_transfer_in, Integer qty_transfer_out,Integer qty_damage, String stand_destination, String stand_source, String description, String comment,String transfer_type, String color, String stock_type,String stockEntryId,int returned,String confirmation,String location,double price_value						
	stockRepo.save(new Stock("MAIN STOCK", currentDate, s.getPid(),s.getProduct(), 0, s.getStockkeeper_name(),s.getCode(), s.getSize(), s.getStockkeeper_approval_qty(), s.getStockkeeper_approval_qty(), s.getStockkeeper_approval_qty(),0, s.getStand(), "MAIN STOCK", "REQUEST", "REQUEST","", s.getColor(), "OUT",s.getStockEntryId(), 0,"Confirmed",s.getLocation(),s.getPrice_value()));

								}

							} else {

								System.out.println("Can't save! this was received before");

							}

						} else {

							// System.out.println("QTY is less than 0");

							// responseMessage="You have forgeten to provide qty!";

						}

					}

					// empty qty
				} else {

					System.out.println("You have done this operation before.");
					responseMessage = "You have done this operation before.";

				}

			}

			// orderDetailsList = new ArrayList<>();
			// orderDetailsList = orderDetailsRepository.findByOrderidDetails(order);

			return new ResponseEntity<>(responseMessage, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PostMapping("/approverequestAvairableqty")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public void stockKeeperApproveAvairableQty(@RequestBody ReportBodyDetails r) {

		try {

			long id = Long.valueOf(r.getText1());
			int qty = Integer.valueOf(r.getText2());
			stockkeeperRepo.updateStockRequestStandKeeperAvairableQty(qty, id);

			// System.out.println("===order===183==="+date1+" "+date2+" "+location+"
			// "+stockRepo.findByrequest_StatusPending(date1,date2,location));

		} catch (Exception e) {
			System.out.println(e);

		}
	}

	@DeleteMapping("/currentrequest/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {

		try {
			stockkeeperRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/printingversion/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> updatePrintingVersion(@PathVariable("id") String id) {

		try {
			
			
			stockRequestSummaryRepo.updatePrintingVersion(id);
			
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
