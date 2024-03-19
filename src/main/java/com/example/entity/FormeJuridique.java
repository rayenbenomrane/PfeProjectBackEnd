package com.example.entity;


import java.util.List;


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
@Table(name = "forme_juridique")
public class FormeJuridique {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator3Name")
    @SequenceGenerator(name = "yourGenerator3Name", sequenceName = "forme_juridique_seq", allocationSize = 1)
    private Long idFormeJuridique;

    private String libelle;

    @OneToMany(mappedBy = "formeJuridique")
    private List<Contribuable> contribuables;
}
