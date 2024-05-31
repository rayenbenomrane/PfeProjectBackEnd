package com.example.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptException;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.CalculationRequest;
import com.example.dtos.CompteById;
import com.example.dtos.ContribuableDtos;
import com.example.dtos.DetailDeclarationDto;
import com.example.dtos.MotDePassdto;
import com.example.dtos.NotificationDto;
import com.example.dtos.ObligationresponseDto;
import com.example.dtos.PaiementDto;
import com.example.dtos.PaymentRequest;
import com.example.dtos.PaymentResponse;
import com.example.dtos.PaymentStatus;
import com.example.dtos.ReclamationDto;
import com.example.dtos.SaveDeclaration;
import com.example.dtos.SaveMontant;
import com.example.dtos.TypeDeclarationDto;
import com.example.entity.Contribuable;
import com.example.entity.Declaration;
import com.example.entity.DetailImpot;
import com.example.entity.Reclamation;
import com.example.entity.TypeImpot;
import com.example.repository.ContribuableRepository;
import com.example.repository.TypeImpotRepository;
import com.example.service.CompteService;
import com.example.service.ContribuableService;
import com.example.service.DeclarationService;
import com.example.service.DetailDeclarationService;
import com.example.service.KonnectPaymentService;
import com.example.service.NotificationService;
import com.example.service.ObligationFiscaleService;
import com.example.service.PaiementService;
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
	@Autowired
	private DetailDeclarationService detaildeclarationservice;
	@Autowired
	private TypeImpotRepository impotrepo;
	@Autowired
	private KonnectPaymentService konnectService;
	@Autowired
	private CompteService compteservice;
	@Autowired
	private PaiementService paiementService;
	@Autowired
	private NotificationService notifservice;


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
	 @PostMapping("/calculate")
	 public double calculate(@RequestBody CalculationRequest request) throws ScriptException {
	     String formula = request.getFormula();
	     for (String key : request.getValues().keySet()) {
	         formula = formula.replaceAll("\\b" + Pattern.quote(key) + "\\b", Matcher.quoteReplacement(String.valueOf(request.getValues().get(key))));
	     }

	     try (Context context = Context.create()) {
	         Value result = context.eval("js", formula);
	         return result.asDouble();
	     } catch (Exception e) {
	         e.printStackTrace();
	         throw new RuntimeException("Error evaluating the formula: " + formula, e);
	     }
	 }

	 @PutMapping("/update")
	 public ResponseEntity<?> updateDetail(@RequestBody DetailDeclarationDto detailDeclarationDto) {
	     boolean isUpdated = detaildeclarationservice.updateDetail(detailDeclarationDto);
	     if (isUpdated) {
	         return ResponseEntity.status(HttpStatus.ACCEPTED).body(isUpdated);
	     } else {
	         return ResponseEntity.status(404).body("Detail not found");
	     }
	 }
	 @GetMapping("/formuledeCalcul")
	 public ResponseEntity<?> findbylibelleimpot(@RequestParam("libelle") String libelle){
		 Optional<TypeImpot> type=impotrepo.findByLibelle(libelle);
		 if(type.isPresent()) {
			 return ResponseEntity.ok(type.get());
		 }else return ResponseEntity.status(404).body("impot not found");
	 }
	 @PutMapping("/updateMontant")
	 public ResponseEntity<?> updateMontant(@RequestBody SaveMontant montant) {
	     boolean isUpdated = declarationService.updateMontantaCalculer(montant);
	     if (isUpdated) {
	         return ResponseEntity.status(HttpStatus.ACCEPTED).body(isUpdated);
	     } else {
	         return ResponseEntity.status(404).body("Montant not found");
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
	 @PostMapping("/init")
	 public ResponseEntity<?> initPayment(@RequestBody PaymentRequest paymentRequest) {
	     PaymentResponse response = konnectService.initiatePayment(paymentRequest);
	     return ResponseEntity.ok(response);
	 }

	 @GetMapping("/{paymentId}")
	 public ResponseEntity<?> getPaymentStatus(@PathVariable String paymentId) {
	     PaymentStatus status = konnectService.getPaymentStatus(paymentId);
	     return ResponseEntity.ok(status);
	 }

	 @GetMapping("/getCompte")
	 public ResponseEntity<?> getCompteById(@RequestParam("idcompte") Long idCompte){
		 CompteById compte=compteservice.getCompteByid(idCompte);
		 if(compte!=null) {
		 return ResponseEntity.ok(compte);
	 }else return ResponseEntity.status(404).body("Compte not found");
		 }
	 @PostMapping("/savePaiement")
	 public ResponseEntity<?> savePaiement(@RequestBody PaiementDto paiement)
	 {
		 boolean saved=paiementService.createPaiement(paiement);
		 if(saved) {
			    return ResponseEntity.status(HttpStatus.ACCEPTED).body(saved);
		 }return ResponseEntity.status(404).body("Paiement not found");
	 }
	 @GetMapping("/notification")
	 public ResponseEntity<List<NotificationDto>> getAllNotifications(@RequestParam("matricule") int matricule) {
	     try {
	         List<NotificationDto> typeList = notifservice.getNotificationByMatricule(matricule);
	         return ResponseEntity.ok(typeList);
	     } catch (ExpiredJwtException ex) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	     }
	 }
	 @PutMapping("/updatechecked")
	 public ResponseEntity<?> updateNotification(@RequestParam("id") long id) {
	        notifservice.updateNotification(id);
	        return ResponseEntity.ok().build();
	    }
	 @PutMapping("/updatedeleted")
	 public ResponseEntity<?> updatedeleted(@RequestParam("id") long id) {
	        notifservice.updateDeleted(id);
	        return ResponseEntity.ok().build();
	    }
	 @PutMapping("/updatepassword")
	 public ResponseEntity<?> updatePassword(@RequestBody MotDePassdto dd){
	      boolean saved=compteservice.updatepassword(dd);
	      if(saved) {
	         return ResponseEntity.ok(saved);
	      }else return ResponseEntity.status(404).body("Compte not found");
	 }
	 @GetMapping("/reclamationsByContribuable")
     public ResponseEntity<?> reclamationByContribuable(@RequestParam("matriculeFiscale") int matriculeFiscale) {
         List<Reclamation> listeReclamations= reclamationservice.reclamationByContribuable(matriculeFiscale);
             if (listeReclamations != null) 
                 return ResponseEntity.ok(listeReclamations);

             return ResponseEntity.status(404).body("no reclamation found");

     }
	 @PutMapping("/acceptreclamation")
	 public ResponseEntity<?> acceptReclamation(@RequestParam("id") long id){
		 
		 boolean updated=reclamationservice.updateEtat(id);
		 if(updated) {
	         return ResponseEntity.ok(updated);
	      }else return ResponseEntity.status(404).body("Compte not found");
		 
	 }
	 @PutMapping("/refusreclamation")
	 public ResponseEntity<?> refusReclamation(@RequestParam("id") long id){
		 
		 boolean updated=reclamationservice.refusEtat(id);
		 if(updated) {
	         return ResponseEntity.ok(updated);
	      }else return ResponseEntity.status(404).body("Compte not found");
		 
	 }
}