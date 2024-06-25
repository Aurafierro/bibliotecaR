package com.gestion.gesion_bibliotec.interfaceService;

import java.util.List;
import java.util.Optional;

import com.gestion.gesion_bibliotec.models.multa;



public interface IMultaService {

	 public String save(multa multa);
	    public List<multa> findAll();
	    public Optional<multa> findOne(String id);
    
}
