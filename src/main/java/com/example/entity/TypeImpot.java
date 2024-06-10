package com.example.entity;

import com.example.dtos.PeriodeDto;
import com.example.dtos.TypeImpotDto;
import com.example.enums.Periode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name = "typeImpot")
public class TypeImpot {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator8Name")
	@SequenceGenerator(name = "yourGenerator8Name", sequenceName = "typeImpot_seq", allocationSize = 1)
	private long idTypeImpot;
	@Column(unique = true)
	private String libelle;

	private String formule;



	
	
	 	private Periode periodicite;

public TypeImpotDto getImpot() {
	TypeImpotDto impot=new TypeImpotDto();
	impot.setLibelle(libelle);
	impot.setId(idTypeImpot);
	impot.setPeriodicite(periodicite);
	return impot;
}

}
