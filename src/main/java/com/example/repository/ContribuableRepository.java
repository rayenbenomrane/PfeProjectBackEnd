package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Contribuable;
import java.util.List;
import java.util.Optional;


public interface ContribuableRepository extends JpaRepository<Contribuable,Long>{

	Optional<Contribuable> findByMatriculeFiscale(int matriculeFiscale);
}
