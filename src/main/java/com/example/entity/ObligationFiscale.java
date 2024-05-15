package com.example.entity;



import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor

@ToString
@Entity
@Table(name = "obligationFiscale")
public class ObligationFiscale {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator7Name")
	@SequenceGenerator(name = "yourGenerator7Name", sequenceName = "Obligation_seq", allocationSize = 1)
	private long idObligationFiscale;



	private Date dateDebut;
	private Date dateFin;

	 @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	    @JoinColumn(name = "contribuable_id")
	 	private Contribuable contribuable;

	 @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	    @JoinColumn(name = "typeImpot_id")
	 	private TypeImpot impot;



}
