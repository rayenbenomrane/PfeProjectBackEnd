package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Pays;
import java.util.List;


public interface PaysRepository extends JpaRepository<Pays, Long>{
List<Pays> findByLibelle(String libelle);
}
