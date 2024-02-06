package com.ananda.sales.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ananda.sales.customdb.CustomQueries;
import com.ananda.sales.model.CashingSummary;
import com.ananda.sales.model.PaymentSummaryReport;
import com.ananda.sales.model.ReportBodyDetails;
import com.ananda.sales.model.ReportSales;
import com.ananda.sales.model.SalesDetailsProfit;
import com.ananda.sales.model.SallersReport;
import com.ananda.sales.repository.CashingRepository;
import com.ananda.sales.repository.ExpensesRepository;
import com.ananda.sales.repository.PaymentsRepository;
import com.ananda.sales.repository.SalesDetailsRepository;
import com.ananda.sales.repository.SalesSummaryRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class ReportController {

	@Autowired
	private SalesSummaryRepository salesSummaryRepository;

	@Autowired
	private SalesDetailsRepository salesDetailsRepository;

	@Autowired
	private PaymentsRepository paymentRepository;

	@Autowired
	private ExpensesRepository expenseRepository;

	@Autowired
	private CashingRepository cashingRepository;

//	@Autowired
//	private SelectOptionRepository selectOptionRepo;

	private long currentPage = 1;
	private long totalItems;
	private long totalPages;

//	private double cashTotal = 0;
//	private double momoTotal = 0;
//	private double visaTotal = 0;
//	private double chequeTotal = 0;
//	private double transferTotal = 0;

	private double reportSales_totalSales = 0;
	private double reportSales_newSales = 0;
	private double reportSales_salesPayment = 0;
	private double reportSales_salesDeposit = 0;
	private double reportSales_salesCredit = 0;

	private double cash = 0;
	private double momo = 0;
	private double momo_charges = 0;
	private double visa = 0;
	private double cheque = 0;
	private double transfer = 0;
	private double total = 0;
	private double gTotal = 0;

	private double expenses = 0;

	private int[] cashingNo;
	private double fiveThousands = 0;
	private double fiveThousandsTotal = 0;
	private double twoThousands = 0;
	private double twoThousandsTotal = 0;
	private double oneThousands = 0;
	private double oneThousandsTotal = 0;
	private double fiveHundred = 0;
	private double fiveHundredTotal = 0;
	private double onehundred = 0;
	private double onehundredTotal = 0;
	private double fifty = 0;
	private double fiftyTotal = 0;
	private double twenty = 0;
	private double twentyTotal = 0;
	private double ten = 0;
	private double tenTotal = 0;
	private double five = 0;
	private double fiveTotal = 0;
	private double two = 0;
	private double twoTotal = 0;
	private double one = 0;
	private double oneTotal = 0;
	private double grandTotal = 0;
	private double amount = 0;
	private double caisseInt = 0;

	private double dollar = 0;
	private double dollar2 = 0;
	private double dollar3 = 0;
	private double dollar4 = 0;
	private double dollarRate = 0;
	private double dollar2Rate = 0;
	private double dollar3Rate = 0;
	private double dollar4Rate = 0;
	
	private double dollarTotal = 0;
	private double dollar2Total = 0;
	private double dollar3Total = 0;
	private double dollar4Total = 0;
	private double totalDollar = 0;
	
	private double euro = 0;
	private double euro2 = 0;
	private double euro3 = 0;
	private double euro4 = 0;
	private double euroRate = 0;
	private double euro2Rate = 0;
	private double euro3Rate = 0;
	private double euro4Rate = 0;
	
	private double euroTotal = 0;
	private double euro2Total = 0;
	private double euro3Total = 0;
	private double euro4Total = 0;
	private double totalEuro = 0;
	
	private double biettageGrandTotal = 0;
	private double headCashierAmount;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private DecimalFormat decimalFormatter = new DecimalFormat("#,###.00");

	private List<SallersReport> sallersReportList;
	List<Double> sallerPerformanceTotal;
	private double sallersReportSum = 0;
//	private int sallersReportSumItem = 0;
	private List<SallersReport> soledProductsReportList;
//	private List<Integer> soledProductsReportListItems;
	private List<SallersReport> soledProductsReportListStand;
	private List<Integer> soledProductsReportListStandItemQty;
	private double sallersAmount = 0;
	private double soldProductAmount = 0;
	private int soldProductQty = 0;
	
	private double standSoldAmount=0;
	private int totalItem;
	private List<SalesDetailsProfit> salesDetailsProfitList;
	private List<Double> salesDetailsProfitListTotal;

	@PostMapping("/salesreport")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getCurrentOrderTemp(@RequestBody ReportBodyDetails r) {

		try {

			// Date date = new Date();
			// String today = formatter.format(date);

			String date1 = r.getText1();
			String date2 = r.getText2();
			String cashier = r.getText4();
			String branch = r.getText5();
			String stand = r.getText6();
			
			String description = r.getText7();

			// System.out.println("date1 " + date1 + " " + date2 + " date2 " + date2 + "
			// cashier " + cashier+" branch:"+branch);

			Map<String, Object> response = new HashMap<>();
			// response.put("tutorials",
			// orderDetailsRepository.findByOrderidDetails(order));
			response.put("currentPage", currentPage);
			response.put("totalItems", totalItems);
			response.put("totalPages", totalPages);

			//////////////////// sales ///////////////////////////

			if (salesSummaryRepository.reportSales_newSales(date1, date2, cashier) != null) {
//				reportSales_newSales = paymentRepository.reportSales_newSales(date1,date1, date2, cashier);
			} else {
				reportSales_newSales = 0;
			}
			
			

			if (paymentRepository.reportSales_salesPayment(date1,date1, date2, cashier) != null) {
				reportSales_salesPayment = paymentRepository.reportSales_salesPayment(date1,date1, date2, cashier);
			} else {
				reportSales_salesPayment = 0;
			}
			

			if (salesSummaryRepository.reportSales_salesDeposit(date1, date2, cashier) != null) {
				reportSales_salesDeposit = salesSummaryRepository.reportSales_salesDeposit(date1, date2, cashier);
			} else {
				reportSales_salesDeposit = 0;
			}
			
			if (salesSummaryRepository.reportSales_totalSales(date1, date2, cashier) != null) {
				reportSales_totalSales = salesSummaryRepository.reportSales_totalSales(date1, date2, cashier);
			} else {
				reportSales_totalSales = 0;
			}
			
			//reportSales_totalSales = reportSales_newSales +reportSales_salesPayment;

			reportSales_salesCredit = reportSales_totalSales - reportSales_salesDeposit - reportSales_newSales;

		//	System.out.println("====report 211====="+reportSales_salesCredit);
			
			//////////////////////// payments ///////////////////////

			if (paymentRepository.findPaymentCashTotal(date1, date2, cashier) != null) {

				cash = paymentRepository.findPaymentCashTotal(date1, date2, cashier);

			} else {
				cash = 0;
			}

			if (paymentRepository.findPaymentMomoTotal(date1, date2, cashier) != null) {

				momo = paymentRepository.findPaymentMomoTotal(date1, date2, cashier);

			} else {
				momo = 0;
			}
			
			if (paymentRepository.findPaymentMomoChargesTotal(date1, date2, cashier) != null) {

				momo_charges = paymentRepository.findPaymentMomoChargesTotal(date1, date2, cashier);

			} else {
				momo_charges = 0;
			}

			if (paymentRepository.findPaymentVisaTotal(date1, date2, cashier) != null) {

				visa = paymentRepository.findPaymentVisaTotal(date1, date2, cashier);

			} else {
				visa = 0;
			}

			if (paymentRepository.findPaymentChequeTotal(date1, date2, cashier) != null) {

				cheque = paymentRepository.findPaymentChequeTotal(date1, date2, cashier);

			} else {
				cheque = 0;
			}

			if (paymentRepository.findPaymentTransferTotal(date1, date2, cashier) != null) {

				transfer = paymentRepository.findPaymentTransferTotal(date1, date2, cashier);

			} else {
				transfer = 0;
			}

			total = cash + momo + visa + cheque + transfer;

			// expenses
			if (expenseRepository.findBydateReport(date1, date2, cashier) != null) {

				expenses = expenseRepository.findBydateReport(date1, date2, cashier);

			} else {
				expenses = 0;
			}

			

			ReportSales reportSales = new ReportSales(decimalFormatter.format(reportSales_totalSales),
					decimalFormatter.format(reportSales_newSales), decimalFormatter.format(reportSales_salesPayment),
					decimalFormatter.format(reportSales_salesDeposit),
					decimalFormatter.format(reportSales_salesCredit));

			PaymentSummaryReport payment = new PaymentSummaryReport(decimalFormatter.format(cash),
					decimalFormatter.format(momo), decimalFormatter.format(cheque), decimalFormatter.format(transfer),
					decimalFormatter.format(visa), decimalFormatter.format(total),decimalFormatter.format(momo_charges));

			/////////// sallers report

			sallersReportList = new ArrayList<>();
			sallersAmount = 0;

			sallerPerformanceTotal = new ArrayList<>();
			
			//System.out.println("=======report======298====="+descrition+" "+branch);

			for (String o : salesSummaryRepository.findSalesBySallerList(date1, date2, branch)) {
				
			//	System.out.println("=======report======298====="+o+" "+branch);

				if (salesDetailsRepository.findSalesBySaller(date1, date2, o) != null) {
					sallersAmount = salesDetailsRepository.findSalesBySaller(date1, date2, o);
					sallerPerformanceTotal.add(sallersAmount);
					
				//	System.out.println("=======report======298====="+o.getValue()+" "+branch);

				} else {
					sallersAmount = 0;

				}

				SallersReport sallerReport = new SallersReport(o, decimalFormatter.format(sallersAmount),
						date1);
				sallersReportList.add(sallerReport);

			}

			sallersReportSum = 0;
			for (double i : sallerPerformanceTotal) {
				sallersReportSum = sallersReportSum + i;

			}
			
			

			// sold products
			soledProductsReportList = new ArrayList<>();
//			soledProductsReportListItems = new ArrayList<>();
			 
			soldProductAmount = 0;

			

			for (String p : salesDetailsRepository.findSaledProducts(date1, date2, branch)) {

				if (salesDetailsRepository.findSaledProductsAmount(date1, date2, p, branch) != null) {
					soldProductAmount = salesDetailsRepository.findSaledProductsAmount(date1, date2, p, branch);
				} else {
					soldProductAmount = 0;
				}

				if (salesDetailsRepository.findSaledProductsQty(date1, date2, p, branch) != null) {
					soldProductQty = salesDetailsRepository.findSaledProductsQty(date1, date2, p, branch);
				} else {
					soldProductQty = 0;
				}

				SallersReport s = new SallersReport(p, decimalFormatter.format(soldProductAmount),
						String.valueOf(soldProductQty));

				soledProductsReportList.add(s);
//				soledProductsReportListItems.add(soldProductQty);
				
				 

			}
			
			 
          
			
			//stand
			soledProductsReportListStand = new ArrayList<>();
					
			System.out.println("Date1:"+date1+" date2:"+date2+" stand:"+stand+" description:"+description);
			
			for (String p : salesDetailsRepository.findSaledProductsStand(date1, date2, stand,description)) {

				if (salesDetailsRepository.findSaledProductsAmountStand(date1, date2, p, stand,description) != null) {
					soldProductAmount = salesDetailsRepository.findSaledProductsAmountStand(date1, date2, p, stand,description);
				} else {
					soldProductAmount = 0;
				}

				if (salesDetailsRepository.findSaledProductsQtyStand(date1, date2, p, stand,description) != null) {
					
					soledProductsReportListStandItemQty= new ArrayList<>();
					
					soldProductQty = salesDetailsRepository.findSaledProductsQtyStand(date1, date2, p, stand,description);
					
					//soledProductsReportListStandItemQty.add(soldProductQty);
					
					 
				} else {
					soldProductQty = 0;
				}

				SallersReport s = new SallersReport(p, decimalFormatter.format(soldProductAmount),
						String.valueOf(soldProductQty));

				soledProductsReportListStand.add(s);

			}
			
			if(salesDetailsRepository.findSaledAmountStand(date1, date2, stand,description)!=null) {
				standSoldAmount=	salesDetailsRepository.findSaledAmountStand(date1, date2, stand,description);
			}else {
				standSoldAmount=0;
			}
			
			if(salesDetailsRepository.findSaledStandTotalItem(date1, date2, stand,description)!=null) {
				totalItem=	salesDetailsRepository.findSaledStandTotalItem(date1, date2, stand,description);
			}else {
				totalItem=0;
			}
			
			 
			
			//System.out.println("=====report 359====="+date1+ " "+date2+" branch"+" "+branch+" desc:"+description+" "+totalItem );

			gTotal = cash - expenses;
			
			//get cashing numbers
			
			//cashingNo = cashingRepository.get(date1, date2, cashier);
			
			
			
			CashingSummary cashing = new CashingSummary(0, decimalFormatter.format(0),
					0, decimalFormatter.format(0), 0,
					decimalFormatter.format(0), fiveHundred, decimalFormatter.format(0),
					0, decimalFormatter.format(0), 0, decimalFormatter.format(0),
					0, decimalFormatter.format(0), 0, decimalFormatter.format(0), 0,
					decimalFormatter.format(0), 0, decimalFormatter.format(0), 0,
					decimalFormatter.format(0), decimalFormatter.format(0),
					decimalFormatter.format(0), decimalFormatter.format(0),"","","","","");
			

			// System.out.println("====report 392==="+reportSales);
			
			cashingNo = cashingRepository.getCashingCount(date1, date2, cashier);
			
			//sallers performance
			
			

			response.put("reportSalesData", reportSales);
			response.put("reportPaymentData", payment);
			response.put("expenses", decimalFormatter.format(expenses));
			response.put("caisseInt", decimalFormatter.format(caisseInt));
			
			response.put("cashingCount", cashingNo);
			response.put("gtotal", decimalFormatter.format(gTotal));
			response.put("sallerData", sallersReportList);
			response.put("sallerDataSum", decimalFormatter.format(sallersReportSum));
			response.put("soldProductsData", soledProductsReportList);
//			response.put("soldProductsDataItem",sumItem );
			response.put("soldProductsDataStand", soledProductsReportListStand);
			response.put("standSoldAmount",decimalFormatter.format(standSoldAmount) );
			response.put("standSoldTotalItem",totalItem );
			
			response.put("cashing", cashing);
			//dollar
			response.put("dollar",decimalFormatter.format(0) );
			response.put("dollarRate",decimalFormatter.format(0) );
			response.put("dollarTotal",decimalFormatter.format(0) );
			
			response.put("dollar2",decimalFormatter.format(0) );
			response.put("dollar2Rate",decimalFormatter.format(0) );
			response.put("dollar2Total",decimalFormatter.format(0) );
			
			response.put("dollar3",decimalFormatter.format(0) );
			response.put("dollar3Rate",decimalFormatter.format(0) );
			response.put("dollar3Total",decimalFormatter.format(0) );
			
			response.put("dollar4",decimalFormatter.format(0) );
			response.put("dollar4Rate",decimalFormatter.format(0) );
			response.put("dollar4Total",decimalFormatter.format(0) );
			response.put("totalDollar",decimalFormatter.format(0) );
			
			//euro
			response.put("euro",decimalFormatter.format(0) );
			response.put("euroRate",decimalFormatter.format(0) );
			response.put("euroTotal",decimalFormatter.format(0) );
			
			response.put("euro2",decimalFormatter.format(0) );
			response.put("euro2Rate",decimalFormatter.format(0) );
			response.put("euro2Total",decimalFormatter.format(0) );
			
			response.put("euro3",decimalFormatter.format(0) );
			response.put("euro3Rate",decimalFormatter.format(0) );
			response.put("euro3Total",decimalFormatter.format(0) );
			
			response.put("euro4",decimalFormatter.format(0) );
			response.put("euro4Rate",decimalFormatter.format(0) );
			response.put("euro4Total",decimalFormatter.format(0) );
			response.put("totalEuro",decimalFormatter.format(0) );
			
			response.put("biettageGrandTotal",decimalFormatter.format(0) );

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	@GetMapping("/cashingreport/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getCashingReport(@PathVariable("id") String idValue) {
		
		
		Map<String, Object> response = new HashMap<>();
		
		if(idValue.equals("undefined") || idValue.equals("")) {
			
			System.out.println("null value======"+idValue);
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		}else {
			System.out.println("id exist");
			long id= Long.valueOf(idValue);
			
			try {
				
				// response.put("tutorials",
				// orderDetailsRepository.findByOrderidDetails(order));
				response.put("currentPage", currentPage);
				response.put("totalItems", totalItems);
				response.put("totalPages", totalPages);
				
				if (cashingRepository.findBy5000Total(id) != null) {

					fiveThousands = cashingRepository.findBy5000Total(id);
					fiveThousandsTotal = fiveThousands * 5000;

				} else {
					fiveThousands = 0;
					fiveThousandsTotal = 0;
				}

				if (cashingRepository.findBy2000Total(id) != null) {

					twoThousands = cashingRepository.findBy2000Total(id);
					twoThousandsTotal = twoThousands * 2000;

				} else {
					twoThousands = 0;
					twoThousandsTotal = 0;
				}

				if (cashingRepository.findBy1000Total(id) != null) {

					oneThousands = cashingRepository.findBy1000Total(id);
					oneThousandsTotal = oneThousands * 1000;

				} else {
					oneThousands = 0;
					oneThousandsTotal = 0;

				}

				if (cashingRepository.findBy500Total(id) != null) {

					fiveHundred = cashingRepository.findBy500Total(id);
					fiveHundredTotal = fiveHundred * 500;

				} else {
					fiveHundred = 0;
					fiveHundredTotal = 0;
				}

				if (cashingRepository.findBy100Total(id) != null) {

					onehundred = cashingRepository.findBy100Total(id);
					onehundredTotal = onehundred * 100;

				} else {
					onehundred = 0;
					onehundredTotal = 0;
				}

				if (cashingRepository.findBy50Total(id) != null) {

					fifty = cashingRepository.findBy50Total(id);
					fiftyTotal = fifty * 50;

				} else {
					fifty = 0;
					fiftyTotal = 0;
				}

				if (cashingRepository.findBy20Total(id) != null) {

					twenty = cashingRepository.findBy20Total(id);
					twentyTotal = twenty * 20;

				} else {
					twenty = 0;
					twentyTotal = 0;
				}

				if (cashingRepository.findBy10Total(id) != null) {

					ten = cashingRepository.findBy10Total(id);
					tenTotal = ten * 10;

				} else {
					ten = 0;
					tenTotal = 0;
				}

				if (cashingRepository.findBy5Total(id) != null) {

					five = cashingRepository.findBy5Total(id);
					fiveTotal = five * 5;

				} else {
					five = 0;
					fiveTotal = 0;
				}

				if (cashingRepository.findBy2Total(id) != null) {

					two = cashingRepository.findBy2Total(id);
					twoTotal = two * 2;

				} else {
					two = 0;
					twoTotal = 0;
				}

				if (cashingRepository.findBy1Total(id) != null) {

					one = cashingRepository.findBy1Total(id);
					oneTotal = one * 1;

				} else {
					one = 0;
					oneTotal = 0;
				}

				if (cashingRepository.findByAmountTotal(id) != null) {

					amount = cashingRepository.findByAmountTotal(id);

				} else {
					amount = 0;
				}
				
				caisseInt=0;

				if (cashingRepository.getCaisseInitTotal(id) != null) {
					caisseInt = cashingRepository.getCaisseInitTotal(id);

				} else {
					caisseInt = 0;

				}
				
						
				//dollar

				if (cashingRepository.findByDollarTotal(id) != null) {
					dollar = cashingRepository.findByDollarTotal(id);

				} else {

					dollar = 0;

				}
				
				if (cashingRepository.findByDollarRateTotal(id) != null) {
					dollarRate = cashingRepository.findByDollarRateTotal(id);

				} else {

					dollarRate = 0;

				}
				
				//dollar 2
				if (cashingRepository.findByDollar2Total(id) != null) {
					dollar2 = cashingRepository.findByDollar2Total(id);

				} else {

					dollar2 = 0;

				}
				
				if (cashingRepository.findByDollarRate2Total(id) != null) {
					dollar2Rate = cashingRepository.findByDollarRate2Total(id);

				} else {

					dollar2Rate = 0;

				}
				
				//dollar 3
				
				if (cashingRepository.findByDollar3Total(id) != null) {
					dollar3 = cashingRepository.findByDollar3Total(id);

				} else {

					dollar3 = 0;

				}
				
				if (cashingRepository.findByDollarRate3Total(id) != null) {
					dollar3Rate = cashingRepository.findByDollarRate3Total(id);

				} else {

					dollar3Rate = 0;

				}
				
				//dollar 4
				if (cashingRepository.findByDollar4Total(id) != null) {
					dollar4 = cashingRepository.findByDollar4Total(id);

				} else {

					dollar4 = 0;

				}
				
				if (cashingRepository.findByDollarRate4Total(id) != null) {
					dollar4Rate = cashingRepository.findByDollarRate4Total(id);

				} else {

					dollar4Rate = 0;

				}
				
				
				
				dollarTotal= dollar*dollarRate;
				dollar2Total= dollar2*dollar2Rate;
				dollar3Total= dollar3*dollar3Rate;
				dollar4Total= dollar4*dollar4Rate;
				
				totalDollar =dollarTotal+dollar2Total+dollar3Total+dollar4Total;
				
				//euro
				if (cashingRepository.findByEuroTotal(id) != null) {
					euro = cashingRepository.findByEuroTotal(id);

				} else {

					euro = 0;

				}
				
				if (cashingRepository.findByEuroRateTotal(id) != null) {
					euroRate = cashingRepository.findByEuroRateTotal(id);

				} else {

					euroRate = 0;

				}
				
				//euro 2
				if (cashingRepository.findByEuro2Total(id) != null) {
					euro2 = cashingRepository.findByEuro2Total(id);

				} else {

					euro2 = 0;

				}
				
				if (cashingRepository.findByEuroRate2Total(id) != null) {
					euro2Rate = cashingRepository.findByEuroRate2Total(id);

				} else {

					euro2Rate = 0;

				}
				
				//euro 3
				
				if (cashingRepository.findByEuro3Total(id) != null) {
					euro3 = cashingRepository.findByEuro3Total(id);

				} else {

					euro3 = 0;

				}
				
				if (cashingRepository.findByEuroRate3Total(id) != null) {
					euro3Rate = cashingRepository.findByEuroRate3Total(id);

				} else {

					euro3Rate = 0;

				}
				
				//dollar 4
				if (cashingRepository.findByEuro4Total(id) != null) {
					euro4 = cashingRepository.findByEuro4Total(id);

				} else {

					euro4 = 0;

				}
				
				if (cashingRepository.findByEuroRate4Total(id) != null) {
					euro4Rate = cashingRepository.findByEuroRate4Total(id);

				} else {

					euro4Rate = 0;

				}
				
				
				
				euroTotal= euro*euroRate;
				euro2Total= euro2*euro2Rate;
				euro3Total= euro3*euro3Rate;
				euro4Total= euro4*euro4Rate;
				
				totalEuro =euroTotal+euro2Total+euro3Total+euro4Total;
				
				//end euro

				grandTotal = fiveThousandsTotal + twoThousandsTotal + oneThousandsTotal + fiveHundredTotal + onehundredTotal
						+ fiftyTotal + twentyTotal + tenTotal + fiveTotal + twoTotal + oneTotal;
				
				biettageGrandTotal =grandTotal + totalDollar +totalEuro;
				
				String headCashier = cashingRepository.getheadCashier(id);
				String headCashierComment = cashingRepository.getheadCashierComment(id);
				
				if(cashingRepository.getheadCashierAmount(id) != null) {
					 headCashierAmount = cashingRepository.getheadCashierAmount(id);
				}
				

				CashingSummary cashing = new CashingSummary(fiveThousands, decimalFormatter.format(fiveThousandsTotal),
						twoThousands, decimalFormatter.format(twoThousandsTotal), oneThousands,
						decimalFormatter.format(oneThousandsTotal), fiveHundred, decimalFormatter.format(fiveHundredTotal),
						onehundred, decimalFormatter.format(onehundredTotal), fifty, decimalFormatter.format(fiftyTotal),
						twenty, decimalFormatter.format(twentyTotal), ten, decimalFormatter.format(tenTotal), five,
						decimalFormatter.format(fiveTotal), two, decimalFormatter.format(twoTotal), one,
						decimalFormatter.format(oneTotal), decimalFormatter.format(grandTotal),
						decimalFormatter.format(amount), decimalFormatter.format(caisseInt),"",headCashier,decimalFormatter.format(headCashierAmount),headCashierComment,"");
				
				response.put("cashing", cashing);
				//dollar
				response.put("dollar",decimalFormatter.format(dollar) );
				response.put("dollarRate",decimalFormatter.format(dollarRate) );
				response.put("dollarTotal",decimalFormatter.format(dollarTotal) );
				
				response.put("dollar2",decimalFormatter.format(dollar2) );
				response.put("dollar2Rate",decimalFormatter.format(dollar2Rate) );
				response.put("dollar2Total",decimalFormatter.format(dollar2Total) );
				
				response.put("dollar3",decimalFormatter.format(dollar3) );
				response.put("dollar3Rate",decimalFormatter.format(dollar3Rate) );
				response.put("dollar3Total",decimalFormatter.format(dollar3Total) );
				
				response.put("dollar4",decimalFormatter.format(dollar4) );
				response.put("dollar4Rate",decimalFormatter.format(dollar4Rate) );
				response.put("dollar4Total",decimalFormatter.format(dollar4Total) );
				response.put("totalDollar",decimalFormatter.format(totalDollar) );
				
				//euro
				response.put("euro",decimalFormatter.format(euro) );
				response.put("euroRate",decimalFormatter.format(euroRate) );
				response.put("euroTotal",decimalFormatter.format(euroTotal) );
				
				response.put("euro2",decimalFormatter.format(euro2) );
				response.put("euro2Rate",decimalFormatter.format(euro2Rate) );
				response.put("euro2Total",decimalFormatter.format(euro2Total) );
				
				response.put("euro3",decimalFormatter.format(euro3) );
				response.put("euro3Rate",decimalFormatter.format(euro3Rate) );
				response.put("euro3Total",decimalFormatter.format(euro3Total) );
				
				response.put("euro4",decimalFormatter.format(euro4) );
				response.put("euro4Rate",decimalFormatter.format(euro4Rate) );
				response.put("euro4Total",decimalFormatter.format(euro4Total) );
				response.put("totalEuro",decimalFormatter.format(totalEuro) );
				
				response.put("biettageGrandTotal",decimalFormatter.format(biettageGrandTotal) );
				response.put("caisseInt", decimalFormatter.format(caisseInt));
				
				
				return new ResponseEntity<>(response, HttpStatus.OK);

				} catch (Exception e) {
					System.out.println(e);
					return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

				}
				
		}
		
		
		
			
				
		
	}
	
	
	@PostMapping("/salesreportprofit")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> salesReportProfit(@RequestBody ReportBodyDetails r) {
		
		try {

			


			String date1 = r.getText1();
			String date2 = r.getText2();
			String cashier = r.getText4();
			String branch = r.getText5();
			String stand = r.getText6();
			
			String description = r.getText7();
			
			CustomQueries cq = new CustomQueries();
			
			salesDetailsProfitList = new ArrayList<>();
			salesDetailsProfitList.clear();
			
			salesDetailsProfitListTotal = new ArrayList<>();
			salesDetailsProfitListTotal.clear();
			
			
			for(String s:cq.profitSoldProductName(date1, date2, branch)) {
				
			 SalesDetailsProfit profit =	cq.getSalesProfit(date1, date2, branch, s);
				
				salesDetailsProfitList.add(profit);
				
				salesDetailsProfitListTotal.add(profit.getTotal_income());
				
//				System.out.println("=====report profit 933==========="+date1+" "+date2+" "+branch+" "+profit);
				
			}
			
			
			//CALCUATE TOTAL INCOME
			
			double total=0;
			
			for(int i=0; i < salesDetailsProfitListTotal.size(); i++) {
				
				total+= salesDetailsProfitListTotal.get(i);
				
			}
			
			//System.out.println("=====report profit 933==========="+date1+" "+date2+" "+branch+" "+total);
		
		Map<String, Object> response = new HashMap<>();
		// response.put("tutorials",
		// orderDetailsRepository.findByOrderidDetails(order));
		response.put("profit", salesDetailsProfitList);
		response.put("profit_total", total);
		
		
		return new ResponseEntity<>(response, HttpStatus.OK);

	} catch (Exception e) {
		System.out.println(e);
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
}

}
