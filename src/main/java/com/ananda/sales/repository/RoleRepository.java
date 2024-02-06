package com.ananda.sales.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ananda.sales.model.ERole;
import com.ananda.sales.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
	
	@Query(value = "SELECT distinct name FROM Role")
	List<String> findRoleList();
	
	@Transactional
	@Query(value = "SELECT  id FROM Role where name=?1")
	Integer getRoleId(String name);
}
