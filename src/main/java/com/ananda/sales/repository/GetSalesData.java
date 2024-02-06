package com.ananda.sales.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ananda.sales.model.Payments;
import com.ananda.sales.model.SalesDetails;
import com.ananda.sales.model.SalesSummary;
import com.ananda.sales.model.Stock;

public class GetSalesData {

	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private final String pattern = "yyyy-MM-dd";

	private PreparedStatement pst2 = null;
	private ResultSet rs2 = null;
	private Connection con = null;

	private List<SalesSummary> depositList;

	private List<String> selectList;
	private List<Payments> paymentsList;
	
	private List<SalesDetails> salesDetailsList;
	private List<Stock> stockList;

	int count = 0;

	private int actualstock;

	private int inQty;
	private int outQty;
	private int transfer_in;
	private int transfer_out;
	private int damage;

	private double amountpayedCash = 0;
	private double amountpayedMomo = 0;
	private double amountpayedVisa = 0;
	private double amountpayedCheque = 0;
	private double amountpayedTransfer = 0;

	private String invoiceno1;
	private String orderdate1;
	private String customer1;
	private String paymenttime1;
	private double orderamount1;
	private String saller1;
	private String cashier1;

	public List<SalesSummary> getSalesData() {

		depositList = new ArrayList<>();
		selectList = new ArrayList<>();

		System.out.println("=========starting get deposit 43==========");

		connection();

		try {

			// get product list
			// pst = con.prepareStatement("select distinct pid,code,name,size,color from
			// products where businesstand ='YES' order by pid asc ");
			pst = con.prepareStatement(
					"select id,invoiceno,saller,amount,totalpayed,totalbalance,date,customer,contact from deposit_data order by id asc ");
			rs = pst.executeQuery();

			selectList.clear();
			while (rs.next()) {

				String id = rs.getString("id");
				invoiceno1 = rs.getString("invoiceno");
				String saller = rs.getString("saller");
				double amount = rs.getDouble("amount");
				double totalpayed = rs.getDouble("totalpayed");
				double balance = rs.getDouble("totalbalance");
				String date = rs.getString("date");
				String customer = rs.getString("customer");
				String contact = rs.getString("contact");

				selectList.add(invoiceno1);

				depositList.add(new SalesSummary(invoiceno1, date, customer, contact, amount, totalpayed, balance,
						"DEPOSIT", saller, "System Admin", date, "Frrom previous app", "T2000", "deborah@golden.com",""));

			}
			rs.close();
			
			
			//create dump payments
			
			

		} catch (SQLException ex) {
			System.out.println(ex);

		}

		return depositList;
	}
	
	public List<SalesDetails> getSalesDetails() {
		
		salesDetailsList = new ArrayList<>();
		
		try {

			for (String k : selectList) {
				
			
			pst = con.prepareStatement(
					"select id,orderid,orderdate,ordertime,orderdetail.pid,orderdetail.price_category,products.productname as product,products.size as size,price,qty,price*qty as amount,cashier,saller,salesstatus,paymentmethod,customer,phone,stand from orderdetail left join products on products.pid = orderdetail.pid where orderid='"+k+"' order by orderdate desc;");
			rs = pst.executeQuery();

			
			while (rs.next()) {

				String id = rs.getString("id");
				String code = rs.getString("price_category");
				String color = rs.getString("saller");
				String customer = rs.getString("customer");
				String ordertime = rs.getString("orderdate")+" 00:00:00";
				String orderid = rs.getString("orderid");
				String phone = rs.getString("phone");
				int pid = rs.getInt("pid");
				double price = rs.getDouble("price");
				String product = rs.getString("product");
				int qty = rs.getInt("qty");
				String status = rs.getString("salesstatus");
				String saller = rs.getString("saller");
				String size = rs.getString("size");
				String stand = rs.getString("stand");
				double total = rs.getDouble("amount");
				String username = rs.getString("saller");
				String paymenttime = "";
				String location = "T2000";
				String cashier = rs.getString("cashier");
				

				

//				salesDetailsList.add(new SalesDetails(orderid, ordertime, product,  code,  pid,  qty,  color,
//						 size,  price,  saller,  customer,  phone,  status,  stand,
//						 username, total, paymenttime, location, cashier));

			}
			
			
			}
			rs.close();
			
			
			//create dump payments
			
			

		} catch (SQLException ex) {
			System.out.println(ex);

		}

		
		
		return salesDetailsList;
		
	}

