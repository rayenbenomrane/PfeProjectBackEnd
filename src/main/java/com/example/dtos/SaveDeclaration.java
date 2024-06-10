package com.example.dtos;


import com.example.enums.TypeDeclarationEnum;

import lombok.Data;

@Data
public class SaveDeclaration {



	private int  moisEffet;

	private int  anneeEffet;

	private Long idObligation;

	private TypeDeclarationEnum type;
}
