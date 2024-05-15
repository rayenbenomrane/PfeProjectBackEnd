package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.DetailImpot;
import com.example.entity.TypeImpot;


public interface DetailImpotRepository extends JpaRepository<DetailImpot,Long>{


	List<DetailImpot>  findByTypeImpot(TypeImpot typeImpot);


}


