package com.example.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class SaveObligation {

	
	private int matricule;
	private Date dateDebut;
	private Date dateFin;
	private TypeImpotDto impot;
	
}
