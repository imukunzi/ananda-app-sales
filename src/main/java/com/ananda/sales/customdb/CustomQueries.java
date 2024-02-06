package com.ananda.sales.customdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.ananda.sales.model.SalesDetails;
import com.ananda.sales.model.SalesDetailsProfit;
import com.ananda.sales.model.SallersReport;
import com.ananda.sales.model.SellersReportPerformance;
import com.ananda.sales.model.User_roles_list;

public class CustomQueries {

	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private final String pattern = "yyyy-MM-dd";

	private Connection con = null;

	private int addNewUserRole;
	private int removeUserRole;
	private String role_id;
	private List<User_roles_list> userRolesList;
	private List<SellersReportPerformance> sallersReportPerformanceList;
	
	private List<SalesDetails> salesDetailsList;
	private List<String> soldItemList;
	private DecimalFormat decimalFormatter = new DecimalFormat("#,###.00");
	
	
	private String code="";
	private String product="";
	private double purchase=0;
	private double sales=0;
	private int qty=0;
	private double total_purchase=0;
	private double total_total_sales=0;
	private double total_income=0;
	
	private SalesDetailsProfit s;
	
	

	// adding new user roles

	public Integer addNewUserRole(int user_id, String role) {

		connection();

		try {
			// get role_id

			pst = con.prepareStatement("select id from roles where name='" + role + "' ");
			rs = pst.executeQuery();

			while (rs.next()) {

				role_id = rs.getString("id");
				// System.out.println("====role id custom 36==="+role_id);

			}

			if (role_id != null) {

				pst = con.prepareStatement("insert into user_roles (user_id,role_id) values (?,?) ");

				int col = 1;
				pst.setInt(col++, user_id);
				pst.setString(col++, role_id);

				addNewUserRole = pst.executeUpdate();

			}
			pst.close();

		} catch (SQLException ex) {
			System.out.println(ex);
		}

		return addNewUserRole;

	}

	public Integer removeRole(int user_id, Integer role_id) {

		connection();

		try {
			// get role_id
			/*
			 * pst = con.prepareStatement( "select id from roles where name='"+role+"' ");
			 * rs = pst.executeQuery();
			 * 
			 * 
			 * while (rs.next()) {
			 * 
			 * role_id = rs.getString("id");
			 * //System.out.println("====role id custom 36==="+role_id);
			 * 
			 * }
			 */

			if (role_id != null) {

				pst = con.prepareStatement("delete from user_roles where user_id=? and role_id=? ");

				int col = 1;
				pst.setInt(col++, user_id);
				pst.setInt(col++, role_id);

				removeUserRole = pst.executeUpdate();

			}
			pst.close();

		} catch (SQLException ex) {
			System.out.println(ex);
		}

		return removeUserRole;

	}

	// get users and roles

	public List<User_roles_list> getUsersAndRoles() {

		connection();

		try {
			// get role_id

			pst = con.prepareStatement(
					"select user_id,role_id,roles.name,users.firstname,users.lastname from user_roles left join users on users.id=user_id left join roles on roles.id=user_roles.role_id order by users.id");
			rs = pst.executeQuery();

			userRolesList = new ArrayList<>();
			userRolesList.clear();

			int id = 1;

			while (rs.next()) {

				int user_id = rs.getInt("user_id");
				int role_id = rs.getInt("role_id");
				String name = rs.getString("name");
				String fistname = rs.getString("firstname");
				String lastname = rs.getString("lastname");

				userRolesList.add(new User_roles_list(id, user_id, role_id, name, fistname, lastname));

				id++;

			}

			rs.close();

		} catch (SQLException ex) {
			System.out.println(ex);
		}

		return userRolesList;

	}

	public List<User_roles_list> getUsersAndRolesSearch(String searchText) {

		connection();

		try {
			// get role_id

			pst = con.prepareStatement(
					"select user_id,role_id,roles.name,users.firstname,users.lastname from user_roles left join users on users.id=user_id left join roles on roles.id=user_roles.role_id where roles.name='"
							+ searchText + "' order by users.id");
			rs = pst.executeQuery();

			userRolesList = new ArrayList<>();
			userRolesList.clear();

			int id = 1;

			while (rs.next()) {

				int user_id = rs.getInt("user_id");
				int role_id = rs.getInt("role_id");
				String name = rs.getString("name");
				String fistname = rs.getString("firstname");
				String lastname = rs.getString("lastname");

				userRolesList.add(new User_roles_list(id, user_id, role_id, name, fistname, lastname));

				id++;

			}

			rs.close();

		} catch (SQLException ex) {
			System.out.println(ex);
		}

		return userRolesList;

	}

