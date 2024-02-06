package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.Customers;

public interface CustomersRepository extends JpaRepository<Customers, Long > {
	
	Page<Customers> findByFullnameContaining(String title, Pageable pageable);
	List<Customers> findByFullnameContaining(String title, Sort sort);
	
	Page<Customers> findByPhoneContaining(String title, Pageable pageable);
	List<Customers> findByPhoneContaining(String title, Sort sort);
	
	@Transactional
	@Query(value = "select count(id) as count from customers where phone= ?1")
	int checkIfCustomerExist(String phone);

}
