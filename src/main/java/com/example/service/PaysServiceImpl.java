package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.PaysDtos;
import com.example.entity.Pays;
import com.example.repository.PaysRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaysServiceImpl implements PaysService{

	@Autowired
	private PaysRepository paysRepo;

	@Override
	public PaysDtos savePays(PaysDtos pd) {
		Pays pays=new Pays();
		pays.setLibelle(pd.getLibelle());

		Pays paysCree=paysRepo.save(pays);
		PaysDtos paysCreeDtos=new PaysDtos();
		paysCreeDtos.setIdPays(paysCree.getIdPays());
		paysCreeDtos.setLibelle(paysCree.getLibelle());

		return paysCreeDtos;

	}

}
