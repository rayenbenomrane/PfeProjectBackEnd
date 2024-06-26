package com.example.entity;

import java.util.Date;

import com.example.enums.TypeDeclarationEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor

@ToString
@Data
@Entity
@Table(name = "declaration")
public class Declaration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator11Name")
	@SequenceGenerator(name = "yourGenerator11Name", sequenceName = "decalaration_seq", allocationSize = 1)
	private long idDeclaration;

	private Date dateDeclaration;


	private int moisEffet;

	private int anneeEffet;



	private Float montantaCalculer;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "obligation_id")
    private ObligationFiscale obligation;

	
	private TypeDeclarationEnum type;
	
	@OneToOne(mappedBy = "declaration", cascade = CascadeType.ALL)
    @JsonManagedReference
	
	private Paiement paiement;




}
