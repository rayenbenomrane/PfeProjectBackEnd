package com.example.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.CompteDto;
import com.example.dtos.ContribuableDtos;
import com.example.dtos.DetailImpotDto;
import com.example.dtos.ImpotDto;
import com.example.dtos.ObligationresponseDto;
import com.example.dtos.PeriodeDto;
import com.example.dtos.SaveObligation;
import com.example.dtos.TypeImpotDto;
import com.example.dtos.UpdatePasswordDto;
import com.example.dtos.UserDtos;
import com.example.entity.Contribuable;
import com.example.entity.Declaration;
import com.example.entity.DetailImpot;
import com.example.entity.TypeImpot;
import com.example.enums.Periode;
import com.example.repository.ContribuableRepository;
import com.example.service.AdminService;
import com.example.service.CompteService;
import com.example.service.ContribuableService;
import com.example.service.DeclarationService;
import com.example.service.DetailImpotService;
import com.example.service.ObligationFiscaleService;
import com.example.service.PeriodiciteService;
import com.example.service.TypeImpotService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.lang.Arrays;
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
	
	@Autowired
	private TypeImpotService impotservice;
	@Autowired
	private DetailImpotService detailservice;
	@Autowired
	private DeclarationService declarationService;

	@Autowired
	private ContribuableRepository contribuableRepository ;
	@Autowired
	private ObligationFiscaleService obligationFiscaleService;

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
	 @PostMapping("/typeImpot")
	 public ResponseEntity<?> createImpot(@RequestBody TypeImpotDto typeImpot) {
	     try {
	         TypeImpotDto impotCreated = impotservice.saveImpot(typeImpot);
	         return ResponseEntity.status(HttpStatus.CREATED).body(impotCreated);
	     } catch (DataIntegrityViolationException e) {
	         // Handle unique constraint violation exception
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Libelle must be unique.");
	     }
	 }
	 @GetMapping("/lesperiodes")
	 public ResponseEntity<List<Periode>> getPeriodes() {
		    List<Periode> periodes = java.util.Arrays.asList(Periode.values());
		    return ResponseEntity.ok(periodes);
		}
	 @GetMapping("/lesimpots")
	 public ResponseEntity<List<TypeImpotDto>> getAllimpots() {
	     try {
	         List<TypeImpotDto> impotsList = impotservice.getAllImpots();
	         return ResponseEntity.ok(impotsList);
	     } catch (ExpiredJwtException ex) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	     }
	 }
	 @PostMapping("/detail")
	 public ResponseEntity<?> createActivite(@RequestBody DetailImpotDto pi){
	 	DetailImpotDto payscree=detailservice.saveDetailImpot(pi);
	 	if(payscree==null)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de creation de detail!");
	     return ResponseEntity.status(HttpStatus.CREATED).body(payscree);
	 }
	 @GetMapping("/detailimpot")
	 public ResponseEntity<?> findByimpot(@RequestParam("libelle") String libelle) {
	 	List<DetailImpot> listDetail= detailservice.findbytypeImpot(libelle);
	         if (listDetail != null)
	         	return ResponseEntity.ok(listDetail);

	         return ResponseEntity.notFound().build();

	 }
	 @GetMapping("/typeimpot")
	 public ResponseEntity<?> findBytypeimpot(@RequestParam("libelle") String libelle) {
	 	 TypeImpotDto  impot= impotservice.findTypeImpotbyLibelle(libelle);
	         if (impot != null)
	         	return ResponseEntity.ok(impot);

	         return ResponseEntity.notFound().build();

	 }
	 @PutMapping("/updateimpot1")
	 public ResponseEntity<?> updateimpot(@RequestBody ImpotDto impotDto) {
	     boolean isUpdated = impotservice.updateImpot(impotDto);
	     if (isUpdated) {
	         return ResponseEntity.status(HttpStatus.ACCEPTED).body(isUpdated);
	     } else {
	         return ResponseEntity.status(404).body("impot not found");
	     }
	 }
	 @PostMapping("/contribuable")
	 public ResponseEntity<?> createContribuable(@RequestBody ContribuableDtos contribuableDto) {
	     try {
	         ContribuableDtos savedContribuable = contribuableservice.saveContribuable(contribuableDto);
	         return ResponseEntity.status(HttpStatus.CREATED).body(savedContribuable);
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la création du contribuable: " + e.getMessage());
	     }
	 }


	 @GetMapping("/declarationbycontribuable")
	 public ResponseEntity<?> getDeclarationsByMatriculeFiscale(@RequestParam("matriculeFiscale") int matriculeFiscale) {
	     List<Declaration> declarations = declarationService.getDeclarationsByMatriculeFiscale(matriculeFiscale);
	     if (declarations.isEmpty()) {
	         return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No declarations found ");
	     }
	     return ResponseEntity.ok(declarations);
	 }
	 @PutMapping("/updateimpot")
	 public ResponseEntity<?> updateTypeImpot(@RequestBody TypeImpotDto request) {
	     TypeImpot result =impotservice.updateTypeImpot(request); 
	     if (result!=null) {
	         return ResponseEntity.ok(result);
	     } else {
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Impot not found!"); 
	     }
	 }
	 @GetMapping("/obligationContribuable/{contribuableId}")
	 public ResponseEntity<?> getObligationsByContribuable(@PathVariable Long contribuableId) {
	     Optional<Contribuable> cd = this.contribuableRepository.findById(contribuableId);
	     if(cd.isPresent()) {
	     List<ObligationresponseDto> obligations = obligationFiscaleService.getlesObligationsdeContribuable(cd.get());
	     return ResponseEntity.ok(obligations);
	 }else return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de consulter de déclaration!");
	     }
	 @GetMapping("/contribuableMatricule")
	 public ResponseEntity<?> findByMatriculeFiscale(@RequestParam("matriculeFiscale") int matriculeFiscale) {
	 		ContribuableDtos contribuable = contribuableservice.findContribuable(matriculeFiscale);
	         if (contribuable != null)
	         	return ResponseEntity.ok(contribuable);

	         return ResponseEntity.notFound().build();

	 }
	 @PostMapping("/obligation")
	 public ResponseEntity<?> createobligation(@RequestBody SaveObligation obligation){
		 
		 boolean saved=obligationFiscaleService.saveObligation(obligation);
		 if(saved) {
			 return ResponseEntity.ok(saved);
		 }else  return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de creation de obligation!");
	 }
	 
}
