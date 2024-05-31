package com.example.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dtos.PasswordDto;
import com.example.dtos.ResetPassword;
import com.example.dtos.SignupRequest;
import com.example.dtos.UserDtos;
import com.example.entity.Compte;
import com.example.entity.User;
import com.example.enums.UserRole;
import com.example.repository.CompteRepository;
import com.example.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CompteRepository compteRepo;
	@Override
	public UserDtos  createCustomer(SignupRequest signupRequest) {

        User user=new User();
        user.setEmail(signupRequest.getEmail());
        //user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.Client);
        user.setPoste(signupRequest.getPoste());
        user.setEnabled(false);
        user.setContribuable(signupRequest.getContribuable());
        user.setNom(signupRequest.getNom());
        user.setPrenom(signupRequest.getPrenom());
        user.setTypeIdentifiant(signupRequest.getTypeIdentifiant());
        user.setValeurIdentifiant(signupRequest.getValueIdentifiant());
        user.setNonLocked(false);
        user.setDateInscri(new Date());
        String randomCode=RandomString.make(64);
        user.setVerificationCode(randomCode);
        User createdCustomer=userRepository.save(user);
        UserDtos createdUserDto=new UserDtos();
        createdUserDto.setIdInscription(createdCustomer.getIdInscription());
        createdUserDto.setEmail(createdCustomer.getEmail());
        createdUserDto.setUserRole(createdCustomer.getUserRole());
        createdUserDto.setVerificationCode(createdCustomer.getVerificationCode());
        createdUserDto.setEnabled(false);
        createdUserDto.setNonLocked(false);
        createdUserDto.setDateInscri(new Date());
        createdUserDto.setContribuable(createdCustomer.getContribuable());
        createdUserDto.setNom(createdCustomer.getNom());
        createdUserDto.setPrenom(createdCustomer.getPrenom());
        createdUserDto.setTypeIdentifiant(createdCustomer.getTypeIdentifiant());
        createdUserDto.setValueIdentifiant(createdCustomer.getValeurIdentifiant());
        return createdUserDto;

    }

	@Override
	public void sendVerificationEmail(UserDtos user) throws UnsupportedEncodingException, MessagingException {

		String subject="Please verify your registration";
		String senderName="direction generale d'impots";
		String mailContent="<p>Dear  "+user.getEmail()+",</p>";
		mailContent +="<p>Please click the link below to verify your registration : <br> </p>";
		String url = "http://localhost:4200/createpassword/" + user.getVerificationCode();
		mailContent += "<h2><a href=\"" + url + "\">verify</a></h2>";
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
	public boolean verify(String verificationCode) {

		 if (verificationCode == null || verificationCode.trim().isEmpty()) {
		        System.out.println("Verification code is empty or whitespace.");
		        return false;
		    }


		 Optional<User> userOptional = userRepository.findByVerificationCode(verificationCode);
		 System.out.println("Input Verification Code: " + verificationCode);
		 System.out.println("User Optional: " + userOptional);

	    if (userOptional.isPresent()) {
	        User user = userOptional.get();

	        System.out.println("Found User: " + user);

	        if (!user.isEnabled()) {
	            user.setEnabled(true);

	            userRepository.save(user);
	            return true;
	        }
	    }

	   // System.out.println("User not found or already enabled.");
	    return false;

	}

	@Override
	public UserDtos validePassword(PasswordDto pd) {

	 User user=new User();
	 user.setContribuable(pd.getInscription().getContribuable());
	 user.setDateInscri(pd.getInscription().getDateInscri());
	 user.setEmail(pd.getInscription().getEmail());
	 user.setEnabled(pd.getInscription().getEnabled());
	 user.setIdInscription(pd.getInscription().getIdInscription());
	 user.setValeurIdentifiant(pd.getInscription().getValueIdentifiant());
	 user.setVerificationCode(pd.getInscription().getVerificationCode());
	 user.setPoste(pd.getInscription().getPoste());
	 user.setPrenom(pd.getInscription().getPrenom());
	 user.setNonLocked(pd.getInscription().isNonLocked());
	 user.setNom(pd.getInscription().getNom());
	 user.setUserRole(pd.getInscription().getUserRole());
	 user.setTypeIdentifiant(pd.getInscription().getTypeIdentifiant());


	 user.setPassword(new BCryptPasswordEncoder().encode(pd.getPassword()));
	 User createdCustomer=userRepository.save(user);
	 UserDtos createdUserDto=new UserDtos();
     createdUserDto.setIdInscription(createdCustomer.getIdInscription());
     createdUserDto.setEmail(createdCustomer.getEmail());
     createdUserDto.setUserRole(createdCustomer.getUserRole());
     createdUserDto.setVerificationCode(createdCustomer.getVerificationCode());
     createdUserDto.setEnabled(createdCustomer.isEnabled());
     createdUserDto.setNonLocked(createdCustomer.isNonLocked());
     createdUserDto.setDateInscri(createdCustomer.getDateInscri());
     createdUserDto.setContribuable(createdCustomer.getContribuable());
     createdUserDto.setNom(createdCustomer.getNom());
     createdUserDto.setPrenom(createdCustomer.getPrenom());
     createdUserDto.setTypeIdentifiant(createdCustomer.getTypeIdentifiant());
     createdUserDto.setValueIdentifiant(createdCustomer.getValeurIdentifiant());
     return createdUserDto;

	}

	@Override
	public UserDtos convertUser(String code) {
		// TODO Auto-generated method stub
		 Optional<User> createdCustomer=userRepository.findByVerificationCode(code);
		 UserDtos createdUserDto=new UserDtos();
		 createdUserDto.setIdInscription(createdCustomer.get().getIdInscription());
	     createdUserDto.setEmail(createdCustomer.get().getEmail());
	     createdUserDto.setUserRole(createdCustomer.get().getUserRole());
	     createdUserDto.setVerificationCode(createdCustomer.get().getVerificationCode());
	     createdUserDto.setEnabled(createdCustomer.get().isEnabled());
	     createdUserDto.setNonLocked(createdCustomer.get().isNonLocked());
	     createdUserDto.setDateInscri(createdCustomer.get().getDateInscri());
	     createdUserDto.setContribuable(createdCustomer.get().getContribuable());
	     createdUserDto.setNom(createdCustomer.get().getNom());
	     createdUserDto.setPrenom(createdCustomer.get().getPrenom());
	     createdUserDto.setTypeIdentifiant(createdCustomer.get().getTypeIdentifiant());
	     createdUserDto.setValueIdentifiant(createdCustomer.get().getValeurIdentifiant());
	     createdUserDto.setPoste(createdCustomer.get().getPoste());
	     return createdUserDto;
	}
	@Override
    public boolean sendUpdatePasswordEmail(String email) throws UnsupportedEncodingException, MessagingException {
Optional<User> ins=this.userRepository.findByEmail(email);
if(ins.isPresent()) {
          String subject = "Password Reset Request";
            String senderName = "Direction Générale des Impôts";
            String mailContent = "<p>Dear " + email + ",</p>";
            mailContent += "<p>We received a request to reset your password. Please click the link below to reset your password: <br></p>";
            String url = "http://localhost:4200/admin/resetpassword/"+email;
            mailContent += "<h2><a href="+ url + ">Reset Password</a></h2>";
            mailContent += "<p>If you did not request a password reset, please ignore this email.</p>";
            mailContent += "<p><br>Direction Générale des Impôts</p>";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom("pfedgi1920@gmail.com", senderName);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(mailContent, true);

            mailSender.send(message);
            return true;
}else return false;
    }
    @Override
    public boolean resetPassword(ResetPassword rp) {
        Optional<User> ins=this.userRepository.findByEmail(rp.getEmail());
        Optional<Compte> compte=this.compteRepo.findByEmail(rp.getEmail());
        if(ins.isPresent()&&compte.isPresent()) {
            ins.get().setPassword(new BCryptPasswordEncoder().encode(rp.getPassword()));
            compte.get().setPassword(new BCryptPasswordEncoder().encode(rp.getPassword()));
            this.compteRepo.save(compte.get());
            this.userRepository.save(ins.get());
            return true;
        }else return false;
    }


}
