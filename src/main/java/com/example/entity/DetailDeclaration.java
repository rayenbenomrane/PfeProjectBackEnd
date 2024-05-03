package com.example.entity;

import com.example.enums.UserRole;

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
@Table(name = "\"DetailDeclaration\"")
public class DetailDeclaration {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator19Name")
	@SequenceGenerator(name = "yourGenerator19Name", sequenceName = "detailDeclaration_seq", allocationSize = 1)
	private Long idDetailDeclaration;
	
	private String valeur;
	
	
	

    @ManyToOne
    @JoinColumn(name = "detailImpot_id")
    private DetailImpot detailImpot;
	
	 @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "declaration_id")
 	private Declaration declaration;

	
}
