package com.example.service;

import java.io.UnsupportedEncodingException;

import com.example.dtos.SignupRequest;
import com.example.dtos.UserDtos;
import com.example.entity.User;

import jakarta.mail.MessagingException;

public interface AuthService {
	UserDtos createCustomer(SignupRequest signupRequest);
	 void sendVerificationEmail(UserDtos user) throws UnsupportedEncodingException, MessagingException;
	  boolean verify(String verificationCode);
}
