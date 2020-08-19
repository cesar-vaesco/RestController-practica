package com.example.cursorest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.example.cursorest.mapper.Mapper;
import com.example.cursorest.model.MProfesor;
import com.example.cursorest.service.IProfesorService;

@RestController
@RequestMapping("/api")
public class ProfesorRestController {

	@Autowired
	private IProfesorService profesorService;

	/*
	 * Ver - listar URL: http://localhost:8040/api/profesores
	 */
	@GetMapping("/profesores")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Profesor> getProfesores() {
		return profesorService.findAll();
	}

	/*
	 * URL: http://localhost:8040/api/profesor/13
	 */
	@GetMapping("/profesor/{id}")
	public ResponseEntity<Profesor> getProfesorId(@PathVariable(value = "id") Long id) {
		Optional<Profesor> optionalProfesor = profesorService.findProfesorById(id);

		if (optionalProfesor.isPresent()) {
			return ResponseEntity.ok(optionalProfesor.get());
		} else {
			return ResponseEntity.noContent().build();
		}

	}

	/*
	 * Buscar un profesor URL: http://localhost:8040/api/findProfesor
	 * 
	 * En el cuerpo de la busqueda se pasa el correo del usuario y cómo respuesta se
	 * recibe objeto completo con sus datos
	 **/
	@PostMapping("/findProfesor")
	public ResponseEntity<?> getProfesor(@RequestBody Profesor profesor) {
		Profesor profesorDB = profesorService.findProfesor(profesor);
		if (profesorDB != null) {
			return new ResponseEntity<>(profesorDB, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * Crear - agregar un nuevo profesor
	 * 
	 * URL: http://localhost:8040/api/sign_up
	 * 
	 * Cuerpo del objeto que se envía { "nombre":"mar", "email": "mar@jos.com",
	 * "password":"var" }
	 */
	@PostMapping("/sign_up")
	public ResponseEntity<Void> addProfesor(@RequestBody Profesor profesor) {
		if (profesorService.findProfesor(profesor) == null) {
			profesorService.save(profesor);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * public Profesor findByEmailAndPassword(String email, String password); -> dao
	 *
	 * public Profesor checkProfesorLogin(Profesor profesor); -> service
	 *
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public Profesor checkProfesorLogin(Profesor
	 * profesor) { return (Profesor)
	 * profesorDao.findByEmailAndPassword(profesor.getEmail(),
	 * profesor.getPassword()); } -> ProfesorServiceImpl
	 */

	/*
	 * Método que valida acceso
	 * 
	 * http://localhost:8040/api/login
	 * 
	 * Datos para generar acceso
	 * 
	 * {"email": "mar@jos.com", "password":"var" }
	 * 
	 */
	@PostMapping("/login")
	public ResponseEntity<?> loginProfesor(@RequestBody Profesor profesor) {

		Profesor profesorDB = profesorService.checkProfesorLogin(profesor);
		if (profesorDB != null) {
			/* Profesor de entity es recorrido */
			List<Profesor> profesores = new ArrayList<>();
			/*
			 * Al haber usuario y contraseña se agrega el profesor de base de datos al
			 * arreglo profesores
			 */
			profesores.add(profesorDB);
			List<MProfesor> mProfesores = new ArrayList<>();
			mProfesores = Mapper.convertirLista(profesores);
			return new ResponseEntity<>(mProfesores, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	/*
	 * Actualizar registro de profesor URL: http://localhost:8040/api/update/{id}
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<Profesor> updateProfesor(@PathVariable(value = "id") Long id,
			@RequestBody Profesor profesor) {
		Optional<Profesor> optionalProfesor = profesorService.findById(id);
		// Confirmar que existe el profesor
		if (optionalProfesor.isPresent()) {
			// Datos que se van a autualizar
			Profesor updateProfesor = optionalProfesor.get();
			updateProfesor.setNombre(profesor.getNombre());
			updateProfesor.setEmail(profesor.getEmail());
			// ->profesorDB.setNombre(profesor.getNombre());
			// ->profesorDB.setEmail(profesor.getEmail());
			// mandar la actualización a la base de datos
			profesorService.updateProfesor(updateProfesor);
			// Retornar respuestas actualización o profesor no encontrado
			return ResponseEntity.ok(updateProfesor);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/* Actualizar registro de profesor con consulta sql */
	@PutMapping("/update_sql/{id}")
	public ResponseEntity<?> updateProfesorSql(@PathVariable(value = "id") Long id, @RequestBody Profesor profesor) {
		// Inicializando profesor
		Profesor profesorDB = null;
		profesorDB = profesorService.findByIdSQL(id);
		// Confirmar que existe el profesor
		if (profesorDB != null) {
			// Datos que se van a autualizar
			profesorDB.setNombre(profesor.getNombre());
			profesorDB.setEmail(profesor.getEmail());
			// mandar la actualización a la base de datos
			profesorService.updateProfesor(profesorDB);
			// Retornar respuestas actualización o profesor no encontrado
			return new ResponseEntity<>(profesorDB, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Profesor> deleteProfe(@PathVariable(value = "id") Long id) {
		Optional<Profesor> deleteProfesor = profesorService.findById(id);
		if (deleteProfesor.isPresent()) {
			profesorService.deleteProfesor(id);
		}
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/deleteAll_post")
	public ResponseEntity<Void> deleteProfesorPost(@RequestBody Profesor profesor) {

		if (profesorService.findProfesor(profesor) != null) {
			profesorService.deleteAllProfesor();
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	/* Borrar todos los profesores */
	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteProfesorAll(@RequestBody Profesor profesor) {
		/*
		 * profesorService.deleteAllProfesor(); return new
		 * ResponseEntity<Void>(HttpStatus.OK);
		 */
		if (profesorService.findProfesor(profesor) != null) {
			profesorService.deleteAllProfesor();
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

	}

}
