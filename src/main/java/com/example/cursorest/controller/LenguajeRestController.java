package com.example.cursorest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursorest.entity.Lenguaje;
import com.example.cursorest.service.ILenguajeService;

@RestController
@RequestMapping("/api")
public class LenguajeRestController {

	@Autowired
	private ILenguajeService lenguajeService;
	
	@GetMapping("/lenguajes")
	public ResponseEntity<?> listaLenguajes(){
		List <Lenguaje> listaLenguajes = lenguajeService.findAll();
			if(listaLenguajes != null) {
				if(listaLenguajes.size() !=0) {
					return new ResponseEntity<>(listaLenguajes, HttpStatus.OK);
				}
			}
			
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
}
