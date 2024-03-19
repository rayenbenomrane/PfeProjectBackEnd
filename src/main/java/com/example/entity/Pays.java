package com.example.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pays")
public class Pays {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator4Name")
    @SequenceGenerator(name = "yourGenerator4Name", sequenceName = "pays_seq", allocationSize = 1)
private long idPays;
private String libelle;
@OneToMany(mappedBy = "pays")
private Set<Contribuable> contribuables;
}
