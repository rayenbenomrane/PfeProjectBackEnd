package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.FormeJuridique;

public interface FormeJuridiqueRepository extends JpaRepository<FormeJuridique, Long>{

List<FormeJuridique> findByLibelle(String libelle);
	
}
