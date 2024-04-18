package com.example.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.dtos.CompteDto;
import com.example.dtos.UserDtos;
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
@Table(name = "\"Compte\"")
public class Compte implements UserDetails{


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "yourGenerator6Name")
	@SequenceGenerator(name = "yourGenerator6Name", sequenceName = "Compte_seq", allocationSize = 1)
	private Long idCompte;

    private String email;
    private String password;
	private UserRole userRole;

    // Other fields
	
    @OneToOne(optional = true)
    @JoinColumn(name = "inscription_id", referencedColumnName = "idInscription")
    private User inscription;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(userRole.name()));
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		if(userRole==UserRole.Admin){
			return true;
		}else return inscription.isNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		if(userRole==UserRole.Admin){
			return true;
		}else return inscription.isEnabled();
	}
	public CompteDto getCompte() {
	    CompteDto compte = new CompteDto();
	    compte.setIdCompte(idCompte);
	    compte.setEmail(email);
	    
	    if (inscription != null) {
	        UserDtos userDto = new UserDtos();
	        userDto.setIdInscription(inscription.getInscription().getIdInscription());
	        userDto.setEmail(inscription.getInscription().getEmail());
	        userDto.setUserRole(inscription.getInscription().getUserRole());
	        userDto.setVerificationCode(inscription.getInscription().getVerificationCode());
	        userDto.setEnabled(inscription.getInscription().getEnabled());
	        userDto.setNonLocked(inscription.getInscription().isNonLocked());
	        userDto.setDateInscri(inscription.getInscription().getDateInscri());
	        userDto.setContribuable(inscription.getInscription().getContribuable());
	        userDto.setNom(inscription.getInscription().getNom());
	        userDto.setPrenom(inscription.getInscription().getPrenom());
	        userDto.setTypeIdentifiant(inscription.getInscription().getTypeIdentifiant());
	        userDto.setValueIdentifiant(inscription.getInscription().getValueIdentifiant());
	        userDto.setPoste(inscription.getInscription().getPoste());
	        userDto.setPassword(inscription.getPassword());
	        compte.setInscription(userDto);
	    } else {
	        // Handle the case where inscription is null, maybe set default values or do something else
	        // For now, let's just set the inscription to null in the CompteDto
	        compte.setInscription(null);
	    }
	    
	    compte.setPassword(password);
	    compte.setUserRole(userRole);
	    
	    return compte;
	}

}
