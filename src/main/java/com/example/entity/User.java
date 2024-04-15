package com.example.entity;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.dtos.UserDtos;
import com.example.enums.Identifiant;
import com.example.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Builder
@Getter

@AllArgsConstructor

@NoArgsConstructor

@ToString
@Data
@Entity
@Table(name = "\"inscription\"")
public class User implements UserDetails{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator5Name")
	@SequenceGenerator(name = "yourGenerator5Name", sequenceName = "user_seq", allocationSize = 1)
	private Long idInscription;

	private String email;
	private Identifiant typeIdentifiant;
	private String valeurIdentifiant;
	private String nom;
	private String prenom;
	private String password;
	private boolean enabled;
	private String verificationCode;
	private boolean NonLocked;
	private String Poste;
	private java.util.Date dateInscri;


	private UserRole userRole;

	 @OneToOne
	    @JoinColumn(name = "contribuable_id", unique = true, nullable = false)
	 private  Contribuable contribuable;



	 @Override
	public String getPassword() {
	        return password;
	    }
	 @Override
		public Collection<? extends GrantedAuthority> getAuthorities() {

			return List.of(new SimpleGrantedAuthority(userRole.name()));
		}


	@Override
	public String getUsername() {

		return email;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return NonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}
	public UserDtos getInscription() {
		UserDtos inscri=new UserDtos();
		inscri.setIdInscription(idInscription);
		inscri.setPassword(password);
		inscri.setEmail(email);
		inscri.setContribuable(contribuable);
		inscri.setDateInscri(dateInscri);
		inscri.setEnabled(enabled);
		inscri.setNonLocked(NonLocked);
		inscri.setNom(nom);
		inscri.setPrenom(prenom);
		inscri.setPoste(Poste);
		inscri.setUserRole(userRole);
		inscri.setTypeIdentifiant(typeIdentifiant);
		inscri.setValueIdentifiant(valeurIdentifiant);
		return inscri;
	}



}
