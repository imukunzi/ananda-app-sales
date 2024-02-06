package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
	
	@Transactional
	@Query(value = "select count(id) as count from delivery where orderno= ?1")
	int findDeliveryCount(String orderno);
	
	Page<Delivery> findBycustomerContaining(String title, Pageable pageable);
	List<Delivery> findBycustomerContaining(String title, Sort sort);
	
	List<Delivery> findByorderNoContaining(String title);
	
	@Transactional
	@Modifying
	@Query(value = "from delivery where customer like %?1%")
	List<Delivery> searchByCustomer(String title);
	
	@Transactional
	@Modifying
	@Query(value = "from delivery where orderno like %?1%")
	List<Delivery> searchByOrderNo(String title);
	
	@Transactional
	@Query(value = "from delivery where location=?1 and oderdate between ?2 and ?3 ")
	List<Delivery> findReportByDate(String location,String date1,String date2);
	
	@Transactional
	@Query(value = "from delivery where location=?1 and deliverystatus='Reste' and oderdate between ?2 and ?3 ")
	List<Delivery> findReportByDateReste(String stock,String date1,String date2);
	
	@Transactional
	@Query(value = "from delivery where deliverystatus='Reste' and orderno=?1 ")
	List<Delivery> findReportResteByOrderNo(String orderNo);
	
	@Transactional
	@Query(value = "from delivery where location=?1 and deliverystatus='Pending' and oderdate between ?2 and ?3 ")
	List<Delivery> findReportByDatePending(String stock,String date1,String date2);
	
	@Transactional
	@Query(value = "from delivery where location=?1 and deliverystatus='Completed' and oderdate between ?2 and ?3 ")
	List<Delivery> findReportByDateCompleted(String stock,String date1,String date2);
	
	@Transactional
	@Modifying
	@Query(value = "update delivery set locked='Locked' where id= ?1")
	int lockDelivery(long id);
	
	@Transactional
	@Modifying
	@Query(value = "update delivery set paymentstatus=?1 where orderno= ?2")
	int updateDeliveryStatus(String status,String orderNo);
	
	@Transactional
	@Modifying
	@Query(value = "update delivery set locked='Authorized' where id= ?1")
	int authorizeDelivery(long id);

}