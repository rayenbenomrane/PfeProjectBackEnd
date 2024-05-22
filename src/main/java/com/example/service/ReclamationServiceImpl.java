package com.example.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.ReclamationDto;

import com.example.entity.Declaration;
import com.example.entity.Reclamation;
import com.example.enums.Etat;

import com.example.repository.DeclarationRepository;
import com.example.repository.ReclamationRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ReclamationServiceImpl implements ReclamationService{


	@Autowired
	private ReclamationRepository reclamationrepo;
	@Autowired
	private DeclarationRepository declarationrepo;


	@Override
	public Reclamation saveReclamation(ReclamationDto c) {
if(c.getIdDeclaration()!=0l) {
	    Optional<Declaration> declaration=declarationrepo.findById(c.getIdDeclaration());

	 if(declaration.isPresent()) {
	    Reclamation newReclamation = new Reclamation();
	    newReclamation.setContenu(c.getContenu());
	    newReclamation.setEtat(Etat.EN_ATTENTE);
	    newReclamation.setSolution(null); 
	    newReclamation.setDateReclamation(new Date());
	    newReclamation.setTitre(c.getTitre());
	    newReclamation.setDeclaration(declaration.get());
	    this.reclamationrepo.save(newReclamation);
	    return newReclamation; 
	 }else return null;
}else {
	Reclamation newReclamation = new Reclamation();
    newReclamation.setContenu(c.getContenu());
    newReclamation.setEtat(Etat.EN_ATTENTE);
    newReclamation.setSolution(null); 
    newReclamation.setDateReclamation(new Date());
    newReclamation.setTitre(c.getTitre());
    this.reclamationrepo.save(newReclamation);
    return newReclamation;
	}


}
}