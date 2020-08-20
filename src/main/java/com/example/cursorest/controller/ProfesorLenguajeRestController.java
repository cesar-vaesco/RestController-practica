package com.example.cursorest.controller;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursorest.entity.Lenguaje;
import com.example.cursorest.entity.Profesor;
import com.example.cursorest.model.ProfesorLenguaje;
import com.example.cursorest.service.ILenguajeService;
import com.example.cursorest.service.IProfesorService;

@RestController
@RequestMapping("/api")
public class ProfesorLenguajeRestController {

	@Autowired
	private ILenguajeService lenguajeService;

	@Autowired
	private IProfesorService profesorService;

	
	
	/*
	 * Requiere se le pase un profesor el cual sera vía id
	 * URL: http://localhost:8040/api/lenguajes_profesor
	 * 
	 * Cuerpo de la consulta: {
  								"id": 17
							}
	 * 
	 * */
	@PostMapping("/lenguajes_profesor")
	public ResponseEntity<?> listaLenguajesProfesor(@RequestBody Profesor profesor) {
		Profesor profesorDB = profesorService.findById(profesor.getId());

		if (profesorDB != null) {
			Collection<Lenguaje> listaLenguajes = profesorDB.getLenguajes();
			if (listaLenguajes != null) {
				return new ResponseEntity<>(listaLenguajes, HttpStatus.OK);
			}
		}

		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	
	/*
	 * URL: http://localhost:8040/api/save_lenguaje_profesor
	 * 
	 * Datos para salvar la relación 
	 * 
	 * {
  			"profesor": {
    				"id": 15 
  			},
  			"lenguaje":{
    				"id":1
  			}
		}
	 * */
	@PostMapping("/save_lenguaje_profesor")
	public ResponseEntity<?> saveLenguajeProfesor(@RequestBody ProfesorLenguaje profesorLenguaje) {
		Profesor profesorDB = profesorService.findById(profesorLenguaje.getProfesor().getId());
		if (profesorDB != null) {
			Lenguaje lenguajeDB = lenguajeService.findLenguajeById(profesorLenguaje.getLenguaje().getId());
			profesorDB.addLenguaje(lenguajeDB);
			profesorService.save(profesorDB);

			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} 
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

}
