package com.example.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.JoinColumn;

@Data
@Entity
@Table(name = "\"contribuable\"")
public class Contribuable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator2Name")
	@SequenceGenerator(name = "yourGenerator2Name", sequenceName = "contribuable_seq", allocationSize = 1)
	private Long idContribuable;
	private int matriculeFiscale;
	private String nomCommercial;
	private String email;
	private String adress;
	private Date dateDeMatriculation; 
	private String raisonSocial;
	private String directeur;
	
	
	 @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	    @JoinColumn(name = "formejuridique_id")
	    private FormeJuridique formeJuridique;

	    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	    @JoinColumn(name = "pays_id")
	    private Pays pays;

	    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	    @JoinColumn(name = "activite_id")
	    private Activite activite;

	 
	
}
