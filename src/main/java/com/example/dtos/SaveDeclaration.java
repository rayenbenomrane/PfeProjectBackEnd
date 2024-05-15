package com.example.dtos;

import com.example.entity.TypeDeclaration;

import lombok.Data;

@Data
public class SaveDeclaration {



	private int  moisEffet;

	private int  anneeEffet;

	private Long idObligation;

	private TypeDeclaration type;
}
