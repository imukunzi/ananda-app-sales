package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.Expenses;


public interface ExpensesRepository extends JpaRepository<Expenses, Long> {
	
	@Query("from expenses where date between ?1 and ?2 and username=?3 ")
	List<Expenses> findBydate(String date1,String date2,String cashier);
	
	@Transactional
	@Query("SELECT sum(amount) as amount from expenses where date between ?1 and ?2 and username=?3 ")
	Double findBydateReport(String date1,String date2,String cashier);

}
