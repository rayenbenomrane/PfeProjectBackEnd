package com.example.dtos;

import com.example.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PasswordDto {
private String password;
private UserDtos inscription;
}
