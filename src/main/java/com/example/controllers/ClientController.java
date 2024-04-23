package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.ContribuableDtos;
import com.example.service.AdminService;
import com.example.service.CompteService;
import com.example.service.ContribuableService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")
public class ClientController {
	@Autowired
	private CompteService compteservice;
	@Autowired
	private AdminService adminservice;
	@Autowired
	private ContribuableService contribuableservice;
	
	
	 @GetMapping("/contribuable/{id}")
	    public ResponseEntity<?> findContribuableByIdCompte(@PathVariable("id") long id) {
	        ContribuableDtos contribuable = contribuableservice.findContribuableByIdCompte(id);
	        if (contribuable != null) {
	            return new ResponseEntity<>(contribuable, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Compte not found", HttpStatus.NOT_FOUND);
	        }
	    }
	
}
