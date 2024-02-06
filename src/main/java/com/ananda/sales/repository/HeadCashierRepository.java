package com.ananda.sales.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.HeadCashierReport;

public interface HeadCashierRepository extends JpaRepository<HeadCashierReport, Long> {
	
	@Transactional
	@Query("SELECT count(id) as count from head_cashier_report where date between ?1 and ?2 and headCashier=?3 and cashier=?4 ")
	Integer checkIfNoDuplicateExit(String date1,String date2,String headCashier,String cashier);
	
	@Transactional
	@Query("from head_cashier_report where date between ?1 and ?2 and cashier=?3 ")
	HeadCashierReport findReport(String date1,String date2,String cashier);
	
	@Transactional
	@Query("select sum(cash) as cash from head_cashier_report where date between ?1 and ?2 and cashier=?3 ")
	double findReportTotalCash(String date1,String date2,String cashier);
	
	@Transactional
	@Query("select sum(momo) as momo from head_cashier_report where date between ?1 and ?2 and cashier=?3 ")
	double findReportTotalMomo(String date1,String date2,String cashier);
	
	@Transactional
	@Query("select sum(cheque) as cheque from head_cashier_report where date between ?1 and ?2 and cashier=?3 ")
	double findReportTotalCheque(String date1,String date2,String cashier);
	
	@Transactional
	@Query("select sum(transfer) as transfer from head_cashier_report where date between ?1 and ?2 and cashier=?3 ")
	double findReportTotalTransfer(String date1,String date2,String cashier);
	
	@Transactional
	@Query("select sum(visa) as visa from head_cashier_report where date between ?1 and ?2 and cashier=?3 ")
	double findReportTotalVisa(String date1,String date2,String cashier);
	
	@Transactional
	@Query("select sum(expenses) as expenses from head_cashier_report where date between ?1 and ?2 and cashier=?3 ")
	double findReportTotalExpenses(String date1,String date2,String cashier);

}
