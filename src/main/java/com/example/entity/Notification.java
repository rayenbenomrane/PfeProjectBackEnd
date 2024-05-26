package com.example.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor

@ToString
@Entity
@Table(name = "notification")
public class Notification {
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator77Name")
	    @SequenceGenerator(name = "yourGenerator77Name", sequenceName = "notification_seq", allocationSize = 1)
		private Long idNotification;
		
		
		private Date dateNotification;
		
		
		private boolean checked;
		
		private boolean deleted;
		
		 @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
		    @JoinColumn(name = "reclamation_id")
		private Reclamation reclamation;
	 
}
