package com.example.cursorest.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.cursorest.entity.Profesor;

public interface IProfesorDao extends JpaRepository<Profesor, Long>{

	
	/** Busquedas personalizadas */
	public Profesor findByEmail(String email);
	
	public Profesor findByEmailAndPassword(String email, String password);
	
	public Optional<Profesor> findById(Long id);
	
	@Query("select p from Profesor p where p.id=?1")
	public Profesor findByIdSQL(Long id);
	
	
	
}
