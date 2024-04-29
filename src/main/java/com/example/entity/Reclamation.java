package com.example.entity;

import java.util.Date;

import com.example.enums.Etat;
import com.example.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "\"Reclamation\"")
public class Reclamation {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator15Name")
	@SequenceGenerator(name = "yourGenerator15Name", sequenceName = "Reclamation_seq", allocationSize = 1)
	private long idReclamation;
	
	
	private String titre;
	
	private String contenu;
	
	private Etat etat;
	
	 private Date dateReclamation;
	 
	 private String solution;
	 
	 

	 @ManyToOne
	    @JoinColumn(name = "contribuable_id",  nullable = false)
	 private  Contribuable contribuable;
	 
	 
	
	
}
