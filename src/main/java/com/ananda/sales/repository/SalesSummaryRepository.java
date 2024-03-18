package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.SalesSummary;

public interface SalesSummaryRepository extends JpaRepository<SalesSummary, Long> {

	@Query("from salessummary where orderid=?1")
	SalesSummary findByorderidContaining(String order);
	
	@Modifying
	@Query("from salessummary where orderid like %?1% and status !='PAYED'")
	List<SalesSummary> searchOrder(String order);
	
	@Modifying
	@Query("from salessummary where customer like %?1% and status !='pending' order by id desc")
	List<SalesSummary> searchSales(String order);
	
	@Modifying
	@Query("from salessummary where phone like %?1% and status !='pending' order by id desc")
	List<SalesSummary> searchSalesWithPhone(String order);
	
	@Query("from salessummary where ordertime between ?1 and ?2 and status=?3 order by id desc")
	List<SalesSummary> findByordertime(String date1,String date2,String status);
	
	@Query("from salessummary where ordertime between ?1 and ?2 and status=?3 and location=?4 order by id desc")
	List<SalesSummary> findByorderlocation(String date1,String date2,String status,String location);
	
	@Modifying
	@Query("from salessummary where paymenttime between ?1 and ?2 and status!='Pending' order by id desc")
	List<SalesSummary> findSalesBypaymenttime(String date1,String date2,String status);
	
	@Modifying
	@Query("from salessummary where paymenttime between ?1 and ?2 and status='Deposit' order by id desc")
	List<SalesSummary> findDepositBypaymenttime(String date1,String date2,String status);
	
	@Modifying
	@Query("from salessummary where paymenttime between ?1 and ?2 and cashier=?3 and status!='Pending' order by id desc ")
	List<SalesSummary> findSalesByCashier(String date1,String date2,String cashier,String status);
	
	@Modifying
	@Query("from salessummary where paymenttime between ?1 and ?2 and cashier=?3 and status='Deposit' order by id desc ")
	List<SalesSummary> findDepositByCashier(String date1,String date2,String cashier,String status);
	
	@Modifying
	@Query("from salessummary where paymenttime between ?1 and ?2 and location=?3 and status='Deposit' order by id desc ")
	List<SalesSummary> findDepositByLocation(String date1,String date2,String location,String status);
	
	@Transactional
	@Query("select sum(amount-amount_payed) from salessummary where paymenttime between ?1 and ?2 and location=?3 and status='Deposit' order by id desc ")
	Double findDepositByLocationSum(String date1,String date2,String location,String status);
	
	@Transactional
	@Query("select count(id) from salessummary where paymenttime between ?1 and ?2 and location=?3 and status='Deposit' order by id desc ")
	Integer findDepositByLocationCount(String date1,String date2,String location,String status);
	
	@Transactional
	@Modifying
	@Query(value = "update salessummary set amount_payed=?1, status=?2,cashier=?3,paymenttime=?4 where orderid=?5")
	void updateSalesSummary(double amount,String status,String cashier,String paymenttime,String orderid);
	
	@Transactional
	@Modifying
	@Query(value = "update salessummary set amount=?1 where orderid=?2 and ordertime=?3")
	void updateSalesSummaryAmount(double amount,String orderid,String orderTime);
	
	@Transactional
	@Modifying
	@Query(value = "update salessummary set amount=?1,ordertime=?2,status='Pending' where orderid=?3")
	void updateSalesSummaryAmount2(double amount,String ordertime,String orderid);
	
	@Transactional
	@Modifying
	@Query(value = "update salessummary set cashier=?1 where orderid=?2")
	void updateSalesDetails(String cashier,String orderid);
	
	@Transactional
	@Modifying
	@Query(value = "update salessummary set comment=?1 where orderid=?2")
	Integer updateSalesSummaryComment(String comment,String orderid);
	
	@Transactional
	@Modifying
	@Query(value = "update salessummary set other=?1 where orderid=?2")
	Integer updateSalesSummaryCommentOther(String comment,String orderid);
	
		
	@Query("from salessummary where orderid=?1")
	SalesSummary findBycommentContaining(String comment);
	
	@Transactional
	@Query("select count(id) as count from salessummary where orderid=?1")
	int  checkIfSalesSummaryExist(String order);
	
	
	//////////////////////  report ///////////////////////////////
	
	@Transactional
	@Query(value = "select sum(amount) from salessummary where  ordertime between ?1 and ?2 and cashier=?3 and status !='Pending'")
	Double reportSales_totalSales(String time1,String time2,String cashier); 
	
	@Transactional
	@Query(value = "select sum(amount) from salessummary where  ordertime between ?1 and ?2 and cashier=?3 and status !='Pending'")
	Double reportSales_newSales(String time1,String time2,String cashier); 
	
		
	@Transactional
	@Query(value = "select sum(amount_payed) from salessummary where  ordertime between ?1 and ?2 and cashier=?3 and status='DEPOSIT'")
	Double reportSales_salesDeposit(String time1,String time2,String cashier); 
	
	
	
	// sales by saller
	@Transactional
	@Query("select distinct saller from salessummary where ordertime between ?1 and ?2 and location=?3 and status!='Pending' ")
	List<String> findSalesBySallerList(String date1, String date2, String branch);
	
	//get sales summary
	@Transactional
	@Query("select amount_payed from salessummary where orderid=?1 ")
	Double findSalesSummaryTotalPaid(String orderid);
	
	@Transactional
	@Modifying
	@Query(value = "delete from salessummary where orderid=?1")
	int deleteOrder(String orderid);
	
	
////////////////////correcting possible saved duplicated payment//////////////////////////////
@Transactional
@Query(value = "select count(id) as count from salessummary where ordertime =?1 and orderid=?2 ")
int  checkSavedSalesSummaryDuplication(String date1,String orderno);

@Transactional
@Query(value = "select id from salessummary where ordertime =?1 and orderid=?2 ")
long[]  selectSalesSummaryDuplicationId(String date1,String orderno);


////////////////////dashboard//////////////////////////////

@Transactional
@Query(value = "select sum(amount_payed) from salessummary where  ordertime between ?1 and ?2 and location=?3  and status!='Pending'")
Double salesMonthlyReport(String date1,String date2,String location); 

      
	
	
}
