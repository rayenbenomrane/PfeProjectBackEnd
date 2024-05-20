package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.dtos.CompteById;
import com.example.dtos.CompteDto;

public interface CompteService {
	boolean saveCompte(CompteDto cd);
	List<CompteDto> getAllCompte();
	boolean blocageCompte(CompteDto cd);
	boolean AcceptCompte(CompteDto cd);
	boolean blocageCompteParEmail(String email);
	void updateFailedAttempt(String email);
	void resetFailedAttempt(String email);
	CompteById getCompteByid(Long id);
}
