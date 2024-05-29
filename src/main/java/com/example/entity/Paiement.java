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
@Table(name = "\"Paiement\"")
public class Paiement {


	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator40Name")
    @SequenceGenerator(name = "yourGenerator40Name", sequenceName = "paiement_seq", allocationSize = 1)
	private Long idPaiment;
	private String numeroTransaction;
	private Date datePaiement;

	 @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	    @JoinColumn(name = "declaration_id")
	 	private Declaration declaration;


}
