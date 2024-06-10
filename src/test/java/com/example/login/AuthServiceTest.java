package com.example.login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.dtos.SignupRequest;
import com.example.dtos.UserDtos;
import com.example.entity.Compte;
import com.example.entity.User;
import com.example.jwt.UserServiceImpli;
import com.example.repository.CompteRepository;
import com.example.repository.UserRepository;
import com.example.service.AuthServiceImpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

	@Mock
    private JavaMailSender mailSender;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CompteRepository compteRepo;
    
    @InjectMocks 
    private UserServiceImpli userservice;

    @InjectMocks
    private AuthServiceImpl authService;
    
    
    @Test
    void testCreateCustomer() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        // Add other required fields

        User savedUser = new User();
        savedUser.setIdInscription(1L);
        savedUser.setEmail(signupRequest.getEmail());
        // Set other properties as needed

        // Mock UserRepository save method to return the saved user
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        UserDtos createdUser = authService.createCustomer(signupRequest);

        // Then
        assertNotNull(createdUser);
        assertEquals(savedUser.getIdInscription(), createdUser.getIdInscription());
        assertEquals(savedUser.getEmail(), createdUser.getEmail());
        // Add more assertions to verify other properties of the created user DTO
    }
    @Test
    void testSendVerificationEmail() throws UnsupportedEncodingException, MessagingException {
        // Given
        UserDtos user = new UserDtos();
        user.setEmail("rbenomrane15@example.com");
        // Add other required fields

        MimeMessage mimeMessage = mock(MimeMessage.class);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        doNothing().when(mailSender).send(any(MimeMessage.class));

        // When
        authService.sendVerificationEmail(user);

        // Then
        // Add assertions to verify the behavior of sendVerificationEmail method
    }

    @Test
    void testVerify() {
        String verificationCode = "abc123";

        // Mock UserRepository findByVerificationCode method
        when(userRepository.findByVerificationCode(verificationCode)).thenReturn(Optional.of(new User()));

        boolean result = authService.verify(verificationCode);

        assertTrue(result);
        // Add assertions to verify the behavior of verify method
    }
    @Test
    void testLoadUserByUsername_UserFound() {
        // Given
        String username = "rbenomrane15@gmail.com";
        // Mock the behavior of compteRepository.findByEmail to return an optional user
        Compte user = new Compte(); // Create a mocked user object
        when(compteRepo.findByEmail(username)).thenReturn(Optional.of(user));

        // When
        UserDetails userDetails = userservice.userDetailsService().loadUserByUsername(username);

        // Then
        assertNotNull(userDetails);
        // Add assertions to verify the behavior when the user is found
    }
    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Given
        String username = "test@example.com";
        // Mock the behavior of compteRepository.findByEmail to return an empty optional
        when(compteRepo.findByEmail(username)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(UsernameNotFoundException.class, () -> {
            userservice.userDetailsService().loadUserByUsername(username);
        });
    }
}
