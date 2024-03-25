package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.CompteDto;
import com.example.dtos.ContribuableDtos;
import com.example.service.CompteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private CompteService compteservice;

	@PostMapping("/Compte")
	public ResponseEntity<?> createCompte(@RequestBody CompteDto compteDto ){
		boolean compteCree=compteservice.saveCompte(compteDto);
		if(compteCree==false) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de creation de compte!");
	    return ResponseEntity.status(HttpStatus.CREATED).body(compteCree);
		
	}
	
	

}
