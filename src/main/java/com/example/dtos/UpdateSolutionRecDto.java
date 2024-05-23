package com.example.dtos;

import com.example.enums.Etat;

import lombok.Data;

@Data
public class UpdateSolutionRecDto {
	private long idReclamation;
	private String solution;
	private Etat etat;
}
