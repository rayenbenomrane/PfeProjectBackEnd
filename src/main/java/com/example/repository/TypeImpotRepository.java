package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.TypeImpot;


public interface TypeImpotRepository extends JpaRepository<TypeImpot,Long>{


	Optional<TypeImpot>  findByLibelle(String libelle);
}
