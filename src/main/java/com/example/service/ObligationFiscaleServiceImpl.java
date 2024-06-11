package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.ObligationresponseDto;
import com.example.dtos.PeriodeDto;
import com.example.dtos.SaveObligation;
import com.example.dtos.TypeImpotDto;
import com.example.entity.Contribuable;
import com.example.entity.Declaration;
import com.example.entity.ObligationFiscale;
import com.example.entity.TypeImpot;
import com.example.repository.ContribuableRepository;
import com.example.repository.DeclarationRepository;
import com.example.repository.ObligationFiscaleRepository;
import com.example.repository.TypeImpotRepository;

@Service
public class ObligationFiscaleServiceImpl implements ObligationFiscaleService{
	@Autowired
	private ObligationFiscaleRepository obligationFiscaleRepository;
	@Autowired
	private DeclarationRepository declarationRepo;
	@Autowired
	private ContribuableRepository contribuablerepo;
	@Autowired 
	private TypeImpotRepository imporepo;
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
		        impot.setFormule(obligation.getImpot().getFormule());
		        impot.setPeriodicite(obligation.getImpot().getPeriodicite());
		        obligationDto.setImpot(impot);

		        lesObligationsDto.add(obligationDto);
		    }

		    return lesObligationsDto;
	}
	@Override
	public boolean saveObligation(SaveObligation ob) {
		// TODO Auto-generated method stub
		Optional<Contribuable> contribuable=contribuablerepo.findByMatriculeFiscale(ob.getMatricule());
		Optional<TypeImpot> impot=imporepo.findById(ob.getImpot().getId());
		List<ObligationFiscale> lesObligations=obligationFiscaleRepository.findByImpotAndContribuable(impot.get(),contribuable.get());
		if(contribuable.isPresent()&& impot.isPresent()&& lesObligations.isEmpty()) {
			ObligationFiscale obligation=new ObligationFiscale();
			obligation.setContribuable(contribuable.get());
			obligation.setDateDebut(ob.getDateDebut());
			obligation.setDateFin(ob.getDateFin());
			obligation.setImpot(impot.get());
			ObligationFiscale obligation1=obligationFiscaleRepository.save(obligation);
			if(obligation1!=null) {
				return true;
			}else return false;
			
		}else return false;
	}

}
