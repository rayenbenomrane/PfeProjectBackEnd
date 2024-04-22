package com.example.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.CompteDto;
import com.example.dtos.ContribuableDtos;
import com.example.dtos.UpdatePasswordDto;
import com.example.dtos.UserDtos;
import com.example.service.AdminService;
import com.example.service.CompteService;
import com.example.service.ContribuableService;

import io.jsonwebtoken.ExpiredJwtException;
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
	@Autowired
	private ContribuableService contribuableservice;
		
	 @ExceptionHandler(ExpiredJwtException.class)
	    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
	        // Log the exception or handle it as needed
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token has expired");
	    }
	 @PostMapping("/Compte")
	 public ResponseEntity<?> createCompte(@RequestBody CompteDto compteDto ){
	     try {
	         boolean compteCree = compteservice.saveCompte(compteDto);
	         if(!compteCree) {
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de creation de compte!");
	         }
	         return ResponseEntity.status(HttpStatus.CREATED).body(compteCree);
	     } catch (ExpiredJwtException ex) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token has expired");
	     }
	 }

	 @GetMapping("/Inscription")
	 public ResponseEntity<List<UserDtos>> getAllInscription() {
	     try {
	         List<UserDtos> inscriptionList = adminservice.getAllInscription();
	         return ResponseEntity.ok(inscriptionList);
	     } catch (ExpiredJwtException ex) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	     }
	 }
	 @PostMapping("/valider")
	 public ResponseEntity<?> validerCompte(@RequestBody UserDtos userDTO) {
	     try {
	         CompteDto compteCree = adminservice.acceptInscri(userDTO);
	         adminservice.sendVerificationEmail(userDTO);
	         
	         if (compteCree == null) {
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Problem de mise a jour");
	         }
	         
	         return ResponseEntity.status(HttpStatus.CREATED).body(compteCree);
	     } catch (ExpiredJwtException ex) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token has expired");
	     } catch (UnsupportedEncodingException | MessagingException e) {
	         // Handle other exceptions as needed
	         e.printStackTrace(); // Example: Log the exception
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
	     }
	 }
	 @GetMapping("/lescompte")
	 public ResponseEntity<List<CompteDto>> getAllcompte() {
	     try {
	         List<CompteDto> compteList = compteservice.getAllCompte();
	         return ResponseEntity.ok(compteList);
	     } catch (ExpiredJwtException ex) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	     }
	 }
	 @PostMapping("/bloqueCompte")
	 public ResponseEntity<?> bloqueCompte(@RequestBody CompteDto compteDto) {
	     try {
	         boolean compteCree = compteservice.blocageCompte(compteDto);
	         if (!compteCree) {
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de blocage de compte!");
	         }
	         return ResponseEntity.status(HttpStatus.CREATED).body(compteCree);
	     } catch (ExpiredJwtException ex) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token has expired");
	     }
	 }
	 @PostMapping("/debloqueCompte")
	 public ResponseEntity<?> deblocageCompte(@RequestBody CompteDto compteDto) {
	     try {
	         boolean compteCree = compteservice.AcceptCompte(compteDto);
	         if (!compteCree) {
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de deblocage de compte!");
	         }
	         return ResponseEntity.status(HttpStatus.CREATED).body(compteCree);
	     } catch (ExpiredJwtException ex) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token has expired");
	     }
	 }
	 @GetMapping("/lesContribuables")
	 public ResponseEntity<List<ContribuableDtos>> getAllcontribuable() {
	     try {
	         List<ContribuableDtos> contribuableList = contribuableservice.lesContribuables();
	         return ResponseEntity.ok(contribuableList);
	     } catch (ExpiredJwtException ex) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	     }
	 }
	 @PostMapping("/changepassword")
	 public ResponseEntity<?> changePswword(@RequestBody UpdatePasswordDto up){
	     CompteDto cd=adminservice.changePassword(up);
	     if(cd==null) {
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de modifer password!");
	     }
	      return ResponseEntity.status(HttpStatus.CREATED).body(cd);
	 }




}
