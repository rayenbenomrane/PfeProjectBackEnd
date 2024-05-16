package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.dtos.DetailDeclarationDto;
import com.example.dtos.SaveDeclaration;
import com.example.dtos.SaveMontant;
import com.example.entity.Declaration;
import com.example.entity.DetailImpot;

public interface DeclarationService {


	Map<DetailImpot, DetailDeclarationDto> saveDeclaration(SaveDeclaration dc);
	boolean updateMontantaCalculer(SaveMontant di);
	List<Declaration> getDeclarationsByMatriculeFiscale(int matriculeFiscale);
}
