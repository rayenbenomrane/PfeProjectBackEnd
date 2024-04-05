package com.example.dtos;

import java.util.Date;

import com.example.entity.Activite;
import com.example.entity.FormeJuridique;
import com.example.entity.Pays;

import lombok.Data;
@Data
public class ContribuableDtos {

	private Long idContribuable;
    private int matriculeFiscale;
    private String nomCommercial;
    private String email;
    private String adress;
    private Date dateDeMatriculation;
    private String raisonSocial;
    private FormeJuridique formeJuridique;
    private Pays pays;
    private  Activite activite;
	private String Directeur;
}
