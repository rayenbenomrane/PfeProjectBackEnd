package com.example.login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.dtos.ReclamationDto;
import com.example.dtos.ReclamtionResponse;
import com.example.dtos.UpdateSolutionRecDto;
import com.example.entity.Contribuable;
import com.example.entity.Declaration;
import com.example.entity.Reclamation;
import com.example.enums.Etat;
import com.example.repository.ContribuableRepository;
import com.example.repository.DeclarationRepository;
import com.example.repository.ReclamationRepository;
import com.example.service.ReclamationServiceImpl;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GestionReclamationTest {
	 @Mock
	    private ReclamationRepository reclamationRepo;

	    @Mock
	    private DeclarationRepository declarationRepo;

	    @Mock
	    private ContribuableRepository contribuableRepository;

	    @InjectMocks
	    private ReclamationServiceImpl reclamationService;

	    @Test
	    public void testSaveReclamation_withDeclaration() {
	        // Prepare test data
	        ReclamationDto  reclamationDto = new ReclamationDto();
	        reclamationDto.setIdDeclaration(1L);
	        reclamationDto.setTitre("Test Title");
	        reclamationDto.setContenu("Test Content");
	        
	        Contribuable contribuable = new Contribuable();
	        contribuable.setIdContribuable(1L);
	        reclamationDto.setContribuable(contribuable);

	        Declaration declaration = new Declaration();
	        when(declarationRepo.findById(1L)).thenReturn(Optional.of(declaration));
	        when(contribuableRepository.findById(1L)).thenReturn(Optional.of(contribuable));
	        when(reclamationRepo.save(any(Reclamation.class))).thenReturn(new Reclamation());

	        // Invoke the method to be tested
	        Reclamation result = reclamationService.saveReclamation(reclamationDto);

	        // Verify interactions and assertions
	        assertNotNull(result);
	        verify(reclamationRepo).save(any(Reclamation.class));
	    }

	    @Test
	    public void testSaveReclamation_withoutDeclaration() {
	        // Prepare test data
	        ReclamationDto reclamationDto = new ReclamationDto();
	        reclamationDto.setIdDeclaration(0L);
	        reclamationDto.setTitre("Test Title");
	        reclamationDto.setContenu("Test Content");
	        
	        Contribuable contribuable = new Contribuable();
	        contribuable.setIdContribuable(1L);
	        reclamationDto.setContribuable(contribuable);

	        when(contribuableRepository.findById(1L)).thenReturn(Optional.of(contribuable));
	        when(reclamationRepo.save(any(Reclamation.class))).thenReturn(new Reclamation());

	        // Invoke the method to be tested
	        Reclamation result = reclamationService.saveReclamation(reclamationDto);

	        // Verify interactions and assertions
	        assertNotNull(result);
	        verify(reclamationRepo).save(any(Reclamation.class));
	    }

	    @Test
	    public void testUpdateSolution() {
	        // Prepare test data
	        UpdateSolutionRecDto updateSolutionRecDto = new UpdateSolutionRecDto();
	        updateSolutionRecDto.setIdReclamation(1L);
	        updateSolutionRecDto.setSolution("New Solution");
	        updateSolutionRecDto.setEtat(Etat.EN_COURS);

	        Reclamation reclamation = new Reclamation();
	        when(reclamationRepo.findById(1L)).thenReturn(Optional.of(reclamation));
	        when(reclamationRepo.save(any(Reclamation.class))).thenReturn(reclamation);

	        // Invoke the method to be tested
	        Reclamation result = reclamationService.updateSolution(updateSolutionRecDto);

	        // Verify interactions and assertions
	        assertNotNull(result);
	        assertEquals("New Solution", result.getSolution());
	        verify(reclamationRepo).save(any(Reclamation.class));
	    }

	    @Test
	    public void testGetAllReclamation() {
	        // Prepare test data
	        List<Reclamation> reclamations = new ArrayList<>();
	        reclamations.add(new Reclamation());
	        when(reclamationRepo.findAll()).thenReturn(reclamations);

	        // Invoke the method to be tested
	        List<ReclamtionResponse> result = reclamationService.getAllReclamation();

	        // Verify interactions and assertions
	        assertNotNull(result);
	        assertEquals(1, result.size());
	        verify(reclamationRepo).findAll();
	    }

	    @Test
	    public void testUpdateEtat() {
	        // Prepare test data
	        Reclamation reclamation = new Reclamation();
	        when(reclamationRepo.findById(1L)).thenReturn(Optional.of(reclamation));
	        when(reclamationRepo.save(any(Reclamation.class))).thenReturn(reclamation);

	        // Invoke the method to be tested
	        boolean result = reclamationService.updateEtat(1L);

	        // Verify interactions and assertions
	        assertTrue(result);
	        verify(reclamationRepo).save(any(Reclamation.class));
	    }

	    @Test
	    public void testRefusEtat() {
	        // Prepare test data
	        Reclamation reclamation = new Reclamation();
	        when(reclamationRepo.findById(1L)).thenReturn(Optional.of(reclamation));
	        when(reclamationRepo.save(any(Reclamation.class))).thenReturn(reclamation);

	        // Invoke the method to be tested
	        boolean result = reclamationService.refusEtat(1L);

	        // Verify interactions and assertions
	        assertTrue(result);
	        verify(reclamationRepo).save(any(Reclamation.class));
	    }
}
