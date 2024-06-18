package com.gestion.gesion_bibliotec.interfaceService;

import java.util.List;
import java.util.Optional;

import com.gestion.gesion_bibliotec.models.prestamo;


public interface IPrestamoService {

	
	public String save(prestamo prestamo);
    public List<prestamo> findAll();
    public List<prestamo> filtroGeneralPrestamo(String prestamo);
    public Optional<prestamo> findOne(String id);
   public   List<prestamo> findByUsuario(String id_usuario);
	
    
}
