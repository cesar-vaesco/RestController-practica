package com.example.cursorest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

	/* Ver - listar */
	@GetMapping("/profesores")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Profesor> getProfesores() {
		return profesorService.findAll();
	}

	/*
	 * Buscar un profesor
	 *
	 * En el cuerpo de la busqueda se pasa el correo del usaurio y cómo respuesta se
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
	
	
	/*
	 * public Profesor findByEmailAndPassword(String email, String password); -> dao
	 *
	 * public Profesor checkProfesorLogin(Profesor profesor); -> service
	 *
	 * @Override
	 * @Transactional(readOnly = true) public Profesor checkProfesorLogin(Profesor
	 * profesor) { return (Profesor)
	 * profesorDao.findByEmailAndPassword(profesor.getEmail(),
	 * profesor.getPassword()); }   -> ProfesorServiceImpl
	 */

	/* Método que valida acceso */
	@PostMapping("/login")
	public ResponseEntity<?> loginProfesor(@RequestBody Profesor profesor) {
		
		Profesor profesorDB = profesorService.checkProfesorLogin(profesor);
		if (profesorDB != null) {
			List<Profesor> profesores = new ArrayList<>();
			profesores.add(profesorDB);
			List<MProfesor> mProfesores = new ArrayList<>();
			mProfesores = Mapper.convertirLista(profesores);
			return new ResponseEntity<>(mProfesores, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
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
			profesorService.updateProfesor(profesorDB);
			// Retornar respuestas actualización o profesor no encontrado
			return new ResponseEntity<>(profesorDB, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
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

	/* Borrar registro de un profesor */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteProfesor(@PathVariable(value = "id") Long id) {
		/*
		 * Para corroborar que no existe el profesor y dar una respuesta a la petición
		 */
		Profesor profesorDB = profesorService.findById(id);
		if (profesorDB == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		/*
		 * Al corroborar que el profesor existe entonces se ejecuta el siguiente código
		 */
		profesorService.deleteProfesor(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
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
