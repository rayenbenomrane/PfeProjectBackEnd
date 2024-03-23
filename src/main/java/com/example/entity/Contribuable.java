package com.example.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.JoinColumn;

@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor

@ToString
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
	
	
	 @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	    @JoinColumn(name = "formejuridique_id")
	 	private FormeJuridique formeJuridique;

	    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	    @JoinColumn(name = "pays_id")
	    private Pays pays;

	    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	    @JoinColumn(name = "activite_id")
	    private Activite activite;

	    @Override
	    public String toString() {
	        return "Contribuable{" +
	                "idContribuable=" + idContribuable +
	                ", matriculeFiscale=" + matriculeFiscale +
	                ", nomCommercial='" + nomCommercial + '\'' +
	                ", email='" + email + '\'' +
	                ", adress='" + adress + '\'' +
	                ", dateDeMatriculation=" + dateDeMatriculation +
	                ", raisonSocial='" + raisonSocial + '\'' +
	                ", directeur='" + directeur + '\'' +
	                ", formeJuridique=" + (formeJuridique != null ? formeJuridique.getIdFormeJuridique() : null) + // Avoid cyclic reference
	                ", pays=" + (pays != null ? pays.getIdPays() : null) + // Avoid cyclic reference
	                ", activite=" + (activite != null ? activite.getIdActivite() : null) + // Avoid cyclic reference
	                '}';
	    }
	
}
