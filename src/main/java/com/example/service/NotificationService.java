package com.example.service;

import java.util.List;

import com.example.dtos.NotificationDto;

public interface NotificationService {

	void creatNotification(Long id,String Solution);
	List<NotificationDto> getNotificationByMatricule(int matricule);
	void updateNotification(Long id);
	void updateDeleted(long id);

}
