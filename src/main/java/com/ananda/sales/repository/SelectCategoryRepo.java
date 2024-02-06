package com.ananda.sales.repository;

 
import org.springframework.data.jpa.repository.JpaRepository;

import com.ananda.sales.model.SelectionCategory;

public interface SelectCategoryRepo extends JpaRepository<SelectionCategory,Long> {
	
	

}
