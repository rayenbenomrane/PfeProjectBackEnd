package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.ReclamtionResponse;
import com.example.dtos.UpdateSolutionRecDto;
import com.example.entity.DetailDeclaration;
import com.example.entity.Reclamation;
import com.example.service.DetailDeclarationService;
import com.example.service.NotificationService;
import com.example.service.ReclamationService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Responsable")
public class ResponsableController {
	
	
	@Autowired
	private ReclamationService reclamationservice;
	@Autowired
	private DetailDeclarationService detailservice;
	@Autowired
	private NotificationService notificationservice;
	
	
	
	
	@PutMapping("/updatereclamation")
	public ResponseEntity<?> saveReclamation(@RequestBody UpdateSolutionRecDto reclamationDto) {
	    Reclamation saved = reclamationservice.updateSolution(reclamationDto);
	    if (saved!=null) {
	    	notificationservice.creatNotification(reclamationDto.getIdReclamation(), reclamationDto.getSolution());
	        return ResponseEntity.ok(saved);
	        
	    } else {
	        return ResponseEntity.badRequest().body(null);
	    }
	}
	@GetMapping("lesreclamations")
	public ResponseEntity<?> lesreclamations(){
		List<ReclamtionResponse> list=reclamationservice.getAllReclamation();
		return ResponseEntity.ok(list);
	}
	@GetMapping("lesdetailsdeclaration")
	public ResponseEntity<?> lesdetails(@RequestParam("declaration") Long iddeclaration){
		List<DetailDeclaration> list=detailservice.getdetailBydeclarationId(iddeclaration);
		return ResponseEntity.ok(list);
	}
}
