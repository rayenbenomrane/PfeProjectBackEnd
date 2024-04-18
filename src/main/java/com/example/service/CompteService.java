package com.example.service;

import java.util.List;

import com.example.dtos.CompteDto;

public interface CompteService {
	boolean saveCompte(CompteDto cd);
	List<CompteDto> getAllCompte();
	boolean blocageCompte(CompteDto cd);
}
