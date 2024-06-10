package com.example.login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.dtos.DetailDeclarationDto;
import com.example.dtos.SaveDeclaration;
import com.example.dtos.SaveMontant;
import com.example.entity.Declaration;
import com.example.entity.DetailImpot;
import com.example.entity.ObligationFiscale;

import com.example.entity.TypeImpot;
import com.example.enums.TypeDeclarationEnum;
import com.example.repository.DeclarationRepository;
import com.example.repository.DetailDeclarationRepository;
import com.example.repository.DetailImpotRepository;
import com.example.repository.ObligationFiscaleRepository;
import com.example.service.DeclarationServiceImpl;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GestionDeclarationTest {
	 	
	
	
		@Mock
	    private ObligationFiscaleRepository obligationRepo;

	    @Mock
	    private DetailImpotRepository detailimpotRepo;

	    @Mock
	    private DetailDeclarationRepository detailDeclarationRepo;

	    @Mock
	    private DeclarationRepository declarationRepo;

	    @InjectMocks
	    private DeclarationServiceImpl declarationService;

	    @Test
	    public void testUpdateMontantaCalculer() {
	        // Prepare test data
	        SaveMontant saveMontant = new SaveMontant();
	        saveMontant.setIdDeclaration(1L); // Set the ID of the declaration for findById

	        Declaration declaration = new Declaration();
	        // Set properties of declaration

	        when(declarationRepo.findById(saveMontant.getIdDeclaration())).thenReturn(Optional.of(declaration));

	        // Invoke the method to be tested
	        boolean updated = declarationService.updateMontantaCalculer(saveMontant);

	        // Verify interactions and assertions
	        assertTrue(updated);
	        assertEquals(saveMontant.getMontantApayer(), declaration.getMontantaCalculer());
	        verify(declarationRepo).findById(saveMontant.getIdDeclaration());
	        verify(declarationRepo).save(declaration);
	    }

	    @Test
	    public void testGetDeclarationsByMatriculeFiscale() {
	        // Prepare test data
	        int matriculeFiscale = 12345; 

	        List<Declaration> declarations = new ArrayList<>();
	        // Add declarations to the list

	        when(declarationRepo.findByObligation_Contribuable_MatriculeFiscale(matriculeFiscale)).thenReturn(declarations);

	        // Invoke the method to be tested
	        List<Declaration> result = declarationService.getDeclarationsByMatriculeFiscale(matriculeFiscale);

	        // Verify interactions and assertions
	        assertEquals(declarations, result);
	        verify(declarationRepo).findByObligation_Contribuable_MatriculeFiscale(matriculeFiscale);
	    }
}
