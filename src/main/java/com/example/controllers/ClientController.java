package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.ContribuableDtos;
import com.example.dtos.ReclamationDto;

import com.example.service.ContribuableService;
import com.example.service.ReclamationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")
public class ClientController {
	
	@Autowired
	private ContribuableService contribuableservice;
	@Autowired
	private ReclamationService reclamationservice;
	
	 @GetMapping("/contribuable/{id}")
	    public ResponseEntity<?> findContribuableByIdCompte(@PathVariable("id") long id) {
	        ContribuableDtos contribuable = contribuableservice.findContribuableByIdCompte(id);
	        if (contribuable != null) {
	            return new ResponseEntity<>(contribuable, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Compte not found", HttpStatus.NOT_FOUND);
	        }
	    }
	 @PostMapping("/savereclamation")
	 public ResponseEntity<?> saveReclamation(@RequestBody ReclamationDto reclamationDto) {
	     Boolean saved = reclamationservice.saveReclamation(reclamationDto);
	     if (saved) {
	         return ResponseEntity.ok("Reclamation saved successfully");
	     } else {
	         return ResponseEntity.badRequest().body("Failed to save reclamation");
	     }
	 }
}
