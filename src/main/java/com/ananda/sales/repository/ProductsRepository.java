package com.ananda.sales.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ananda.sales.model.Products;


public interface ProductsRepository extends JpaRepository<Products, Long> {
	
	Page<Products> findByCodeContaining(String title, Pageable pageable);
	List<Products> findByCodeContaining(String title, Sort sort);
	
	Page<Products> findByNameContaining(String title, Pageable pageable);
	List<Products> findByNameContaining(String title, Sort sort);
	
	@Transactional
	@Modifying
	@Query(value = "update products set current_stock_qty=?1 where id=?2")
	void updateavailableQty(int qty,Long id);
	
	@Query(value = "select count(id) as count from products where name=?1 and code=?2 and size=?3 and color=?4")
	int checkIfProductExist(String productname,String code,String size,String color);
	
	@Query(value = "select max_price as price from products where pid=?1 ")
	double getProductPrice(int pid);
	
	@Query(value = "select suply_price as price from products where pid=?1 ")
	double getSupplyPrice(int pid);
	
	@Query(value = "from products where barcode=?1 ")
	Products scanProduct(String barcode);

}
