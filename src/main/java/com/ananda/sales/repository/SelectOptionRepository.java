package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.SelectOptions;

public interface SelectOptionRepository extends JpaRepository<SelectOptions, Long> {
	
	Page<SelectOptions> findByCategoryContaining(String title, Pageable pageable);
	List<SelectOptions> findByCategoryContaining(String title, Sort sort);
	List<SelectOptions> findByCategoryContaining(String category);
	
	@Modifying
	@Query("from select_options where category ='SALLERS' and other=?1 order by value asc")
	List<SelectOptions> findSallers(String other);
	
//	@Modifying
//	@Query("from select_options where category ='SALLERS' and other=?1 order by value asc")
//	List<SelectOptions> findSallersByBranch(String other);
	
	@Modifying
	@Query("from select_options where category='SALLERS' and other=?1")
	List<SelectOptions> findSallersByBranch(String other);
	
	
	@Query("from select_options where category ='SALLERS' and value=?1 order by value asc")
	SelectOptions findSallerDetails(String saller);
	
	@Modifying
	@Query("from select_options where category ='CASHIER' and other=?1 order by value asc")
	List<SelectOptions> findCashierByBranch(String other); 
	
	@Transactional
	@Query("from select_options where category ='SALLERS' and value=?1")
	SelectOptions findByValue(String value);
	
	
	@Modifying
	@Query("from select_options where category ='STOCK-NAME' and other=?1 order by value asc")
	List<SelectOptions> findStandByBranch(String other);
	
	@Query(value = "select value from select_options where category ='COMMISSION' ")
	double getCommissionPercentage(int pid);
	 

}
