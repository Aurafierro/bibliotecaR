package com.gestion.gesion_bibliotec.controller;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.gestion.gesion_bibliotec.interfaces.IPrestamo;
import com.gestion.gesion_bibliotec.models.estado;
import com.gestion.gesion_bibliotec.models.prestamo;


@RequestMapping("/api/v1/prestamo")
@RestController
@CrossOrigin
public class prestamoController {

	
	 @Autowired
	    private IPrestamo prestamoService;

	    @PostMapping("/")
	    public ResponseEntity<Object> save(@ModelAttribute("prestamo") prestamo prestamo) {
	    	
	    	
	    	 if (prestamo.getFecha_prestamo() == null || prestamo.getFecha_devolucion() == null ||
	    		        prestamo.getUsuario() == null || prestamo.getLibro() == null || 
	    		        prestamo.getEstado() == null) {
	    		        return new ResponseEntity<>("Fecha de préstamo, Fecha de devolución, Usuario que realiza el préstamo, Libro prestado y Estado son obligatorios", HttpStatus.BAD_REQUEST);
	    		    }
	        
	        
	        prestamoService.save(prestamo);
	        return new ResponseEntity<>(prestamo, HttpStatus.OK);
	    }
	    @GetMapping("/")
		public ResponseEntity<Object>findAll(){
			var Listaprestamo = prestamoService.findAll();
			return new ResponseEntity<>(Listaprestamo, HttpStatus.OK);
		}
		
		//filtro
		@GetMapping("/busquedafiltro/{filtro}")
		public ResponseEntity<Object>findFiltro(@PathVariable estado filtro){
			var Listaprestamo = prestamoService.filtroGeneralPrestamo(filtro);
			return new ResponseEntity<>(Listaprestamo, HttpStatus.OK);
		}
		
		
		@GetMapping("/{id_prestamo}")
		public ResponseEntity<Object> findOne ( @PathVariable String id_prestamo){
			var prestamo= prestamoService.findById(id_prestamo);
			return new ResponseEntity<>(prestamo, HttpStatus.OK);
		}
		 @GetMapping("/historial/{id_usuario}") // Nuevo endpoint
		    public ResponseEntity<Object> findHistorial(@PathVariable String id_usuario) {
		        var historial = prestamoService.findByUsuario(id_usuario);
		        return new ResponseEntity<>(historial, HttpStatus.OK);
		    }

	
		
		@PutMapping("/{id_prestamo}")
		public ResponseEntity<Object> update(@PathVariable String id_prestamo, @ModelAttribute("prestamo") prestamo prestamoUpdate) {

		    if (prestamoUpdate.contieneCamposVacios()) {
		        return new ResponseEntity<>("Todos los campos son obligatorios", HttpStatus.BAD_REQUEST);
		    }

		    var prestamo = prestamoService.findById(id_prestamo).orElse(null);
		    if (prestamo != null) {

		        prestamo.setLibro(prestamoUpdate.getLibro());
		        prestamo.setUsuario(prestamoUpdate.getUsuario());
		        prestamo.setFecha_prestamo(prestamoUpdate.getFecha_prestamo());
		        prestamo.setFecha_devolucion(prestamoUpdate.getFecha_devolucion());

		        prestamoService.save(prestamo);
		        return new ResponseEntity<>("Guardado", HttpStatus.OK);

		    } else {
		        return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		    }
		}
}
