package com.example.entity;


import java.util.List;

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
@Table(name = "forme_juridique")
public class FormeJuridique {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator3Name")
    @SequenceGenerator(name = "yourGenerator3Name", sequenceName = "forme_juridique_seq", allocationSize = 1)
    private Long idFormeJuridique;

    private String libelle;

    @OneToMany(mappedBy = "formeJuridique")
    @JsonIgnore
    private List<Contribuable> contribuables;
}
