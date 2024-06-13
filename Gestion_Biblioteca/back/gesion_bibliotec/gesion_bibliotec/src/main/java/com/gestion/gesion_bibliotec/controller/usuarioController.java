package com.gestion.gesion_bibliotec.controller;

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


import com.gestion.gesion_bibliotec.interfaceService.IUsuarioService;

import com.gestion.gesion_bibliotec.models.usuario;

@RequestMapping("/api/v1/usuario")
@RestController
@CrossOrigin
public class usuarioController {

	
	 @Autowired
	    private IUsuarioService usuarioService;

	    @PostMapping("/")
	    public ResponseEntity<Object> save(@ModelAttribute("usuario") usuario usuario) {
	        List<usuario> usuarios = usuarioService.filtroGeneralUsuario(usuario.getNombre());
	        if (!usuarios.isEmpty()) {
	            return new ResponseEntity<>("El usuario ya tiene un ingreso activo", HttpStatus.BAD_REQUEST);
	        }
	        if (usuario.getCorreo().equals("")) {
	            return new ResponseEntity<>("El correo del  suario ya está registrado en el sistema", HttpStatus.BAD_REQUEST);
	        }

	        usuarioService.save(usuario);
	        return new ResponseEntity<>(usuario, HttpStatus.OK);
	    }
	    @GetMapping("/")
		public ResponseEntity<Object>findAll(){
			var Listausuario = usuarioService.findAll();
			return new ResponseEntity<>(Listausuario, HttpStatus.OK);
		}
		
		//filtro
		@GetMapping("/busquedafiltro/{filtro}")
		public ResponseEntity<Object>findFiltro(@PathVariable String filtro){
			var Listausuario = usuarioService.filtroGeneralUsuario(filtro);
			return new ResponseEntity<>(Listausuario, HttpStatus.OK);
		}
		
		
		@GetMapping("/{id_usuario}")
		public ResponseEntity<Object> findOne ( @PathVariable String id_usuario){
			var usuario= usuarioService.findOne(id_usuario);
			return new ResponseEntity<>(usuario, HttpStatus.OK);
		}
		
		
		 //ELIMINADO FISICO:
	    @DeleteMapping("/{id_usuario}")
		public ResponseEntity<Object> delete(@PathVariable String id_usuario){
	    	usuarioService.delete(id_usuario);
					return new ResponseEntity<>("El usuario se eliminó con éxito",HttpStatus.OK);
		}

		@PutMapping("/{id_usuario}")
		public ResponseEntity<Object> update(@PathVariable String id_usuario, @ModelAttribute("usuario") usuario usuarioUpdate) {
		    
			// Verificar si hay campos vacíos
			
			if (usuarioUpdate.contieneCamposVacios()) {
				return new ResponseEntity<>("Todos los campos son obligatorios", HttpStatus.BAD_REQUEST);
			}

			var usuario = usuarioService.findOne(id_usuario).get();
			if (usuario != null) {
		
		        if (!usuario.getCorreo().equals(usuarioUpdate.getCorreo())) {
		            // El número de documento se está cambiando, verificar si ya está en uso
		            List<usuario> usuarioConMismoNombre = usuarioService.filtroGeneralUsuario(usuarioUpdate.getCorreo());
		            if (!usuarioConMismoNombre.isEmpty()) {
		                // Si hay otros médicos con el mismo número de documento, devuelve un error
		                return new ResponseEntity<>("El usuario  ya tiene un ingreso activo", HttpStatus.BAD_REQUEST);
		            }
		        }


		        usuario.setNombre(usuarioUpdate.getNombre());
		        usuario.setCorreo(usuarioUpdate.getCorreo());
		        usuario.setDireccion(usuarioUpdate.getDireccion());
		        usuario.setTelefono(usuarioUpdate.getTelefono());
		        usuario.setDescripcion_casa(usuarioUpdate.getDescripcion_casa());
		        

		        usuarioService.save(usuario);
				return new ResponseEntity<>("Guardado", HttpStatus.OK);

			} else {
				return new ResponseEntity<>("Error usuario no encontrado", HttpStatus.BAD_REQUEST);
			}
		}
}
