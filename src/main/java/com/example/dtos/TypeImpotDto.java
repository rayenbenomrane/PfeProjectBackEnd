package com.example.dtos;

import com.example.enums.Periode;

import lombok.Data;



@Data
public class TypeImpotDto {

	private long id;
	private String libelle;
	private String formule;
 	private Periode periodicite;

}
