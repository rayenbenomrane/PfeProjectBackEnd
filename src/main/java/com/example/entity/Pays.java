package com.example.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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

@Table(name = "pays")
public class Pays {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator4Name")
    @SequenceGenerator(name = "yourGenerator4Name", sequenceName = "pays_seq", allocationSize = 1)
private long idPays;
private String libelle;
@OneToMany(mappedBy = "pays")
@JsonIgnore
private Set<Contribuable> contribuables;
}
