package com.example.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.ContribuableDtos;
import com.example.dtos.DetailDeclarationDto;
import com.example.dtos.ObligationDto;
import com.example.dtos.ObligationresponseDto;
import com.example.dtos.ReclamationDto;
import com.example.dtos.SaveDeclaration;
import com.example.dtos.TypeDeclarationDto;
import com.example.entity.Contribuable;
import com.example.entity.DetailImpot;
import com.example.entity.Reclamation;
import com.example.repository.ContribuableRepository;
import com.example.service.ContribuableService;
import com.example.service.DeclarationService;
import com.example.service.ObligationFiscaleService;
import com.example.service.ReclamationService;
import com.example.service.TypeDeclarationService;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")
public class ClientController {

	@Autowired
	private ContribuableService contribuableservice;
	@Autowired
	private ReclamationService reclamationservice;
	@Autowired
	private DeclarationService declarationService;
	@Autowired 
	private ContribuableRepository contribuableRepository ;
	@Autowired
	private ObligationFiscaleService obligationFiscaleService;
	@Autowired
	private TypeDeclarationService typeservice;

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
	     Reclamation saved = reclamationservice.saveReclamation(reclamationDto);
	     if (saved!=null) {
	         return ResponseEntity.ok(saved);
	     } else {
	         return ResponseEntity.badRequest().body(null);
	     }
	 }
	 @PostMapping("/declaration")
	 public ResponseEntity<?> createDeclaration(@RequestBody SaveDeclaration declarationDtos) {
	     Map<DetailImpot, DetailDeclarationDto> detailMap = declarationService.saveDeclaration(declarationDtos);
	     
	     if (detailMap.isEmpty()) {
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de création de déclaration!");
	     } else {
	         return ResponseEntity.status(HttpStatus.CREATED).body(detailMap);
	     }
	 }
	 @GetMapping("/obligationContribuable/{contribuableId}")
	 public ResponseEntity<?> getObligationsByContribuable(@PathVariable Long contribuableId) {
	     Optional<Contribuable> cd = this.contribuableRepository.findById(contribuableId);
	     if(cd.isPresent()) {
	     List<ObligationresponseDto> obligations = obligationFiscaleService.getlesObligationsdeContribuable(cd.get());
	     return ResponseEntity.ok(obligations);
	 }else return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de création de déclaration!");
	     }
	 @GetMapping("/contribuableMatricule")
	 public ResponseEntity<?> findByMatriculeFiscale(@RequestParam("matriculeFiscale") int matriculeFiscale) {
	 		ContribuableDtos contribuable = contribuableservice.findContribuable(matriculeFiscale);
	         if (contribuable != null)
	         	return ResponseEntity.ok(contribuable);

	         return ResponseEntity.notFound().build();

	 }
	 @GetMapping("/typedeclaration")
	 public ResponseEntity<List<TypeDeclarationDto>> getAllcompte() {
	     try {
	         List<TypeDeclarationDto> typeList = typeservice.lesTypes();
	         return ResponseEntity.ok(typeList);
	     } catch (ExpiredJwtException ex) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	     }
	 }
}
