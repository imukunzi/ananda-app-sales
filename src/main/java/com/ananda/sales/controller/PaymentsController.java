package com.ananda.sales.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ananda.sales.model.Delivery;
import com.ananda.sales.model.OrderDetails;
import com.ananda.sales.model.PaymentSummary;
import com.ananda.sales.model.Payments;
import com.ananda.sales.model.ReportBodyDetails;
import com.ananda.sales.model.SalesDetails;
import com.ananda.sales.model.Stock;
import com.ananda.sales.repository.DeliveryRepository;
import com.ananda.sales.repository.OrderDetailsRepository;
import com.ananda.sales.repository.PaymentsRepository;
import com.ananda.sales.repository.SalesDetailsRepository;
import com.ananda.sales.repository.SalesSummaryRepository;
import com.ananda.sales.repository.StockLevelRepository;
import com.ananda.sales.repository.StockRepository;
import com.ananda.sales.sms.SendSms;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class PaymentsController {

	@Autowired
	private PaymentsRepository paymentsRepository;

	@Autowired
	private SalesSummaryRepository salesSummaryRepository;
	
	@Autowired
	private SalesDetailsRepository salesDetailsRepo;

//	@Autowired
//	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Autowired
	private StockLevelRepository stockLevelRepo;
	
	@Autowired
	private StockRepository stockRepo;
	
	

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

	private List<SalesDetails> orderList = new ArrayList<>();

	private long currentPage = 1;
	private long totalItems;
	private long totalPages;
	
	private List<Payments> pay;
	
	private SendSms sms;

	private Sort.Direction getSortDirection(String direction) {

		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;

	}

	@PostMapping("/newpayment")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> saveNewPayment(@RequestBody Payments p) {

		try {
			
			String date1 = formatter2.format(new Date())+" 00:00:00";
			String date2 = formatter2.format(new Date())+" 59:59:59";
			String cashier =p.getCashier();
			String orderId = p.getOrderno();
			Double cash = p.getCash();
			Double momo = p.getMomo();
			Double cheque = p.getCheque();
			Double transfer = p.getTransfer();
			Double visa = p.getVisa();
			Double total_received = cash + momo + cheque + transfer + visa;
			String orderid = p.getOrderno();
			String customer = p.getCustomer();
			String phone = p.getPhone();
			String orderTime = p.getOrderTime();
			String saller = p.getSaller();
			Double amount = p.getAmount();
			Double amount_payed = p.getAmount_payed();
			Double balance = p.getBalance();
			
			int count = paymentsRepository.checkPaymentDuplication(date1, date2, cashier,orderId, total_received);
			
			if(total_received > balance) {
				
			//	System.out.println("====payment====121 received:"+total_received+" balance:"+balance);
				
				return new ResponseEntity<>("The received amount is more than balance!", HttpStatus.OK);
				
			}else if (count > 0) {
				
				//System.out.println("========103===payment==="+count);
				
				return new ResponseEntity<>("This payment was receied before.", HttpStatus.OK);

			} else {
				
				//System.out.println("========109===payment==="+count);
				
				Date date = new Date();

				//String orderid = p.getOrderno();
				//String customer = p.getCustomer();
				//String phone = p.getPhone();
				//String orderTime = p.getOrderTime();
				//String saller = p.getSaller();
				//Double amount = p.getAmount();
				//Double amount_payed = p.getAmount_payed();
				//Double balance = p.getBalance();
				//Double cash = p.getCash();
				//Double momo = p.getMomo();
				//Double cheque = p.getCheque();
				//Double transfer = p.getTransfer();
				//Double visa = p.getVisa();
				//Double total_received = cash + momo + cheque + transfer + visa;
				String payment_time = formatter.format(date);
				//String cashier = p.getCashier();
				String delivery = "NOT DELIVERED";

				double totalPayed = amount_payed + total_received;
				String location = p.getLocation();
				double momo_charges= p.getMomo_charges();
				double momo_chages_amount=momo*momo_charges/100;
				
				//recheck bouble payment
				

				 paymentsRepository.save(new Payments(orderid, customer, phone, orderTime, saller, amount, amount_payed, balance, cash,
								momo, cheque, transfer, visa, total_received, payment_time, cashier, delivery, location,momo_chages_amount));

				 
								 
				 //check for any possible dublicated payments and sales summary
				 
				 int countSavedPayment =paymentsRepository.checkSavedPaymentDuplication(payment_time, cashier, orderid);
				 long[] paymentIds =paymentsRepository.selectPaymentDuplicationId(payment_time, cashier, orderid);
				 
				 int countSavedSalesSummary =salesSummaryRepository.checkSavedSalesSummaryDuplication(orderTime,orderid);
				 long[] salesSummaryIds =salesSummaryRepository.selectSalesSummaryDuplicationId(orderTime,orderid);
				 
				 
				 				 
				 if(countSavedPayment > 1) {
					 
					 System.out.println("==========165==duplication detected======"+paymentIds.length+" "+paymentIds[0]);
					//int i = paymentsRepository.deletePaymentDuplication(payment_time, cashier, orderid,paymentIds[0]);
					paymentsRepository.deleteById(paymentIds[0]);
					
				 }else {
					 
					 System.out.println("==========167==no duplication======"+paymentIds.length);
				 }
				 
				 if (countSavedSalesSummary > 1) {
					 System.out.println("==========181==== sales duplication detected===="+salesSummaryIds.length+" "+salesSummaryIds[0]+" "+salesSummaryIds[1]);
				 }else {
					 System.out.println("==========181==== no sales duplication detected===="+salesSummaryIds.length);
				 }

				String status;

				if (totalPayed == amount) {
					status = "PAYED";
				} else if (totalPayed < amount) {
					status = "DEPOSIT";
				} else {
					status = "ERROR";
				}
				
				

				salesSummaryRepository.updateSalesSummary(totalPayed, status,cashier,payment_time, orderid);
				salesSummaryRepository.updateSalesDetails(cashier, orderid);
				salesDetailsRepo.updateSalesDetails(cashier,status, orderid);
				
				
				

				// generate default delivery

				// get orderdetails
				orderList = salesDetailsRepo.findByOrderidContaining(orderid);

				// check if default delevery data
				//int count = deliveryRepository.findDeliveryCount(orderid);
				
				//check if this payment was not done today to avaid duplicate payment and stock
			
				if(deliveryRepository.findDeliveryCount(orderid) <1) {
					
					for (SalesDetails detail : orderList) {

						String orderDate = detail.getOrderTime();
						String orderNo = detail.getOrderid();
						String stock = detail.getStand();
						String item = detail.getProduct();
						String code = detail.getCode();
						String size = detail.getSize();
						String color = detail.getColor();
						int pid = detail.getPid();
						int qtyOrdered = detail.getQty();
						int qtyToDeliver = detail.getQty();
						int qtyDelivered = 0;
						int lastQtyDelivered = 0;
						int qtyRemaining = detail.getQty();
						//String paymentStatus = "DONE";
						String paymentStatus = detail.getSalesStatus();
						String deliveryStatus = "PENDING";
						String locked = "Authorized";
						String deliveryDate = "";
						String createdBy = p.getSaller();
						String branch = p.getLocation();

						deliveryRepository.save(new Delivery(orderDate, customer, orderNo, stock, item,code,size,color, pid, qtyOrdered,
								qtyToDeliver, qtyDelivered, lastQtyDelivered, qtyRemaining, paymentStatus, deliveryStatus,
								locked, deliveryDate, createdBy, deliveryDate,branch));
						
						//updte stock level
						//get current stocklevel
						int existingQty =stockLevelRepo.getCurrentStockLevel(detail.getPid(), detail.getStand());
						int totalQty2 = existingQty - detail.getQty();
						
									
						String status1 = "Critical";
						 
						if(totalQty2 >700) {
							status1="Full";
						}else if(totalQty2<700 && totalQty2 >500) {
							status1="Normal";
						}else if(totalQty2 <500 && totalQty2 > 200) {
							status1="Low";
						}else if(totalQty2<200) {
							status1="Critical";
						}
						
						//update stock-level
						stockLevelRepo.updateStockLevel(totalQty2,status1, detail.getPid(), detail.getStand());
						
						//save sold item in stock out
						
						Stock p1 = stockRepo.save(new Stock(detail.getStand(), formatter2.format(date), detail.getPid(), detail.getProduct(),
								0, "FROM SALES", detail.getCode(), detail.getSize(), detail.getQty(),
								0, 0, 0,"", "", "FROM SALES", "FROM SALES","", detail.getColor(), "OUT", "",0, "Confirmed", detail.getLocation(),detail.getPrice()));
						
						
						
					}
					
				}

				
				
				//recheck if sales summary updated 
				//double totalPayedPerOrder = salesSummaryRepository.findSalesSummaryTotalPaid(orderid);
				
				double totalCashPayedPerOrder = paymentsRepository.findPaymentCashTotalPerOderNo(orderid);
				double totalMomoPayedPerOrder = paymentsRepository.findPaymentMomoTotalOderNo(orderid);
				double totalVisaPayedPerOrder = paymentsRepository.findPaymentVisaTotalOderNo(orderid);
				double totalChequePayedPerOrder = paymentsRepository.findPaymentChequeTotalOderNo(orderid);
				double totalTransferPayedPerOrder = paymentsRepository.findPaymentTransferTotalOderNo(orderid);
				
				double total = totalCashPayedPerOrder+totalMomoPayedPerOrder+totalVisaPayedPerOrder+totalChequePayedPerOrder+totalTransferPayedPerOrder;
				
				//System.out.println("=======payment 231====="+totalPayedPerOrder+ " total"+total);
				
				if (total == amount) {
					status = "PAYED";
				} else if (total < amount) {
					status = "DEPOSIT";
				} else {
					status = "ERROR";
				}

				salesSummaryRepository.updateSalesSummary(total, status,cashier,payment_time, orderid);
				deliveryRepository.updateDeliveryStatus(status, orderid);
				
				sms = new SendSms();
				
				String message;
				String source = "YES PHONE";
		        String destination="25"+phone;
		        double remaining_amount = amount - totalPayed;
				
				if(balance > 0) {
					
				message ="Thank you for shopping with YES PHONE. OFFICE LINE:(+250)788447848";
					
				}
				
				
		        if (balance < 1) {

		            message = "Thank you for shopping with YES PHONE. OFFICE LINE:(+250)788447848";

		        } else {
		            message = "Thank you for shopping with YES PHONE. " + total_received +"Rwf was received. Total payment is:"+totalPayed+ " Rwf. Remaining Balance is " + remaining_amount + "Rwf. OFFICE LINE:(+250)788447848";
		        }

		        				 
				 //send sms
//				 sms.submitMessage("clr-yesphone", "Gold@21e", message,"0", "1", destination, source, "rslr.connectbind.com",8080);
////				 (String username, String password, String message, String type, String dlr, String destination,
////							String source, String server, int port)
									
				
				return new ResponseEntity<>("Payment saved successfully.", HttpStatus.OK);

			}

			

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	/*
	 * @GetMapping("/payments")
	 * 
	 * @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") public
	 * ResponseEntity<Map<String, Object>>getPayments(@RequestParam(required =
	 * false) String code,
	 * 
	 * @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue =
	 * "50") int size,@RequestParam(required = false) String
	 * location,@RequestParam(required = false) String role,@RequestParam(required =
	 * false) String date,@RequestParam(required = false) String username,
	 * 
	 * @RequestParam(required = false) String report,@RequestParam(required = false)
	 * String cashier,@RequestParam(defaultValue = "id,desc") String[] sort) {
	 * 
	 * try { List<Order> orders = new ArrayList<Order>();
	 * 
	 * if (sort[0].contains(",")) { // will sort more than 2 fields //
	 * sortOrder="field, direction" for (String sortOrder : sort) { String[] _sort =
	 * sortOrder.split(","); orders.add(new Order(getSortDirection(_sort[1]),
	 * _sort[0])); } } else { // sort=[field, direction] orders.add(new
	 * Order(getSortDirection(sort[1]), sort[0])); }
	 * 
	 * List<Payments> payments = new ArrayList<Payments>(); Pageable pagingSort =
	 * PageRequest.of(page, size, Sort.by(orders));
	 * 
	 * 
	 * 
	 * Page<Payments> pageTuts; if (code != null) { pageTuts =
	 * paymentsRepository.findByordernoContaining(code, pagingSort); payments =
	 * pageTuts.getContent();
	 * 
	 * currentPage= pageTuts.getNumber(); totalItems=pageTuts.getTotalElements();
	 * totalPages=pageTuts.getTotalPages();
	 * 
	 * 
	 * } else {
	 * 
	 * if(role.equals("Manager")) {
	 * 
	 * String date1 = date+ " 00:00:00"; String date2 = date+ " 59:59:59";
	 * 
	 * 
	 * pageTuts = paymentsRepository.findAll(pagingSort); payments =
	 * paymentsRepository.findByCashier(date1,date2,cashier); currentPage=
	 * pageTuts.getNumber(); totalItems=pageTuts.getTotalElements();
	 * totalPages=pageTuts.getTotalPages();
	 * 
	 * System.out.println("refresh manager currentPage "+currentPage+" total Item"
	 * +totalItems+" totalPAGES "+totalPages+" size"
	 * +size+" username"+username+" date "+date+
	 * " report "+report+" cashier "+cashier);
	 * 
	 * }else { String date1 = date+ " 00:00:00"; String date2 = date+ " 59:59:59";
	 * System.out.println("refresh cashier currentPage "+currentPage+" total Item"
	 * +totalItems+" totalPAGES "+totalPages+" size"
	 * +size+" username"+username+" date "+date); payments =
	 * paymentsRepository.findByCashier(date1,date2,username);
	 * 
	 * //currentPage= pageTuts.getNumber(); currentPage= 0;
	 * totalItems=paymentsRepository.findByCashierCount(date1,date2,username); long
	 * pages =totalItems/size; totalPages=pages;
	 * //totalPages=pageTuts.getTotalPages();
	 * 
	 * }
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * Map<String, Object> response = new HashMap<>(); response.put("tutorials",
	 * payments); response.put("currentPage", currentPage);
	 * response.put("totalItems", totalItems); response.put("totalPages",
	 * totalPages);
	 * 
	 * return new ResponseEntity<>(response, HttpStatus.OK);
	 * 
	 * 
	 * 
	 * try {
	 * 
	 * String date1 = r.getText1(); String date2 = r.getText2(); String role =
	 * r.getText3(); String location = r.getText4();
	 * 
	 * if(role.equals("Manager")) { List<Payments> pay =
	 * paymentsRepository.findByPayment_time(date1, date2); return new
	 * ResponseEntity<>(pay, HttpStatus.OK); }else {
	 * 
	 * List<Payments> pay = paymentsRepository.findByPayment_time_location(date1,
	 * date2,location); return new ResponseEntity<>(pay, HttpStatus.OK);
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * } catch (Exception e) { System.out.println(e); return new
	 * ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	 * 
	 * }
	 * 
	 * }
	 */
	@PostMapping("/payments")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getPayments(@RequestBody ReportBodyDetails r) {

		try {

			String date1 = r.getText1();
			String date2 = r.getText2();
			//String location = r.getText3();
			String cashier = r.getText4();
			String searchText = r.getText5();
			
			if(searchText == null || searchText.equals("")) {
				pay = paymentsRepository.findByCashier(date1, date2,cashier);
			}else {
				pay = paymentsRepository.findByorderno(searchText);
			}
		
				
				
		

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", pay);
			response.put("currentPage", currentPage);
			response.put("totalItems", totalItems);
			response.put("totalPages", totalPages);

			return new ResponseEntity<>(response, HttpStatus.OK);

			

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/paymentstotal")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<PaymentSummary> getPaymentsTotal(@RequestBody ReportBodyDetails r) {

		try {

			String date1 = r.getText1();
			String date2 = r.getText2();

			Double cash = paymentsRepository.findPaymentCashTotal(date1, date2);
			Double momo = paymentsRepository.findPaymentMomoTotal(date1, date2);
			Double visa = paymentsRepository.findPaymentVisaTotal(date1, date2);
			Double cheque = paymentsRepository.findPaymentChequeTotal(date1, date2);
			Double transfer = paymentsRepository.findPaymentTransferTotal(date1, date2);
			Double total = cash + momo + visa + transfer + cheque;

			PaymentSummary pay = new PaymentSummary(cash, momo, cheque, transfer, visa, total);

			return new ResponseEntity<>(pay, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/paymentsbycashier")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Payments>> getPaymentsByCashier(@RequestBody ReportBodyDetails r) {

		try {

			String date1 = r.getText1();
			String date2 = r.getText2();
			String cashier = r.getText3();

			List<Payments> pay = paymentsRepository.findByCashier(date1, date2, cashier);

			return new ResponseEntity<>(pay, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/paymentsearch")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Payments>> searchPayments(@RequestBody ReportBodyDetails r) {

		try {

			String orderno = r.getText1();

			List<Payments> pay = paymentsRepository.findByorderno(orderno);

			return new ResponseEntity<>(pay, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/paymentstotalbycashier")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<PaymentSummary> getPaymentsTotalByCashier(@RequestBody ReportBodyDetails r) {

		try {

			String date1 = r.getText1();
			String date2 = r.getText2();
			String cashier = r.getText3();

			Double cash = paymentsRepository.findPaymentCashTotal(date1, date2, cashier);
			Double momo = paymentsRepository.findPaymentMomoTotal(date1, date2, cashier);
			Double visa = paymentsRepository.findPaymentVisaTotal(date1, date2, cashier);
			Double cheque = paymentsRepository.findPaymentChequeTotal(date1, date2, cashier);
			Double transfer = paymentsRepository.findPaymentTransferTotal(date1, date2, cashier);
			Double total = cash + momo + visa + transfer + cheque;

			PaymentSummary pay = new PaymentSummary(cash, momo, cheque, transfer, visa, total);

			return new ResponseEntity<>(pay, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/journal")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<PaymentSummary> getJournal(@RequestBody ReportBodyDetails r) {

		try {

			String date1 = r.getText1();
			String date2 = r.getText2();
			String cashier = r.getText3();

			Double cash = paymentsRepository.findPaymentCashTotal(date1, date2, cashier);
			Double momo = paymentsRepository.findPaymentMomoTotal(date1, date2, cashier);
			Double visa = paymentsRepository.findPaymentVisaTotal(date1, date2, cashier);
			Double cheque = paymentsRepository.findPaymentChequeTotal(date1, date2, cashier);
			Double transfer = paymentsRepository.findPaymentTransferTotal(date1, date2, cashier);
			Double total = cash + momo + visa + transfer + cheque;

			PaymentSummary pay = new PaymentSummary(cash, momo, cheque, transfer, visa, total);

			return new ResponseEntity<>(pay, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
