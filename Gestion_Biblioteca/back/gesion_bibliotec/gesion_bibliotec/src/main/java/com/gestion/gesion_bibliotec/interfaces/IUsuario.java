package com.gestion.gesion_bibliotec.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import com.gestion.gesion_bibliotec.models.usuario;


public interface IUsuario  extends CrudRepository<usuario,String>{
	   @Query("SELECT u FROM usuario u WHERE "
	            + "u.nombre LIKE %:filtro% OR "
	         
	            + "u.correo LIKE %:filtro%")
	    List<usuario> filtroGeneralUsuario(@Param("filtro") String filtro);


}
