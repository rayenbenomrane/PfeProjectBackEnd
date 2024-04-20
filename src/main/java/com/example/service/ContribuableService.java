package com.example.service;

import java.util.List;

import com.example.dtos.ContribuableDtos;

public interface ContribuableService {

	ContribuableDtos saveContribuable(ContribuableDtos cd);
	ContribuableDtos findContribuable(int matriculeFiscale);
	List<ContribuableDtos> lesContribuables();

}
