package com.gestion.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.gestion.biblioteca.interfaceService.ILibroService;
import com.gestion.biblioteca.models.libro;

@RequestMapping("/api/v1/libro")
@RestController
@CrossOrigin

public class libroController {


    @Autowired
    private ILibroService libroService;

    @PostMapping("/")
    public ResponseEntity<Object> save(@ModelAttribute("libro") libro libro) {
        List<libro> libros = libroService.filtroGeneral(libro.getTitulo());
        if (!libros.isEmpty()) {
            return new ResponseEntity<>("El libro ya tiene un ingreso activo", HttpStatus.BAD_REQUEST);
        }
        if (libro.getIsbn().equals("")) {
            return new ResponseEntity<>("El ISBN del libro ya está dentro del sistema", HttpStatus.BAD_REQUEST);
        }

        libroService.save(libro);
        return new ResponseEntity<>(libro, HttpStatus.OK);
    }
    @GetMapping("/")
	public ResponseEntity<Object>findAll(){
		var ListaLibro = libroService.findAll();
		return new ResponseEntity<>(ListaLibro, HttpStatus.OK);
	}
	
	//filtro
	@GetMapping("/busquedafiltro/{filtro}")
	public ResponseEntity<Object>findFiltro(@PathVariable String filtro){
		var ListaLibro = libroService.filtroGeneral(filtro);
		return new ResponseEntity<>(ListaLibro, HttpStatus.OK);
	}
	
	
	@GetMapping("/{id_libro}")
	public ResponseEntity<Object> findOne ( @PathVariable String id_libro ){
		var libro= libroService.findOne(id_libro);
		return new ResponseEntity<>(libro, HttpStatus.OK);
	}
	
	
	 //ELIMINADO FISICO:
    @DeleteMapping("/{id_libro}")
	public ResponseEntity<Object> delete(@PathVariable String id_libro){
		 libroService.delete(id_libro);
				return new ResponseEntity<>("El libro se eliminó con éxito",HttpStatus.OK);
	}

	@PutMapping("/{id_libro}")
	public ResponseEntity<Object> update(@PathVariable String id_libro, @ModelAttribute("libro") libro libroUpdate) {
	    
		// Verificar si hay campos vacíos
		
		if (libroUpdate.contieneCamposVacios()) {
			return new ResponseEntity<>("Todos los campos son obligatorios", HttpStatus.BAD_REQUEST);
		}

		var libro = libroService.findOne(id_libro).get();
		if (libro != null) {
			  // Verificar si el número de documento se está cambiando
	        if (!libro.getTitulo().equals(libroUpdate.getTitulo())) {
	            // El número de documento se está cambiando, verificar si ya está en uso
	            List<libro> libroConMismoNombre = libroService.filtroGeneral(libroUpdate.getTitulo());
	            if (!libroConMismoNombre.isEmpty()) {
	                // Si hay otros médicos con el mismo número de documento, devuelve un error
	                return new ResponseEntity<>("El libro  ya tiene un ingreso activo", HttpStatus.BAD_REQUEST);
	            }
	        }


	        libro.setTitulo(libroUpdate.getTitulo());
	        libro.setAutor(libroUpdate.getAutor());
	        libro.setIsbn(libroUpdate.getIsbn());
	        libro.setGenero(libroUpdate.getGenero());
	        libro.setNum_ejemplares_disponibles(libroUpdate.getNum_ejemplares_disponibles());
	        libro.setNum_ejemplares_ocupados(libroUpdate.getNum_ejemplares_ocupados());
	        

			libroService.save(libro);
			return new ResponseEntity<>("Guardado", HttpStatus.OK);

		} else {
			return new ResponseEntity<>("Error medico no encontrado", HttpStatus.BAD_REQUEST);
		}
	}
}
	

