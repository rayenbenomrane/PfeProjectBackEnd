package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Notification;
import com.example.entity.Reclamation;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
			List<Notification> findByReclamation(Reclamation reclamation);
}
