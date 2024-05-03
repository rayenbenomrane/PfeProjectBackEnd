package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.ObligationDto;
import com.example.dtos.PeriodeDto;
import com.example.dtos.TypeImpotDto;
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
		/*List<ObligationFiscale> lesobligations=obligationFiscaleRepository.findByContribuable(cd);

		Declaration declaration;
		for (ObligationFiscale obligation : lesobligations) {
	        if (obligation.getDeclaration().getIdDeclaration() == iddecalaration) {
	            declaration=obligation.getDeclaration();
	            return declaration;
	        }
	    }*/
		return null;

	}
	@Override
	public List<ObligationDto> getlesObligationsdeContribuable(Contribuable cd) {
		List<ObligationFiscale> lesobligations=obligationFiscaleRepository.findByContribuable(cd);
		 List<ObligationDto> lesObligationsDto = new ArrayList<>();
		    
		    for(ObligationFiscale obligation : lesobligations) {
		        ObligationDto obligationDto = new ObligationDto();
		        obligationDto.setDateDebut(obligation.getDateDebut());
		        obligationDto.setDateFin(obligation.getDateFin());
		        // Assuming TypeImpotDto has a constructor that takes ObligationFiscale as argument
		        
		        TypeImpotDto impot=new TypeImpotDto();
		        impot.setLibelle(obligation.getImpot().getLibelle());
		        PeriodeDto periode=new PeriodeDto();
		        periode.setIdPeriodicite(obligation.getImpot().getPeriodicite().getIdPeriodicite());
		        periode.setPeriode(obligation.getImpot().getPeriodicite().getPeriode());
		        impot.setPeriodicite(periode);
		        obligationDto.setImpot(impot);
		        
		        lesObligationsDto.add(obligationDto);
		    }
		    
		    return lesObligationsDto;
	}

}
