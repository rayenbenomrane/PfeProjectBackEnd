package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement,Long> {



	List<Paiement> findByNumeroTransaction(String numeroTransaction);
	 List<Paiement> findByDeclaration_Obligation_Contribuable_MatriculeFiscale(int matriculeFiscale);

}
