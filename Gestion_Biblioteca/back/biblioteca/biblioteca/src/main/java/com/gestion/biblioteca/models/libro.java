package com.gestion.biblioteca.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence. Id;

@Entity
public class libro {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(name="id_libro", nullable= false, length = 36)
	private String id_libro;
	
	
	@Column(name="titulo",nullable=false, length = 100)
	private String titulo;
	
	
	@Column(name="autor",nullable=false, length = 100)
	private String autor;
	
	@Column(name="isbn",nullable=false, length=13)
	private String isbn;
	
	@Column(name="genero", nullable=false, length=50)
	private String genero;
	
	@Column(name="num_ejemplares_disponibles", nullable=false)
	private  int num_ejemplares_disponibles;
	
	@Column(name="num_ejemplares_ocupados", nullable=false)
	private  int num_ejemplares_ocupados;

	public libro() {
		super();
	}
	
	
	

	public libro(String id_libro, String titulo, String autor, String isbn, String genero,
			int num_ejemplares_disponibles, int num_ejemplares_ocupados) {
		super();
		this.id_libro = id_libro;
		this.titulo = titulo;
		this.autor = autor;
		this.isbn = isbn;
		this.genero = genero;
		this.num_ejemplares_disponibles = num_ejemplares_disponibles;
		this.num_ejemplares_ocupados = num_ejemplares_ocupados;
	}




	public String getId_libro() {
		return id_libro;
	}

	public void setId_libro(String id_libro) {
		this.id_libro = id_libro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getNum_ejemplares_disponibles() {
		return num_ejemplares_disponibles;
	}

	public void setNum_ejemplares_disponibles(int num_ejemplares_disponibles) {
		this.num_ejemplares_disponibles = num_ejemplares_disponibles;
	}

	public int getNum_ejemplares_ocupados() {
		return num_ejemplares_ocupados;
	}

	public void setNum_ejemplares_ocupados(int num_ejemplares_ocupados) {
		this.num_ejemplares_ocupados = num_ejemplares_ocupados;
	}




	public boolean contieneCamposVacios() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	
	
}
