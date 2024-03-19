package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.ContribuableDtos;
import com.example.entity.Contribuable;
import com.example.repository.ContribuableRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContribuableServiceImpl implements ContribuableService{
	@Autowired
	private ContribuableRepository contribuableRepo;

	@Override
	@Transactional
	public ContribuableDtos saveContribuable(ContribuableDtos cd) {
		Contribuable contribuable=new Contribuable();
		contribuable.setEmail(cd.getEmail());
		contribuable.setAdress(cd.getAdress());
		contribuable.setMatriculeFiscale(cd.getMatriculeFiscale());
		contribuable.setActivite(cd.getActivite());
		contribuable.setRaisonSocial(cd.getRaisonSocial());
		contribuable.setFormeJuridique(cd.getFormeJuridique());
		contribuable.setPays(cd.getPays());
		contribuable.setNomCommercial(cd.getNomCommercial());
		contribuable.setDateDeMatriculation(cd.getDateDeMatriculation());
		contribuable.setPays(cd.getPays());
		contribuable.setDirecteur(cd.getDirecteur());
		Contribuable contribuableCree=contribuableRepo.save(contribuable);
		ContribuableDtos contribuablecreeDtos=new ContribuableDtos();
		contribuablecreeDtos.setIdContribuable(contribuableCree.getIdContribuable());
		contribuablecreeDtos.setFormeJuridique(contribuableCree.getFormeJuridique());
		contribuablecreeDtos.setActivite(contribuableCree.getActivite());
		contribuablecreeDtos.setPays(contribuableCree.getPays());
		contribuablecreeDtos.setEmail(contribuableCree.getEmail());
		contribuablecreeDtos.setAdress(contribuableCree.getAdress());
		contribuablecreeDtos.setDateDeMatriculation(contribuableCree.getDateDeMatriculation());
		contribuablecreeDtos.setNomCommercial(contribuableCree.getNomCommercial());
		contribuablecreeDtos.setMatriculeFiscale(contribuableCree.getMatriculeFiscale());
		contribuablecreeDtos.setPays(contribuableCree.getPays());
		contribuablecreeDtos.setDirecteur(contribuableCree.getDirecteur());
		contribuablecreeDtos.setRaisonSocial(contribuableCree.getRaisonSocial());
		return contribuablecreeDtos;
		
		
		
		
		
	}

}
