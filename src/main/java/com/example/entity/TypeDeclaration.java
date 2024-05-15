package com.example.entity;

import com.example.dtos.TypeDeclarationDto;
import com.example.enums.TypeDeclarationEnum;

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
@Table(name = "TypeDeclaration")
public class TypeDeclaration {





	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator20Name")
	 @SequenceGenerator(name = "yourGenerator20Name", sequenceName = "typedeclaration_seq", allocationSize = 1)
	private Long idTypeDeclaration;


	 private TypeDeclarationEnum libelle;

	 public TypeDeclarationDto getType() {
		 TypeDeclarationDto type=new TypeDeclarationDto();
		 type.setIdTypeDeclaration(idTypeDeclaration);
		 type.setType(libelle);
		 return type;
	 }

}
