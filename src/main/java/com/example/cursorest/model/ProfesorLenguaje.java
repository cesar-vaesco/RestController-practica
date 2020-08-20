package com.example.cursorest.model;

import com.example.cursorest.entity.Lenguaje;
import com.example.cursorest.entity.Profesor;

public class ProfesorLenguaje {

	
	private Profesor profesor;
	private Lenguaje lenguaje;
	
	public Profesor getProfesor() {
		return profesor;
	}
	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	public Lenguaje getLenguaje() {
		return lenguaje;
	}
	public void setLenguaje(Lenguaje lenguaje) {
		this.lenguaje = lenguaje;
	}
	
	
}
