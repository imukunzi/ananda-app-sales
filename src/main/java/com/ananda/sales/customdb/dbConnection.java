package com.ananda.sales.customdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
	
	public static Connection connection() throws Exception {

        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/db_ananda_sales_yes_phone?allowPublicKeyRetrieval=true&useSSL=false";
           // String url = "jdbc:mysql://192.168.1.11:3306/db_isimbi_mart_v2";
            con = DriverManager.getConnection(url, "rudram", "rudram1008***");
        } catch (ClassNotFoundException ex) {
            throw new Exception("Driver not found !");
        } catch (SQLException ex) {
            System.out.println(ex);
            
        }

        //server url
        return con;

    }

}
