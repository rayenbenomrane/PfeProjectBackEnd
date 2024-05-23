package com.example.dtos;

import java.util.Date;

import com.example.entity.Contribuable;
import com.example.enums.Etat;

import lombok.Data;
@Data
public class ReclamationDto {
	 private long idReclamation;
	    private String titre;
	    private String contenu;
	    private Etat etat;
	    private Date dateReclamation;
	    private String solution;
	    private long idDeclaration;
	    private Contribuable contribuable;
}
