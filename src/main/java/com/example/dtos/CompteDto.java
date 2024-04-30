package com.example.dtos;

import com.example.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompteDto {
	private long idCompte;
	private String email;
    private String password;
	private UserRole userRole;
	private UserDtos inscription;
}
