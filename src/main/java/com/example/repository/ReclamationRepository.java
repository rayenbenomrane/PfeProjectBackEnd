package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Contribuable;
import com.example.entity.Reclamation;

public interface ReclamationRepository extends JpaRepository<Reclamation,Long>{
List<Reclamation> findByContribuable(Contribuable contribuable);
}
