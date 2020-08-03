package com.example.cursorest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursorest.entity.Profesor;
import com.example.cursorest.service.IProfesorService;

@RestController
@RequestMapping("/api")
public class ProfesorRestController {

	@Autowired
	private IProfesorService  profesorService;
	
	@GetMapping("/profesores")
	public List<Profesor> getProfesores(){
		return profesorService.findAll();
	}
	
	
}
