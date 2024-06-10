package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Echeance;
import com.example.entity.TypeImpot;

public interface EcheanceRepository extends JpaRepository<Echeance,Long>{

	void deleteByTypeImpot(TypeImpot typeImpot);
}
