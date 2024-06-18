package com.gestion.gesion_bibliotec.models;



import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class prestamo {

	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(name="id_prestamo", nullable= false, length = 36)
	private String id_prestamo;

	@ManyToOne
	@JoinColumn (name="id_libro")
	private libro libro;
	
	@ManyToOne
	@JoinColumn (name="id_usuario")
	private usuario usuario;
	
	@Column(name="fecha_prestamo", nullable=false)
	private LocalDate fecha_prestamo;

	@Column(name="fecha_devolucion", nullable=false)
	private LocalDate fecha_devolucion;

	
	@Enumerated(EnumType.STRING)
	@Column(name="estado", nullable= false, length =10)
	private estado estado;


	public prestamo() {
		super();
	}


	public prestamo(String id_prestamo, com.gestion.gesion_bibliotec.models.libro libro,
			com.gestion.gesion_bibliotec.models.usuario usuario, LocalDate fecha_prestamo, LocalDate fecha_devolucion,
			com.gestion.gesion_bibliotec.models.estado estado) {
		super();
		this.id_prestamo = id_prestamo;
		this.libro = libro;
		this.usuario = usuario;
		this.fecha_prestamo = fecha_prestamo;
		this.fecha_devolucion = fecha_devolucion;
		this.estado = estado;
	}


	public String getId_prestamo() {
		return id_prestamo;
	}


	public void setId_prestamo(String id_prestamo) {
		this.id_prestamo = id_prestamo;
	}


	public libro getLibro() {
		return libro;
	}


	public void setLibro(libro libro) {
		this.libro = libro;
	}


	public usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(usuario usuario) {
		this.usuario = usuario;
	}


	public LocalDate getFecha_prestamo() {
		return fecha_prestamo;
	}


	public void setFecha_prestamo(LocalDate fecha_prestamo) {
		this.fecha_prestamo = fecha_prestamo;
	}


	public LocalDate getFecha_devolucion() {
		return fecha_devolucion;
	}


	public void setFecha_devolucion(LocalDate fecha_devolucion) {
		this.fecha_devolucion = fecha_devolucion;
	}


	public estado getEstado() {
		return estado;
	}


	public void setEstado(estado estado) {
		this.estado = estado;
	}


	public boolean contieneCamposVacios() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
