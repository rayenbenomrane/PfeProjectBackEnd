package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Declaration;

public interface DeclarationRepository extends JpaRepository<Declaration,Long>{

}
