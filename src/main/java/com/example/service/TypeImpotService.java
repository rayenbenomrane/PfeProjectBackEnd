package com.example.service;

import java.util.List;

import com.example.dtos.ImpotDto;
import com.example.dtos.TypeImpotDto;

public interface TypeImpotService {
	TypeImpotDto saveImpot(TypeImpotDto td);
	List<TypeImpotDto> getAllImpots();
	TypeImpotDto findTypeImpotbyLibelle(String libelle);
	boolean updateImpot(ImpotDto id);

}
