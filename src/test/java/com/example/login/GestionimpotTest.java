package com.example.login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.dtos.ImpotDto;
import com.example.dtos.PeriodeDto;
import com.example.dtos.TypeImpotDto;
import com.example.entity.Periodicite;
import com.example.entity.TypeImpot;
import com.example.enums.Periode;
import com.example.repository.EcheanceRepository;
import com.example.repository.PeriodiciteRepository;
import com.example.repository.TypeImpotRepository;
import com.example.service.TypeImpotServiceImpl;

import io.jsonwebtoken.lang.Arrays;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GestionimpotTest {

    @Mock
    private PeriodiciteRepository periodiciteRepository;

    @Mock
    private TypeImpotRepository typeImpotRepository;
    @Mock
    private EcheanceRepository echeanceRepository;

    @InjectMocks
    private TypeImpotServiceImpl typeImpotService;

    
    

    @Test
    public void testFindTypeImpotbyLibelle() {
        // Prepare test data
        String libelle = "ImpotTest";
        TypeImpot typeImpot = new TypeImpot();
        typeImpot.setLibelle(libelle);
       
        typeImpot.setPeriodicite(Periode.MENSUELLE);
        when(typeImpotRepository.findByLibelle(libelle)).thenReturn(Optional.of(typeImpot));

        // Invoke the method to be tested
        TypeImpotDto foundImpot = typeImpotService.findTypeImpotbyLibelle(libelle);

        // Verify interactions and assertions
        verify(typeImpotRepository).findByLibelle(libelle);
        assertNotNull(foundImpot);
        assertEquals(libelle, foundImpot.getLibelle());
        assertEquals(1L, foundImpot.getPeriodicite());
        assertEquals(Periode.MENSUELLE, foundImpot.getPeriodicite());
    }

    @Test
    public void testUpdateImpot() {
        // Prepare test data
        String libelle = "ImpotTest";
        ImpotDto impotDto = new ImpotDto();
        impotDto.setLibelle(libelle);
        impotDto.setFormule("New Formula");
        TypeImpot typeImpot = new TypeImpot();
        typeImpot.setLibelle(libelle);
        when(typeImpotRepository.findByLibelle(libelle)).thenReturn(Optional.of(typeImpot));
        when(typeImpotRepository.save(any(TypeImpot.class))).thenReturn(typeImpot);

        // Invoke the method to be tested
        boolean updated = typeImpotService.updateImpot(impotDto);

        // Verify interactions and assertions
        verify(typeImpotRepository).findByLibelle(libelle);
        verify(typeImpotRepository).save(any(TypeImpot.class));
        assertTrue(updated);
    }
}