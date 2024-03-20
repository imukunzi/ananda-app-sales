package com.ananda.sales.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.Prevision;


public interface PrevisionRepository extends JpaRepository<Prevision, Long> {
	
	@Transactional
	@Query(value = "select count(id) as count from prevision where year =?1 and type=?2 ")
	int  checkSavedPrevisionDuplication(int year,String type);

}
