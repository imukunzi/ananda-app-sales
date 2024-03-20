package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.Prevision;
import com.ananda.sales.model.Products;


public interface PrevisionRepository extends JpaRepository<Prevision, Long> {
	
	Page<Prevision> findByYearContaining(String title, Pageable pageable);
	List<Prevision> findByYearContaining(String title, Sort sort);
	
	@Transactional
	@Query(value = "select count(id) as count from prevision where year =?1 and type=?2 ")
	int  checkSavedPrevisionDuplication(int year,String type);

}
