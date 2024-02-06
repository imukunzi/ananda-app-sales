package com.ananda.sales.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ananda.sales.model.Members;

public interface MembersRepository extends JpaRepository<Members, Long> {
	
	Page<Members> findByfirstnameContaining(String title, Pageable pageable);
	List<Members> findByfirstnameContaining(String title, Sort sort);
	List<Members> findByAccountContaining(String account);
	
	 	

}
