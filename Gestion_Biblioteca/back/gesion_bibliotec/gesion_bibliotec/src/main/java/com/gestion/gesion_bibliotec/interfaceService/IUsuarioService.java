package com.gestion.gesion_bibliotec.interfaceService;

import java.util.List;
import java.util.Optional;


import com.gestion.gesion_bibliotec.models.usuario;

public interface IUsuarioService {

	public String save(usuario usuario);
    public List<usuario> findAll();
    public List<usuario> filtroGeneralUsuario(String filtro);
    public Optional<usuario> findOne(String id);
    public int delete(String id);
}
