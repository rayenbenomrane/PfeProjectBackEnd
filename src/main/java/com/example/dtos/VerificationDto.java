package com.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VerificationDto {

	private String message;
	private boolean valide;
	private UserDtos inscription;
}

