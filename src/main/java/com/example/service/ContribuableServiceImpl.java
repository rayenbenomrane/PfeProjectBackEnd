package com.example.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.ContribuableDtos;
import com.example.entity.Activite;
import com.example.entity.Compte;
import com.example.entity.Contribuable;
import com.example.entity.FormeJuridique;
import com.example.entity.Pays;
import com.example.repository.ActiviteRepository;
import com.example.repository.CompteRepository;
import com.example.repository.ContribuableRepository;
import com.example.repository.FormeJuridiqueRepository;
import com.example.repository.PaysRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContribuableServiceImpl implements ContribuableService{
	@Autowired
	private ContribuableRepository contribuableRepo;
	@Autowired
	private CompteRepository compterepository;
	@Autowired
	private FormeJuridiqueRepository forme;
	@Autowired
	private PaysRepository paysr;
	@Autowired
	private ActiviteRepository activiterepo;
	

	@Override
	@Transactional
	public ContribuableDtos saveContribuable(ContribuableDtos cd) {
		Optional<Contribuable> existingContribuable = contribuableRepo.findByMatriculeFiscale(cd.getMatriculeFiscale());
	    if (existingContribuable.isPresent()) {
	        
	    	throw new RuntimeException("Le matricule fiscal existe déjà dans la base de données");
	    }
	    
		List<FormeJuridique> lesformes=forme.findByLibelle(cd.getFormeJuridique());
		List<Pays> lespays=paysr.findByLibelle(cd.getPays());
		List<Activite> lesactivites=activiterepo.findByLibelle(cd.getPays());
		if(lesformes.isEmpty()) {
			FormeJuridique formejuridique=new FormeJuridique();
			formejuridique.setLibelle(cd.getFormeJuridique());

			FormeJuridique formejuridiqueCree=forme.save(formejuridique);
			lesformes.add(formejuridiqueCree);
		}
		if(lespays.isEmpty()) {
			Pays pays=new Pays();
			pays.setLibelle(cd.getPays());

			Pays paysCree=paysr.save(pays);
			lespays.add(paysCree);
		}
		if(lesactivites.isEmpty()) {
			Activite activite=new Activite();
			activite.setLibelle(cd.getActivite());

			Activite activiteCree=activiterepo.save(activite);
			lesactivites.add(activiteCree);
		}
		Contribuable contribuable=new Contribuable();
		contribuable.setEmail(cd.getEmail());
		contribuable.setAdress(cd.getAdress());
		contribuable.setMatriculeFiscale(cd.getMatriculeFiscale());
		contribuable.setActivite(lesactivites.get(0));
		contribuable.setFormeJuridique(lesformes.get(0));
		contribuable.setPays(lespays.get(0));
		contribuable.setRaisonSocial(cd.getRaisonSocial());
		contribuable.setNomCommercial(cd.getNomCommercial());
		contribuable.setDateDeMatriculation(cd.getDateDeMatriculation());
		contribuable.setDirecteur(cd.getDirecteur());
		Contribuable contribuableCree=contribuableRepo.save(contribuable);
		ContribuableDtos contribuablecreeDtos=new ContribuableDtos();
		contribuablecreeDtos.setIdContribuable(contribuableCree.getIdContribuable());
		contribuablecreeDtos.setFormeJuridique(contribuableCree.getFormeJuridique().getLibelle());
		contribuablecreeDtos.setActivite(contribuableCree.getActivite().getLibelle());
		contribuablecreeDtos.setPays(contribuableCree.getPays().getLibelle());
		contribuablecreeDtos.setEmail(contribuableCree.getEmail());
		contribuablecreeDtos.setAdress(contribuableCree.getAdress());
		contribuablecreeDtos.setDateDeMatriculation(contribuableCree.getDateDeMatriculation());
		contribuablecreeDtos.setNomCommercial(contribuableCree.getNomCommercial());
		contribuablecreeDtos.setMatriculeFiscale(contribuableCree.getMatriculeFiscale());
		contribuablecreeDtos.setDirecteur(contribuableCree.getDirecteur());
		contribuablecreeDtos.setRaisonSocial(contribuableCree.getRaisonSocial());
		return contribuablecreeDtos;





	}

	@Override
	public ContribuableDtos findContribuable(int matriculeFiscale) {
	    Optional<Contribuable> contribuableOptional = contribuableRepo.findByMatriculeFiscale(matriculeFiscale);
	    if (contribuableOptional.isPresent()) {
	        Contribuable contribuable = contribuableOptional.get();

	        // Initialize lazy-loaded properties
	        Hibernate.initialize(contribuable.getFormeJuridique());
	        Hibernate.initialize(contribuable.getPays());
	        Hibernate.initialize(contribuable.getActivite());

	        ContribuableDtos contribuableDto = new ContribuableDtos();
	        contribuableDto.setIdContribuable(contribuable.getIdContribuable());
	        contribuableDto.setFormeJuridique(contribuable.getFormeJuridique().getLibelle());
	        contribuableDto.setActivite(contribuable.getActivite().getLibelle());
	        contribuableDto.setPays(contribuable.getPays().getLibelle());
	        contribuableDto.setEmail(contribuable.getEmail());
	        contribuableDto.setAdress(contribuable.getAdress());
	        contribuableDto.setDateDeMatriculation(contribuable.getDateDeMatriculation());
	        contribuableDto.setNomCommercial(contribuable.getNomCommercial());
	        contribuableDto.setMatriculeFiscale(contribuable.getMatriculeFiscale());
	        contribuableDto.setDirecteur(contribuable.getDirecteur());
	        contribuableDto.setRaisonSocial(contribuable.getRaisonSocial());

	        return contribuableDto;
	    } else {
	        return null;
	    }
	}

	@Override
	public List<ContribuableDtos> lesContribuables() {
		// TODO Auto-generated method stub
		return contribuableRepo.findAll().stream().map(Contribuable::getContribuable).collect(Collectors.toList());
	}

	@Override
	public ContribuableDtos findContribuableByIdCompte(long IdCompte) {
	Optional<Compte> compteTrouve=compterepository.findById(IdCompte);
	 if (compteTrouve.isPresent()) {
	        Compte compteTrouve1 = compteTrouve.get();

	        // Assuming there's a method to retrieve registration associated with the account
	        Contribuable contribuable = compteTrouve1.getInscription().getContribuable();
	        ContribuableDtos contribuableTrouve=new ContribuableDtos();
	        contribuableTrouve.setActivite(contribuable.getActivite().getLibelle());
	        contribuableTrouve.setFormeJuridique(contribuable.getFormeJuridique().getLibelle());
	        contribuableTrouve.setPays(contribuable.getPays().getLibelle());
	        contribuableTrouve.setAdress(contribuable.getAdress());
	        contribuableTrouve.setDateDeMatriculation(contribuable.getDateDeMatriculation());
	        contribuableTrouve.setDirecteur(contribuable.getDirecteur());
	        contribuableTrouve.setEmail(contribuable.getEmail());
	        contribuableTrouve.setIdContribuable(contribuable.getIdContribuable());
	        contribuableTrouve.setMatriculeFiscale(contribuable.getMatriculeFiscale());
	        contribuableTrouve.setNomCommercial(contribuable.getNomCommercial());
	        contribuableTrouve.setRaisonSocial(contribuable.getRaisonSocial());
	        return contribuableTrouve;
	 }else return null;

	 }


	}






