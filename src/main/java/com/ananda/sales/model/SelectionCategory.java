package com.ananda.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
 

@Entity
@Table(name="selection_category",uniqueConstraints = { 
		@UniqueConstraint(columnNames = "category") 
	})
public class SelectionCategory {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
	@Column(name = "category")
	private String category;

	public SelectionCategory(String category) {
		super();
		this.category = category;
	}

	public SelectionCategory() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "SelectionCategory [id=" + id + ", category=" + category + "]";
	}

	
}

