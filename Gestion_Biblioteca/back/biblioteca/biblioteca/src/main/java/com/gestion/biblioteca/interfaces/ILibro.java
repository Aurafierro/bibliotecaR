package com.gestion.biblioteca.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestion.biblioteca.models.libro;


@Repository
public interface ILibro extends CrudRepository<libro,String> {

	
	    @Query("SELECT l FROM libro l WHERE l.titulo LIKE %:filtro% OR l.autor LIKE %:filtro%")
	    List<libro> filtroGeneral(@Param("filtro") String filtro);
	}



