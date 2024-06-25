package com.gestion.gesion_bibliotec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.gestion.gesion_bibliotec.interfaceService.IPrestamoService;
import com.gestion.gesion_bibliotec.interfaces.IPrestamo;

import com.gestion.gesion_bibliotec.models.prestamo;



public class prestamoService implements IPrestamoService {

	 @Autowired
	    private IPrestamo data;

	    @Override
	    public String save(prestamo prestamo) {
	        data.save(prestamo);
	        return prestamo.getId_prestamo();
	    }

	    @Override
	    public List<prestamo> findAll() {
	        return (List<prestamo>) data.findAll();
	    }

	    @Override
	    public List<prestamo> filtroGeneralPrestamo(String filtro) {
	        return data.filtroGeneralPrestamo(filtro);
	    }

	    @Override
	    public Optional<prestamo> findOne(String id) {
	        return data.findById(id);
	    }

	
	
}
