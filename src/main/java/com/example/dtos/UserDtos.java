package com.example.dtos;

import com.example.entity.Contribuable;
import com.example.enums.Identifiant;
import com.example.enums.UserRole;

import lombok.Data;

@Data
public class UserDtos {
	private Long idInscription;
	
	private String email;
	
	private String Poste;
	
	private String password;
	
	private String VerificationCode;
	
	private Boolean enabled;
	
	private boolean NonLocked;
	private java.util.Date dateInscri;
	
	private UserRole userRole;
	private Identifiant typeIdentifiant;
	private String valueIdentifiant;
	private String nom;
	private String prenom;
	private Contribuable contribuable;
}
