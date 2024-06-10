package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.DetailDeclarationDto;
import com.example.entity.Declaration;
import com.example.entity.DetailDeclaration;
import com.example.repository.DeclarationRepository;
import com.example.repository.DetailDeclarationRepository;
@Service
public class DetailDeclarationServiceImpl implements DetailDeclarationService{


	@Autowired
	private DetailDeclarationRepository detailrepo;
	@Autowired
	private DeclarationRepository declarationrepo;


	@Override
	public boolean updateDetail(DetailDeclarationDto dd) {
	Optional<DetailDeclaration> detail=detailrepo.findById(dd.getIddetailDeclaration());
	if(detail.isPresent()) {
		detail.get().setValeur(dd.getValeur());
		
		detailrepo.save(detail.get());
		return true;
	}else return false;
	}


	@Override
	public List<DetailDeclaration> getdetailBydeclarationId(long id) {
		Optional<Declaration> declaration=declarationrepo.findById(id);
		List<DetailDeclaration> list=detailrepo.findByDeclaration(declaration.get());
		return list;

	}


}