	public List<SellersReportPerformance> getSallersPerformanceCommission(String date1, String date2, String saller,String description) {

		connection();

		try {
			// get role_id

			pst = con.prepareStatement(
					"select product,code,pid,price,qty,saller,ordertime from salesdetail where ordertime between '" + date1
							+ "' and '" + date2 + "' and saller='" + saller + "' and description='"+description+"';");
			rs = pst.executeQuery();

			sallersReportPerformanceList = new ArrayList<>();
			sallersReportPerformanceList.clear();

			while (rs.next()) {

				String item = rs.getString("product");
				String code = rs.getString("code");
				int pid = rs.getInt("pid");
				double price = rs.getInt("price");
				int qty = rs.getInt("qty");
				double supply_price = 0;
				double percentage = 0;
				String totalCommission="";
				 
				
				sallersReportPerformanceList
						.add(new SellersReportPerformance(item, code,pid, price, qty, supply_price, percentage,totalCommission,description));

			}

			rs.close();

		} catch (SQLException ex) {
			System.out.println(ex);
		}

		return sallersReportPerformanceList;

	}
	
	
	public List<SalesDetails> getSallersPerformanceSoldItems(String date1, String date2, String saller1,String description) {

		connection();

		try {
			// get soled items
			
			pst = con.prepareStatement("select distinct code as code FROM salesdetail where saller='"+saller1+"' and ordertime between '"+date1+"' and '"+date2+"' and description='"+description+"' and salesstatus!='TEMPORARY' order by code asc;");
			rs = pst.executeQuery();

			soldItemList = new ArrayList<>();
			soldItemList.clear();
			
			while (rs.next()) {

				String code = rs.getString("code");
				soldItemList.add(code);
				//System.out.println("========custom query 259 items====="+code);
				
			}
			
			//System.out.println("========custom query 259 items====="+soldItemList);
			
			
			/////////////////////////////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////////////////////////////
			
			salesDetailsList = new ArrayList<>();
			salesDetailsList.clear();
			
			for(String code1:soldItemList) {
				
				pst = con.prepareStatement("select ANY_VALUE(product) as product,ANY_VALUE(description) as description,code,ANY_VALUE(pid) as pid,ANY_VALUE(price) as price,sum(ANY_VALUE(qty)) as qty,saller,ANY_VALUE(ordertime) as saller,SUM(ANY_VALUE((qty*price))) as total from salesdetail where ordertime between '"+date1+"' and '"+date2+"' and saller='"+saller1+"' and code='"+code1+"'; ;");
				rs = pst.executeQuery();

				

				while (rs.next()) {

					String product = rs.getString("product");
					String code = rs.getString("code");
					int pid = rs.getInt("pid");
					double price = rs.getInt("price");
					int qty = rs.getInt("qty");
					double supply_price = 0;
					double percentage = 0;
					String totalCommission="";
					String orderid = "";
					String orderTime = "";
//					String .product = = rs.getString("product");
//					String code  = rs.getString("code");
//					int pid = 0;
//					int qty = rs.getInt("qty");
					String color =  "";
					String size =  "";
//					String price =  rs.getInt("price");
					String saller =  rs.getString("saller");
					String customer = "";
					String phone = "";
					String salesStatus = "";
					String stand = "";
					String username = "";
					double total= rs.getDouble("total") ;
					String paymentTime="";
					String location ="";
					String cashier = "";
					double min_price=0;
					String authorization_status="";
					String authorization_by="";
					double product_price=0;
					String description1 =  rs.getString("description");
					
					SalesDetails s =   new SalesDetails(  orderid,   orderTime,   product,   code,   pid,   qty,   color,
							  size,   price,   saller,   customer,   phone,   salesStatus,   stand,
							  username,  total,  paymentTime,  location,  cashier,  min_price,  authorization_status,  authorization_by,  product_price,description1);

					salesDetailsList.add(s);
					
					//System.out.println("========custom query 293 qty====="+qty);

				}
				
			}
			

			

			rs.close();

		} catch (SQLException ex) {
			System.out.println(ex);
		}

		return salesDetailsList;

	}
	
	///profit report
	
		public List<String> profitSoldProductName(String date1,String date2,String branch) {
			
			connection();
			
			try {
				pst = con.prepareStatement("select distinct code as code FROM salesdetail where  ordertime between '"+date1+"' and '"+date2+"' and location='"+branch+"' and salesstatus!='TEMPORARY' order by code asc;");
				
				rs = pst.executeQuery();

				soldItemList = new ArrayList<>();
				soldItemList.clear();
				
				while (rs.next()) {

					String code1 = rs.getString("code");
					soldItemList.add(code1);
					
					//System.out.println("=====report profit 933==========="+date1+" "+date2+" "+branch+" "+code);
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return soldItemList;
			
		}
		
  public SalesDetailsProfit getSalesProfit(String date1,String date2,String branch,String code) {
	  
	  
	  connection();
		
		try {
			
			//get product name
			pst = con.prepareStatement("select  product as product FROM salesdetail where  ordertime between '"+date1+"' and '"+date2+"' and code='"+code+"' and location='"+branch+"' and salesstatus!='TEMPORARY' order by product asc limit 1;");
			rs = pst.executeQuery();

			while (rs.next()) {

				 product = rs.getString("product");
				
			}
			
			//get product purchase price
			pst = con.prepareStatement("select  suply_price as suply_price FROM products where  code='"+code+"' limit 1;");
			rs = pst.executeQuery();

			while (rs.next()) {

				 purchase = rs.getDouble("suply_price");
				
			}
			
			//get product sale price
			pst = con.prepareStatement("select  max_price as max_price FROM products where  code='"+code+"' limit 1;");
			rs = pst.executeQuery();

			while (rs.next()) {

				 sales = rs.getDouble("max_price");
				
			}
			
			//get product qty
			pst = con.prepareStatement("select  sum(qty) as qty FROM salesdetail where  ordertime between '"+date1+"' and '"+date2+"' and code='"+code+"' and location='"+branch+"' and salesstatus!='TEMPORARY' ;");
			rs = pst.executeQuery();

			while (rs.next()) {

				 qty = rs.getInt("qty");
				
			}
			
			total_purchase=purchase*qty;
			total_total_sales=sales*qty;
			
			total_income=total_total_sales-total_purchase;
			
			 s= new	SalesDetailsProfit(0,  code,  product,  purchase,  sales,  qty,  total_purchase,total_total_sales, total_income);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	  
	  return s;
	  
  }

	public void connection() {
		try {
			con = com.ananda.sales.customdb.dbConnection.connection();
		} catch (Exception ex) {
			System.out.println(ex);

		}
	}
	
	
	
	

}
