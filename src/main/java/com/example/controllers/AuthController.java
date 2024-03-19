package com.example.controllers;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.ActiviteDtos;
import com.example.dtos.AuthenticationRequest;
import com.example.dtos.AuthenticationResponse;
import com.example.dtos.ContribuableDtos;
import com.example.dtos.FormeJuridiqueDtos;
import com.example.dtos.PaysDtos;
import com.example.dtos.SignupRequest;
import com.example.dtos.UserDtos;
import com.example.entity.User;
import com.example.jwt.UserService;
import com.example.repository.UserRepository;
import com.example.service.ActiviteService;
import com.example.service.AuthService;
import com.example.service.ContribuableService;
import com.example.service.FormeJuridiqueService;
import com.example.service.PaysService;
import com.example.utils.JwtUtils;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
@Autowired
private AuthenticationManager authenticationManager;
@Autowired
private UserService userservice;
@Autowired
private JwtUtils jwtUtil;
@Autowired
private  UserRepository userRepository;
@Autowired
private AuthService authService;
@Autowired
private ActiviteService activiteservice;
@Autowired
private FormeJuridiqueService formejuridiqueservice;
@Autowired
private ContribuableService contribuableservice;
@Autowired
private PaysService paysservice;



@PostMapping("/signup")
public ResponseEntity<?> createCustomer(@RequestBody SignupRequest signupRequest ) throws UnsupportedEncodingException, MessagingException{
    UserDtos createdUserDto=authService.createCustomer(signupRequest);
    authService.sendVerificationEmail(createdUserDto);
    if(createdUserDto==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request!");
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
}


@PostMapping("/login")
public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)throws 
BadCredentialsException,
DisabledException,
UsernameNotFoundException{
	try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
	}catch (BadCredentialsException e) {
		throw new BadCredentialsException("Incorrect username or password");// TODO: handle exception
	}
	final UserDetails userDetails=userservice.userDetailsService()
			.loadUserByUsername(authenticationRequest.getEmail());
	Optional<User> optionalUser= userRepository.findByEmail(userDetails.getUsername());
	final String jwt=jwtUtil.generateToken(userDetails);
	AuthenticationResponse authenticationResponse=new AuthenticationResponse();
	if(optionalUser.isPresent()) {
		authenticationResponse.setJwt(jwt);
		authenticationResponse.setUserId(optionalUser.get().getIdInscription());
		authenticationResponse.setUserRole(optionalUser.get().getUserRole());
		
	}
	return authenticationResponse;
}


@GetMapping("/verify")
public String verificationresponse(@RequestParam("code") String parambody) {
	 if (parambody != null && parambody.endsWith("\"")) {
	        parambody = parambody.substring(0, parambody.length() - 1);
	    }

    Boolean verified = authService.verify(parambody);

    if (verified) {
    	
        return "Verification confirmed";
    } else {
        return "Something went wrong";
    }
}
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
