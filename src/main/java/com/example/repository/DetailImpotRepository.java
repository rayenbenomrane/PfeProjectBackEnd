package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dtos.DetailImpotDto;
import com.example.entity.DetailImpot;
import com.example.entity.TypeImpot;

import java.util.List;


public interface DetailImpotRepository extends JpaRepository<DetailImpot,Long>{

	
	List<DetailImpot>  findByTypeImpot(TypeImpot typeImpot);
	
	
}


