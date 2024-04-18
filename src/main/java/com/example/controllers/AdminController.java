package com.example.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.CompteDto;
import com.example.dtos.UserDtos;
import com.example.service.AdminService;
import com.example.service.CompteService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private CompteService compteservice;
	@Autowired
	private AdminService adminservice;

	@PostMapping("/Compte")
	public ResponseEntity<?> createCompte(@RequestBody CompteDto compteDto ){
		boolean compteCree=compteservice.saveCompte(compteDto);
		if(!compteCree) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de creation de compte!");
	    return ResponseEntity.status(HttpStatus.CREATED).body(compteCree);

	}
	@GetMapping("/Inscription")
	public ResponseEntity<List<UserDtos>> getAllInscription(){



		List<UserDtos> inscriptionList=adminservice.getAllInscription();
		return ResponseEntity.ok(inscriptionList);
	}
	@PostMapping("/valider")
	public ResponseEntity<?> validerCompte(@RequestBody UserDtos userDTO){
		CompteDto compteCree=adminservice.acceptInscri(userDTO);
		try {
			adminservice.sendVerificationEmail(userDTO);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(compteCree==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("problem de mise a jour");
		return ResponseEntity.status(HttpStatus.CREATED).body(compteCree);
	}
	@GetMapping("/lescompte")
	public ResponseEntity<List<CompteDto>> getAllcompte(){



		List<CompteDto> inscriptionList=compteservice.getAllCompte();
		return ResponseEntity.ok(inscriptionList);
	}
	@PostMapping("/bloqueCompte")
	public ResponseEntity<?> bloqueCompte(@RequestBody CompteDto compteDto ){
		boolean compteCree=compteservice.blocageCompte(compteDto);
		if(!compteCree) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de creation de compte!");
	    return ResponseEntity.status(HttpStatus.CREATED).body(compteCree);

	}



}
