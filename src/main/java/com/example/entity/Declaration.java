package com.example.entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor

@ToString
@Data
@Entity
@Table(name = "declaration")
public class Declaration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator11Name")
	@SequenceGenerator(name = "yourGenerator11Name", sequenceName = "decalaration_seq", allocationSize = 1)
	private long idDeclaration;

	private Date dateDeclaration;








}