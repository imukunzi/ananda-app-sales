package com.ananda.sales.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.AccessControl;


public interface AccessContollRepository extends JpaRepository<AccessControl, Long> {
	
	Page<AccessControl> findByMacAddressContaining(String title, Pageable pageable);
	List<AccessControl> findByMacAddressContaining(String title, Sort sort);
	
	
	@Query(value = "FROM accesscontrol where user=?1")
	AccessControl findByUserContaining(String user);
	
	
}
