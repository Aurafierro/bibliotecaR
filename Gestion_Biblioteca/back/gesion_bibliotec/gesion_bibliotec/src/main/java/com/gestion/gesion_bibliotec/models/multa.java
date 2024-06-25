package com.gestion.gesion_bibliotec.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class multa {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(name="id_multa", nullable= false, length = 36)
	private String id_multa;

	@ManyToOne
	@JoinColumn (name="id_usuario")
	private usuario usuario;
	
	@ManyToOne
	@JoinColumn (name="id_prestamo")
	private prestamo prestamo;
	
	@Column(name="valor_multa", nullable=false, length=100)
	private String valor_multa;
	
	@Column(name="fecha_multa", nullable=false)
	private LocalDate fecha_multa;
	
	@Column(name="estado_multa", nullable=false)
	private EstadoMulta estado_multa;

	public multa() {
		super();
	}

	public multa(String id_multa, com.gestion.gesion_bibliotec.models.usuario usuario,
			com.gestion.gesion_bibliotec.models.prestamo prestamo, String valor_multa, LocalDate fecha_multa,
			EstadoMulta estado_multa) {
		super();
		this.id_multa = id_multa;
		this.usuario = usuario;
		this.prestamo = prestamo;
		this.valor_multa = valor_multa;
		this.fecha_multa = fecha_multa;
		this.estado_multa = estado_multa;
	}

	public String getId_multa() {
		return id_multa;
	}

	public void setId_multa(String id_multa) {
		this.id_multa = id_multa;
	}

	public usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(usuario usuario) {
		this.usuario = usuario;
	}

	public prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(prestamo prestamo) {
		this.prestamo = prestamo;
	}

	public String getValor_multa() {
		return valor_multa;
	}

	public void setValor_multa(String valor_multa) {
		this.valor_multa = valor_multa;
	}

	public LocalDate getFecha_multa() {
		return fecha_multa;
	}

	public void setFecha_multa(LocalDate fecha_multa) {
		this.fecha_multa = fecha_multa;
	}

	public EstadoMulta getEstado_multa() {
		return estado_multa;
	}

	public void setEstado_multa(EstadoMulta estado_multa) {
		this.estado_multa = estado_multa;
	}

	
	
 
}
