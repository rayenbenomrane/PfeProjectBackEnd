package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Contribuable;
import com.example.entity.ObligationFiscale;

public interface ObligationFiscaleRepository extends JpaRepository<ObligationFiscale,Long>{

List<ObligationFiscale> findByContribuable(Contribuable contribuable);
}
