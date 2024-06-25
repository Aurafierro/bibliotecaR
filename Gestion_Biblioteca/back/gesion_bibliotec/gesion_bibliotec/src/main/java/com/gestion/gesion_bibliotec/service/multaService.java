package com.gestion.gesion_bibliotec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.gesion_bibliotec.interfaceService.IMultaService;
import com.gestion.gesion_bibliotec.interfaces.IMulta;
import com.gestion.gesion_bibliotec.models.multa;

@Service
public class multaService implements IMultaService {

    @Autowired
    private IMulta data;

    @Override
    public String save(multa multa) {
        data.save(multa);
        return multa.getId_multa();
    }

    @Override
    public List<multa> findAll() {
        return (List<multa>) data.findAll();
    }

    @Override
    public Optional<multa> findOne(String id) {
        return data.findById(id);
    }
}
