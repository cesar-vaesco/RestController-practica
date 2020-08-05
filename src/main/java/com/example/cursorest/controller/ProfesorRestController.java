package com.example.cursorest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursorest.entity.Profesor;
import com.example.cursorest.service.IProfesorService;

@RestController
@RequestMapping("/api")
public class ProfesorRestController {

	@Autowired
	private IProfesorService profesorService;

	/* Ver - listar */
	@GetMapping("/profesores")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Profesor> getProfesores() {
		return profesorService.findAll();
	}

	/* Crear - agregar un nuevo profesor */
	@PostMapping("/sign_up")
	public ResponseEntity<Void> addProfesor(@RequestBody Profesor profesor) {
		if (profesorService.findProfesor(profesor) == null) {
			profesorService.save(profesor);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}

	/* Actualizar registro de profesor */
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateProfesor(@PathVariable(value = "id") Long id, @RequestBody Profesor profesor) {
		// Inicializando profesor
		Profesor profesorDB = null;
		profesorDB = profesorService.findById(id);
		// Confirmar que existe el profesor
		if (profesorDB != null) {
			// Datos que se van a autualizar
			profesorDB.setNombre(profesor.getNombre());
			profesorDB.setEmail(profesor.getEmail());
			// mandar la actualización a la base de datos
			profesorService.uptadeProfesor(profesorDB);
			// Retornar respuestas actualización o profesor no encontrado
			return new ResponseEntity<>(profesorDB, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	/* Borrar registro de un profesor */

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteProfesor(@PathVariable(value = "id") Long id) {
		/*Para corroborar que no existe el profesor y dar una respuesta a la prtición*/
		Profesor profesorDB = profesorService.findById(id);
		if (profesorDB == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		/*Al corroborar que el profesor existe entonces se ejecuta el siguiente código*/
		profesorService.deleteProfesor(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
