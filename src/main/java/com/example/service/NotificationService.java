package com.example.service;

import java.util.List;

import com.example.dtos.NotificationDto;
import com.example.entity.Notification;

public interface NotificationService {

	void creatNotification(Long id,String Solution);
	List<NotificationDto> getNotificationByMatricule(int matricule);
	
}
