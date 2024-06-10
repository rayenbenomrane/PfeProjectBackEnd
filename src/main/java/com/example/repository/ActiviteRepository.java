package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Activite;
import java.util.List;


public interface ActiviteRepository extends JpaRepository<Activite,Long>{

	List<Activite> findByLibelle(String libelle);
}
