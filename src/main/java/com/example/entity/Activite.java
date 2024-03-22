package com.example.entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "activite")
public class Activite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator1Name")
    @SequenceGenerator(name = "yourGenerator1Name", sequenceName = "activite_seq", allocationSize = 1)
    private Long idActivite;

    private String libelle;
    @OneToMany(mappedBy = "activite")
    @JsonIgnore
    private List<Contribuable> contribuables;


}
