package com.example.cursorest.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.cursorest.dao.ILenguajeDao;
import com.example.cursorest.entity.Lenguaje;


public class LenguajeServiceImpl implements ILenguajeService {

	@Autowired
	private ILenguajeDao lenguajeDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Lenguaje> findAll() {
		return (List<Lenguaje>) lenguajeDao.findAll();
	}

	@Override
	@Transactional
	public void saveLenguaje(Lenguaje lenguaje) {
		lenguajeDao.save(lenguaje);
	}

	@Override
	@Transactional(readOnly = true)
	public Lenguaje FindLenguajeById(Long id) {
		return lenguajeDao.findByIdSQL(id);
	}
	
	

}
