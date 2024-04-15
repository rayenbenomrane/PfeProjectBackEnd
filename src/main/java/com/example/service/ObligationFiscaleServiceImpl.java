package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Contribuable;
import com.example.entity.Declaration;
import com.example.entity.ObligationFiscale;
import com.example.repository.ObligationFiscaleRepository;

@Service
public class ObligationFiscaleServiceImpl implements ObligationFiscaleService{
	@Autowired
	private ObligationFiscaleRepository obligationFiscaleRepository;
	@Override
	public Declaration getNumerodeclaration(Contribuable cd, int iddecalaration) {
		List<ObligationFiscale> lesobligations=obligationFiscaleRepository.findByContribuable(cd);

		Declaration declaration;
		for (ObligationFiscale obligation : lesobligations) {
	        if (obligation.getDeclaration().getIdDeclaration() == iddecalaration) {
	            declaration=obligation.getDeclaration();
	            return declaration;
	        }
	    }
		return null;

	}

}
