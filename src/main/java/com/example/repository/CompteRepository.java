package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Compte;


public interface CompteRepository extends JpaRepository<Compte,Long>{
	 Optional<Compte> findByEmail(String email);
	 
	 

}
