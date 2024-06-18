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
	private usuario id_prestamo;
	
	@Column(name="valor_multa", nullable=false, length=100)
	private String valor_multa;
	
	@Column(name="fecha_multa", nullable=false)
	private LocalDate fecha_multa;
	
	@Column(name="estado", nullable=false)
	private EstadoMulta estado;

	
  /*
   * Usuario multado.
o
Préstamo
o
Valor multa.
o
Fecha multa.
o
Estado.
   */
}
