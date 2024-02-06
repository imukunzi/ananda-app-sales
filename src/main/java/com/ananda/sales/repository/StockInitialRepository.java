package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.StockInitial;

public interface StockInitialRepository extends JpaRepository<StockInitial, Long> {
	
	@Transactional
	@Query(value = "select count(id) from stock_initial where pid=?1 and date=?2")
	Integer checkIfStockInitialExist(int pid,String date);
	
	@Transactional
	@Query(value = "from stock_initial where date=?1 and stockname=?2 and description=?3")
	List<StockInitial> getTodayStockInitial(String date,String stock,String description);
	
	@Transactional
	@Query(value = "from stock_initial where date=?1 and stockname=?2 and name like %?3%")
	List<StockInitial> getTodayStockInitialSearch(String date,String stock,String searchText);
	
	@Transactional
	@Modifying
	@Query(value = "update stock_initial set qty_out=?1,price_out=?2 where pid=?3 and date=?4 and stockname=?5")
	Integer updateStockInitialQtyOut(int qty_out,double price_out,int pid,String date,String stock);
	

}
