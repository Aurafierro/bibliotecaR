package com.gestion.gesion_bibliotec.interfaces;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.gestion.gesion_bibliotec.models.usuario;

public interface IUsuario extends CrudRepository<usuario,String> {

	List<usuario> filtroGeneralUsuario(String usuario);

}
