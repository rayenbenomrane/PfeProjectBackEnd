package com.example.entity;

import com.example.dtos.PeriodeDto;
import com.example.enums.Periode;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Periodicite")
public class Periodicite {

	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator9Name")
	 @SequenceGenerator(name = "yourGenerator9Name", sequenceName = "periodicite_seq", allocationSize = 1)
	private long idPeriodicite;

	private Periode periode;
	public PeriodeDto getperiodicite() {
		PeriodeDto periode1=new PeriodeDto();
		periode1.setIdPeriodicite(idPeriodicite);
		periode1.setPeriode(periode);
		return periode1;
	}
}
