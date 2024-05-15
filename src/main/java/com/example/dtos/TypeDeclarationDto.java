package com.example.dtos;

import com.example.enums.TypeDeclarationEnum;

import lombok.Data;

@Data
public class TypeDeclarationDto {



	private Long idTypeDeclaration;

	private TypeDeclarationEnum type;
}
