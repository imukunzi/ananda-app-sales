package com.ananda.sales.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

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

import com.ananda.sales.customdb.CustomQueries;
import com.ananda.sales.model.Customers;
import com.ananda.sales.model.OrderDetails;
import com.ananda.sales.model.OrderDetailsTotal;
import com.ananda.sales.model.Payments;
import com.ananda.sales.model.ReportBodyDetails;
import com.ananda.sales.model.SalesDetails;
import com.ananda.sales.model.SalesSummary;
import com.ananda.sales.model.SelectOptions;
import com.ananda.sales.model.SellersReportPerformance;
import com.ananda.sales.model.Stock;
import com.ananda.sales.repository.CustomersRepository;
import com.ananda.sales.repository.GetSalesData;
import com.ananda.sales.repository.OrderDetailsRepository;
import com.ananda.sales.repository.PaymentsRepository;
import com.ananda.sales.repository.ProductsRepository;
import com.ananda.sales.repository.SalesDetailsRepository;
import com.ananda.sales.repository.SalesSummaryRepository;
import com.ananda.sales.repository.SelectOptionRepository;
import com.ananda.sales.repository.StockRepository;
import com.ananda.sales.repository.UserRepository;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import net.bytebuddy.utility.RandomString;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class OrderDetailsController {

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private SalesDetailsRepository salesDetailsRepository;

	@Autowired
	private SalesSummaryRepository saleSummaryRepository;

	@Autowired
	private PaymentsRepository paymentRepository;

	@Autowired
	private CustomersRepository customerRepo;

	@Autowired
	private StockRepository stockRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ProductsRepository productRepo;

	@Autowired
	private SelectOptionRepository selectectionRepo;

	private CustomQueries customQueries = new CustomQueries();

	// private List<OrderDetails> orderDetailsList;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private DecimalFormat decimalFormatter = new DecimalFormat("#,###.00");

	private SalesDetails sale;
	private Double orderAmount;

	private long currentPage = 1;
	private long totalItems;
	private long totalPages;
	private List<SalesSummary> sales;
	private List<Double> sumList;
	private List<Integer> sumListItem;

	private List<SellersReportPerformance> sellersReportPerformanceList;
	private List<SalesDetails> sellersReportPerformanceSaleDetailsList;
	private List<Integer> sellersReportPerformanceSaleDetailsListSumItems;
	private List<Double> sellersReportPerformanceSaleDetailsListSumAmount;

	private double cashTotal = 0;
	private double momoTotal = 0;
	private double visaTotal = 0;
	private double chequeTotal = 0;
	private double transferTotal = 0;

	private String orderMessage = "";
	private int count;

	private double creditSum = 0;
	private int creditCount = 0;

	@PostMapping("/orderdetails")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> saveProducts(@RequestBody OrderDetails order) {

		// String token = RandomString.make(7);

		Date date = new Date();

		String orderid = order.getOrderid();
		String orderTime = formatter.format(date);
		String product = order.getProduct();
		String code = order.getCode();
		int pid = order.getPid();
		int qty = order.getQty();
		String color = order.getColor();
		String size = order.getSize();
		double price = order.getPrice();
		String saller = order.getSaller();
		String customer = order.getCustomer();
		String phone = order.getPhone();
		String salesStatus = order.getSalesStatus();
		String stand = order.getStand();
		String username = order.getUsername();
		Double total = qty * price;
		String location = order.getLocation();
		String cashier = order.getCashier();
		double min_price = order.getMin_price();
		// String authorization_status=order.getAuthorization_status();
		String authorization_status;
		double product_price = order.getProduct_price();
		
         String dealer = order.getDealer();
		
		double saller_percentage = 0;
		String deal_type = order.getDeal_type();
		
		if(selectectionRepo.findByValue(saller) !=null) {
			SelectOptions s = selectectionRepo.findByValue(saller);
			 saller_percentage = s.getSaller_percentage();
		}
		
		
		
		String mbe_tracking = order.getMbe_tracking();
		
		double dealer_percentage = 0;
		
		if(deal_type.equals("ON-SITE")) {
			dealer_percentage =5;
		}else if(deal_type.equals("ONLINE")) {
			dealer_percentage =3;
		}else {
			dealer_percentage =0;
		}

		if (price > min_price || price == min_price) {
			authorization_status = "NORMAL-PRICE";
		} else {
			authorization_status = "LOW-PRICE";
		}

		String authorization_by = order.getAuthorization_by();
		String desctription = order.getDescription();

		try {

			String date1 = formatter2.format(new Date()) + " 00:00:00";
			String date2 = formatter2.format(new Date()) + " 59:59:59";

			int count = salesDetailsRepository.checkIfProductExistOnThisCurrentOrder(pid, orderid, date1, date2, stand,
					price);

			if (saleSummaryRepository.checkIfSalesSummaryExist(orderid) < 1) {
				saleSummaryRepository.save(new SalesSummary(orderid, orderTime, customer, phone, 0.0, 0.0, 0.0,
						"TEMPORARY", saller, username, "", "", location, cashier, ""));
			}

			if (count < 1) {
				SalesDetails o = salesDetailsRepository.save(new SalesDetails(orderid, orderTime, product, code, pid, qty,
						color, size, price, saller, customer, phone, salesStatus, stand, username, total,"",location,cashier,min_price,authorization_status,authorization_by,dealer,deal_type,saller_percentage,
						mbe_tracking, dealer_percentage));
				 orderMessage="Added....";
				  
				 //check if customer exist
				 checkIfCustomerExist(phone, customer, username);

				return new ResponseEntity<>(orderMessage, HttpStatus.CREATED);

			} else {
				int qty_existing = salesDetailsRepository.getQtyIfProductExistOnThisCurrentOrder(pid, orderid, date1,
						date2, stand);
				int qty_total = qty + qty_existing;
				double amount_new = qty_total * price;

				// System.out.println("Exist updating....existing qty:"+qty_existing+"
				// qty:"+qty+" total:"+qty_total+ " amount"+amount_new);
				orderMessage = "Item updated...";
				salesDetailsRepository.updateifProductExistOnThisCurrentOrder(qty_total, amount_new, pid, orderid,
						date1, date2, stand);

				// check if ordersummary exist

				// check if customer exist
				checkIfCustomerExist(phone, customer, username);

				return new ResponseEntity<>(orderMessage, HttpStatus.CREATED);

			}

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/orderno/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> createOrderNo(@PathVariable String username) {

		long id = userRepo.getMemberAccountId(username);

		String x = formatter.format(new Date());
		String y = formatter2.format(new Date());
		String z = formatter3.format(new Date());

		String salt = x.substring(x.length() - 2);
		String salt2 = y.substring(y.length() - 2);
		String salt3 = z.substring(z.length() - 2);

		String token = RandomString.make(7);
		String order = id + salt + salt2 + salt3 + token;

		// System.out.println("========oder=====157===="+order);

		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@GetMapping("/orderdetails")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<OrderDetails>> getOrderDetailsByNo() {

		try {
			List<OrderDetails> o = orderDetailsRepository.findAll();

			return new ResponseEntity<>(o, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/currentorder/{order}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getCurrentOrder(@PathVariable String order) {

		// System.out.println("=======order 179======="+order);

		try {

			// orderDetailsList = new ArrayList<>();

			// orderDetailsList = orderDetailsRepository.findByOrderidDetails(order);

			cashTotal = paymentRepository.findPaymentCashTotalByOrderNo(order);
			momoTotal = paymentRepository.findPaymentMomoTotalByOrderNo(order);
			visaTotal = paymentRepository.findPaymentVisaTotalByOrderNo(order);
			chequeTotal = paymentRepository.findPaymentChequeTotalByOrderNo(order);
			transferTotal = paymentRepository.findPaymentTransferTotalByOrderNo(order);

			// System.out.println("cash "+cashTotal+" "+momoTotal+" visa "+chequeTotal+ "
			// trans "+transferTotal+" id "+order+" "+orderDetailsList);

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", salesDetailsRepository.findByOrderidDetails(order));
			response.put("currentPage", currentPage);
			response.put("totalItems", totalItems);
			response.put("totalPages", totalPages);

			response.put("cash", cashTotal);
			response.put("momo", momoTotal);
			response.put("visa", visaTotal);
			response.put("cheque", chequeTotal);
			response.put("transfer", transferTotal);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PostMapping("/currentordersaller")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getCurrentOrderSaller(@RequestBody ReportBodyDetails r) {

		String saller = r.getText1();
		String date1 = r.getText2();
		String date2 = r.getText3();

		System.out.println("=======order 261=======" + saller + " " + date1 + " " + date2);

		try {

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", salesDetailsRepository.findBysallerDetails(saller, date1, date2));

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PostMapping("/currentordersalleritemsummary")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getCurrentOrderSallerItemSummary(@RequestBody ReportBodyDetails r) {

		String saller = r.getText1();
		String date1 = r.getText2();
		String date2 = r.getText3();
		String description = r.getText4();

		 
		CustomQueries c = new CustomQueries();
		
		//sellersReportPerformanceSaleDetailsList = new ArrayList<>();
		
		sellersReportPerformanceSaleDetailsListSumItems = new ArrayList<>();
		sellersReportPerformanceSaleDetailsListSumAmount = new ArrayList<>();
		
		

		try {
			
			sellersReportPerformanceSaleDetailsList =	c.getSallersPerformanceSoldItems(date1, date2, saller,description);
			
			for(SalesDetails s:sellersReportPerformanceSaleDetailsList) {
				
				int qty =s.getQty();
				double amount =s.getTotal();
				sellersReportPerformanceSaleDetailsListSumItems.add(qty);
				sellersReportPerformanceSaleDetailsListSumAmount.add(amount);
				
				
			}
			
			int totalItem=0;
			for(int i=0;i<sellersReportPerformanceSaleDetailsListSumItems.size();i++) {
				totalItem+=sellersReportPerformanceSaleDetailsListSumItems.get(i);
				
			}
			
			int totalAmount=0;
			for(int i=0;i<sellersReportPerformanceSaleDetailsListSumAmount.size();i++) {
				totalAmount+=sellersReportPerformanceSaleDetailsListSumAmount.get(i);
				
			}
			
			//System.out.println("=======sallers details summary 327=======" + saller + " " + date1 + " " + date2+" description"+description+" total item:"+totalItem + " "+c.getSallersPerformanceSoldItems(date1, date2, saller,description));

			 

			Map<String, Object> response = new HashMap<>();
			//response.put("tutorials", salesDetailsRepository.findBysallerDetails(saller, date1, date2));
			response.put("tutorials", sellersReportPerformanceSaleDetailsList);
			response.put("soldProductsDataItem", totalItem);
			response.put("soldProductsDataItemAmount", totalAmount);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PostMapping("/currentordersallerreport")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getCurrentOrderSallerReport(@RequestBody ReportBodyDetails r) {

		String saller = r.getText1();
		String date1 = r.getText2();
		String date2 = r.getText3();
		String description = r.getText4();

		try {

			Map<String, Object> response = new HashMap<>();
			sellersReportPerformanceList = new ArrayList<>();
			sellersReportPerformanceList.clear();

			sumList = new ArrayList<>();
			sumListItem = new ArrayList<>();
			sumList.clear();
			sumListItem.clear();
			
			int j=0;

			for (SellersReportPerformance s : customQueries.getSallersPerformanceCommission(date1, date2, saller,description)) {

				String item = s.getItem();
				String code = s.getCode();
				int pid = s.getPid();
//				double price = productRepo.getProductPrice(s.getPid());
				double price = s.getPrice();
				int qty = s.getQty();
				double supply_price = productRepo.getSupplyPrice(s.getPid());
				double percentage = selectectionRepo.getCommissionPercentage(s.getPid());

				double commission = (price * qty - supply_price * qty) * percentage / 100;
				String description1=s.getDescription();

				sumList.add(commission);
				sumListItem.add(qty);

				sellersReportPerformanceList.add(new SellersReportPerformance(item, code, pid, price, qty, supply_price,
						percentage, decimalFormatter.format(commission),description1));

//				 System.out.println("=======sallers report 327======="+saller+" "+ date1+" "+" item:"+item+" "+code+" "+
//				 date2+" "+" supply price "+supply_price+" "+sellersReportPerformanceList);
				j++;

			}
			
			 

			double sum = 0;
			for (int i = 0; i < sumList.size(); i++) {
				sum += sumList.get(i);

			}
			
			int sumItem=0;
			
			for(int i=0; i < sumListItem.size(); i++) {
				sumItem+=sumListItem.get(i);
				
				
			}
			
			//System.out.println("======controller==426======"+sumItem+" j:"+j);
			//System.out.println(sellersReportPerformanceList);

			response.put("tutorials", sellersReportPerformanceList);
			response.put("tutorialsSum", decimalFormatter.format(sum));
			response.put("tutorialsSumItem", sumItem);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/currentordertemp/{order}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getCurrentOrderTemp(@PathVariable String order) {

		try {

			// update salesSammary totals

			// System.out.println("cash "+cashTotal+" "+momoTotal+" visa "+chequeTotal+ "
			// trans "+transferTotal+" id "+order+" "+orderDetailsList);

			int count = saleSummaryRepository.checkIfSalesSummaryExist(order);

			// System.out.println("===order===202==="+count);

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", salesDetailsRepository.findByOrderidDetails(order));
			response.put("currentPage", currentPage);
			response.put("totalItems", totalItems);
			response.put("totalPages", totalPages);
			response.put("orderSummaryCount", count);

			// orderDetailsList = new ArrayList<>();
			// orderDetailsList = orderDetailsRepository.findByOrderidDetails(order);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/currentordersummary/{order}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<OrderDetailsTotal> getCurrentOrderSummary(@PathVariable String order) {

		try {

			// orderDetailsList = new ArrayList<>();

			SalesSummary t = saleSummaryRepository.findByorderidContaining(order);
			String customer = t.getCustomer();
			String phone = t.getPhone();
			String orderNo = t.getOrderid();
			String date = t.getOrderTime();
			String total = decimalFormatter.format(t.getAmount());
			String payed = decimalFormatter.format(t.getAmount_payed());
			double balanceAmount = t.getAmount() - t.getAmount_payed();
			String balance = decimalFormatter.format(balanceAmount);
			String saller = t.getSaller();
			String cashier = t.getCashier();
			String other = t.getOther();

			OrderDetailsTotal x = new OrderDetailsTotal(customer, phone, orderNo, date, total, payed, balance, other,
					saller, cashier);

			return new ResponseEntity<>(x, HttpStatus.OK);  

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/currentordertotal/{order}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Double> getCurrentOrderTotal(@PathVariable String order) {

		try {
			orderAmount = salesDetailsRepository.findByOrderid(order);

			return new ResponseEntity<>(orderAmount, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	// analyse new sale before saving
	@GetMapping("/newsaleAnalysis/{orderno}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> analyseOrder(@PathVariable String orderno) {

		double totalAnalysis = salesDetailsRepository.findByOrderid(orderno);

		int x = salesDetailsRepository.analyselowPrices(orderno);

		String authoriztion_required;

		if (x > 0) {
			authoriztion_required = "REQUIRED";

		} else {
			authoriztion_required = "NOT-REQUIRED";
		}

		Map<String, Object> response = new HashMap<>();

		response.put("orderNoAnalyis", salesDetailsRepository.analyseOrder(orderno));
		response.put("noOfItemsAnalysis", salesDetailsRepository.analyseOrderItems(orderno));
		response.put("totalAnalysis", decimalFormatter.format(totalAnalysis));
		response.put("authorization_required", authoriztion_required);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// analyse new sale before saving
	@GetMapping("/checkifunauthorizedrowprice/{orderno}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Integer> checkIfUnauthorizedRowprice(@PathVariable String orderno) {

		int x = salesDetailsRepository.analyselowPrices(orderno);

		return new ResponseEntity<>(x, HttpStatus.OK);
	}

	@GetMapping("/newsale/{orderno}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Integer> saveNewSale(@PathVariable String orderno) {

		try {

			String orderTime = formatter.format(new Date());

			// check if this order is not saved

			int count = salesDetailsRepository.checkIfOrderExist(orderno);
			double salesTotal = salesDetailsRepository.findByOrderid(orderno);

			// System.out.println("====275===="+orderno+" count:"+count);

			if (count > 0) {

				String cashier = "";
				String status = "Pending";

				int y = salesDetailsRepository.updateSalesDetailsWithTime(status, orderTime, orderno);
				saleSummaryRepository.updateSalesSummaryAmount2(salesTotal, orderTime, orderno);
				;

				// System.out.println("order 367 "+y);

				return new ResponseEntity<>(y, HttpStatus.OK);

			} else {

				/*
				 * //double orderSummaryAmout=0;
				 * 
				 * for (OrderDetails order : orderDetailsList) {
				 * 
				 * String orderid = order.getOrderid(); String orderTime =
				 * formatter.format(date); String product = order.getProduct(); String code =
				 * order.getCode(); int pid = order.getPid(); int qty = order.getQty(); String
				 * color = order.getColor(); String size = order.getSize(); double price =
				 * order.getPrice(); String saller = order.getSaller(); String customer =
				 * order.getCustomer(); String phone = order.getPhone(); String salesStatus =
				 * order.getSalesStatus(); String stand = order.getStand(); String username =
				 * order.getUsername(); Double total = qty * price;
				 * 
				 * String paymentTIme = formatter.format(date); String location =
				 * order.getLocation(); String cashier = order.getCashier();
				 * 
				 * sale = salesDetailsRepository.save(new SalesDetails(orderid, orderTime,
				 * product, code, pid, qty, color, size, price, saller, customer, phone,
				 * salesStatus, stand, username, total, paymentTIme,location,cashier));
				 * 
				 * //delivery //deliveryRepository.save(new Delivery(orderTime, customer,
				 * orderid, stand, product,pid, qty, // qty, 0, 0, qty, "Pending", // "Pending",
				 * "Locked", "", username, ""));
				 * 
				 * 
				 * 
				 * }
				 */

				// saveSalesSummary();
				return new ResponseEntity<>(0, HttpStatus.OK);

			}

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	/*
	 * public void saveSalesSummary() {
	 * 
	 * 
	 * Date date = new Date(); String orderid = sale.getOrderid(); String orderTime
	 * = sale.getOrderTime();
	 * 
	 * String saller = sale.getSaller(); String customer = sale.getCustomer();
	 * String phone = sale.getPhone(); String status = sale.getSalesStatus();
	 * 
	 * String username = sale.getUsername(); //Double amount = orderSummaryAmout;
	 * Double amount_payed = 0.0;
	 * 
	 * 
	 * 
	 * String comment ="No comment"; String location = sale.getLocation(); String
	 * cashier = sale.getCashier();
	 * 
	 * String paymentTIme = formatter.format(date);
	 * 
	 * orderAmount = orderDetailsRepository.findByOrderid(orderid);
	 * 
	 * //System.out.println("=====order==369======"+orderAmount);
	 * 
	 * //checkIf sales summary exist
	 * 
	 * int count_salesSummary =
	 * saleSummaryRepository.checkIfSalesSummaryExist(orderid);
	 * 
	 * //System.out.println("======this summary exist====="+orderid);
	 * 
	 * if(count_salesSummary > 0) {
	 * 
	 * // System.out.println("======this summary exist====="+orderid);
	 * 
	 * }else {
	 * 
	 * saleSummaryRepository.save(new SalesSummary(orderid, orderTime, customer,
	 * phone, orderAmount, amount_payed,orderAmount, status,saller, username,
	 * paymentTIme,comment,location,cashier));
	 * 
	 * 
	 * }
	 * 
	 * int countSavedSalesSummary
	 * =saleSummaryRepository.checkSavedSalesSummaryDuplication(orderTime,orderid);
	 * long[] salesSummaryIds
	 * =saleSummaryRepository.selectSalesSummaryDuplicationId(orderTime,orderid);
	 * 
	 * 
	 * if (countSavedSalesSummary > 1) { //
	 * System.out.println("==========181==== sales duplication detected===="
	 * +salesSummaryIds.length+" "+salesSummaryIds[0]+" "+salesSummaryIds[1]);
	 * saleSummaryRepository.deleteById(salesSummaryIds[0]); }else { //
	 * System.out.println("==========order==== no sales duplication detected===="
	 * +salesSummaryIds.length); }
	 * 
	 * //check if sales summary total is correct
	 * 
	 * String date1 = formatter2.format(new Date())+" 00:00:00"; String date2 =
	 * formatter2.format(new Date())+" 59:59:59";
	 * 
	 * double salesTotal =
	 * salesDetailsRepository.checkSalesDetailsOrderAmount(date1, date2, orderid);
	 * 
	 * // correct any possible unmatching sales summary amount //if(salesTotal !=
	 * orderAmount) {
	 * 
	 * //System.out.println("========406=====sales total="+salesTotal+
	 * "orderamount: "+orderAmount);
	 * saleSummaryRepository.updateSalesSummaryAmount(salesTotal, orderid,
	 * orderTime);
	 * 
	 * // }
	 * 
	 * //check if customer exist int count =
	 * customerRepo.checkIfCustomerExist(phone);
	 * 
	 * if(count<1) {
	 * 
	 * customerRepo.save(new
	 * Customers(customer,"",phone,"","saved From sales","saved From sales",username
	 * ));
	 * 
	 * }
	 * 
	 * 
	 * 
	 * }
	 */

	// check if new order was saved
	@GetMapping("/newsalecheckifsaved/{orderno}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> CheckIfOrderWasSaved(@PathVariable String orderno) {

		// System.out.println("=====489===check if saved=="+orderno);

		double totalAnalysis = salesDetailsRepository.findByOrderid(orderno);

		Map<String, Object> response = new HashMap<>();

		response.put("orderNoAnalyis", salesDetailsRepository.analyseOrder(orderno));
		response.put("noOfItemsAnalysis", salesDetailsRepository.analyseOrderItems(orderno));
		response.put("totalAnalysis", decimalFormatter.format(totalAnalysis));
		response.put("saller", salesDetailsRepository.analyseOrderSaller(orderno));
		response.put("customer", salesDetailsRepository.analyseOrderCustomer(orderno));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/currentorder/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

		try {
			salesDetailsRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/removeorder/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> deleteOrder(@PathVariable("id") String id) {

		try {

			// System.out.println("======delete order 466======="+id);
			int i = saleSummaryRepository.deleteOrder(id);
			int k = salesDetailsRepository.deleteOrder(id);

			// System.out.println("============="+i+" "+k+" "+id);
			return new ResponseEntity<>("Deleted", HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/ordersummary")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<SalesSummary>> getSalesSummary() {

		try {
			// Date date = new Date();
			List<SalesSummary> sales = saleSummaryRepository.findAll();

			return new ResponseEntity<>(sales, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/searchorder/{orderid}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<SalesSummary>> searchOrderSummary(@PathVariable("orderid") String orderid) {

		try {
			// Date date = new Date();
			List<SalesSummary> sales = saleSummaryRepository.searchOrder(orderid);

			return new ResponseEntity<>(sales, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/searchsales/{orderid}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> searchSalesSummary(@PathVariable("orderid") String orderid) {

		try {
			// Date date = new Date();

			List<SalesSummary> sales = saleSummaryRepository.searchSales(orderid);

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", sales);
			response.put("currentPage", currentPage);
			response.put("totalItems", totalItems);
			response.put("totalPages", totalPages);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/searchsaleswithphone/{orderid}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> searchSalesSummaryWithPhone(@PathVariable("orderid") String orderid) {

		try {
			// Date date = new Date();
			List<SalesSummary> sales = saleSummaryRepository.searchSalesWithPhone(orderid);

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", sales);
			response.put("currentPage", currentPage);
			response.put("totalItems", totalItems);
			response.put("totalPages", totalPages);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/pendingordersummary")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<SalesSummary>> getOrdersSummaryByDate(@RequestBody ReportBodyDetails r) {

		try {

			String date1 = r.getText1();
			String date2 = r.getText2();
			String status = r.getText3();
			String role = r.getText4();
			String location = r.getText5();

			if (role.equals("Manager")) {
				List<SalesSummary> sales = saleSummaryRepository.findByordertime(date1, date2, status);
				return new ResponseEntity<>(sales, HttpStatus.OK);
			} else {
				List<SalesSummary> sales = saleSummaryRepository.findByorderlocation(date1, date2, status, location);
				return new ResponseEntity<>(sales, HttpStatus.OK);

			}

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/salessummary")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getSalesSummaryByDate(@RequestBody ReportBodyDetails r) {

		try {

			String date1 = r.getText1();
			String date2 = r.getText2();
			String status = r.getText3();
			String location = r.getText4();
			String cashier = r.getText5();
			String searchText = r.getText6();

			String locationCredit = r.getText7();

			if (status.equals("PAYED")) {

				if (searchText == null || searchText.equals("")) {
					sales = saleSummaryRepository.findSalesByCashier(date1, date2, cashier, status);
					// System.out.println("========sales summary ======+"+cashier+" "+status+"
					// "+date1+" "+date2+" "+sales);
				} else {

					sales = saleSummaryRepository.searchSales(searchText);

				}

				Map<String, Object> response = new HashMap<>();
				response.put("tutorials", sales);
				response.put("currentPage", currentPage);
				response.put("totalItems", totalItems);
				response.put("totalPages", totalPages);

				return new ResponseEntity<>(response, HttpStatus.OK);

			} else {

				sales = saleSummaryRepository.findDepositByLocation(date1, date2, locationCredit, status);

				if (saleSummaryRepository.findDepositByLocationSum(date1, date2, locationCredit, status) != null) {
					creditSum = saleSummaryRepository.findDepositByLocationSum(date1, date2, locationCredit, status);
				} else {
					creditSum = 0;
				}

				creditCount = saleSummaryRepository.findDepositByLocationCount(date1, date2, locationCredit, status);

				Map<String, Object> response = new HashMap<>();
				response.put("tutorials", sales);
				response.put("currentPage", currentPage);
				response.put("totalItems", totalItems);
				response.put("totalPages", totalPages);
				response.put("creditSum", decimalFormatter.format(creditSum));
				response.put("creditCount", creditCount);

				return new ResponseEntity<>(response, HttpStatus.OK);
			}

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/savedorder/{orderno}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<SalesSummary> getSavedOrder(@PathVariable("orderno") String orderNo) {

		try {

			// Date date = new Date();
			SalesSummary s = saleSummaryRepository.findByorderidContaining(orderNo);

			return new ResponseEntity<>(s, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/salessummarycommentOther")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> saveSalesCommentOther(@RequestBody ReportBodyDetails b) {

		try {
			String order = b.getText1();
			String comment = b.getText2();

			int i = saleSummaryRepository.updateSalesSummaryCommentOther(comment, order);

//			System.out.println("====order====836=="+i+" "+order+" "+comment);

			return new ResponseEntity<>(i + "", HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/salessummarycomment")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> saveSalesComment(@RequestBody ReportBodyDetails b) {

		try {
			String order = b.getText1();
			String comment = b.getText2();

			int i = saleSummaryRepository.updateSalesSummaryCommentOther(comment, order);

			// System.out.println("====order====836=="+i+" "+order+" "+comment);

			return new ResponseEntity<>(i + "", HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	// @GetMapping("/regacydeposit")
//	//@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//	public void getRegacyDeposit() {
//
//		try {
//			
//			GetSalesData s = new GetSalesData();
//						
//			//update stocklevel
//            count=0;
//            for(SalesSummary i: s.getSalesData()) {
//            	
//            	//Date date = new Date();
//        		String orderid = i.getOrderid();
//        		String orderTime = i.getOrderTime();
//
//        		String saller = i.getSaller();
//        		String customer = i.getCustomer();
//        		String phone = i.getPhone();
//        		String status = i.getStatus();
//
//        		String username = i.getUsername();
//        		//Double amount = orderSummaryAmout;
//        		Double amount_payed = i.getAmount_payed();
//        		
//        		
//        		
//        		String comment =i.getComment();
//        		String location = i.getLocation();
//        		String cashier = i.getCashier();
//
//        		String paymentTIme = i.getPaymentTime();
//        		
//        		double orderAmount = i.getAmount();
//        		
//        		//System.out.println("=====order==369======"+orderAmount);
//        		
//        		//checkIf sales summary exist 
//        		
//        		int count_salesSummary = saleSummaryRepository.checkIfSalesSummaryExist(orderid);
//        		
//        		//System.out.println("======this summary exist====="+orderid);
//        		
//        		if(count_salesSummary > 0) {
//        			
//        		//	System.out.println("======this summary exist====="+orderid);
//        			
//        		}else {
//        			saleSummaryRepository.save(new SalesSummary(orderid, orderTime, customer, phone, orderAmount, amount_payed,orderAmount, status,
//        					saller, username, paymentTIme,comment,location,cashier,""));
//        			
//        						
//        		}
//        		
//        		int countSavedSalesSummary =saleSummaryRepository.checkSavedSalesSummaryDuplication(orderTime,orderid);
//        		 long[] salesSummaryIds =saleSummaryRepository.selectSalesSummaryDuplicationId(orderTime,orderid);
//        		 
//        		
//        		if (countSavedSalesSummary > 1) {
//        			// System.out.println("==========181==== sales duplication detected===="+salesSummaryIds.length+" "+salesSummaryIds[0]+" "+salesSummaryIds[1]);
//        			 saleSummaryRepository.deleteById(salesSummaryIds[0]);
//        		 }else {
//        			// System.out.println("==========order==== no sales duplication detected===="+salesSummaryIds.length);
//        		 }
//            	
//            }
//            
//            System.out.println("Update completed: ");
//            
//            //get payment
//            for(Payments pay:s.getPayments()) {
//            	
//            	 String orderno= pay.getOrderno();
//            	  String customer= pay.getCustomer();
//            	  String phone=pay.getPhone();
//            	  String orderTime=pay.getOrderTime();
//            	  String saller = pay.getSaller();
//            	  Double amount = pay.getAmount();
//            	  Double amount_payed = pay.getAmount_payed();
//            	  Double balance = pay.getBalance();
//            	  Double cash = pay.getCash();
//            	  Double momo = pay.getMomo();
//            	  Double cheque = pay.getCheque();
//            	  Double transfer = pay.getTransfer();
//            	  Double visa = pay.getVisa();
//            	  Double total_received = pay.getTotal_received();
//            	  String payment_time = pay.getPayment_time();
//            	  String cashier = pay.getCashier();
//            	  String delivery = pay.getDelivery();
//            	  String location = pay.getLocation();
//            	  
//            	  paymentRepository.save(new Payments(orderno, customer, phone, orderTime, saller, amount, amount_payed, balance, cash, momo, cheque, transfer, visa, total_received, payment_time, cashier, delivery, location));
//            	
//            }
//            
//            for(SalesDetails order: s.getSalesDetails()) {
//            	
//            	String orderid = order.getOrderid();
//				String orderTime = order.getOrderTime();
//				String product = order.getProduct();
//				String code = order.getCode();
//				int pid = order.getPid();
//				int qty = order.getQty();
//				String color = order.getColor();
//				String size = order.getSize();
//				double price = order.getPrice();
//				String saller = order.getSaller();
//				String customer = order.getCustomer();
//				String phone = order.getPhone();
//				String salesStatus = order.getSalesStatus();
//				String stand = order.getStand();
//				String username = order.getUsername();
//				Double total = qty * price;
//
//				String paymentTIme = order.getPaymentTime();
//				String location = order.getLocation();
//				String cashier = order.getCashier();
//				double min_price=order.getMin_price();
//				String authorization_status=order.getAuthorization_status();				
//				String authorization_by = order.getAuthorization_by();
//				
//				String stockOutData = formatter2.format(new Date());
//				
//				
//
//				sale = salesDetailsRepository.save(new SalesDetails(orderid, orderTime, product, code, pid, qty, color,
//						size, price, saller, customer, phone, salesStatus, stand, username, total, paymentTIme,location,cashier,min_price,authorization_status,authorization_by));
//				
//				 orderDetailsRepository.save(new OrderDetails(orderid, orderTime, product, code, pid, qty, color,
//						size, price, saller, customer, phone, salesStatus, stand, username, total, paymentTIme,location,cashier,min_price,authorization_status,authorization_by));
//				 
//				
//				
//				
//				//stock in
//				 
//				// Stock p = stockRepo.save(new Stock(stand, stockOutData, pid, product,0, "", code, size, qty,0, 0, 0,"", "","", "Legacy Data","", color, "OUT", "",	0, "Pending", location));
//				
//            	
//            }
//            
//for(Stock order: s.getStockout()) {
//            	
//	long id = order.getId();
//	String stand = order.getStand();
//	String date = order.getDate1();
//	int pid = order.getPid();
//	String product = order.getProduct();
//	String color = order.getColor();
//	int qty_in = order.getQty_in();
//	String stockkeeper = order.getStockkeeper();
//	String code = order.getCode();
//	String size = order.getSize();
//	int qty_out = order.getQty_out();
//	String location = order.getLocation();
//	double price = order.get
//
//				
//				 //stock out
//				 
// Stock p = stockRepo.save(new Stock(stand, date, pid, product,qty_in, stockkeeper,
//			code, size, qty_out, qty_in, qty_in,
//			qty_in, "", "", "Legacy Data", "",
//			"", color, "OUT","",qty_in,"Pending",location,price));
//				
//            	
//            }
//            
//             
//			
//
//		} catch (Exception e) {
//			System.out.println(e);
//			
//
//		}
//
//	}
//	
	public void checkIfCustomerExist(String phone, String customer, String username) {

		// check if customer exist
		int count = customerRepo.checkIfCustomerExist(phone);
		if (count < 1) {
			customerRepo.save(new Customers(customer, "", phone, "", "saved From sales", "saved From sales", username,"",""));

		}

	}

	// authorize low price

	@PostMapping("/authorizelowprice")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> getAccessAuthorization(@RequestBody ReportBodyDetails r) {

		int id = Integer.valueOf(r.getText1());
		String user = r.getText2();
		String status = "AUTHORIZED";

		int i = salesDetailsRepository.updateOrderLowPriceAuthorization(user, id);

		if (i == 1) {

			return new ResponseEntity<>("Done", HttpStatus.OK);

		} else {
			return new ResponseEntity<>("Incorrect code!", HttpStatus.OK);
		}

		// System.out.println("Order====1135====Authorize low price ======"+id+"
		// "+user+" user:"+status+" "+i);

	}

}
