package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

	Page<Stock> findByCodeContaining(String title, Pageable pageable);
	List<Stock> findByCodeContaining(String title, Sort sort);
	
	Page<Stock> findByDate1Containing(String title, Pageable pageable);
	List<Stock> findByDate1Containing(String title, Sort sort);
	
	@Transactional
	@Modifying
	@Query(value = "from stock where  date1 between ?1 and ?2 and stand=?3 and stock_type =?4 order by id desc")
	List<Stock> findByStockandDate(String date1,String date2,String stock,String type);
		
	@Transactional
	@Modifying
	@Query(value = "from stock where date1 between ?1 and ?2 and stock_type=?3 order by id desc")
	List<Stock> findStockInByDate(String date1, String date2,String stock_type);
	
	@Transactional
	@Modifying
	@Query(value = "from stock where date1 between ?1 and ?2 and stock_type=?3 and confirmation='Confirmed' and location=?4 order by id desc")
	List<Stock> findStockInByDateInspection(String date1, String date2,String stock_type,String location);
	
	@Transactional
	@Query(value = "select count(qty_in) from stock where confirmation='Confirmed' and date1 between ?1 and ?2 and stock_type=?3 and location=?4")
	Integer findStockInByDateInspectionSum(String date1, String date2,String stock_type,String location);
	
	@Transactional
	@Modifying
	@Query(value = "from stock where date1 between ?1 and ?2 and stock_type=?3 and confirmation='Confirmed' and location=?4 and code=?5 order by id desc")
	List<Stock> findStockInByDateInspectionSearch(String date1, String date2,String stock_type,String location,String code);
	
	@Transactional
	@Query(value = "select count(qty_in) from stock where confirmation='Confirmed' and date1 between ?1 and ?2 and stock_type=?3 and location=?4 and code=?5")
	Integer findStockInByDateInspectionSumSearch(String date1, String date2,String stock_type,String location,String code);
	
	@Transactional
	//@Modifying
	@Query(value = "select sum(qty_out) from stock where confirmation='Confirmed' and date1 between ?1 and ?2 and stock_type=?3 and location=?4")
	Integer findStockOutByDateInspectionSum(String date1, String date2,String stock_type,String location);
	
	@Transactional
	@Modifying
	@Query(value = "from stock where  code like %?1% and stock_type=?2 order by id desc")
	List<Stock> findStockInByDateSearch(String searchText,String stock_type);
	
	@Transactional
	@Modifying
	@Query(value = "from stock where  product like %?1% and stock_type=?2 order by id desc")
	List<Stock> findStockInByDateSearchwithname(String searchText,String stock_type);
			
		
	@Transactional
	@Modifying
	@Query(value = "update stock set confirmation='Confirmed' where id=?1")
	void updatedStockConfirmed(long id);
	
	@Transactional
	@Query(value = "select confirmation from  stock where id=?1")
	String checkIfConfirmed(long id);
	
	
	@Transactional
	@Query(value = "select sum(qty_out) from  stock where pid=?1 and date1=?2 and stand=?3")
	Integer getTodayStockOut(int pid,String date,String stock);
	
	@Transactional
	@Query(value = "select max(price_value) from  stock where pid=?1 and date1=?2 and stand=?3 and stock_type='OUT'")
	Integer getTodayStockOutPrice(int pid,String date,String stock);
	
}

