package com.example.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.example.dtos.CompteDto;
import com.example.dtos.UpdatePasswordDto;
import com.example.dtos.UserDtos;

import jakarta.mail.MessagingException;

public interface AdminService {
List<UserDtos> getAllInscription();
CompteDto acceptInscri(UserDtos ud);
void sendVerificationEmail(UserDtos user) throws UnsupportedEncodingException, MessagingException;
public CompteDto changePassword(UpdatePasswordDto up);
public boolean verifyPassword(String rawPassword, String encodedPasswordFromDB);
}
