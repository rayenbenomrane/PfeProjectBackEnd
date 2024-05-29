package com.example.dtos;



import com.example.enums.TypedeDetailImpot;

import lombok.Data;
@Data
public class DetailImpotDto {

	private long idDetailImpot;


	 private String libelle;

	 private TypedeDetailImpot typeDetail;


	 private int ordre;

	 private boolean  obligatoire;

	 private TypeImpotDto typeImpot;
	 private boolean calculable;
	 private String formule;
}
