package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.TypeDeclarationDto;

import com.example.entity.TypeDeclaration;
import com.example.repository.TypeDeclarationRepository;

@Service
public class TypeDeclarationServiceImpl implements TypeDeclarationService{

	@Autowired
	private TypeDeclarationRepository typeDeclarationRepo;
	
	@Override
	public List<TypeDeclarationDto> lesTypes() {
		return typeDeclarationRepo.findAll().stream().map(TypeDeclaration::getType).collect(Collectors.toList());
	}

}
