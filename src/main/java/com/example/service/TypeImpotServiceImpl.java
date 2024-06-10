package com.example.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.stereotype.Service;

import com.example.dtos.ImpotDto;
import com.example.dtos.PeriodeDto;
import com.example.dtos.TypeImpotDto;
import com.example.entity.Echeance;
import com.example.entity.Periodicite;
import com.example.entity.TypeImpot;
import com.example.enums.Periode;
import com.example.repository.EcheanceRepository;
import com.example.repository.PeriodiciteRepository;
import com.example.repository.TypeImpotRepository;

import jakarta.transaction.Transactional;


@Service
public class TypeImpotServiceImpl implements TypeImpotService{


	@Autowired
	private EcheanceRepository echeanceRepository;
	
	
	@Autowired
	private TypeImpotRepository impotrepo;

	@Override
	public TypeImpotDto saveImpot(TypeImpotDto td) {
		TypeImpot impot=new TypeImpot();
		impot.setLibelle(td.getLibelle());
		 

impot.setPeriodicite(td.getPeriodicite());

	
		TypeImpot  impot1=impotrepo.save(impot);
		if(impot.getPeriodicite()==Periode.MENSUELLE) {
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
			
		}else if (impot.getPeriodicite() == Periode.TRIMESTRE) {
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
	    } else if (impot.getPeriodicite() == Periode.SEMESTRE) {
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
	    } else if (impot.getPeriodicite() == Periode.ANNUELLE) {
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
	
		savedImpot.setPeriodicite(impot1.getPeriodicite());
		return savedImpot;
	}


	@Override
	public List<TypeImpotDto> getAllImpots() {
		// TODO Auto-generated method stub
		return impotrepo.findAll().stream().map(TypeImpot::getImpot).collect(Collectors.toList());
	}


	@Override
	public TypeImpotDto findTypeImpotbyLibelle(String libelle) {
		Optional<TypeImpot> typetrouve=impotrepo.findByLibelle(libelle);
		if(typetrouve.get()!=null) {
			TypeImpotDto impot=new TypeImpotDto();
			impot.setLibelle(typetrouve.get().getLibelle());

			
			impot.setPeriodicite(typetrouve.get().getPeriodicite());
			return impot;
		}else return null;
	}


	@Override
	public boolean updateImpot(ImpotDto id) {
		Optional<TypeImpot> typetrouve=impotrepo.findByLibelle(id.getLibelle());
		if(typetrouve.get()!=null) {
			typetrouve.get().setFormule(id.getFormule());
			impotrepo.save(typetrouve.get());
			return true;
		}else return false;

	}
	@Override
	@Transactional
	 public TypeImpot updateTypeImpot(TypeImpotDto td) {
		 Optional<TypeImpot> type=impotrepo.findById(td.getId());
		 if(type.isPresent()) {
			 type.get().setLibelle(td.getLibelle());
			
			 if(!type.get().getPeriodicite().equals(td.getPeriodicite())) {
				
				 type.get().setPeriodicite(td.getPeriodicite());
				 echeanceRepository.deleteByTypeImpot(type.get());
				 createEcheances(type.get());
			 }
			 impotrepo.save(type.get());
			 return type.get();
		 }return null;
	 }
	 

	    private void createEcheances(TypeImpot typeImpot) {
	        if (typeImpot.getPeriodicite() == Periode.MENSUELLE) {
	            for (int mois = 1; mois <= 12; mois++) {
	                Echeance echeance = new Echeance();
	                echeance.setJour(15);
	                echeance.setMois(mois);
	                echeance.setNumeroEcheance(mois);

	                int annee = 0;
	                if (mois == 12) {
	                    echeance.setMois(1);
	                    annee = 1;
	                }

	                echeance.setAnnee(annee);
	                echeance.setTypeImpot(typeImpot);
	                echeanceRepository.save(echeance);
	            }
	        } else if (typeImpot.getPeriodicite() == Periode.TRIMESTRE) {
	            for (int trimestre = 1; trimestre <= 4; trimestre++) {
	                Echeance echeance = new Echeance();
	                echeance.setJour(15);
	                echeance.setMois(trimestre + 3);
	                echeance.setNumeroEcheance(trimestre);
	                int annee = 0;
	                if (trimestre == 4) {
	                    echeance.setMois(1);
	                    annee = 1;
	                }
	                echeance.setAnnee(annee);
	                echeance.setTypeImpot(typeImpot);
	                echeanceRepository.save(echeance);
	            }
	        } else if (typeImpot.getPeriodicite() == Periode.SEMESTRE) {
	            for (int semestre = 1; semestre <= 2; semestre++) {
	                Echeance echeance = new Echeance();
	                echeance.setJour(15);
	                echeance.setMois(semestre + 6);
	                echeance.setNumeroEcheance(semestre);
	                int annee = 0;
	                if (semestre == 2) {
	                    echeance.setMois(1);
	                    annee = 1;
	                }
	                echeance.setAnnee(annee);
	                echeance.setTypeImpot(typeImpot);
	                echeanceRepository.save(echeance);
	            }
	        } else if (typeImpot.getPeriodicite() == Periode.ANNUELLE) {
	            Echeance echeance = new Echeance();
	            echeance.setJour(15);
	            echeance.setMois(1);
	            echeance.setNumeroEcheance(1);
	            echeance.setAnnee(1);
	            echeance.setTypeImpot(typeImpot);
	            echeanceRepository.save(echeance);
	        }
	    }

	
	
	
}
