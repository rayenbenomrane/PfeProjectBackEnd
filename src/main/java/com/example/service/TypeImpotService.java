package com.example.service;

import java.util.List;

import com.example.dtos.ImpotDto;
import com.example.dtos.TypeImpotDto;
import com.example.entity.TypeImpot;

public interface TypeImpotService {
	TypeImpotDto saveImpot(TypeImpotDto td);
	List<TypeImpotDto> getAllImpots();
	TypeImpotDto findTypeImpotbyLibelle(String libelle);
	boolean updateImpot(ImpotDto id);
	TypeImpot updateTypeImpot(TypeImpotDto td);

}
