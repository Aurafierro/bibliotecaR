package com.gestion.gesion_bibliotec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.gesion_bibliotec.interfaceService.IUsuarioService;
import com.gestion.gesion_bibliotec.interfaces.IUsuario;
import com.gestion.gesion_bibliotec.models.usuario;



@Service
public class usuarioService  implements IUsuarioService {

	 @Autowired
	    private IUsuario data;

	    @Override
	    public String save(usuario usuario) {
	        data.save(usuario);
	        return usuario.getId_usuario();
	    }

	    @Override
	    public List<usuario> findAll() {
	        return (List<usuario>) data.findAll();
	    }

	    @Override
	    public List<usuario> filtroGeneralUsuario(String usuario) {
	        return data.filtroGeneralUsuario(usuario);
	    }

	    @Override
	    public Optional<usuario> findOne(String id) {
	        return data.findById(id);
	    }

	    @Override
	    public int delete(String id) {
	        data.deleteById(id);
	        return 1;
	    }
}
