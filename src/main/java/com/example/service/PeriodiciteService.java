package com.example.service;

import java.util.List;

import com.example.dtos.PeriodeDto;
import com.example.entity.Periodicite;

public interface PeriodiciteService {

	
	List<PeriodeDto> findAllPeriode();
}
