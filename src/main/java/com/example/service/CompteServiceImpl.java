package com.example.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dtos.CompteDto;
import com.example.dtos.UserDtos;
import com.example.entity.Compte;
import com.example.entity.User;
import com.example.enums.UserRole;
import com.example.repository.CompteRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CompteServiceImpl implements CompteService{


	@Autowired
	private CompteRepository compteRepository;



		@Override
		public boolean saveCompte(CompteDto cd) {
		    Compte newCompte = new Compte();
		    newCompte.setEmail(cd.getEmail());
		    newCompte.setUserRole(cd.getUserRole());
		    if (newCompte.getUserRole() == UserRole.Admin) {
		        newCompte.setPassword(new BCryptPasswordEncoder().encode(cd.getPassword()));
		    } else {
		        newCompte.setPassword(cd.getPassword());
		    }
		    User UserDto=new User();
		       UserDto.setIdInscription(cd.getInscription().getIdInscription());
		       UserDto.setEmail(cd.getInscription().getEmail());
		        UserDto.setUserRole(cd.getInscription().getUserRole());
		        UserDto.setVerificationCode(cd.getInscription().getVerificationCode());
		        UserDto.setEnabled(cd.getInscription().getEnabled());
		        UserDto.setNonLocked(cd.getInscription().isNonLocked());
		        UserDto.setDateInscri(cd.getInscription().getDateInscri());
		        UserDto.setContribuable(cd.getInscription().getContribuable());
		        UserDto.setNom(cd.getInscription().getNom());
		      UserDto.setPrenom(cd.getInscription().getPrenom());
		        UserDto.setTypeIdentifiant(cd.getInscription().getTypeIdentifiant());
		        UserDto.setValeurIdentifiant(cd.getInscription().getValueIdentifiant());
		    newCompte.setInscription(UserDto);
		    Compte compteCree = compteRepository.save(newCompte);


		    if (compteCree != null && compteCree.getIdCompte() != null) {

		        return true;
		    } else {

		        return false;
		    }
		}



		@Override
		public List<CompteDto> getAllCompte() {
			return compteRepository.findAll().stream().map(Compte::getCompte).collect(Collectors.toList());
		}



		@Override
		public boolean blocageCompte(CompteDto cd) {
		    // Search for the Compte entity by its ID
		    Optional<Compte> existingCompteOptional = compteRepository.findById(cd.getIdCompte());
		    
		    // Check if the Compte exists
		    if (existingCompteOptional.isPresent()) {
		        Compte existingCompte = existingCompteOptional.get();

		        // Update existing Compte details
		        existingCompte.setEmail(cd.getEmail());
		        existingCompte.setUserRole(cd.getUserRole());
		        
		        if (existingCompte.getUserRole() == UserRole.Admin) {
		            existingCompte.setPassword(new BCryptPasswordEncoder().encode(cd.getPassword()));
		        } else {
		            existingCompte.setPassword(cd.getPassword());
		        }

		        // Update associated User details
		        User existingUser = existingCompte.getInscription();
		        existingUser.setEmail(cd.getInscription().getEmail());
		        existingUser.setUserRole(cd.getInscription().getUserRole());
		        existingUser.setVerificationCode(cd.getInscription().getVerificationCode());
		        existingUser.setEnabled(cd.getInscription().getEnabled());
		        existingUser.setNonLocked(false); // Consider revising this logic
		        existingUser.setDateInscri(cd.getInscription().getDateInscri());
		        existingUser.setContribuable(cd.getInscription().getContribuable());
		        existingUser.setNom(cd.getInscription().getNom());
		        existingUser.setPrenom(cd.getInscription().getPrenom());
		        existingUser.setTypeIdentifiant(cd.getInscription().getTypeIdentifiant());
		        existingUser.setValeurIdentifiant(cd.getInscription().getValueIdentifiant());
		        existingUser.setPoste(cd.getInscription().getPoste());
		        existingUser.setPassword(cd.getPassword()); // Revisit this assignment
		        
		        // Save the updated Compte
		        Compte compteCree = compteRepository.save(existingCompte);

		        // Check if the account is locked
		        if (!compteCree.getInscription().isNonLocked()) {
		            return true;
		        } else {
		            return false;
		        }
		    } else {
		        // Compte with the given ID not found
		        return false;
		    }
		}






}
