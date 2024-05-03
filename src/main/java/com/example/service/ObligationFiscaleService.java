package com.example.service;

import java.util.List;

import com.example.dtos.ObligationDto;
import com.example.entity.Contribuable;
import com.example.entity.Declaration;

public interface ObligationFiscaleService {
	Declaration getNumerodeclaration(Contribuable cd,int iddecalaration);
	
	List<ObligationDto> getlesObligationsdeContribuable(Contribuable cd);
}
