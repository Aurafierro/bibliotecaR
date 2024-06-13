package com.gestion.gesion_bibliotec.interfaceService;

import java.util.List;
import java.util.Optional;

import com.gestion.gesion_bibliotec.models.libro;



public interface ILibroService {
    public String save(libro libro);
    public List<libro> findAll();
    public List<libro> filtroGeneral(String filtro);
    public Optional<libro> findOne(String id);
    public int delete(String id);
}
