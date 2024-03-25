package com.example.dtos;

import com.example.entity.User;
import com.example.enums.UserRole;

import lombok.Data;

@Data
public class CompteDto {
	private String email;
    private String password;
	private UserRole userRole;
	private User inscription;
}
