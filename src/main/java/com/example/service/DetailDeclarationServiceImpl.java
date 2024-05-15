package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.DetailDeclarationDto;
import com.example.entity.DetailDeclaration;
import com.example.repository.DetailDeclarationRepository;
@Service
public class DetailDeclarationServiceImpl implements DetailDeclarationService{

	
	@Autowired 
	private DetailDeclarationRepository detailrepo;
	
	
	@Override
	public boolean updateDetail(DetailDeclarationDto dd) {
	Optional<DetailDeclaration> detail=detailrepo.findById(dd.getIddetailDeclaration());
	if(detail.isPresent()) {
		detail.get().setValeur(dd.getValeur());
		detailrepo.save(detail.get());
		return true;
	}else return false;
	}

}
