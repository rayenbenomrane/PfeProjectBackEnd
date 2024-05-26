package com.example.dtos;

import java.util.Date;

import com.example.entity.Declaration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

	private long idReclamation;
	private Date dateReponse;
	private String titre;
	private String Solution;
	
	
}
