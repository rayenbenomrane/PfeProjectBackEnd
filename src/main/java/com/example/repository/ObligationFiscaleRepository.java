package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Contribuable;
import com.example.entity.ObligationFiscale;
import com.example.entity.TypeImpot;

public interface ObligationFiscaleRepository extends JpaRepository<ObligationFiscale,Long>{

List<ObligationFiscale> findByContribuable(Contribuable contribuable);
List<ObligationFiscale> findByImpot(TypeImpot impot);
List<ObligationFiscale> findByImpotAndContribuable(TypeImpot impot, Contribuable contribuable);
}
