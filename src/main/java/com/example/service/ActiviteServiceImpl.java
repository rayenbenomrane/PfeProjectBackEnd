package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.ActiviteDtos;
import com.example.entity.Activite;
import com.example.repository.ActiviteRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ActiviteServiceImpl implements ActiviteService{

	@Autowired
	private ActiviteRepository Activiterepo;
	
	@Override
	public ActiviteDtos saveActivite(ActiviteDtos ad) {
		Activite activite=new Activite();
		activite.setLibelle(ad.getLibelle());
		
		Activite activiteCree=Activiterepo.save(activite);
		ActiviteDtos activiteCreeDtos=new ActiviteDtos();
		activiteCreeDtos.setIdActivite(activiteCree.getIdActivite());
		activiteCreeDtos.setLibelle(activiteCree.getLibelle());
		
		return activiteCreeDtos;
	}

}
