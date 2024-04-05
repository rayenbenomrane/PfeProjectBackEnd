package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dtos.CompteDto;
import com.example.entity.Compte;
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
		    newCompte.setInscription(cd.getInscription());
		    Compte compteCree = compteRepository.save(newCompte);


		    if (compteCree != null && compteCree.getIdCompte() != null) {

		        return true;
		    } else {

		        return false;
		    }
		}





}