	public List<Payments> getPayments() {
		
		paymentsList = new ArrayList<>();

		connection();
		
		try {
			
			count=1;

			for (String k : selectList) {
				
				System.out.println("get data "+k+ " "+count);
				count ++;
				
				/*
				 * pst = con.prepareStatement(
				 * "insert into payments (orderno,orderdate,customer,paymenttime,orderamount,amountpayed ,paymentmethod,saller,cashier) values ('"
				 * +k+"','00-00-00','customer','2000-01-01 00:00:00','0','0','Cash','Saller','Cashier') "
				 * ); int i = pst.executeUpdate();
				 */
				
				 /* 
				  pst = con.prepareStatement(
				  "insert into payments (orderno,orderdate,customer,paymenttime,orderamount,amountpayed ,paymentmethod,saller,cashier) values ('"
				  +k+"','00-00-00','customer','2000-01-01 00:00:00','0','0','Visa card','Saller','Cashier') "
				  ); int j = pst.executeUpdate();
				
				 * pst = con.prepareStatement(
				 * "insert into payments (orderno,orderdate,customer,paymenttime,orderamount,amountpayed ,paymentmethod,saller,cashier) values ('"
				 * +k+"','00-00-00','customer','2000-01-01 00:00:00','0','0','Visa card','Saller','Cashier') "
				 * ); int l = pst.executeUpdate();
				 * 
				 * pst = con.prepareStatement(
				 * "insert into payments (orderno,orderdate,customer,paymenttime,orderamount,amountpayed ,paymentmethod,saller,cashier) values ('"
				 * +k+"','00-00-00','customer','2000-01-01 00:00:00','0','0','Cheque','Saller','Cashier') "
				 * ); int m = pst.executeUpdate();
				 * 
				 * pst = con.prepareStatement(
				 * "insert into payments (orderno,orderdate,customer,paymenttime,orderamount,amountpayed ,paymentmethod,saller,cashier) values ('"
				 * +k+"','00-00-00','customer','2000-01-01 00:00:00','0','0','Bank Transfer','Saller','Cashier') "
				 * ); int n = pst.executeUpdate();
				 */
				
				//System.out.println("=====save==="+i);

				//System.out.println("Select payments " + k);

				// get product list
				// pst = con.prepareStatement("select distinct pid,code,name,size,color from
				// products where businesstand ='YES' order by pid asc ");
				pst = con.prepareStatement("select id,orderno,orderdate,customer,paymenttime,orderamount,amountpayed as cash,paymentmethod,saller,cashier from payments where orderno='"+k+"' group by id; ");
								// pst.setString(1, k);
					rs = pst.executeQuery();
			 
						

			    count=1;
				while (rs.next()) {

					String id = rs.getString("id");
					String invoiceno = rs.getString("orderno");
					String orderdate = rs.getString("orderdate")+" 00:00:00";
					String customer = rs.getString("customer");
					String paymenttime = rs.getString("paymenttime");
					double orderamount = rs.getDouble("orderamount");
					double amountpayedCash = rs.getDouble("cash");
					String saller = rs.getString("saller");
					String cashier = rs.getString("cashier");
					
					
					
//					paymentsList.add(new Payments( invoiceno,  customer,  "078",  orderdate,saller,  orderamount,
//							0.0,  0.0,  amountpayedCash,  0.0,  0.0,  0.0,  0.0,
//							 amountpayedCash,  paymenttime,  "deborah@golden.com",  "NOT YET", "T2000"));
//					
//					System.out.println("=========248====selection=="+k+" orderno:"+invoiceno1+" date:"+orderdate1+" amount:"+orderamount1+" payed:"+amountpayedCash);
//					
				}
				
				
				

				/*
				 * // momo
				 * 
				 * 
				 * pst = con.prepareStatement(
				 * "select id,orderno,orderdate,customer,paymenttime,orderamount,sum(amountpayed) as momo,paymentmethod,saller,cashier from payments where paymentmethod='Mobile Money' and orderno='"
				 * +k+"' group by id;" );
				 * 
				 * 
				 * rs = pst.executeQuery();
				 * 
				 * 
				 * while (rs.next()) {
				 * 
				 * String id = rs.getString("id"); invoiceno1 = rs.getString("orderno");
				 * orderdate1 = rs.getString("orderdate"); customer1 = rs.getString("customer");
				 * paymenttime1 = rs.getString("paymenttime"); orderamount1 =
				 * rs.getDouble("orderamount"); amountpayedMomo = rs.getDouble("momo"); saller1
				 * = rs.getString("saller"); cashier1 = rs.getString("cashier");
				 * 
				 * } // rs.close();
				 * 
				 * // visa
				 * 
				 * pst = con.prepareStatement(
				 * "sselect id,orderno,orderdate,customer,paymenttime,orderamount,sum(amountpayed) as visa,paymentmethod,saller,cashier from payments where paymentmethod='Visa card' and orderno='"
				 * +k+"' group by id;" );
				 * 
				 * 
				 * rs = pst.executeQuery();
				 * 
				 * 
				 * if (rs.next()) {
				 * 
				 * String id = rs.getString("id"); invoiceno1 = rs.getString("orderno");
				 * orderdate1 = rs.getString("orderdate"); customer1 = rs.getString("customer");
				 * paymenttime1 = rs.getString("paymenttime"); orderamount1 =
				 * rs.getDouble("orderamount"); amountpayedVisa = rs.getDouble("visa"); saller1
				 * = rs.getString("saller"); cashier1 = rs.getString("cashier");
				 * 
				 * } // rs.close();
				 * 
				 * //cheque
				 * 
				 * pst = con.prepareStatement(
				 * "select id,orderno,orderdate,customer,paymenttime,orderamount,sum(amountpayed) as cheque,paymentmethod,saller,cashier from payments where paymentmethod='Cheque' and orderno='"
				 * +k+"' group by id;" );
				 * 
				 * 
				 * rs = pst.executeQuery();
				 * 
				 * 
				 * if (rs.next()) {
				 * 
				 * String id = rs.getString("id"); invoiceno1 = rs.getString("orderno");
				 * orderdate1 = rs.getString("orderdate"); customer1 = rs.getString("customer");
				 * paymenttime1 = rs.getString("paymenttime"); orderamount1 =
				 * rs.getDouble("orderamount"); amountpayedCheque = rs.getDouble("cheque");
				 * saller1 = rs.getString("saller"); cashier1 = rs.getString("cashier");
				 * 
				 * } // rs.close();
				 * 
				 * 
				 * 
				 * //transfer
				 * 
				 * pst = con.prepareStatement(
				 * "select id,orderno,orderdate,customer,paymenttime,orderamount,sum(amountpayed) as transfer,paymentmethod,saller,cashier from payments where paymentmethod='Bank Transfer' and orderno='"
				 * +k+"' group by id;" );
				 * 
				 * rs = pst.executeQuery();
				 * 
				 * if (rs.next()) {
				 * 
				 * String id = rs.getString("id"); invoiceno1 = rs.getString("orderno");
				 * orderdate1 = rs.getString("orderdate"); customer1 = rs.getString("customer");
				 * paymenttime1 = rs.getString("paymenttime"); orderamount1 =
				 * rs.getDouble("orderamount"); amountpayedTransfer = rs.getDouble("transfer");
				 * saller1 = rs.getString("saller"); cashier1 = rs.getString("cashier");
				 * 
				 * }
				 */
				 
				//System.out.println("ORDER:" + k + " CASH:" + amountpayedCash + " momo:" + amountpayedMomo + " visa:"
					//s	+ amountpayedVisa + " checque:" + amountpayedCheque + " transfer:" + amountpayedTransfer);

				
			}
			count++;
			
		} catch (SQLException ex) {
			System.out.println(ex);

		}

			///rs.close();

		return paymentsList;

	}
	
