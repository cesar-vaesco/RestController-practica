package com.example.cursorest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursorest.entity.Lenguaje;
import com.example.cursorest.service.ILenguajeService;

@RestController
@RequestMapping("/api")
public class LenguajeRestController {

	@Autowired
	private ILenguajeService lenguajeService;
	
	
	/**
	 * URL de consulta:http://localhost:8040/api/lenguajes
	 * */
	@GetMapping("/lenguajes")
	public ResponseEntity<?> listaLenguajes(){
		List <Lenguaje> listaLenguajes = lenguajeService.findAll();
			if(listaLenguajes != null) {
				if(listaLenguajes.size() !=0) {
					return new ResponseEntity<>(listaLenguajes, HttpStatus.OK);
				}
			}
			
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * URL de creación de lenguaje:http://localhost:8040/api/crear-lenguaje
	 * 
	 * Cuerpo de la creación:  {
  								"nombre":"Java"
							    }
	 * */
	@PostMapping("/crear_lenguaje")
	public ResponseEntity<?> agregarLenguaje(@RequestBody Lenguaje lenguaje){
		lenguajeService.saveLenguaje(lenguaje);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	
	
	
}
