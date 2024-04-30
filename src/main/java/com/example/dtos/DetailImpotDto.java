package com.example.dtos;


import com.example.enums.NatureRebrique;
import com.example.enums.TypedeDetailImpot;

import lombok.Data;
@Data
public class DetailImpotDto {

	private long idDetailImpot;
	 
	 
	 private String libelle;
	 
	 private TypedeDetailImpot typeDetail;
	 
	 private NatureRebrique naturerebrique;
	 
	 private int ordre;
	 
	 private boolean  obligatoire;
	
	 private TypeImpotDto typeImpot;
}
