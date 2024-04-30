package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.PeriodeDto;
import com.example.dtos.TypeImpotDto;
import com.example.entity.Echeance;
import com.example.entity.Periodicite;
import com.example.entity.TypeImpot;
import com.example.enums.Periode;
import com.example.repository.EcheanceRepository;
import com.example.repository.PeriodiciteRepository;
import com.example.repository.TypeImpotRepository;


@Service
public class TypeImpotServiceImpl implements TypeImpotService{

	
	@Autowired
	private EcheanceRepository echeanceRepository;
	@Autowired
	private TypeImpotRepository typeImpotRepository;
	@Autowired
	private PeriodiciteRepository periodiciteRepository;
	@Autowired
	private TypeImpotRepository impotrepo;
	
	@Override
	public TypeImpotDto saveImpot(TypeImpotDto td) {
		TypeImpot impot=new TypeImpot();
		impot.setLibelle(td.getLibelle());
		 Periodicite periodicite = periodiciteRepository.findById(td.getPeriodicite().getIdPeriodicite())
                 .orElseThrow(() -> new IllegalArgumentException("Invalid periodicite"));

impot.setPeriodicite(periodicite);
Periodicite nb=new Periodicite();
nb.setIdPeriodicite(td.getPeriodicite().getIdPeriodicite());
nb.setPeriode(td.getPeriodicite().getPeriode());
		impot.setPeriodicite(nb);
		TypeImpot     impot1=typeImpotRepository.save(impot);
		if(impot.getPeriodicite().getPeriode()==Periode.MENSUELLE) {
			 for (int mois = 1; mois <= 12; mois++) {
		            Echeance echeance = new Echeance();
		            echeance.setJour(15); // Jour de l'échéance (exemple : le 15 de chaque mois)
		            echeance.setMois(mois+1); // Mois de l'échéance (1 pour janvier, 2 pour février, etc.)
		            echeance.setNumeroEcheance(mois); // Numéro de l'échéance (1 à 12 pour chaque mois)
		            
		            
		            int annee = 0;
		            if (mois == 12) {
		            	echeance.setMois(1);
		                annee = 1;
		            }
		           
		            echeance.setAnnee(annee); 
		            
		            echeance.setTypeImpot(impot); 

		           
		            echeanceRepository.save(echeance); // Assurez-vous d'injecter echeanceRepository dans votre classe
		        }
			
		}else if (impot.getPeriodicite().getPeriode() == Periode.TRIMESTRE) {
	        for (int trimestre = 1; trimestre <= 4; trimestre++) {
	        	Echeance echeance = new Echeance();
	            echeance.setJour(15); // Jour de l'échéance (exemple : le 15 de chaque mois)
	            echeance.setMois(trimestre+3); // Mois de l'échéance (1 pour janvier, 2 pour février, etc.)
	            echeance.setNumeroEcheance(trimestre);
	            int annee = 0;
	            if (trimestre == 4) {
	            	echeance.setMois(1);
	                annee = 1;
	            }
	            echeance.setAnnee(annee); 
	            
	            echeance.setTypeImpot(impot); 

	           
	            echeanceRepository.save(echeance);
	        }
	    } else if (impot.getPeriodicite().getPeriode() == Periode.SEMESTRE) {
	        for (int semestre = 1; semestre <= 2; semestre++) {
	        	Echeance echeance = new Echeance();
	            echeance.setJour(15); 
	            echeance.setMois(7); 
	            echeance.setNumeroEcheance(semestre);
	            int annee = 0;
	            if (semestre == 2) {
	            	echeance.setMois(1);
	                annee = 1;
	            }
	            echeance.setAnnee(annee); 
	            
	            echeance.setTypeImpot(impot); 
	            
	           
	            echeanceRepository.save(echeance);
	        }
	    } else if (impot.getPeriodicite().getPeriode() == Periode.ANNUELLE) {
	    	Echeance echeance = new Echeance();
            echeance.setJour(15); 
            echeance.setMois(1); 
            echeance.setNumeroEcheance(1);
         
           
            echeance.setAnnee(1); 
            
            echeance.setTypeImpot(impot); 
            
           
            echeanceRepository.save(echeance);
	    }
		
		TypeImpotDto savedImpot=new TypeImpotDto();
		
		savedImpot.setLibelle(impot1.getLibelle());
		PeriodeDto pd=new PeriodeDto();
		pd.setIdPeriodicite(impot1.getPeriodicite().getIdPeriodicite());
		pd.setPeriode(impot1.getPeriodicite().getPeriode());
		savedImpot.setPeriodicite(pd);
		return savedImpot;
	}


	@Override
	public List<TypeImpotDto> getAllImpots() {
		// TODO Auto-generated method stub
		return impotrepo.findAll().stream().map(TypeImpot::getImpot).collect(Collectors.toList());
	}

}
