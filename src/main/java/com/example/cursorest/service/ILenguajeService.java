package com.example.cursorest.service;

import java.util.List;

import com.example.cursorest.entity.Lenguaje;

public interface ILenguajeService {

	
	public List<Lenguaje> findAll();
	
	public void saveLenguaje(Lenguaje lenguaje);
	
	public  Lenguaje FindLenguajeById(Long id);
}
