package com.ananda.sales.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ananda.sales.model.User;
import com.ananda.sales.model.User_roles_list;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	// used for password reset
	User findByResetPasswordToken(String token);

	@Query(value = "FROM users where account=?1")
	User findByAccount(String account);

	@Transactional
	@Query(value = "FROM users where username=?1")
	User getMemberAccount(String username);
	
	@Transactional
	@Query(value = "FROM users order by userGroup asc")
	List<User> getUserByGroup(String username);
	
//	@Transactional
//	@Query(value = "select user_id,role_id,roles.name,users.firstname,users.lastname from user_roles left join users on users.id=user_id left join roles on roles.id=user_roles.role_id order by users.id")
//	List<User_roles_list> getUserRoles();

	@Transactional
	@Query(value = "select id FROM users where username=?1")
	long getMemberAccountId(String username);
	
	

}
