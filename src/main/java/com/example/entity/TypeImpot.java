package com.example.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "typeImpot")
public class TypeImpot {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator8Name")
	@SequenceGenerator(name = "yourGenerator8Name", sequenceName = "typeImpot_seq", allocationSize = 1)
	private long idTypeImpot;

	private String libelle;

	@OneToOne
	@JoinColumn(name = "echeance_id", referencedColumnName = "idEcheance")
	private Echeance echeance;

	 @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	    @JoinColumn(name = "periodicite_id")
	 	private Periodicite periodicite;



}
