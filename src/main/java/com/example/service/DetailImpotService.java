package com.example.service;

import java.util.List;

import com.example.dtos.DetailImpotDto;

import com.example.entity.DetailImpot;

public interface DetailImpotService {

	DetailImpotDto saveDetailImpot(DetailImpotDto di);
	List<DetailImpot> findbytypeImpot(String libelle);
}
