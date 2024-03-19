package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.ActiviteDtos;
import com.example.dtos.ContribuableDtos;
import com.example.dtos.FormeJuridiqueDtos;
import com.example.dtos.PaysDtos;

import com.example.service.ActiviteService;

import com.example.service.ContribuableService;
import com.example.service.FormeJuridiqueService;
import com.example.service.PaysService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

private ActiviteService activiteservice;
private FormeJuridiqueService formejuridiqueservice;
private ContribuableService contribuableservice;
private PaysService paysservice;

@PostMapping("/formejuridique")
public ResponseEntity<?> createFormejuridique(@RequestBody FormeJuridiqueDtos formejuridiqueDtos){
	FormeJuridiqueDtos formeJuridiqueCree=formejuridiqueservice.saveFormeJuridique(formejuridiqueDtos);
	if(formeJuridiqueCree==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de creation de forme juridique!");
    return ResponseEntity.status(HttpStatus.CREATED).body(formeJuridiqueCree);
		
}

@PostMapping("/contribuable")
public ResponseEntity<?> createContribuable(@RequestBody ContribuableDtos contribuableDtos ){
	ContribuableDtos contribuableCree=contribuableservice.saveContribuable(contribuableDtos);
	if(contribuableCree==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de creation de Contribuable!");
    return ResponseEntity.status(HttpStatus.CREATED).body(contribuableCree);
	
}
@PostMapping("/activite")
public ResponseEntity<?> createActivite(@RequestBody ActiviteDtos activiteDtos){
	ActiviteDtos activitecree=activiteservice.saveActivite(activiteDtos);
	if(activitecree==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de creation d'activite!");
    return ResponseEntity.status(HttpStatus.CREATED).body(activitecree);
}
@PostMapping("/pays")
public ResponseEntity<?> createActivite(@RequestBody PaysDtos paysDtos){
	PaysDtos payscree=paysservice.savePays(paysDtos);
	if(payscree==null)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de creation d'pays!");
    return ResponseEntity.status(HttpStatus.CREATED).body(payscree);
}


}
