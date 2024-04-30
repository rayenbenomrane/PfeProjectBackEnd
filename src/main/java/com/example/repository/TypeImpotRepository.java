package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.TypeImpot;
import java.util.List;
import java.util.Optional;


public interface TypeImpotRepository extends JpaRepository<TypeImpot,Long>{

	
	Optional<TypeImpot>  findByLibelle(String libelle);
}
