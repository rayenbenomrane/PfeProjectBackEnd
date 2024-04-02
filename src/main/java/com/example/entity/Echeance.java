package com.example.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Echeance")
public class Echeance {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator10Name")
	 @SequenceGenerator(name = "yourGenerator10Name", sequenceName = "echeance_seq", allocationSize = 1)
	private long idEcheance;
	
	 private int jour;
	 private int mois;
	 private int numeroEcheance;
	 private int annee;
	 
}
