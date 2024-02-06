package com.ananda.sales.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ananda.sales.model.StockLegacy;
import com.ananda.sales.model.StockLevel;
import com.ananda.sales.model.StockStandLevel;

public class GetStockData {
	
	private PreparedStatement pst = null;
    private ResultSet rs = null;
    private final String pattern = "yyyy-MM-dd";

    private PreparedStatement pst2 = null;
    private ResultSet rs2 = null;
    private Connection con=null;

    private List<StockLegacy> stocStandLevelList;
  
    private List<StockStandLevel> selectList;
    
    int count=0;
    
    private int actualstock;
    
    private int inQty;
    private int outQty;
    private int transfer_in;
    private int transfer_out;
    private int damage;
	
	public void getStockLevel(String productStand,String productCode) {
		
		stocStandLevelList= new ArrayList<>();
		selectList= new ArrayList<>();
		
		System.out.println("=========starting get stock 37=========="+productCode+" "+productStand);
		
		connection();
		
		try {

            //get product list
           // pst = con.prepareStatement("select distinct pid,code,name,size,color from products where businesstand ='YES' order by pid asc ");
            pst = con.prepareStatement("select distinct pid,code,name,size,color from products where code ='"+productCode+"' order by pid asc ");
            rs = pst.executeQuery();

            selectList.clear();
            while (rs.next()) {

                String pid = rs.getString("pid");
                String code = rs.getString("code");
                String productName = rs.getString("name");
                String size = rs.getString("size");
                String color = rs.getString("color");
               

                selectList.add(new StockStandLevel(pid, code, productName, size,color));
               
               // System.out.println("=======Products======="+productName+" code "+code);
                

            }
            rs.close();
           
           
            count=1;
            stocStandLevelList.clear();
            System.out.println("=======start updating=======");
            for(StockStandLevel product: selectList){
               
                           
             String pid = product.getPid();
             String code = product.getCode();
             String productName = product.getProductName();
             String size = product.getSize();
             String color = product.getColor();
             
            
               
              //stock in  
            pst = con.prepareStatement("SELECT id,stand,date1,pid,product,sum(qty_in) as inqty FROM stock_legacy_data where stand = '" + productStand + "' and pid='"+pid+"' group by id");
            rs = pst.executeQuery();
            
            if(rs.next()) {
           
             inQty = rs.getInt("inqty");
            
            }
           
           
            //stock out
            pst = con.prepareStatement("SELECT id,stand,date1,pid,product,sum(qty_out) as outqty FROM stock_legacy_data where stand = '" +productStand+ "' and pid='"+pid+"' group by id");
            rs = pst.executeQuery();
            if(rs.next()) {
           
             outQty = rs.getInt("outqty");
            
            }
           
            //transfer in
            pst = con.prepareStatement("SELECT id,stand_source,date1,pid,product,sum(qty_transfer_in) as transfer_in_qty FROM stock_legacy_data where  pid='"+pid+"' and stand_destination='"+productStand+"' group by id");
            rs = pst.executeQuery();
            
            if(rs.next()) {
           
            transfer_in = rs.getInt("transfer_in_qty");
            
            }
           
            //transfer out
            pst = con.prepareStatement("SELECT id,stand_destination,date1,pid,product,sum(qty_transfer_in) as transfer_out_qty FROM stock_legacy_data where  pid='"+pid+"' and stand_source='"+productStand+"' group by id");
            rs = pst.executeQuery();
           
            if(rs.next()) {
           
             transfer_out = rs.getInt("transfer_out_qty");
            }
           
//            //damange
            pst = con.prepareStatement("select id,stand,date1,code,product,sum(qty_damage) as damage_qty,description,comments,stockkeeper from stock_legacy_data where pid='"+pid+"' group by id; ");
            rs = pst.executeQuery();
            
            if(rs.next()) {
           
            damage = rs.getInt("damage_qty");
            
            }
           
             actualstock = inQty-outQty+transfer_in-transfer_out+damage;
           
           
            stocStandLevelList.add(new StockLegacy(productStand,pid, code, productName,size, inQty, outQty, transfer_in, transfer_out,damage, actualstock,color));
                
                count++;
                System.out.println("Actual stock: "+count+"Stand"+productStand+"=product="+productName+" pid "+pid+" code:"+code+" qty"+actualstock);
            }
            //System.out.println(stocStandLevelList);
            
            
            //update stocklevel
            count=0;
            for(StockLegacy s:stocStandLevelList) {
            	
            	String sql_save = "update stock_level set current_stock_qty=?,updated_from_regacy=? where pid=? AND updated_from_regacy=?;";
                pst = con.prepareStatement(sql_save);

                int col = 1;
                pst.setInt(col++, s.getActualStock());
                pst.setString(col++, "Updated");
                pst.setString(col++, s.getPid());
                pst.setString(col++, "Pending");
                

                int i = pst.executeUpdate();
                count++;
                
                System.out.println("Updated: "+count+" "+s.getPid());
            	
            }
            
            System.out.println("Update completed: ");
           

        } catch (SQLException ex) {
            System.out.println(ex);
            
        }
		
		
	}
	
	public void connection() {
        try {
            con = com.ananda.sales.repository.DBConnection.pmartConnection();
        } catch (Exception ex) {
            System.out.println(ex);
            
        }
    }

}
