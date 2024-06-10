package com.example.service;

import java.util.List;

import com.example.dtos.PaiementDto;
import com.example.entity.Paiement;

public interface PaiementService {


	boolean createPaiement(PaiementDto pa);

	List<Paiement> getPaiementsByContribuableMatriculeFiscale(int matriculeFiscale);
}
