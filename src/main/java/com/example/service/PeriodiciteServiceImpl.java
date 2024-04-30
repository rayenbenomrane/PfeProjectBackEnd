package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.PeriodeDto;
import com.example.entity.Periodicite;
import com.example.repository.PeriodiciteRepository;
@Service
public class PeriodiciteServiceImpl implements PeriodiciteService{

	@Autowired
	private PeriodiciteRepository perioderepo;
	
	
	@Override
	public List<PeriodeDto> findAllPeriode() {
		return perioderepo.findAll().stream().map(Periodicite::getperiodicite).collect(Collectors.toList());
	}

}
