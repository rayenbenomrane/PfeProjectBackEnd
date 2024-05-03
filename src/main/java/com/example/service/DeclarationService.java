package com.example.service;

import java.util.Map;

import com.example.dtos.DetailDeclarationDto;
import com.example.dtos.SaveDeclaration;
import com.example.entity.DetailDeclaration;
import com.example.entity.DetailImpot;

public interface DeclarationService {
	
	
	Map<DetailImpot, DetailDeclarationDto> saveDeclaration(SaveDeclaration dc);

}
