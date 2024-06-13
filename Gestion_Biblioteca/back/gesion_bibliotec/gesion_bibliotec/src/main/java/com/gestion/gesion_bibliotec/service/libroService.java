package com.gestion.gesion_bibliotec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.gesion_bibliotec.interfaceService.ILibroService;
import com.gestion.gesion_bibliotec.interfaces.ILibro;
import com.gestion.gesion_bibliotec.models.libro;



@Service
public class libroService implements ILibroService {

    @Autowired
    private ILibro data;

    @Override
    public String save(libro libro) {
        data.save(libro);
        return libro.getId_libro();
    }

    @Override
    public List<libro> findAll() {
        return (List<libro>) data.findAll();
    }

    @Override
    public List<libro> filtroGeneral(String filtro) {
        return data.filtroGeneral(filtro);
    }

    @Override
    public Optional<libro> findOne(String id) {
        return data.findById(id);
    }

    @Override
    public int delete(String id) {
        data.deleteById(id);
        return 1;
    }
}
