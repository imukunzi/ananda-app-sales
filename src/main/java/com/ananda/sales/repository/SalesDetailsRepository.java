package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.OrderDetails;
import com.ananda.sales.model.SalesDetails;

public interface SalesDetailsRepository extends JpaRepository<SalesDetails, Integer> {

	// orders
	// check if this project exist on this order

	@Transactional
	@Query(value = "FROM salesdetail where orderid=?1")
	List<SalesDetails> findByOrderidDetails(String order);
	
	@Transactional
	@Query(value = "FROM salesdetail where saller=?1 and ordertime between ?2 and ?3 and salesstatus!='TEMPORARY' order by code asc")
	List<SalesDetails> findBysallerDetails(String saller,String date1,String date2);
	
	@Transactional
	@Query(value = "select distinct code FROM salesdetail where saller=?1 and ordertime between ?2 and ?3 and salesstatus!='TEMPORARY' order by code asc")
	List<String> findBysallerDetailsDistinctItems(String saller,String date1,String date2);

	@Query(value = "SELECT count(id)FROM salesdetail where pid=?1 and orderid=?2 and ordertime between ?3 and ?4 and stand=?5 and price=6")
	int checkIfProductExistOnThisCurrentOrder(int pid, String orderId, String date1, String date2, String stand,double price);

	@Query(value = "SELECT sum(qty)FROM salesdetail where pid=?1 and orderid=?2 and ordertime between ?3 and ?4 and stand=?5")
	int getQtyIfProductExistOnThisCurrentOrder(int pid, String orderId, String date1, String date2, String stand);

	@Transactional
	@Modifying
	@Query(value = "update salesdetail set qty=?1,total=?2 where pid=?3 and orderid=?4 and ordertime between ?5 and ?6 and stand=?7")
	void updateifProductExistOnThisCurrentOrder(int qty, double amount, int pid, String orderId, String date1,
			String date2, String stand);

	// sales by saller
	@Transactional
	@Query("select distinct(product) from salesdetail where ordertime between ?1 and ?2 and location=?3 order by product asc ")
	List<String> findSaledProducts(String date1, String date2,String branch);

	@Transactional
	@Query("select sum(total) from salesdetail where ordertime between ?1 and ?2 and product=?3 and location=?4 and salesstatus !='TEMPORARY'")
	Double findSaledProductsAmount(String date1, String date2, String product,String branch);
	
	@Transactional
	@Query("select distinct(product) from salesdetail where ordertime between ?1 and ?2 and stand=?3 and description=?4 and salesstatus !='TEMPORARY' order by product asc")
	List<String> findSaledProductsStand(String date1, String date2,String stand,String description);

	@Transactional
	@Query("select sum(total) from salesdetail where ordertime between ?1 and ?2 and product=?3 and stand=?4 and description=?5 and salesstatus !='TEMPORARY'")
	Double findSaledProductsAmountStand(String date1, String date2, String product,String stand,String description);
	
	@Transactional
	@Query("select sum(total) from salesdetail where ordertime between ?1 and ?2 and stand=?3 and description=?4 and salesstatus !='TEMPORARY'")
	Double findSaledAmountStand(String date1, String date2,String stand,String description);
	
	@Transactional
	@Query("select sum(qty) from salesdetail where ordertime between ?1 and ?2 and stand=?3 and description=?4 and salesstatus !='TEMPORARY'")
	Integer findSaledStandTotalItem(String date1, String date2,String stand,String description);

	@Transactional
	@Query("select sum(qty) from salesdetail where ordertime between ?1 and ?2 and product=?3 and location=?4 and salesstatus !='TEMPORARY'")
	Integer findSaledProductsQty(String date1, String date2, String product,String branch);
	
	@Transactional
	@Query("select sum(qty) from salesdetail where ordertime between ?1 and ?2 and product=?3 and stand=?4 and description=?5 and salesstatus !='TEMPORARY'")
	Integer findSaledProductsQtyStand(String date1, String date2, String product,String stand,String description);

	@Transactional
	@Modifying
	@Query(value = "update salesdetail set cashier=?1,salesstatus=?2 where orderid=?3")
	int updateSalesDetails(String cashier, String status, String orderid);

	@Transactional
	@Modifying
	@Query(value = "update salesdetail set salesstatus=?1,ordertime=?2 where orderid=?3")
	int updateSalesDetailsWithTime(String status, String orderTIme, String orderid);

	@Query(value = "select count(id) from salesdetail where orderid=?1")
	int checkIfOrderExist(String orderid);

	@Transactional
	@Modifying
	@Query(value = "delete from salesdetail where orderid=?1 and salesstatus='Pending'")
	int deleteOrder(String orderid);

	@Transactional
	@Query("select sum(total) from salesdetail where ordertime between ?1 and ?2 and orderid=?3 and salesstatus !='TEMPORARY'")
	Double checkSalesDetailsOrderAmount(String date1, String date2, String orderid);

	// ANALYSIS
	@Transactional
	@Query(value = "SELECT distinct orderid FROM salesdetail where orderid=?1")
	String analyseOrder(String orderid);

	@Transactional
	@Query(value = "SELECT distinct customer FROM salesdetail where orderid=?1")
	String analyseOrderCustomer(String orderid);

	@Transactional
	@Query(value = "SELECT distinct saller FROM salesdetail where orderid=?1")
	String analyseOrderSaller(String orderid);

	@Transactional
	@Query(value = "SELECT count(id) FROM salesdetail where orderid=?1")
	int analyseOrderItems(String orderid);
	
	@Transactional
	@Query(value = "SELECT count(id) FROM salesdetail where orderid=?1 and authorization_status='LOW-PRICE'")
	int analyselowPrices(String orderid);

	@Query(value = "SELECT sum(total)FROM salesdetail where orderid=?1")
	Double findByOrderid(String orderid);

	
	List<SalesDetails> findByOrderidContaining(String order);
	
	@Transactional
	@Modifying
	@Query(value = "update orderdetail set comment=?1 where orderid=?2")
	void updateOrderComment(String comment, String orderid);
	
	@Transactional
	@Modifying
	@Query(value = "update salesdetail set authorization_status='AUTHORIZED', authorization_by=?1 where id=?2")
	Integer updateOrderLowPriceAuthorization(String authorization_by, int id);
	
	//sales by saller
	@Transactional
	@Query("select sum(total) from salesdetail where ordertime between ?1 and ?2 and saller=?3  and salesstatus !='TEMPORARY' order by id desc ")
	Double findSalesBySaller(String date1,String date2,String saller);
	
	// dashboard
	@Transactional
	@Query("select sum(price - purchase_price) from salesdetail where ordertime between ?1 and ?2 and location=?3  and salesstatus !='TEMPORARY' and salesstatus !='Pending' order by id desc ")
	Double findSalesProfit(String date1,String date2,String location); 

}
