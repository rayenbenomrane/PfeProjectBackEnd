package com.example.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dtos.CompteDto;
import com.example.dtos.UpdatePasswordDto;
import com.example.dtos.UserDtos;
import com.example.entity.Compte;
import com.example.entity.User;
import com.example.enums.UserRole;
import com.example.repository.CompteRepository;
import com.example.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl	implements AdminService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CompteRepository compteRepository;


	@Override
	public List<UserDtos> getAllInscription() {
		// TODO Auto-generated method stub
		return userRepository.findAll().stream().map(User::getInscription).collect(Collectors.toList());
	}



	@Override
	public CompteDto acceptInscri(UserDtos ud) {
		 User user=new User();
		 	user.setIdInscription(ud.getIdInscription());
	        user.setEmail(ud.getEmail());
	        user.setPassword(ud.getPassword());
	        user.setUserRole(UserRole.Client);
	        user.setPoste(ud.getPoste());
	        user.setEnabled(ud.getEnabled());
	        user.setContribuable(ud.getContribuable());
	        user.setNom(ud.getNom());
	        user.setPrenom(ud.getPrenom());
	        user.setTypeIdentifiant(ud.getTypeIdentifiant());
	        user.setValeurIdentifiant(ud.getValueIdentifiant());
	        user.setNonLocked(true);
	        user.setDateInscri(ud.getDateInscri());
	        user.setVerificationCode(ud.getVerificationCode());
	        User modifierCustomer=userRepository.save(user);
	        Compte compteCree=new Compte();
	        compteCree.setEmail(modifierCustomer.getEmail());
	        compteCree.setPassword(modifierCustomer.getPassword());
	        compteCree.setUserRole(modifierCustomer.getUserRole());
	        compteCree.setInscription(modifierCustomer);
	        Compte compteCreefinal=compteRepository.save(compteCree);
	        CompteDto compteCreeDto=new CompteDto();
	        compteCreeDto.setEmail(compteCreefinal.getEmail());
	        compteCreeDto.setPassword(compteCreefinal.getPassword());
	        compteCreeDto.setUserRole(compteCreefinal.getUserRole());
	        UserDtos UserDto=new UserDtos();
	       UserDto.setIdInscription(compteCreefinal.getInscription().getIdInscription());
	       UserDto.setEmail(compteCreefinal.getInscription().getEmail());
	        UserDto.setUserRole(compteCreefinal.getInscription().getUserRole());
	        UserDto.setVerificationCode(compteCreefinal.getInscription().getVerificationCode());
	        UserDto.setEnabled(true);
	        UserDto.setNonLocked(true);
	        UserDto.setDateInscri(compteCreefinal.getInscription().getDateInscri());
	        UserDto.setContribuable(compteCreefinal.getInscription().getContribuable());
	        UserDto.setNom(compteCreefinal.getInscription().getNom());
	      UserDto.setPrenom(compteCreefinal.getInscription().getPrenom());
	        UserDto.setTypeIdentifiant(compteCreefinal.getInscription().getTypeIdentifiant());
	        UserDto.setValueIdentifiant(compteCreefinal.getInscription().getValeurIdentifiant());
	        compteCreeDto.setInscription(UserDto);
	        return compteCreeDto;


	}
	@Override
public void sendVerificationEmail(UserDtos user) throws UnsupportedEncodingException, MessagingException {

		String subject="Felicitation,Compte Cree";
		String senderName="direction generale d'impots";
		String mailContent="<p>Dear  "+user.getEmail()+",</p>";
		mailContent +="<p>vous pouvez acceder a votre compte : <br> </p>";
		String url="http://localhost:4200/home";
		mailContent+="<h2><a href="+url+"\">Acceder</a></h2>";
		mailContent+="<p><br>DGI </p> ";
		MimeMessage message=mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message);
		helper.setFrom("pfedgi1920@gmail.com",senderName);
		helper.setTo(user.getEmail());
		helper.setText(mailContent, true);
		helper.setSubject(subject);

	        mailSender.send(message);

	}
	@Override
	public CompteDto changePassword(UpdatePasswordDto up) {
	    Optional<Compte> c=compteRepository.findById(up.getId());
	    if(verifyPassword(up.getPasswordPrec(), c.get().getPassword())) {
	        Compte nvCompte=new Compte();
	        nvCompte.setIdCompte(c.get().getIdCompte());
	        nvCompte.setEmail(c.get().getEmail());
	        nvCompte.setPassword((new BCryptPasswordEncoder().encode(up.getNvPassword())));
	        nvCompte.setUserRole(c.get().getUserRole());
	         Compte compteCreefinal=compteRepository.save(nvCompte);
	           CompteDto compteCreeDto=new CompteDto();
	           compteCreeDto.setEmail(compteCreefinal.getEmail());
	           compteCreeDto.setPassword(compteCreefinal.getPassword());
	           compteCreeDto.setUserRole(compteCreefinal.getUserRole());
	           return compteCreeDto;
	    }
	    else {
	        return null;
	    }

	}
	@Override
	public boolean verifyPassword(String rawPassword, String encodedPasswordFromDB) {

	       BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    return encoder.matches(rawPassword, encodedPasswordFromDB);
	}

}
