package com.example.cursorest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.cursorest.entity.Lenguaje;

public interface ILenguajeDao extends JpaRepository<Lenguaje, Long>{

	/*Consulta personalizada*/
	@Query("Select l from Lenguaje l where l.id=?1")
	public Lenguaje findByIdSQL(Long id);
}
