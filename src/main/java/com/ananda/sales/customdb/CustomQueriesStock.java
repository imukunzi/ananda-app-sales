package com.ananda.sales.customdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ananda.sales.model.Stock;
import com.ananda.sales.model.User_roles_list;

public class CustomQueriesStock {

	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private final String pattern = "yyyy-MM-dd";

	private Connection con = null;

	
	private List<Stock> stockList;

	// adding new user roles

	
	// get users and roles

	public List<Stock> getUsersAndRoles() {

		connection();

		try {
			// get role_id

			pst = con.prepareStatement("select id,code,comment,date1,description,pid,product,qty_damage,qty_in,qty_out,qty_transfer_in,qty_transfer_out,size,stand,stand_destination,stand_source,stockkeeper,transfer_type,color,stock_type,stockentryid,returned,confirmation,location,branch_manager_approval,branch_manager_approval_date,branch_manager_name,business_operation_manager_approval,business_operation_manager_approval_date,business_operation_manager_name,qty_requested,request_status,stand_receiption_confirmation,stand_receiption_confirmation_date,stand_supply_request_date,stand_supply_request_officer,stockkeeper_approval,stockkeeper_approval_date,stockkeeper_approval_qty,stockkeeper_name from stock;");
			rs = pst.executeQuery();

			stockList = new ArrayList<>();
			stockList.clear();
			
			
			while (rs.next()) {

				int user_id = rs.getInt("user_id");
				int role_id = rs.getInt("role_id");
				String name = rs.getString("name");
				String fistname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				
				//userRolesList.add(new User_roles_list(id,user_id, role_id, name, fistname, lastname));
				
				 
				 

			}
			
			rs.close();

		} catch (SQLException ex) {
			System.out.println(ex);
		}

		return stockList;

	}

	
	 

	public void connection() {
		try {
			con = com.ananda.sales.customdb.dbConnection.connection();
		} catch (Exception ex) {
			System.out.println(ex);

		}
	}

}
