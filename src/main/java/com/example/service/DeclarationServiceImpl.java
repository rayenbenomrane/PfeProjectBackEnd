package com.example.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.DetailDeclarationDto;
import com.example.dtos.SaveDeclaration;
import com.example.entity.Declaration;
import com.example.entity.DetailDeclaration;
import com.example.entity.DetailImpot;
import com.example.entity.ObligationFiscale;
import com.example.repository.DeclarationRepository;
import com.example.repository.DetailDeclarationRepository;
import com.example.repository.DetailImpotRepository;
import com.example.repository.ObligationFiscaleRepository;
@Service
public class DeclarationServiceImpl implements DeclarationService{

	
	@Autowired 
	private ObligationFiscaleRepository obligationRepo;
	@Autowired
	private DetailImpotRepository detailimpotRepo;
	@Autowired
	private DetailDeclarationRepository detailDeclarationRepo;
	@Autowired
	private DeclarationRepository declarationRepo;
	
	@Override
	public Map<DetailImpot, DetailDeclarationDto> saveDeclaration(SaveDeclaration dc) {
	    Optional<ObligationFiscale> obligation = obligationRepo.findById(dc.getIdObligation());
	    Map<DetailImpot, DetailDeclarationDto> detailMap = new HashMap<>();
	    
	    if (obligation.isPresent()) {
	        Declaration newDeclaration = new Declaration();
	        newDeclaration.setObligation(obligation.get());
	        newDeclaration.setAnneeEffet(dc.getAnneeEffet());
	        newDeclaration.setMoisEffet(dc.getMoisEffet());
	        newDeclaration.setDateDeclaration(new Date());
	        this.declarationRepo.save(newDeclaration);
	        List<DetailImpot> lesDetailsImpot = detailimpotRepo.findByTypeImpot(obligation.get().getImpot());

	        for (DetailImpot detail : lesDetailsImpot) {
	            DetailDeclaration newDetailDeclaration = new DetailDeclaration();
	            newDetailDeclaration.setValeur(""); // Set valeur if needed

	            // Assuming you have setters for detailImpot and declaration in DetailDeclaration class
	            newDetailDeclaration.setDetailImpot(detail);
	            newDetailDeclaration.setDeclaration(newDeclaration);
	            this.detailDeclarationRepo.save(newDetailDeclaration);
	            DetailDeclarationDto dto=new DetailDeclarationDto();
	            dto.setIddetailDeclaration(newDetailDeclaration.getIdDetailDeclaration());
	            dto.setValeur(null);// Put the DetailImpot object as key and DetailDeclaration object as value into the map
	            detailMap.put(detail, dto);
	        }
	    }

	    return detailMap;
	}
}
