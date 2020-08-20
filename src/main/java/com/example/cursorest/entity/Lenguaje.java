package com.example.cursorest.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "lenguajes")
public class Lenguaje implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;

	private String nombre;

	@Column(name ="date")
	@Temporal(TemporalType.DATE)
	private Date date;

	/* Crear la relación muchos a muchos */
	@ManyToMany
	@JoinTable(name = "profesores_lenguajes",
			   joinColumns = @JoinColumn(name = "lenguaje_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "profesor_id", referencedColumnName = "id"))
	private Set<Profesor> profesor = new HashSet<Profesor>();

	
	/* Método que permite asignar la fecha de creación de un objeto */
	@PrePersist
	public void prePersist() {
		date = new Date();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<Profesor> getProfesor() {
		return profesor;
	}

	public void setProfesor(Set<Profesor> profesor) {
		this.profesor = profesor;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
