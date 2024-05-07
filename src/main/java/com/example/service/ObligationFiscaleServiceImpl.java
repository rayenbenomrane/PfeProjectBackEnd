package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.ObligationDto;
import com.example.dtos.ObligationresponseDto;
import com.example.dtos.PeriodeDto;
import com.example.dtos.TypeImpotDto;
import com.example.entity.Contribuable;
import com.example.entity.Declaration;
import com.example.entity.ObligationFiscale;
import com.example.repository.DeclarationRepository;
import com.example.repository.ObligationFiscaleRepository;

@Service
public class ObligationFiscaleServiceImpl implements ObligationFiscaleService{
	@Autowired
	private ObligationFiscaleRepository obligationFiscaleRepository;
	@Autowired
	private DeclarationRepository declarationRepo;
	@Override
	public Declaration getNumerodeclaration(Contribuable cd, Long iddecalaration) {
		  List<ObligationFiscale> lesobligations = obligationFiscaleRepository.findByContribuable(cd);
		    Optional<Declaration> declaration = declarationRepo.findById(iddecalaration);

		    if (declaration.isPresent()) {
		        for (ObligationFiscale obligation : lesobligations) {
		            if (obligation.getIdObligationFiscale() == declaration.get().getObligation().getIdObligationFiscale()) {
		                return declaration.get();
		            }
		        }
		    }

		    return null;

	}
	@Override
	public List<ObligationresponseDto> getlesObligationsdeContribuable(Contribuable cd) {
		List<ObligationFiscale> lesobligations=obligationFiscaleRepository.findByContribuable(cd);
		 List<ObligationresponseDto> lesObligationsDto = new ArrayList<>();
		    
		    for(ObligationFiscale obligation : lesobligations) {
		    	ObligationresponseDto obligationDto = new ObligationresponseDto();
		        obligationDto.setDateDebut(obligation.getDateDebut());
		        obligationDto.setDateFin(obligation.getDateFin());
		        obligationDto.setIdObligation(obligation.getIdObligationFiscale());
		      
		        
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
