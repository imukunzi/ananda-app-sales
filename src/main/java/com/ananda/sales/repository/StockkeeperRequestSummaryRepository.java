package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.Stock;
import com.ananda.sales.model.StockRequestSummary;

public interface StockkeeperRequestSummaryRepository extends JpaRepository<StockRequestSummary, Long> {

	@Query(value = "SELECT count(id)FROM stock_request_summary where stockEntryId=?1 and stand_supply_request_date between ?2 and ?3 and stand=?4")
	int checkIfCurrentStockRequestExist(String orderId, String date1, String date2, String stand); // check if this item
																									// was add before on
																									// this current
																									// request

	@Transactional
	@Query(value = "FROM stock_request_summary where stockEntryId=?1")
	StockRequestSummary findBystockEntryIdSummary(String order); // get saved request from request by request no

	// PENDING REQUEST
	@Transactional
	@Query(value = "FROM stock_request_summary where request_Status='Pending' and stand_supply_request_date between ?1 and ?2 and location=?3")
	List<StockRequestSummary> findByrequest_StatusPending(String time1, String time2, String location); // get temporary
																										// saved request
	// PENDING REQUEST
		@Transactional
		@Query(value = "FROM stock_request_summary where request_Status=?1 and stand_supply_request_date between ?2 and ?3 and location=?4")
		List<StockRequestSummary> findByrequest_Status(String status,String time1, String time2, String location); // get temporary
																											// saved request
	
	// from request form

	@Transactional
	@Modifying
	@Query(value = "update stock_request_summary set stand_supply_request_date=?1,request_Status=?2 where stockEntryId=?3")
	int updateStockRequest(String time, String status, String orderid);

	// APPROVING REQUEST
	// BUSINESS OPERATION MANAGER
	@Transactional
	@Modifying
	@Query(value = "update stock_request_summary set business_operation_manager_approval_date=?1,business_operation_manager_approval=?2,business_operation_manager_name=?3 where stockEntryId=?4")
	int updateStockRequestBusinessOperationManager(String time, String status, String name, String stockEntry);

	// BRANCH MANAGER
	@Transactional
	@Modifying
	@Query(value = "update stock_request_summary set branch_manager_approval_date=?1,branch_manager_approval=?2,branch_manager_name=?3 where stockEntryId=?4")
	int updateStockRequestBranchManager(String time, String status, String name, String stockEntry);
	
	// STOCK KEEPER
		@Transactional
		@Modifying
		@Query(value = "update stock_request_summary set stockkeeper_approval_date=?1,stockkeeper_approval=?2,stockkeeper_name=?3 where stockEntryId=?4")
		int updateStockRequestStockKeeper(String time, String status, String name, String stockEntry);
		
		// STOCK KEEPER
		@Transactional
		@Modifying
		@Query(value = "update stock_request_summary set request_Status='RECIEVED', stand_receiption_confirmation_date=?1,stand_receiption_confirmation=?2,stand_supply_request_officer=?3 where stockEntryId=?4")
		int updateStockRequestStandKeeper(String time, String status, String name, String stockEntry);
		
		@Transactional
		@Modifying
		@Query(value = "update stock_request_summary set printing_version='COPY' where stockEntryId=?1")
		int updatePrintingVersion(String id);



}
