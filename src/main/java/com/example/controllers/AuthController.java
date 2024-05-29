package com.example.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.AuthenticationRequest;
import com.example.dtos.AuthenticationResponse;
import com.example.dtos.CompteDto;
import com.example.dtos.ContribuableDtos;
import com.example.dtos.DeclarationDto;
import com.example.dtos.DetailDeclarationDto;
import com.example.dtos.MotDePassdto;
import com.example.dtos.ObligationresponseDto;
import com.example.dtos.PasswordDto;
import com.example.dtos.SaveDeclaration;
import com.example.dtos.SignupRequest;
import com.example.dtos.UserDtos;
import com.example.dtos.VerificationDto;
import com.example.entity.Compte;
import com.example.entity.Contribuable;
import com.example.entity.Declaration;
import com.example.entity.DetailImpot;
import com.example.jwt.UserService;
import com.example.repository.CompteRepository;
import com.example.repository.ContribuableRepository;
import com.example.service.AdminService;
import com.example.service.AuthService;
import com.example.service.CompteService;
import com.example.service.ContribuableService;
import com.example.service.DeclarationService;
import com.example.service.ObligationFiscaleService;
import com.example.utils.JwtUtils;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
@Autowired
private AuthenticationManager authenticationManager;
@Autowired
private UserService userservice;
@Autowired
private JwtUtils jwtUtil;

@Autowired
private AuthService authService;

@Autowired
private ContribuableService contribuableservice;

@Autowired
private CompteRepository compteRepository;
@Autowired
private ObligationFiscaleService obligationFiscaleService;
@Autowired
private AdminService adminservice;



@Autowired
private DeclarationService declarationService;

@Autowired
private ContribuableRepository contribuableRepository ;
@Autowired
private CompteService compteservice;




@PostMapping("/signup")
public ResponseEntity<?> createCustomer(@RequestBody SignupRequest signupRequest ) throws UnsupportedEncodingException, MessagingException{

	UserDtos createdUserDto=authService.createCustomer(signupRequest);
    authService.sendVerificationEmail(createdUserDto);
    if(createdUserDto==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request!");
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
}


@PostMapping("/login")
public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)throws
BadCredentialsException,
DisabledException,
UsernameNotFoundException{
	try {
	    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
	    compteservice.resetFailedAttempt(authenticationRequest.getEmail());

	} catch (BadCredentialsException e) {

		 compteservice.updateFailedAttempt(authenticationRequest.getEmail());

	        Optional<Compte> compteOptional = compteRepository.findByEmail(authenticationRequest.getEmail());
	        if (compteOptional.isPresent() && compteOptional.get().getFailedAttempt() == 3) {

	            compteservice.blocageCompteParEmail(authenticationRequest.getEmail());
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account is disabled");
	        }

	        // Return response for incorrect password
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
	} catch (LockedException e) {
	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account is disabled");
	} catch (UsernameNotFoundException e) {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	}
	final UserDetails userDetails=userservice.userDetailsService()
			.loadUserByUsername(authenticationRequest.getEmail());
	Optional<Compte> optionalUser= compteRepository.findByEmail(userDetails.getUsername());
	final String jwt=jwtUtil.generateToken(userDetails,optionalUser.get().getUserRole());
	AuthenticationResponse authenticationResponse=new AuthenticationResponse();
	if(optionalUser.isPresent()) {
		authenticationResponse.setJwt(jwt);
		authenticationResponse.setUserId(optionalUser.get().getIdCompte());
		authenticationResponse.setUserRole(optionalUser.get().getUserRole());

	}
	return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);
}


@GetMapping("/verify")
public ResponseEntity<?> verificationresponse(@RequestParam("code") String parambody) {
	 if (parambody != null && parambody.endsWith("\"")) {
	        parambody = parambody.substring(0, parambody.length() - 1);
	    }

    Boolean verified = authService.verify(parambody);
   UserDtos user=authService.convertUser(parambody);

    if (verified) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new VerificationDto("succefful validation",true,user));
    } else {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new VerificationDto("something went wrong",false,user));
    }
}

@GetMapping("/contribuableMatricule")
public ResponseEntity<?> findByMatriculeFiscale(@RequestParam("matriculeFiscale") int matriculeFiscale) {
		ContribuableDtos contribuable = contribuableservice.findContribuable(matriculeFiscale);
        if (contribuable != null)
        	return ResponseEntity.ok(contribuable);

        return ResponseEntity.notFound().build();

}

@PostMapping("/checkDeclaration")
public ResponseEntity<?> checkDeclaration(@RequestBody DeclarationDto request) {
	Declaration result = obligationFiscaleService.getNumerodeclaration(request.getCd(), request.getIddecalaration());
    if (result!=null) {
        return ResponseEntity.ok().body(result);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Declaration not found!");
    }
}

@GetMapping("/Inscription")
public ResponseEntity<List<UserDtos>> getAllInscription(){



	List<UserDtos> inscriptionList=adminservice.getAllInscription();
	return ResponseEntity.ok(inscriptionList);
}

@PostMapping("/savepassword")
public ResponseEntity<?> savePassword(@RequestBody PasswordDto signupRequest ) throws UnsupportedEncodingException, MessagingException{

	UserDtos createdUserDto=authService.validePassword(signupRequest);

    if(createdUserDto==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request!");
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
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
@GetMapping("/contribuable/{contribuableId}")
public ResponseEntity<?> getObligationsByContribuable(@PathVariable Long contribuableId) {
    Optional<Contribuable> cd = this.contribuableRepository.findById(contribuableId);
    if(cd.isPresent()) {
    List<ObligationresponseDto> obligations = obligationFiscaleService.getlesObligationsdeContribuable(cd.get());
    return ResponseEntity.ok(obligations);
}else return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme de création de déclaration!");
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
/*@PostMapping("/calculate")
public double calculate(@RequestBody CalculationRequest request) throws ScriptException {
	  String formula = request.getFormula();
      for (String key : request.getValues().keySet()) {
          formula = formula.replaceAll("\\b" + key + "\\b", String.valueOf(request.getValues().get(key)));
      }

      try (Context context = Context.create()) {
          Value result = context.eval("js", formula);
          return result.asDouble();
      } catch (Exception e) {
          e.printStackTrace();
          throw new RuntimeException("Error evaluating the formula: " + formula, e);
      }
}
@PutMapping("/updateimpot")
public ResponseEntity<?> updateimpot(@RequestBody ImpotDto impotDto) {
    boolean isUpdated = impotservice.updateImpot(impotDto);
    if (isUpdated) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(isUpdated);
    } else {
        return ResponseEntity.status(404).body("impot not found");
    }
}*/
@PutMapping("/updatepassword")
public ResponseEntity<?> updatePassword(@RequestBody MotDePassdto dd){
	 boolean saved=compteservice.updatepassword(dd);
	 if(saved) {
		return ResponseEntity.ok().build();
	 }return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
}

}