	public List<Stock> getStockout() {
		
		stockList = new ArrayList<>();
		
		connection();

		try {

			pst = con.prepareStatement(
					"SELECT id,stand,date1,stock.pid,stock.product as product,qty_in,stockkeeper,stock.code as code,stock.size as size,qty_out,products.color as color FROM stock left join products on products.pid= stock.pid where qty_out IS NOT NULL and date1 between '2021-11-01' and '2022-03-30' order by id desc;");
			rs = pst.executeQuery();

			stockList.clear();
			while (rs.next()) {

				String id = rs.getString("id");
				String stand = rs.getString("stand");
				String date = rs.getString("date1");
				int pid = rs.getInt("pid");
				String product = rs.getString("product");
				String color = rs.getString("color");
				int qty_in = rs.getInt("qty_in");
				String stockkeeper = rs.getString("stockkeeper");
				String code = rs.getString("code");
				String size = rs.getString("size");
				int qty_out = rs.getInt("qty_out");
				String location = "T2000";
				
				
				
				
                stockList.add(new Stock( stand, date, pid, product,qty_in, stockkeeper,
            			code, size, qty_out, qty_in, qty_in,
            			qty_in, "", "", "Legacy Data", "",
            			"", color, "OUT","",qty_in,"Pending",location,0));

		
			
			
			}
			rs.close();
			
			
			//create dump payments
			
			

		} catch (SQLException ex) {
			System.out.println(ex);

		}

		return stockList;
		
		
		
	}

	public void connection() {
		try {
			con = com.ananda.sales.repository.DBConnection.pmartConnection();
		} catch (Exception ex) {
			System.out.println(ex);

		}
	}

}
