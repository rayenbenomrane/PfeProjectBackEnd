package com.example.dtos;

import lombok.Data;

@Data
public class UpdatePasswordDto {
	private Long id;
	private String passwordPrec;
	private String nvPassword;
}
