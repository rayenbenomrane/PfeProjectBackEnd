package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Contribuable;


public interface ContribuableRepository extends JpaRepository<Contribuable,Long>{

	Optional<Contribuable> findByMatriculeFiscale(int matriculeFiscale);
}
