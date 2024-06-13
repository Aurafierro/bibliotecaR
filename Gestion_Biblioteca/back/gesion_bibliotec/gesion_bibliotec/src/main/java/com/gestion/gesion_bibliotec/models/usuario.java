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
	
	@Column(name="correo",nullable=false, length=100)
	private String correo;
	
	@Column(name="telefono",nullable=false, length=100)
	private String telefono;
	
	@Column(name="descripcion_casa",nullable=false, length=100)
	private String descripcion_casa;
	

	@Enumerated(EnumType.STRING)
	@Column(name="tipo_usuario", nullable= false, length =10)
	private tipoUsuario tipo_usuario;


	public usuario() {
		super();
	}


	public usuario(String id_usuario, String nombre, String direccion, String correo, String telefono,
			String descripcion_casa, tipoUsuario tipo_usuario) {
		super();
		this.id_usuario = id_usuario;
		this.nombre = nombre;
		this.direccion = direccion;
		this.correo = correo;
		this.telefono = telefono;
		this.descripcion_casa = descripcion_casa;
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


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getDescripcion_casa() {
		return descripcion_casa;
	}


	public void setDescripcion_casa(String descripcion_casa) {
		this.descripcion_casa = descripcion_casa;
	}


	public tipoUsuario getTipo_usuario() {
		return tipo_usuario;
	}


	public void setTipo_usuario(tipoUsuario tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}


	public boolean contieneCamposVacios() {
		// TODO Auto-generated method stub
		return false;
	}

	
	

}
