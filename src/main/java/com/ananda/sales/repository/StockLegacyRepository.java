package com.ananda.sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.StockLegacy;

public interface StockLegacyRepository extends JpaRepository<StockLegacy, Long> {
	
	@Modifying
	@Query("select stand,pid,code,product,size,sum(entry),sum(exit),sum(transfer_in),sum(transfer_out),(damange),color from stock_legacy_data roup")
	List<StockLegacy> findStockLegacySum();

}
