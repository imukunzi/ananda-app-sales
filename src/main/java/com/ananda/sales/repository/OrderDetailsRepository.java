package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.OrderDetails;
 

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

	
	
	
	
	//@Query(value = "SELECT sum(total)FROM orderdetail where orderid=?1")
	//Double findByOrderid(String orderid);
	
	/*
	 * @Transactional
	 * 
	 * @Query(value = "FROM orderdetail where orderid=?1") OrderDetails
	 * findByOrderidTotal(String orderid);
	 */
	
	//@Transactional
	//@Modifying
	//@Query(value = "update orderdetail set comment=?1 where orderid=?2")
	//void updateOrderComment(String comment,String orderid);
	
	
	
	
	
	
	
	//ANALYSIS
	/*
	 * @Transactional
	 * 
	 * @Query(value = "SELECT distinct orderid FROM orderdetail where orderid=?1")
	 * String analyseOrder(String orderid);
	 * 
	 * @Transactional
	 * 
	 * @Query(value = "SELECT count(id) FROM orderdetail where orderid=?1") int
	 * analyseOrderItems(String orderid);
	 */
	
		
}
