package com.example.entity;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.enums.Identifiant;
import com.example.enums.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.JoinColumn;
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
	
	
	
}
