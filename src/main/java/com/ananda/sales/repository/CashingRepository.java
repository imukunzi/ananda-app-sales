package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.Cashing;

public interface CashingRepository extends JpaRepository<Cashing, Long> {
	
	@Query("from cashing where date between ?1 and ?2 ")
	List<Cashing> findByDateContaining(String date, String date2);
	
	@Query("from cashing where date between ?1 and ?2 and cashier=?3")
	List<Cashing> findBycashierContaining(String date, String date2,String cashier);
	
	//report
	@Transactional
	@Modifying
	@Query("update cashing set headcashieramount=?1,comment=?2,receivedby=?3,headcashier=?4 where id=?5")
	Integer confirmCashing(String amount, String comment,String receivedBy,String headCashier,long id);
	
	@Transactional
	@Query("select sum(fiveThousands) from cashing where id=?1")
	Double findBy5000Total(long id);
	
	@Transactional
	@Query("select sum(twoThousands) from cashing where id=?1")
	Double findBy2000Total(long id);
	
	@Transactional
	@Query("select sum(oneThousands) from cashing where id=?1")
	Double findBy1000Total(long id);
	
	@Transactional
	@Query("select sum(fiveHundred) from cashing where id=?1")
	Double findBy500Total(long id);
	
	@Transactional
	@Query("select sum(onehundred) from cashing where id=?1")
	Double findBy100Total(long id);
	
	@Transactional
	@Query("select sum(fifty) from cashing where id=?1")
	Double findBy50Total(long id);
	
	@Transactional
	@Query("select sum(twenty) from cashing where id=?1")
	Double findBy20Total(long id);
	
	@Transactional
	@Query("select sum(ten) from cashing where id=?1")
	Double findBy10Total(long id);
	
	@Transactional
	@Query("select sum(five) from cashing where id=?1")
	Double findBy5Total(long id);
	
	@Transactional
	@Query("select sum(two) from cashing where id=?1")
	Double findBy2Total(long id);
	
	@Transactional
	@Query("select sum(one) from cashing where id=?1")
	Double findBy1Total(long id);
	
	@Transactional
	@Query("select sum(dollar) from cashing where id=?1")
	Double findByDollarTotal(long id);
	
	@Transactional
	@Query("select sum(dollarRate) from cashing where id=?1")
	Double findByDollarRateTotal(long id);
	
	@Transactional
	@Query("select sum(dollar2) from cashing where id=?1")
	Double findByDollar2Total(long id);
	
	@Transactional
	@Query("select sum(dollarRate2) from cashing where id=?1")
	Double findByDollarRate2Total(long id);
	
	@Transactional
	@Query("select sum(dollar3) from cashing where id=?1")
	Double findByDollar3Total(long id);
	
	@Transactional
	@Query("select sum(dollarRate3) from cashing where id=?1")
	Double findByDollarRate3Total(long id);
	
	@Transactional
	@Query("select sum(dollar4) from cashing where id=?1")
	Double findByDollar4Total(long id);
	
	@Transactional
	@Query("select sum(dollarRate4) from cashing where id=?1")
	Double findByDollarRate4Total(long id);
	
	//euro
	@Transactional
	@Query("select sum(euro) from cashing where id=?1")
	Double findByEuroTotal(long id);
	
	@Transactional
	@Query("select sum(euroRate) from cashing where id=?1")
	Double findByEuroRateTotal(long id);
	
	@Transactional
	@Query("select sum(euro2) from cashing where id=?1")
	Double findByEuro2Total(long id);
	
	@Transactional
	@Query("select sum(euroRate2) from cashing where id=?1")
	Double findByEuroRate2Total(long id);
	
	@Transactional
	@Query("select sum(euro3) from cashing where id=?1")
	Double findByEuro3Total(long id);
	
	@Transactional
	@Query("select sum(euroRate3) from cashing where id=?1")
	Double findByEuroRate3Total(long id);
	
	@Transactional
	@Query("select sum(euro4) from cashing where id=?1")
	Double findByEuro4Total(long id);
	
	@Transactional
	@Query("select sum(euroRate4) from cashing where id=?1")
	Double findByEuroRate4Total(long id);
	
	@Transactional
	@Query("select sum(amount) from cashing where id=?1")
	Double findByAmountTotal(long id);
	
	
	  @Transactional
	  @Query("select sum(caisseInit) from cashing where id=?1") Double
	  getCaisseInitTotal(long id);
	  
	  @Transactional
	  @Query("select headCashier from cashing where id=?1") 
	  String getheadCashier(long id);
	  
	  @Transactional
	  @Query("select sum(headCashierAmount) from cashing where id=?1") 
	  Double getheadCashierAmount(long id);
	  
	  @Transactional
	  @Query("select comment from cashing where id=?1") 
	  String getheadCashierComment(long id);
	  
	  	  
	  @Transactional
	  @Query("select id from cashing where date between ?1 and ?2 and cashier=?3") 
	  int[] getCashingCount(String date, String date2,String cashier);
	 
	
		
	
}
