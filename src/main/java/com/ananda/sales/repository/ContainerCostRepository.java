package com.ananda.sales.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ananda.sales.model.ContainerCost;

public interface ContainerCostRepository extends JpaRepository<ContainerCost, Long> {
	Page<ContainerCost> findByproductContaining(String title, Pageable pageable);
	List<ContainerCost> findByproductContaining(String title, Sort sort);
}
