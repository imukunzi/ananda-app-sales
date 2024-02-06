package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.Stock;
import com.ananda.sales.model.StockRequestSummary;

public interface StockkeeperRepository extends JpaRepository<Stock, Long> {

	// MAKING REQUEST
	@Transactional
	@Query(value = "FROM stock where stockEntryId=?1 and request_Status='TEMPORARY'")
	List<Stock> findBystockEntryIdDetails(String order); // get temporary saved request from request form
	
	@Query(value = "FROM stock where id=?1")
	Stock findByid(long id);
	
	

	@Transactional
	@Query(value = "FROM stock where stockEntryId=?1 and comment='REQUEST-FROM-BRANCH'")
	List<Stock> findBystockEntryIdDetailsData(String order); // get saved request from request by request no

	@Query(value = "SELECT count(id)FROM stock where pid=?1 and stockEntryId=?2 and stand_supply_request_date between ?3 and ?4 and stand=?5")
	int checkIfProductExistOnThisCurrentRequest(int pid, String orderId, String date1, String date2, String stand); // check
																													// if
																													// this
																													// item
																													// was
																													// add
																													// before
																													// on
																													// this
																													// current
																													// request

	@Query(value = "SELECT sum(qty_requested) as qty_requested FROM stock where pid=?1 and stockEntryId=?2 and stand_supply_request_date between ?3 and ?4 and stand=?5")
	int checkIfProductExistOnThisCurrentRequestQty(int pid, String orderId, String date1, String date2, String stand); // if
																														// this
																														// item
																														// was
																														// add
																														// before
																														// return
																														// existing
																														// qty

	@Transactional
	@Modifying
	@Query(value = "update stock set qty_requested=?1 where pid=?2 and stockEntryId=?3 and stand_supply_request_date between ?4 and ?5 and stand=?6")
	int checkIfProductExistOnThisCurrentRequestQtyAndAddNewRequestedQty(int qty, int pid, String orderId, String date1,
			String date2, String stand); // add new requested qty on existing qty

	@Transactional
	@Modifying
	@Query(value = "update stock set stand_supply_request_date=?1,request_Status=?2 where stockEntryId=?3")
	int updateStockRequest(String time, String status, String orderid);

	// end making request

	// PENDING REQUEST
	@Transactional
	@Query(value = "FROM stock where request_Status='Pending' and stand_supply_request_date between ?1 and ?2 and location=?3")
	List<Stock> findByrequest_StatusPending(String time1, String time2, String location); // get temporary saved request
																							// from request form

	// APPROVING REQUEST
	// BUSINESS OPERATION MANAGER
	@Transactional
	@Modifying
	@Query(value = "update stock set business_operation_manager_approval_date=?1,business_operation_manager_approval=?2,business_operation_manager_name=?3 where stockEntryId=?4")
	int updateStockRequestBusinessOperationManager(String time, String status, String name, String stockEntry);

	// BRANCH MANAGER
	@Transactional
	@Modifying
	@Query(value = "update stock set branch_manager_approval_date=?1,branch_manager_approval=?2,branch_manager_name=?3 where stockEntryId=?4")
	int updateStockRequestBranchManager(String time, String status, String name, String stockEntry);

	// STOCK KEEPER
	@Transactional
	@Modifying
	@Query(value = "update stock set stockkeeper_approval_date=?1,stockkeeper_approval=?2,stockkeeper_name=?3 where stockEntryId=?4")
	int updateStockRequestStockKeeper(String time, String status, String name, String stockEntry);
	
	@Transactional
	@Modifying
	@Query(value = "update stock set stock_type='IN',confirmation='Confirmed',date1=?1,request_Status=?2,stockkeeper=?3,qty_in=?4 where id=?5")
	int updateStockRequestStockKeeperReceived(String time, String status, String name,int qty, long id);

	// STOCK KEEPER
	@Transactional
	@Modifying
	@Query(value = "update stock set request_Status='RECIEVED', stand_receiption_confirmation_date=?1,stand_receiption_confirmation=?2,stand_supply_request_officer=?3 where stockEntryId=?4")
	int updateStockRequestStandKeeper(String time, String status, String name, String stockEntry);
	
	// STOCK KEEPER confirm avairable qty
		@Transactional
		@Modifying
		@Query(value = "update stock set stockkeeper_approval_qty=?1 where id=?2")
		int updateStockRequestStandKeeperAvairableQty(int qty, long id);
		
		// get sum qty on stockkeper approval
		@Transactional
		@Query(value = "select sum(stockkeeper_approval_qty) from stock where stockEntryId=?1")
		int getStockkeeperapproval_sum_qty( String stockEntry);
		   

}
