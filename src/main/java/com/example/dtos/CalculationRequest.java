package com.example.dtos;

import java.util.Map;

import lombok.Data;
@Data
public class CalculationRequest {
	 private String formula;
	    private Map<String, Double> values;
}
