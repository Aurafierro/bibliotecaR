package com.gestion.gesion_bibliotec.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class usuario {

	
	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(name="id_usuario", nullable= false, length = 36)
	private String id_usuario;
	
	
	@Column(name="nombre",nullable=false, length = 100)
	private String nombre;
	
	
	@Column(name="direccion",nullable=false, length = 100)
	private String direccion;
	
	@Column(name="correo",nullable=false, length=13)
	private String isbn;
	
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_usuario", nullable=false, length=50)
	private tipoUsuario tipo_usuario;

	public usuario() {
		super();
	}

	public usuario(String id_usuario, String nombre, String direccion, String isbn, tipoUsuario tipo_usuario) {
		super();
		this.id_usuario = id_usuario;
		this.nombre = nombre;
		this.direccion = direccion;
		this.isbn = isbn;
		this.tipo_usuario = tipo_usuario;
	}

	public String getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public tipoUsuario getTipo_usuario() {
		return tipo_usuario;
	}

	public void setTipo_usuario(tipoUsuario tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}

	
}
