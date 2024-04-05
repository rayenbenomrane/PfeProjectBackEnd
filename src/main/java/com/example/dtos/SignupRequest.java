package com.example.dtos;

import com.example.entity.Contribuable;
import com.example.enums.Identifiant;

import lombok.Data;

@Data
public class SignupRequest {

	private String email;
	private String name;
	private String poste;
	private String password;
	private String nom;
	private String prenom;
	private Contribuable contribuable;
	private Identifiant typeIdentifiant;
	private String valueIdentifiant;

}
