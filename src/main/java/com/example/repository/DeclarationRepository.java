package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Declaration;
import com.example.entity.ObligationFiscale;

public interface DeclarationRepository extends JpaRepository<Declaration,Long>{

	
	Optional<Declaration> findByMoisEffetAndAnneeEffetAndObligation(int moisEffet, int anneeEffet, ObligationFiscale obligation);
	
}
