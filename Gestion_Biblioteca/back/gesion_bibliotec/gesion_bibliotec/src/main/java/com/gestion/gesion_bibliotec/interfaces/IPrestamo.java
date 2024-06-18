package com.gestion.gesion_bibliotec.interfaces;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gestion.gesion_bibliotec.models.estado;
import com.gestion.gesion_bibliotec.models.prestamo;

public interface IPrestamo  extends CrudRepository<prestamo,String> {

   
    @Query("SELECT p FROM prestamo p WHERE p.estado = :estado")
    List<prestamo> filtroGeneralPrestamo(@Param("estado") String filtro);



    @Query("SELECT p FROM prestamo p WHERE p.usuario.id = :id_usuario") 
    List<prestamo> findByUsuario(@Param("id_usuario") String id_usuario);

 
	}