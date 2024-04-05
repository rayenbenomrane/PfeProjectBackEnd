package com.example.service;

import com.example.dtos.ContribuableDtos;

public interface ContribuableService {

	ContribuableDtos saveContribuable(ContribuableDtos cd);
	ContribuableDtos findContribuable(int matriculeFiscale);

}
