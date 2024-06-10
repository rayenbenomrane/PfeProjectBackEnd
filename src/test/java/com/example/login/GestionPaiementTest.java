package com.example.login;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.dtos.PaiementDto;
import com.example.entity.Declaration;
import com.example.entity.Paiement;
import com.example.repository.DeclarationRepository;
import com.example.repository.PaiementRepository;

import com.example.service.PaiementService;
import com.example.service.PaiementServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GestionPaiementTest {
	 @Mock
	    private DeclarationRepository declarationRepo;

	    @Mock
	    private PaiementRepository paiementRepo;

	    @InjectMocks
	    private PaiementServiceImpl paiementService; 
	@Test
	    public void testCreatePaiement() {
	         
	        PaiementDto paiementDto = new PaiementDto();
	        paiementDto.setIddeclaration(1L); // Set the ID of the declaration
	        paiementDto.setNumeroTransaction("123456789"); // Set a transaction number

	        Declaration declaration = new Declaration();
	        // Set properties of the declaration if needed

	        // Mock the behavior of the repository methods
	        when(declarationRepo.findById(paiementDto.getIddeclaration())).thenReturn(Optional.of(declaration));
	        when(paiementRepo.findByNumeroTransaction(paiementDto.getNumeroTransaction())).thenReturn(new ArrayList<>());

	        Paiement savedPaiement = new Paiement();
	        savedPaiement.setDeclaration(declaration);
	        savedPaiement.setNumeroTransaction(paiementDto.getNumeroTransaction());
	        savedPaiement.setDatePaiement(new Date());

	        when(paiementRepo.save(any(Paiement.class))).thenReturn(savedPaiement);

	        // Invoke the method to be tested
	        boolean result = paiementService.createPaiement(paiementDto);

	        // Verify interactions and assertions
	        assertTrue(result);
	        verify(declarationRepo).findById(paiementDto.getIddeclaration());
	        verify(paiementRepo).findByNumeroTransaction(paiementDto.getNumeroTransaction());
	        verify(paiementRepo).save(any(Paiement.class));
	    }
}
