package com.example.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
	private Long idNotification;
	private long idReclamation;
	private Date dateReponse;
	private String titre;
	private String Solution;
	private boolean checked;
	private boolean deleted;


}
