package com.example.cursorest.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "profesores")
public class Profesor implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre")
	private String nombre;

	@Column(length = 60, unique = true)
	private String email;

	private String password;

	@Column(length = 2000)
	private String foto;

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

	/*@OneToMany:  Un profesor va a tener n numero de cursos asociados */
	@OneToMany(cascade = CascadeType.ALL) 
	// Al borrar al profesor se borran en automatico los cursos añadidos a el
	@JoinColumn(name = "profesor_id", referencedColumnName = "id")
	// Hace referencia a la columna a la cúal estara asociada con la entidad Curso
	private List<Curso> curso = new ArrayList<>();
	//Todo curso creado sera agregado a una array list

	/* Método que permite asignar la fecha de creación de un objeto */
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	/* Sección de getters y setters */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public List<Curso> getCurso() {
		return curso;
	}

	public void setCurso(List<Curso> curso) {
		this.curso = curso;
	}

	private static final long serialVersionUID = 1L;

}
