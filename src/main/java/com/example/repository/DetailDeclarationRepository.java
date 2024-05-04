package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Declaration;
import com.example.entity.DetailDeclaration;

public interface DetailDeclarationRepository extends JpaRepository<DetailDeclaration,Long>{

			List<DetailDeclaration> findByDeclaration(Declaration declaration);


}


