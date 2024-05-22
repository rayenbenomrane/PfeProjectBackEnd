package com.example.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dtos.CompteById;
import com.example.dtos.CompteDto;
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
		    if (newCompte.getUserRole() == UserRole.Admin || newCompte.getUserRole() == UserRole.Responsable) {
		        newCompte.setPassword(new BCryptPasswordEncoder().encode(cd.getPassword()));
		    } else {
		        newCompte.setPassword(cd.getPassword());
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
		    }

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

		    Optional<Compte> existingCompteOptional = compteRepository.findById(cd.getIdCompte());

		    // Check if the Compte exists
		    if (existingCompteOptional.isPresent()) {
		        Compte existingCompte = existingCompteOptional.get();


		        existingCompte.setEmail(cd.getEmail());
		        existingCompte.setUserRole(cd.getUserRole());


		        if (existingCompte.getUserRole() == UserRole.Admin) {
		            existingCompte.setPassword(new BCryptPasswordEncoder().encode(cd.getPassword()));
		        } else {
		            existingCompte.setPassword(cd.getPassword());
		        }


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



		@Override
		public boolean AcceptCompte(CompteDto cd) {
			 Optional<Compte> existingCompteOptional = compteRepository.findById(cd.getIdCompte());


			    if (existingCompteOptional.isPresent()) {
			        Compte existingCompte = existingCompteOptional.get();


			        existingCompte.setEmail(cd.getEmail());
			        existingCompte.setUserRole(cd.getUserRole());
			        existingCompte.setFailedAttempt(0);
			        if (existingCompte.getUserRole() == UserRole.Admin) {
			            existingCompte.setPassword(new BCryptPasswordEncoder().encode(cd.getPassword()));
			        } else {
			            existingCompte.setPassword(cd.getPassword());
			        }


			        User existingUser = existingCompte.getInscription();
			        existingUser.setEmail(cd.getInscription().getEmail());
			        existingUser.setUserRole(cd.getInscription().getUserRole());
			        existingUser.setVerificationCode(cd.getInscription().getVerificationCode());
			        existingUser.setEnabled(cd.getInscription().getEnabled());
			        existingUser.setNonLocked(true); // Consider revising this logic
			        existingUser.setDateInscri(cd.getInscription().getDateInscri());
			        existingUser.setContribuable(cd.getInscription().getContribuable());
			        existingUser.setNom(cd.getInscription().getNom());
			        existingUser.setPrenom(cd.getInscription().getPrenom());
			        existingUser.setTypeIdentifiant(cd.getInscription().getTypeIdentifiant());
			        existingUser.setValeurIdentifiant(cd.getInscription().getValueIdentifiant());
			        existingUser.setPoste(cd.getInscription().getPoste());
			        existingUser.setPassword(cd.getPassword());


			        Compte compteCree = compteRepository.save(existingCompte);


			        if (!compteCree.getInscription().isNonLocked()) {
			            return false;
			        } else {
			            return true;
			        }
			    } else {

			        return false;
			    }
		}



		@Override
		public boolean blocageCompteParEmail(String email) {
			// TODO Auto-generated method stub
			 Optional<Compte> existingCompteOptional = compteRepository.findByEmail(email);


			    if (existingCompteOptional.isPresent()) {
			        Compte existingCompte = existingCompteOptional.get();


			        existingCompte.setEmail(existingCompteOptional.get().getEmail());
			        existingCompte.setUserRole(existingCompteOptional.get().getUserRole());

			        if (existingCompte.getUserRole() == UserRole.Admin) {
			            existingCompte.setPassword(new BCryptPasswordEncoder().encode(existingCompteOptional.get().getPassword()));
			        } else {
			            existingCompte.setPassword(existingCompteOptional.get().getPassword());
			        }


			        User existingUser = existingCompte.getInscription();
			        existingUser.setEmail(existingCompteOptional.get().getInscription().getEmail());
			        existingUser.setUserRole(existingCompteOptional.get().getInscription().getUserRole());
			        existingUser.setVerificationCode(existingCompteOptional.get().getInscription().getVerificationCode());
			        existingUser.setEnabled(existingCompteOptional.get().getInscription().isEnabled());
			        existingUser.setNonLocked(false); // Consider revising this logic
			        existingUser.setDateInscri(existingCompteOptional.get().getInscription().getDateInscri());
			        existingUser.setContribuable(existingCompteOptional.get().getInscription().getContribuable());
			        existingUser.setNom(existingCompteOptional.get().getInscription().getNom());
			        existingUser.setPrenom(existingCompteOptional.get().getInscription().getPrenom());
			        existingUser.setTypeIdentifiant(existingCompteOptional.get().getInscription().getTypeIdentifiant());
			        existingUser.setValeurIdentifiant(existingCompteOptional.get().getInscription().getValeurIdentifiant());
			        existingUser.setPoste(existingCompteOptional.get().getInscription().getPoste());
			        existingUser.setPassword(existingCompteOptional.get().getPassword());


			        Compte compteCree = compteRepository.save(existingCompte);


			        if (!compteCree.getInscription().isNonLocked()) {
			            return false;
			        } else {
			            return true;
			        }
			    } else {

			        return false;
			    }
		}



		@Override
		public void updateFailedAttempt(String email) {
		    Optional<Compte> existingCompteOptional = compteRepository.findByEmail(email);
		    if(existingCompteOptional.isPresent()) {
		        Compte existingCompte = existingCompteOptional.get();
		        int currentFailedAttempt = existingCompte.getFailedAttempt();
		        existingCompte.setFailedAttempt(currentFailedAttempt + 1);
		        compteRepository.save(existingCompte);
		    }
		}



		@Override
		public void resetFailedAttempt(String email) {
			// TODO Auto-generated method stub
			Optional<Compte> existingCompteOptional = compteRepository.findByEmail(email);
		    if(existingCompteOptional.isPresent()) {
		        Compte existingCompte = existingCompteOptional.get();

		        existingCompte.setFailedAttempt(0);
		        compteRepository.save(existingCompte);
		    }

		}



		@Override
		public CompteById getCompteByid(Long id) {
			
			
		Optional<Compte>  compte=compteRepository.findById(id);
		if(compte.isPresent()) {
			CompteById compteDto=new CompteById();
			compteDto.setEmail(compte.get().getEmail());
			compteDto.setFirstName(compte.get().getInscription().getNom());
			compteDto.setLastName(compte.get().getInscription().getPrenom());
			return compteDto;
		}else return null;
		}







}
