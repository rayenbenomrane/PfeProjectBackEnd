package com.example.dtos;

import java.util.Date;

import com.example.entity.Contribuable;
import com.example.entity.Declaration;
import com.example.enums.Etat;

import lombok.Data;
@Data
public class ReclamtionResponse {

	 private long idReclamation;
	    private String titre;
	    private String contenu;
	    private Etat etat;
	    private Date dateReclamation;
	    private String solution;
	    private Declaration declaration;
	    private Contribuable contribuable;
}
