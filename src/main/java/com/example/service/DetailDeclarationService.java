package com.example.service;

import java.util.List;

import com.example.dtos.DetailDeclarationDto;
import com.example.entity.DetailDeclaration;

public interface DetailDeclarationService {

boolean	updateDetail(DetailDeclarationDto dd);
List<DetailDeclaration> getdetailBydeclarationId(long id);
}
