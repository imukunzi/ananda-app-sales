package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.AccessControllOtp;

public interface AccessControllOtpRepository extends JpaRepository<AccessControllOtp, Long> {
	
	Page<AccessControllOtp> findByphoneContaining(String title, Pageable pageable);
	List<AccessControllOtp> findByPhoneContaining(String title, Sort sort);
	
	@Transactional
	@Query(value = "select count(id) as count FROM accesscontrolotp where accessCode=?1 and type='Authorization'")
	Integer findIfAuthorizationCodeExist(String code);
	
	@Transactional
	@Query(value = "select count(id) as count FROM accesscontrolotp where accessCode=?1 and type=?2")
	Integer findIfAuthorizationCodeExistWithType(String code,String type);
	
	@Transactional
	@Query(value = "select count(id) as count FROM accesscontrolotp where accessCode=?1 and type='Delete'")
	Integer findIfDeleteCodeExist(String code);
	
	@Transactional
	@Query(value = "select user FROM accesscontrolotp where accessCode=?1 and type='Authorization'")
	String findIfAuthorizationCodeExistUser(String code);
	
	@Transactional
	@Query(value = "select user FROM accesscontrolotp where accessCode=?1 and type=?2")
	String findIfAuthorizationCodeExistUserwithType(String code,String type);
	
	@Transactional
	@Query(value = "select user FROM accesscontrolotp where accessCode=?1 and type='Delete'")
	String findIfDeleteCodeExistUser(String code);
	
	@Transactional
	@Query(value = "select other  FROM accesscontrolotp where accessCode=?1 and type='Authorization'")
	String findIfAuthorizationCodeExistUserBranch(String code);
	
	@Transactional
	@Query(value = "select other  FROM accesscontrolotp where accessCode=?1 and type=?2")
	String findIfAuthorizationCodeExistUserBranchWithType(String code,String type);
	
	@Transactional
	@Query(value = "select other  FROM accesscontrolotp where accessCode=?1 and type='Delete'")
	String findIfDeleteCodeExistUserBranch(String code);
	
	@Transactional
	@Modifying
	@Query(value = "update accesscontrolotp set accessCode=?1 where id=?2 ")
	Integer resetAccessCode(String code,long id);

}
