package com.example.cursorest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursorest.entity.Curso;
import com.example.cursorest.entity.Profesor;
import com.example.cursorest.service.ICursoService;

@RestController
@RequestMapping("/api")
public class CursoRestController {

	@Autowired
	private ICursoService cursoService;

	/*
	 * VER TODOS LOS CURSOS
	 * 
	 * URL: http://localhost:8040/api/cursos
	 */
	@GetMapping("/cursos")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<?> listaCursos() {
		List<Curso> listaCursos = cursoService.findAll();
		if (listaCursos != null) {
			if (listaCursos.size() != 0) {
				return new ResponseEntity<>(listaCursos, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	/*
	 * URL: http://localhost:8040/api/crear_curso
	 * 
	 * Cuerpo de petición:
	 * 
	 * { "nombre":"Curso C#", "profesorId":"14"}
	 * 
	 * El curso se guarda en el ID del profesor  
	 *   
	 *  Al consultar al profesor se genera una respuesta similar a: 
	 *   
	 *    {
        "id": 14,
        "nombre": "César",
        "email": "ces@ces.com",
        "password": "ces",
        "foto": null,
        "createAt": "2020-08-05",
        "curso": [
            {
                "curso_id": 4,
                "nombre": "Curso C#",
                "profesorId": 14
            }
        ]
    },
	 **/

	@PostMapping("/crear_curso")
	public ResponseEntity<?> agregarCurso(@RequestBody Curso curso) {
		cursoService.saveCurso(curso);
		return new ResponseEntity<Void>(HttpStatus.CREATED);

	}

	@PostMapping("/cursos_profesor")
	public ResponseEntity<?> verCursosProfesor(@RequestBody Profesor profesor) {
		List<Curso> listaCursos = cursoService.getCursosProfesor(profesor.getId());
		if (listaCursos != null) {
			if (listaCursos.size() != 0) {
				return new ResponseEntity<>(listaCursos, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

}// Fin de la clase
