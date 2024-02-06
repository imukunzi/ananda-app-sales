package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import com.ananda.sales.model.StockLevel;

public interface StockLevelRepository extends JpaRepository<StockLevel, Long> {
	
//	Page<StockLevel> findBycodeContaining(String title, Pageable pageable);
//	List<StockLevel> findBycodeContaining(String title, Sort sort);
	
	Page<StockLevel> findBynameContaining(String title, Pageable pageable);
	List<StockLevel> findBynameContaining(String title, Sort sort);
	
	Page<StockLevel> findBystocknameContaining(String title, Pageable pageable);
	List<StockLevel> findBystocknameContaining(String title, Sort sort);
		
	@Transactional
	@Query(value = "select count(*) FROM stock_level where  pid=?1 and stockname=?2 ")
	Integer checkIfProductExist(int pid,String stockName);
	
	@Transactional
	@Query(value = "select max(current_stock_qty) FROM stock_level where  pid=?1 and stockname=?2")
	Integer getAvailableQty(int pid,String stockName);
	
	@Transactional
	@Modifying
	@Query(value = "update stock_level set current_stock_qty=?1,status=?2 where  pid=?3 and stockname=?4 ")
	void updateStockLevel(int current_qty,String status,int pid,String stockName);
	

	
	@Transactional
	@Query(value = "select max(current_stock_qty) FROM stock_level where  pid=?1 and stockname=?2 ")
	Integer getCurrentStockLevel(int pid,String stockName);
	
	@Transactional
	@Query(value = "select max(current_stock_qty) FROM stock_level where  name=?1 and stockname=?2 ")
	Integer getCurrentStockLevelProduct(String product,String stockName);
	
	@Transactional
	@Query(value = "FROM stock_level where  stockname=?1 and description=?2 ")
	List<StockLevel> getStockLevelByStockByDescription(String stock,String descriptin);

}
