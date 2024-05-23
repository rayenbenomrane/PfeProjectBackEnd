package com.example.service;

import java.util.List;

import com.example.dtos.ReclamationDto;
import com.example.dtos.ReclamtionResponse;
import com.example.dtos.UpdateSolutionRecDto;
import com.example.entity.Reclamation;

public interface ReclamationService {
			Reclamation saveReclamation(ReclamationDto r);
			List<ReclamtionResponse> getAllReclamation();
			Reclamation updateSolution(UpdateSolutionRecDto rd);
}
