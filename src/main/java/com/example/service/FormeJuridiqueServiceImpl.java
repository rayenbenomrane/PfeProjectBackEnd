package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.FormeJuridiqueDtos;
import com.example.entity.FormeJuridique;
import com.example.repository.FormeJuridiqueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FormeJuridiqueServiceImpl implements FormeJuridiqueService{

	@Autowired
	private FormeJuridiqueRepository formeJuridiqueRepo;

	@Override
	public FormeJuridiqueDtos saveFormeJuridique(FormeJuridiqueDtos fd) {

		FormeJuridique formejuridique=new FormeJuridique();
		formejuridique.setLibelle(fd.getLibelle());

		FormeJuridique formejuridiqueCree=formeJuridiqueRepo.save(formejuridique);
		FormeJuridiqueDtos formejuridiqueCreeDtos=new FormeJuridiqueDtos();
		formejuridiqueCreeDtos.setIdFormeJuridique(formejuridiqueCree.getIdFormeJuridique());

		formejuridiqueCreeDtos.setLibelle(formejuridiqueCree.getLibelle());
		return formejuridiqueCreeDtos;




	}

}
