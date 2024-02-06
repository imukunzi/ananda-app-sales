package com.ananda.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ananda.sales.model.FileDB;


@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

}
