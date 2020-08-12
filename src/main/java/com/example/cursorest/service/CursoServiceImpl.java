package com.example.cursorest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.cursorest.dao.ICursoDao;
import com.example.cursorest.entity.Curso;

public class CursoServiceImpl implements ICursoService {

	@Autowired
	private ICursoDao cursoDao;
	

	@Override
	@Transactional(readOnly = true)
	public List<Curso> findAll() {
		return (List<Curso>) cursoDao.findAll();
	}

	@Override
	@Transactional
	public void saveCurso(Curso curso) {
		cursoDao.save(curso);
	}

	@Override
	public List<Curso> getCursoProfesor(Long id) {
		
		return (List<Curso>) cursoDao.findByProfesorID(id);
	}

}
