package com.example.entity;

import java.util.List;

import com.example.enums.NatureRebrique;
import com.example.enums.TypedeDetailImpot;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "detailImpot")
public class DetailImpot {
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator17Name")
	 @SequenceGenerator(name = "yourGenerator17Name", sequenceName = "detailImpot_seq", allocationSize = 1)
	 private long idDetailImpot;
	 
	 
	 private String libelle;
	 
	 private TypedeDetailImpot typeDetail;
	 
	 private NatureRebrique naturerebrique;
	 
	 private int ordre;
	 
	 private boolean  obligatoire;
	 
	 
	 
	 
	 @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	    @JoinColumn(name = "typeImpot_id")
	 	private TypeImpot typeImpot;

	 
	 
	 
	 
	 
	 
	 
	 
	 
}
