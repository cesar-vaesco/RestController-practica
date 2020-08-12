package com.example.cursorest.service;

import java.util.List;

import com.example.cursorest.entity.Curso;

public interface ICursoService {

	public List<Curso> findAll();

	public void saveCurso(Curso curso);

	public List<Curso> getCursoProfesor(Long id);
}
