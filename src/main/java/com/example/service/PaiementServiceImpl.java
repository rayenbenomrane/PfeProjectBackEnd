package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.PaiementDto;
import com.example.entity.Declaration;
import com.example.entity.Paiement;
import com.example.repository.DeclarationRepository;
import com.example.repository.PaiementRepository;

@Service
public class PaiementServiceImpl implements PaiementService {

	@Autowired
	private DeclarationRepository declarationrepo;
	@Autowired
	private PaiementRepository paiementrepo;
	
	@Override
	public boolean createPaiement(PaiementDto paiementDto) {
	    Optional<Declaration> optionalDeclaration = declarationrepo.findById(paiementDto.getIddeclaration());

	    if (optionalDeclaration.isPresent()) {
	    	List<Paiement> list=paiementrepo.findByNumeroTransaction(paiementDto.getNumeroTransaction());
	    	if(list.isEmpty()) {
 	        Declaration declaration = optionalDeclaration.get();

	        Paiement paiement = new Paiement();
	        paiement.setDeclaration(declaration);
	        paiement.setNumeroTransaction(paiementDto.getNumeroTransaction());
	        paiement.setDatePaiement(new Date());

	        Paiement savedPaiement = paiementrepo.save(paiement);

	        return savedPaiement != null;
	    }else 
	    	return false; 
	    	}else {
	        return false;
	    }
	}

	
}
