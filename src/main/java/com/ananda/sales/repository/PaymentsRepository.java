package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.Payments;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {
	
		
	Page<Payments> findByLocationContaining(String title, Pageable pageable);
	List<Payments> findByLocationContaining(String title, Sort sort);
	
	Page<Payments> findByordernoContaining(String title, Pageable pageable);
	List<Payments> findByordernoContaining(String title, Sort sort);
	
	

	@Query("from payments where payment_time between ?1 and ?2 ")
	List<Payments> findByPayment_time(String date1,String date2);
	
	@Query("from payments where payment_time between ?1 and ?2 and location=?3 ")
	List<Payments> findByPayment_time_location(String date1,String date2,String location);
	
	
	@Query("from payments where payment_time between ?1 and ?2 and cashier=?3 ")
	List<Payments> findByCashier(String date1,String date2,String cashier);
	
	@Transactional
	@Query("select count(id) as count from payments where payment_time between ?1 and ?2 and cashier=?3 ")
	long findByCashierCount(String date1,String date2,String cashier);
	
		
	@Query("from payments where orderno like ?1% ")
	List<Payments> findByorderno(String orderno);
	
	
	//total all
	@Transactional
	@Query(value = "select sum(cash) as cash from payments where payment_time between ?1 and ?2")
	Double  findPaymentCashTotal(String date1,String date2);
	
	@Transactional
	@Query(value = "select sum(momo) as momo from payments where payment_time between ?1 and ?2")
	Double findPaymentMomoTotal(String date1,String date2);
	
	@Transactional
	@Query(value = "select sum(visa) as visa from payments where payment_time between ?1 and ?2")
	Double findPaymentVisaTotal(String date1,String date2);
	
	@Transactional
	@Query(value = "select  sum(cheque) as cheque from payments where payment_time between ?1 and ?2")
	Double findPaymentChequeTotal(String date1,String date2);
	
	@Transactional
	@Query(value = "select sum(transfer) as transfer from payments where payment_time between ?1 and ?2")
	Double findPaymentTransferTotal(String date1,String date2);
	
	//total per orderNo
	@Transactional
	@Query(value = "select sum(cash) as cash from payments where orderno=?1")
	Double  findPaymentCashTotalPerOderNo(String orderno);
	
	@Transactional
	@Query(value = "select sum(momo) as momo from payments where orderno=?1")
	Double findPaymentMomoTotalOderNo(String orderno);
	
	@Transactional
	@Query(value = "select sum(visa) as visa from payments where orderno=?1")
	Double findPaymentVisaTotalOderNo(String orderno);
	
	@Transactional
	@Query(value = "select  sum(cheque) as cheque from payments where orderno=?1")
	Double findPaymentChequeTotalOderNo(String orderno);
	
	@Transactional
	@Query(value = "select sum(transfer) as transfer from payments where orderno=?1")
	Double findPaymentTransferTotalOderNo(String orderno);
	
	////////////////total payment cashier
	@Transactional
	@Query(value = "select sum(cash) as cash from payments where payment_time between ?1 and ?2 and cashier=?3")
	Double  findPaymentCashTotal(String date1,String date2,String cashier);
	
	@Transactional
	@Query(value = "select sum(momo) as momo from payments where payment_time between ?1 and ?2 and cashier=?3")
	Double findPaymentMomoTotal(String date1,String date2,String cashier);
	
	@Transactional
	@Query(value = "select sum(momo_charges) as momo_charges from payments where payment_time between ?1 and ?2 and cashier=?3")
	Double findPaymentMomoChargesTotal(String date1,String date2,String cashier);
	
	@Transactional
	@Query(value = "select sum(visa) as visa from payments where payment_time between ?1 and ?2 and cashier=?3")
	Double findPaymentVisaTotal(String date1,String date2,String cashier);
	
	@Transactional
	@Query(value = "select  sum(cheque) as cheque from payments where payment_time between ?1 and ?2 and cashier=?3")
	Double findPaymentChequeTotal(String date1,String date2,String cashier);
	
	@Transactional
	@Query(value = "select sum(transfer) as transfer from payments where payment_time between ?1 and ?2 and cashier=?3")
	Double findPaymentTransferTotal(String date1,String date2,String cashier);
	
	@Transactional
	@Query(value = "select sum(cash) as cash from payments where orderno=?1")
	Double  findPaymentCashTotalByOrderNo(String orderNo);
	
	@Transactional
	@Query(value = "select sum(momo) as momo from payments where orderno=?1")
	Double findPaymentMomoTotalByOrderNo(String ordeNo);
	
	@Transactional
	@Query(value = "select sum(visa) as visa from payments where orderno=?1")
	Double findPaymentVisaTotalByOrderNo(String orderNo);
	
	@Transactional
	@Query(value = "select  sum(cheque) as cheque from payments where orderno=?1")
	Double findPaymentChequeTotalByOrderNo(String orderNo);
	
	@Transactional
	@Query(value = "select sum(transfer) as transfer from payments where orderno=?1")
	Double findPaymentTransferTotalByOrderNo(String orderNo);
	
	//check for payment duplication
	@Transactional
	@Query(value = "select count(id) as count from payments where payment_time between ?1 and ?2 and cashier=?3 and orderno=?4 and total_received=?5")
	int  checkPaymentDuplication(String date1,String date2,String cashier,String orderno,double totalReceived);
	
	
	////////////////////correcting possible saved duplicated payment//////////////////////////////
	@Transactional
	@Query(value = "select count(id) as count from payments where payment_time =?1 and cashier=?2 and orderno=?3 ")
	int  checkSavedPaymentDuplication(String date1,String cashier,String orderno);
	
	@Transactional
	@Query(value = "select id from payments where payment_time =?1 and cashier=?2 and orderno=?3 ")
	long[]  selectPaymentDuplicationId(String date1,String cashier,String orderno);
	
	
//////////////////// end correcting possible saved duplicated payment//////////////////////////////
	
	@Transactional
	@Query(value = "select sum(total_received) from payments where  ordertime < ?1 and payment_time between ?2 and ?3 and cashier=?4")
	Double reportSales_salesPayment(String ordertime,String time1,String time2,String cashier); 
	
	@Transactional
	@Query(value = "select sum(total_received) from payments where   payment_time between ?1 and ?2 and cashier=?3")
	Double reportSales_newSales(String time1,String time2,String cashier); 
	

			
	
}
