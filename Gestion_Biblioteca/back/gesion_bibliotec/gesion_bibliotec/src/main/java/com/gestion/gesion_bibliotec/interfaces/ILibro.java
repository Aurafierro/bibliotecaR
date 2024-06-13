package com.gestion.gesion_bibliotec.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestion.gesion_bibliotec.models.libro;


@Repository
public interface ILibro extends CrudRepository<libro,String> {
    
    @Query("SELECT l FROM libro l WHERE "
            + "l.titulo LIKE %:filtro% OR "
            + "l.nombre_autor LIKE %:filtro% OR "
            + "l.genero LIKE %:filtro% OR "
            + "l.isbn LIKE %:filtro%")
    List<libro> filtroGeneral(@Param("filtro") String filtro);
}


