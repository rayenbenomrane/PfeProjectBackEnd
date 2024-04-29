package com.example.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.ReclamationDto;
import com.example.entity.Contribuable;
import com.example.entity.Reclamation;
import com.example.enums.Etat;
import com.example.repository.ContribuableRepository;
import com.example.repository.ReclamationRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ReclamationServiceImpl implements ReclamationService{

	
	@Autowired 
	private ReclamationRepository reclamationrepo;
	@Autowired
	private ContribuableRepository contribuableRepository;
	
	
	@Override
	public Reclamation saveReclamation(ReclamationDto c) {
	   
	    Contribuable existingContribuable = this.contribuableRepository.findById(c.getContribuable().getIdContribuable())
	            .orElseThrow(() -> new IllegalArgumentException("Contribuable not found"));

	    // Create a new Reclamation object and set its attributes
	    Reclamation newReclamation = new Reclamation();
	    newReclamation.setContenu(c.getContenu());
	    newReclamation.setEtat(Etat.EN_ATTENTE);
	    newReclamation.setSolution(null); // Assuming solution can be null initially
	    newReclamation.setDateReclamation(new Date());
	    newReclamation.setTitre(c.getTitre());

	    // Set the existing Contribuable in the Reclamation entity
	    newReclamation.setContribuable(existingContribuable);

	    // Save the Reclamation entity
	    this.reclamationrepo.save(newReclamation);

	    return newReclamation; // Indicate that the save operation is attempted
	}


}
